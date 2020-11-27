package com.dal.carbonfootprint;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private static int spashTime=5000; // 5 sec

    // Variable
    GifImageView bgapp;
    ConstraintLayout contentSection;
    ImageView imageView;
    TextView textView;

    Animation frombottom;
    Animation fadein;
    Animation fadeout;
    Animation side;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        bgapp= findViewById(R.id.bgapp);
        contentSection = findViewById(R.id.constraintLayout);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fadein = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        fadeout = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        side = AnimationUtils.loadAnimation(this, R.anim.fromside);



        bgapp.startAnimation(fadein);
        contentSection.startAnimation(fadein);
        //logoframe.startAnimation(clover)

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        imageView.setAnimation(frombottom);
        textView.setAnimation(side);

        new Handler().postDelayed(new Runnable() {
                // This method will be executed once the timer is over
                // Start your app main activity
                //bgapp.animate().translationY(-2500f).setDuration(1600).startDelay = 800
            @Override
            public void run(){
                bgapp.startAnimation(fadeout);
                contentSection.startAnimation(fadeout);
                startActivity(new Intent(getApplicationContext(),SignInActivity.class));
                finish();
            }
        }, spashTime);
    }
}