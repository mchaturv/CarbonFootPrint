package com.dal.carbonfootprint.trips;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.dal.carbonfootprint.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class AddTripActivity extends AppCompatActivity {

    Context mcontext = null;
    private TripViewModel tripViewModel;

    String selectedDate;
    public static final int REQUEST_CODE = 11;

    TextView dateFilledExposedDropdown;
    AutoCompleteTextView sourceFilledExposedDropdown ;
    AutoCompleteTextView destinationFilledExposedDropdown ;
    AutoCompleteTextView distanceFilledExposedDropdown;


    private DatePickerDialog.OnDateSetListener mDateSetListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.fragment_addtrips);
        //TextInputLayout dateinputlayout = view.findViewById(R.id.date);

        dateFilledExposedDropdown = findViewById(R.id.tvDate);
        //tripViewModel = new ViewModelProvider(this).get(VehcileInfo.class);
        System.out.println("Entered on oncreateview");
        //View root = inflater.inflate(R.layou, container, false);
        final Calendar calendar = Calendar.getInstance();
        //Button btn = findViewById(R.id.add_date);
        final String[] selecteddate = new String[3];

        AutoCompleteTextView mDisplayDate = (AutoCompleteTextView) findViewById(R.id.tvDate);

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddTripActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("TAG", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day + "/" + month + "/" + year;
                selecteddate[0] = day+"";
                selecteddate[1] =month+"";
                selecteddate[2] =year+"";
                mDisplayDate.setText(date);
            }
        };





//        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                calendar.set(Calendar.YEAR, year);
//                calendar.set(Calendar.MONTH, monthOfYear);
//                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                String myFormat = "MM/dd/yy"; //In which you need put here
//                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//                dateFilledExposedDropdown.setText(sdf.format(calendar.getTime()));
//            }
//
//        };
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                System.out.println(
//                        "here on click the date "
//                );
//                int mYear = calendar.get(Calendar.YEAR);
//                int mMonth= calendar.get(Calendar.MONTH);
//                int mDay= calendar.get(Calendar.DAY_OF_MONTH);
//
//
//                DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),date, mYear, mMonth, mDay);
//                datePickerDialog.show();
//            }
//        });







//        dateFilledExposedDropdown.setOnClickListener(new View.OnClickListener() {
//                 @Override
//                 public void onClick(View v) {
//                     System.out.println(
//                             "here on click the date "
//                     );
//                     int mYear = calendar.get(Calendar.YEAR);
//                     int mMonth= calendar.get(Calendar.MONTH);
//                     int mDay= calendar.get(Calendar.DAY_OF_MONTH);
//
//
//                     DatePickerDialog datePickerDialog = new DatePickerDialog(getApplicationContext(),date, mYear, mMonth, mDay);
//                     datePickerDialog.show();
//                 }
//        });


                Button addTrip = (Button) findViewById(R.id.add_trip);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> docData = new HashMap<>();

                //vehicle type
                 sourceFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_source);
                 destinationFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_destination);
                 distanceFilledExposedDropdown = findViewById(R.id.filled_exposed_dropdown_distance);


                String source= sourceFilledExposedDropdown.getText().toString();
                String destination = destinationFilledExposedDropdown.getText().toString();
                String distance = distanceFilledExposedDropdown.getText().toString();
                String travelDate = dateFilledExposedDropdown.getText().toString();

                docData.put("source", source);
                docData.put("destination", destination);
                docData.put("Distance", distance);
                docData.put("date", selecteddate[0]);
                docData.put("month", selecteddate[1]);
                docData.put("year", selecteddate[2]);



                FirebaseAuth mAuth;
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser!=null) {
                    docData.put("User Id", currentUser.getEmail());
                }

                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference tripRef = db.collection("TripDetails").document();
                tripRef
                        .set(docData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Fragment frag = new Fragment(R.layout.fragment_trips);

                                FragmentManager fragmentManager = getSupportFragmentManager();
                                fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, frag).commit();
                                Toast.makeText(getApplicationContext(), "Trip Details Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Sorry couldn't add Vehicle Details.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });



    }








}
