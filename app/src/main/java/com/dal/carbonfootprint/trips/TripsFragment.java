package com.dal.carbonfootprint.trips;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dal.carbonfootprint.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class TripsFragment extends Fragment {

    private TripViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(TripViewModel.class);
        return inflater.inflate(R.layout.fragment_trips, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        getList(view);

    }

    private void getList(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference docRef = db.collection("TripDetails");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Trip> myListData = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult())
                    {

                        myListData.add(new Trip(document.getData().get("Name").toString(),document.getData().get("Date").toString(),document.getData().get("Distance").toString()));

                    }
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.usersRecycler);
                    MyListAdapter adapter = new MyListAdapter(myListData);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(adapter);

                } else {

                    Log.i("This is Tag", "get failed with ", task.getException());
                }
            }
        });
    }


}