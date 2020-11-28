package com.dal.carbonfootprint.dashboard;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DashBoardFragment extends Fragment {

    DashBoardData dashBoardData;
    Year selectedYear = Year.now();
    Month selectedMonth;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dashBoardData = new DashBoardData();
        setupPie(view);
        setupBar(view);
    }

    private void setupBar(View view) {

        float[] monthlyCarbonEmission = {98.8f, 123.6f, 98.8f, 123.6f, 98.8f, 123.6f, 98.8f, 123.6f, 23.0f, 23.3f, 123.0f, 56.0f};
        ArrayList<String> dateDislay = new ArrayList<>();

        ArrayList<BarEntry> barEntries = new ArrayList<>();
//        for (int i=0; i<monthlyCarbonEmission.length;i++){
//            barEntries.add((new BarEntry(1,monthlyCarbonEmission[i])));
//        }
        int noOfDays = selectedMonth.length(selectedYear.isLeap());
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Month currentMonth = Month.of(cal.get(Calendar.MONTH));

        if (selectedYear.equals(Year.now()) && selectedMonth.equals(currentMonth)) {
            noOfDays = cal.get(Calendar.DAY_OF_MONTH);
        }

        for (int i = 1; i <= noOfDays; i++) {
            dateDislay.add(i + " "+ selectedMonth.toString().substring(0,3));
            float ceResult = dashBoardData.calculateCarbonFootprint(i);
            barEntries.add(new BarEntry(i, ceResult));
        }

        BarDataSet dataset = new BarDataSet(barEntries, "Carbon Emission For " + selectedMonth.toString());
        dataset.setColors(ColorTemplate.MATERIAL_COLORS);
        dataset.setValueTextColor(android.R.color.black);
        dataset.setValueTextSize(10f);

        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(dataset);
        data.setValueTextColor(android.R.color.black);
        data.setValueTextSize(10f);
        data.setBarWidth(0.9f);

        BarChart chart = (BarChart) view.findViewById(R.id.barchart);
        chart.setData(data);

        chart.setFitBars(true);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        chart.setMaxVisibleValueCount(50);
        //chart.setPinchZoom(true);
        chart.setDrawGridBackground(true);
        chart.setPinchZoom(true);
        chart.setDrawValueAboveBar(true);
        //chart.set
        chart.getDescription().setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dateDislay));

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f);
        //xAxis.setAxisMinimum(1);
        xAxis.setLabelCount(dateDislay.size()/2);
        xAxis.setLabelRotationAngle(270f);


        chart.animateY(1500);
        chart.invalidate();

        Legend l = chart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTypeface(Typeface.MONOSPACE);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        l.setXEntrySpace(5f); // space between the legend entries on the x-axis
        l.setYEntrySpace(5f);
    }

    private void setupPie(View view) {
        Year[] year = {Year.of(2019), Year.now()};
        ArrayList<Month> months = new ArrayList<Month>();
        //Log.println(5, String.valueOf(Tag.CREATOR),String.valueOf(selectedYear));
        int month;
        if (selectedYear.equals(Year.now())) {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            month = cal.get(Calendar.MONTH);
            selectedMonth = Month.of(month+1);
            System.out.println(Month.of(month));
            //Log.println(5, String.valueOf(Tag.CREATOR),String.valueOf(month));
        } else {
            month = 12;
            selectedMonth = Month.of(1);
        }
        System.out.println(month);
        int i = 1;
        while (i <= month+1) {
            System.out.println(Month.of(i));
            months.add(Month.of(i));
            i++;
        }

        ArrayList<Float> monthlyCarbonEmission = new ArrayList<Float>();
        //float[] monthlyCarbonEmission1 = {988.80f, 123.66f, 98.8f, 123.6f, 98.8f, 123.6f, 98.8f, 123.6f, 23.0f, 23.3f, 123.0f, 56.0f};

        int k = 0;
        for (Month m : months) {
            float result = dashBoardData.calculateCarbonFootprint(m);
            System.out.println(m.toString() + "-->" + result);
            monthlyCarbonEmission.add(result);
            //System.out.println(monthlyCarbonEmission1[k]);
            k++;
        }

        //String[] monthName = {"Jan", "Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int j = 0; j < monthlyCarbonEmission.size(); j++) {
            PieEntry entry = new PieEntry(monthlyCarbonEmission.get(j), months.get(j).toString().substring(0, 3));
            //PieEntry entry = new PieEntry(monthlyCarbonEmission1[j] ,months.get(j).toString());
            System.out.println(entry.toString());
            pieEntries.add(entry);
        }

        PieDataSet dataset = new PieDataSet(pieEntries, "Carbon Emission for "+selectedYear.toString());
        dataset.setSliceSpace(3f);
        dataset.setSelectionShift(5f);
        dataset.setColors(ColorTemplate.JOYFUL_COLORS);
        dataset.setValueTextColor(android.R.color.black);
        dataset.setValueTextSize(75);

        dataset.setHighlightEnabled(true); // allow highlighting for DataSet
        // set this to false to disable the drawing of highlight indicator (lines)

        PieData data = new PieData(dataset);
        data.setValueTextColor(Color.BLACK);
        //data.setValueFormatter(new IndexAxisValueFormatter(monthlyCarbonEmission.toArray()));
        data.setValueTextSize(15f);

        PieChart chart = (PieChart) view.findViewById(R.id.chart);
        chart.setData(data);
        System.out.println(selectedMonth.toString());
        System.out.println(selectedMonth.getValue());
        System.out.println(pieEntries.get(0));
        System.out.println(pieEntries.get(selectedMonth.getValue()-1));
        PieEntry entry =  pieEntries.get(selectedMonth.getValue()-1);

        Highlight high= new Highlight(selectedMonth.getValue()-1,0.0f,0);
        // dataset index for piechart is always 0
        chart.highlightValues(new Highlight[] { high });
        //chart.setUsePercentValues(true);
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



//        Legend l = chart.getLegend();
//        l.setFormSize(10f); // set the size of the legend forms/shapes
//        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
//        l.setTypeface(Typeface.MONOSPACE);
//        l.setWordWrapEnabled(true);
//        l.setTextSize(12);
//        l.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//        l.setXEntrySpace(5f); // space between the legend entries on the x-axis
//        l.setYEntrySpace(5f);



        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                //int x = chart.getData().getDataSetForEntry(e).getEntryIndex((PieEntry) e);
                //selectedMonth = months.get(x);
                //
                System.out.println(h.toString());
                System.out.println(h.getDataIndex()+","+h.getDataSetIndex());
                selectedMonth = Month.of(((int)(h.getX()-1)));
                setupBar(view);
                //view.findViewById(R.id.barchart)
            }

            @Override
            public void onNothingSelected() {

            }
        });

    }
}

