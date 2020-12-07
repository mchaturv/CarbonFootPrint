package com.dal.carbonfootprint.trips;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dal.carbonfootprint.EditFragment;
import com.dal.carbonfootprint.HomeActivity;
import com.dal.carbonfootprint.R;
import com.dal.carbonfootprint.VehicleInfoActivity;
import com.dal.carbonfootprint.profile.VehcileInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import io.perfmark.Tag;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AddTripFragment extends  Fragment {

    Context mcontext = null;
    private TripViewModel tripViewModel;

    String selectedDate;
    public static final int REQUEST_CODE = 11;

    TextView dateFilledExposedDropdown;


    private DatePickerDialog.OnDateSetListener mDateSetListener;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mcontext = getContext();
        View view = inflater.inflate(R.layout.fragment_addtrips, container, false);
        //TextInputLayout dateinputlayout = view.findViewById(R.id.date);

        dateFilledExposedDropdown = view.findViewById(R.id.tvDate);
        //tripViewModel = new ViewModelProvider(this).get(VehcileInfo.class);
        System.out.println("Entered on oncreateview");
        //View root = inflater.inflate(R.layou, container, false);
        final Calendar calendar = Calendar.getInstance();
        //Button btn = view.findViewById(R.id.add_date);

        dateFilledExposedDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Please note that use your package name here
//                Calendar cal = Calendar.getInstance();
//                int year = cal.get(Calendar.YEAR);
//                int month = cal.get(Calendar.MONTH);
//                int day = cal.get(Calendar.DAY_OF_MONTH);
//                Log.d("yag","onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//                System.out.println("onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
//                DatePickerDialog dialog = new DatePickerDialog(
//                        getContext(),
//                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
//                        mDateSetListener,
//                        year,month,day);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                dialog.show();
                DatePickerFragment dpf = new DatePickerFragment().newInstance();
                dpf.setCallBack(onDate);
                dpf.show(getFragmentManager().beginTransaction(), "DatePickerFragment");
            }
        });


        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("yag","onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                dateFilledExposedDropdown.setText(date);
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
//                     DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),date, mYear, mMonth, mDay);
//                     datePickerDialog.show();
//                 }
//        });


                Button addTrip = (Button) view.findViewById(R.id.add_trip);
        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> docData = new HashMap<>();

                //vehicle type
                AutoCompleteTextView sourceFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown_source);
                AutoCompleteTextView destinationFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown_destination);
                AutoCompleteTextView distanceFilledExposedDropdown = view.findViewById(R.id.filled_exposed_dropdown_distance);


                String source= sourceFilledExposedDropdown.getText().toString();
                String destination = destinationFilledExposedDropdown.getText().toString();
                String distance = distanceFilledExposedDropdown.getText().toString();
                String travelDate = dateFilledExposedDropdown.getText().toString();

                docData.put("source", source);
                docData.put("destination", destination);
                docData.put("Distance", distance);
                docData.put("Date", travelDate);


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
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.nav_host_fragment, frag);
                                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                                ft.addToBackStack(null);
                                ft.commit();
                                Toast.makeText(getActivity(), "Trip Details Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), "Sorry couldn't add Vehicle Details.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });


        return view;
    }





    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            TextView tvDate = (TextView) getActivity().findViewById(R.id.tvDate);
            tvDate.setText(String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                    + "-" + String.valueOf(dayOfMonth));
        }
    };


}
