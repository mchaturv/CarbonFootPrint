package com.dal.carbonfootprint.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.dal.carbonfootprint.R;
import com.dal.carbonfootprint.EditFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class TravelFragment extends Fragment {

    private TravelViewModel travelViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        travelViewModel =
                new ViewModelProvider(this).get(TravelViewModel.class);
        System.out.println("Entered on oncreateview");
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView carName = (TextView) root.findViewById(R.id.usercar);
        TextView carBrand = (TextView)root.findViewById(R.id.userbrand);
        TextView carModel = (TextView)root.findViewById(R.id.usermodel);
        TextView carYear = (TextView)root.findViewById(R.id.useryear);
        TextView carSep = (TextView)root.findViewById(R.id.userspec);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("CarDetails")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                System.out.println(document.getData().get("CarName"));
                                carName.setText((document.getData().get("CarName")).toString());
                                carBrand.setText((document.getData().get("Brand")).toString());
                                carModel.setText((document.getData().get("Model")).toString());
                                carYear.setText((document.getData().get("Year")).toString());
                                carSep.setText((document.getData().get("Specification")).toString());
                            }
                        } else {
                            System.out.println("Error"+task.getException());
                            Log.w("teja", "Error getting documents.", task.getException());
                        }
                    }
                });
        Button ID = (Button) root.findViewById(R.id.button);
        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager;
                fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EditFragment NAME = new EditFragment();
                fragmentTransaction.replace(R.id.nav_host_fragment, NAME);
                fragmentTransaction.commit();

            }
        });


        return root;
    }
}