package com.dal.carbonfootprint.dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dal.carbonfootprint.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Carbon Vision
 */
public class DashBoardFragment extends Fragment {


    Year selectedYear = Year.now();
    Month selectedMonth;
    static ArrayList<Float> monthlyCarbonEmission;
    static int checkCount=0;
    static int k=0;

    /**
     * Method to create the view associated to the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        monthlyCarbonEmission = new ArrayList<Float>();
        return root;
    }

    /**
     * Method to perform the operation once the view associated to the fragment is created
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Vehcile vehcile = new Vehcile();
        Spinner spinner = (Spinner) view.findViewById(R.id.years_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        String[] years = {"2020", "2019"};
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this.getContext(),
                android.R.layout.simple_spinner_dropdown_item,years);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Specify the layout to use when the list of choices appears. Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
        getUserVehcileDetails(vehcile,view);
    }

    /**
     * Method to fetch the User vehicle details from the fire store for the given user
     * @param vehcile
     * @param view
     */
    private void getUserVehcileDetails(Vehcile vehcile, View view){
        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // specifying the User Vehicle DB collection for the logged in user
        db.collection("UserVehicle").whereEqualTo("User Id" ,currentUser.getEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // when the task to fetch the document is completed
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        System.out.println(document.getData().get("Vehicle Model Name").toString());
                        vehcile.setVehicleType(document.getData().get("Vehicle type").toString());
                        vehcile.setModelYear(document.getData().get("Model Year").toString());
                        vehcile.setVehicleBrand(document.getData().get("Vehicle Brand").toString());
                        vehcile.setModelName(document.getData().get("Vehicle Model Name").toString());
                        vehcile.setFuelConsumption(Float.parseFloat(document.getData().get("Fuel Consumption").toString()));
                    }
                    checkCount=0;
                    List<PieEntry> pieEntries= setupPieData(view,vehcile);
                    setupBarData(view, vehcile);
                } else {
                    System.out.println("Error"+task.getException());
                    Log.w("Error", "Error getting documents.", task.getException());
                }
            }
        });
    }


    /**
     * Method for Setting up the Bar graph on the view
     * @param view
     * @param vehcile
     */
    private void setupBarData(View view, Vehcile vehcile) {

        ArrayList<String> dateDisplay = new ArrayList<>();

        // Arraylidt to store Bar Entries
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        // Taking out number of days for given month
        int noOfDays = selectedMonth.length(selectedYear.isLeap());
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Month currentMonth = Month.of(cal.get(Calendar.MONTH)+1);
        if (selectedYear.equals(Year.now()) && selectedMonth.equals(currentMonth)) {
            noOfDays = cal.get(Calendar.DAY_OF_MONTH);
        }
        k=0;
        for (int i = 1; i <= noOfDays; i++) {
            dateDisplay.add(i + " " + selectedMonth.toString().substring(0, 3));

            // Setting up the firebase fire store auth
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            int finalI = i;
            int finalNoOfDays = noOfDays;

            // fetching up the user travel data for the given date
            db.collection("TripDetails").whereEqualTo("User Id", currentUser.getEmail()).
                    whereEqualTo("month", selectedMonth.getValue()).whereEqualTo("year", selectedYear.getValue()).whereEqualTo("date", i)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                float totalValue = 0.0f;

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            int distance = Integer.parseInt(document.getData().get("Distance").toString());
                            float tempCF = distance * vehcile.getFuelConsumption() * 1.42f;
                            // Entering the calculated carbon footprint to bar entries
                            barEntries.add(new BarEntry(k, tempCF));
                        }
                        if (k == finalNoOfDays-1) {
                            setupBar(view, barEntries, vehcile, dateDisplay);
                        }
                        k++;
                    } else {
                        Log.w("Error", "Error getting documents.", task.getException());
                    }
                }
            });
        }
    }

    /**
     * Method for Setting up the Bar graph on the view
     * @param view
     * @param barEntries
     * @param vehcile
     * @param dateDislay
     */
    private void setupBar(View view, ArrayList<BarEntry> barEntries,Vehcile vehcile, ArrayList<String> dateDislay) {

        //Assigning the data set to be display
        BarDataSet dataset = new BarDataSet(barEntries, "Carbon Emission For " + selectedMonth.toString());
        dataset.setColors(ColorTemplate.MATERIAL_COLORS);
        dataset.setValueTextColor(android.R.color.black);
        dataset.setValueTextSize(10f);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(dataset);
        data.setValueTextColor(android.R.color.black);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        // Defining the bar chart for the view
        BarChart chart = (BarChart) view.findViewById(R.id.barchart);
        chart.setData(data);

        // Setting up the Bar chart feature and characteristic
        chart.setFitBars(true);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setMaxVisibleValueCount(50);
        chart.setDrawGridBackground(true);
        chart.setPinchZoom(true);
        chart.setDrawValueAboveBar(true);
        chart.getDescription().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dateDislay));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(dateDislay.size()/2);
        xAxis.setLabelRotationAngle(270f);

        chart.animateY(1500);
        chart.invalidate();

        // Setting up the legends for the bar graph to be display
        Legend l = chart.getLegend();
        l.setFormSize(10f);
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setTypeface(Typeface.MONOSPACE);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f);
        l.setYEntrySpace(5f);
    }

    /**
     * Method to set up the data for display carbon emission on monthly basis in pie chart
     * @param view
     * @param vehcile
     * @return
     */
    private List<PieEntry> setupPieData(View view, Vehcile vehcile) {
        Year[] year = {Year.of(2019), Year.now()};
        ArrayList<Month> months = new ArrayList<Month>();
        int month;
        // looking for the year selected by the user through spinner in the rendered view
        if (selectedYear.equals(Year.now())) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            month = cal.get(Calendar.MONTH);
            selectedMonth = Month.of(month + 1);
        } else {
            month = 12;
            selectedMonth = Month.of(1);
        }
        int i = 1;
        while (i <= month + 1) {
            months.add(Month.of(i));
            i++;
        }
        checkCount= 0;

        // Array List to store entries to be made in pie chart
        List<PieEntry> pieEntries = new ArrayList<>();

        for (Month m : months) {

            // Setting up the firebase fire store auth
            FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            // fetching up the user travel data for the given month
            db.collection("TripDetails").whereEqualTo("User Id", currentUser.getEmail()).
                    whereEqualTo("month", m.getValue()).whereEqualTo("year", selectedYear.getValue())
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                float totalValue = 0.0f;

                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            int distance = Integer.parseInt(document.getData().get("Distance").toString());
                            float tempCF = distance * vehcile.getFuelConsumption() * 1.42f;
                            totalValue += tempCF;
                        }

                        // Storing monthly carbon emission into pie entry
                        //monthlyCarbonEmission.add(totalValue);
                        PieEntry entry = new PieEntry(totalValue, months.get(checkCount).toString().substring(0, 3));
                        pieEntries.add(entry);
                        if (checkCount == months.size() - 1) {
                            setupPie(view, pieEntries, vehcile);
                        }
                        checkCount++;
                    } else {
                        Log.w("teja", "Error getting documents.", task.getException());
                    }
                }
            });
        }
        return pieEntries;
    }

    /**
     * Method to setup and display the pie chart into the view
     * @param view
     * @param pieEntries
     * @param vehcile
     */
    private void setupPie(View view, List<PieEntry> pieEntries , Vehcile vehcile) {

        // defining the Dataset to store the value for the piechart
        PieDataSet dataset = new PieDataSet(pieEntries, "Carbon Emission for "+selectedYear.toString());
        dataset.setSliceSpace(3f);
        dataset.setSelectionShift(5f);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        dataset.setValueTextColor(android.R.color.black);
        dataset.setValueTextSize(75);
        dataset.setHighlightEnabled(true);

        PieData data = new PieData(dataset);
        data.setValueTextColor(Color.BLACK);
        data.setValueTextSize(15f);

        PieChart chart = (PieChart) view.findViewById(R.id.chart);
        chart.setData(data);

        pieEntries.get(selectedMonth.getValue()-1);

        Highlight high= new Highlight(selectedMonth.getValue()-1,0.0f,0);

        chart.highlightValues(new Highlight[] { high });
        chart.getDescription().setEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        chart.setExtraOffsets(20, 1, 5, 2);
        chart.setDragDecelerationFrictionCoef(0.15f);
        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(android.R.color.white);
        chart.setTransparentCircleRadius(61f);
        chart.setClickable(true);
        chart.setTouchEnabled(true);
        chart.animateXY(1000,1000, Easing.EaseInOutElastic);
        chart.setCenterText("2020");
        chart.setCenterTextSize(24);
        chart.invalidate();
        Description description = new Description();
        description.setText("Carbon Emission for "+selectedYear);
        chart.setDescription(description);


        /**
         * On click listener for pie chart, on selection of the given month, data in the bar chart need to changed
         */
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                selectedMonth = Month.of(((int)(h.getX()-1)));
                setupBarData(view, vehcile);
            }
            @Override
            public void onNothingSelected() {
            }
        });
    }
}

