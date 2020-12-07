package com.dal.carbonfootprint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mahfa.dnswitch.DayNightSwitch;

public class MainActivity1 extends AppCompatActivity {

    private static int spashTime=5000; // 5 sec

    // Variable
    ImageView bgapp;
    ConstraintLayout contentSection;
    ImageView imageView;
    TextView textView;

    ImageView sun, daylandscape, nightlandscape, factory;
    View light,dark;
    DayNightSwitch animSwitch;

    Animation frombottom;
    Animation fadein;
    Animation fadeout;
    Animation side;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.mainactivity1);

        animSwitch = findViewById(R.id.switchkey);
        //animSwitch.setIsNight(false);

        sun = findViewById(R.id.sun);
        //sun.setTranslationY(-110);
        daylandscape = findViewById(R.id.nightlandscape);
        nightlandscape = findViewById(R.id.nightlandscape);
        light = findViewById(R.id.safeEarth);
        dark = findViewById(R.id.pollutedEarth);
        factory = findViewById(R.id.factoryImage);

        animSwitch.setIsNight(true);

//        sun.animate().translationY(180).setDuration(1000);
//        light.animate().alpha(0).setDuration(1300);
//        daylandscape.animate().alpha(0).setDuration(1300);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        animSwitch.setIsNight(false);

        sun.animate().translationY(-280).setDuration(4300);
        factory.animate().alpha(0).setDuration(3700);
        dark.animate().alpha(0).setDuration(4300);
        nightlandscape.animate().alpha(0).setDuration(4300);


        bgapp= findViewById(R.id.bgapp1);
        contentSection = findViewById(R.id.constraintLayout);
        //imageView = findViewById(R.id.imageView);
        //textView = findViewById(R.id.textView);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        side = AnimationUtils.loadAnimation(this, R.anim.fromside);

        bgapp.startAnimation(fadein);
        //contentSection.startAnimation(fadein);
        //logoframe.startAnimation(clover)

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //imageView.setAnimation(frombottom);
        //textView.setAnimation(side);

        new Handler().postDelayed(new Runnable() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //bgapp.animate().translationY(-2500f).setDuration(1600).startDelay = 800
            @Override
            public void run(){
                bgapp.startAnimation(fadeout);
                //contentSection.startAnimation(fadeout);
                check();
            }
        }, spashTime);
    }

    public void check(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser)

        if(currentUser!=null) {
            //hideProgressBar();
//            FirebaseFirestore db =  FirebaseFirestore.getInstance();
//            Map<String, Object> userVehcile = new HashMap<>();
//            userVehcile.put('name')
            String email = currentUser.getEmail();
            String brand="Random";
            System.out.println(email);
            System.out.println("---**************************************************");
            FirebaseFirestore.getInstance().collection("UserVehicle").whereEqualTo("User Id" ,email)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(task.isSuccessful()){
                        System.out.println("****************************************************");
                        System.out.println("**"+task.getResult().size());
                        if(task.getResult().size()>0) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {

                                Log.d("Document", doc.getId() + "-" + doc.getData());
                                System.out.println(doc.getId() + "-" + doc.getData());
                                System.out.println("*******************--------------*****************");
                            }
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), VehicleInfoActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });

        } else {
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }
        finish();
    }
}