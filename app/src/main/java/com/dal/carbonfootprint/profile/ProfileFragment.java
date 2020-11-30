package com.dal.carbonfootprint.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import com.bumptech.glide.Glide;
import com.dal.carbonfootprint.R;
import com.dal.carbonfootprint.SignInActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ProfileFragment extends Fragment {

    private DatabaseReference firebaseDatabaseReference;
    private FirebaseDatabase fireabseDatabase;
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvEmail;
    private TextView tvEmailVerifiied;
    private TextView tveditProfile;
    private TextView tvaboutus;
    private TextView tvsupport;
    private TextView favourite;
    private final int RC_SIGN_IN = 1;
    private final String TAG = "SignInActivity";
    @NotNull
    public GoogleSignInClient mGoogleSignInClient;
    @NotNull
    public GoogleSignInOptions mGoogleSignInOptions;
    private FirebaseAuth firebaseAuth;
    private String email;
    private String password;
    private String name;
    private Uri photoUr;
    private boolean isEmailVerified;
    private ImageView profileimage;
    private TextView etmessage;
    private TextView btnSignout;

    //public static final profileFragment.Companion Companion = new profileFragment.Companion((DefaultConstructorMarker)null);

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        initializeFirebase();
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        startProfileFragment(view);
        String uid;
        FirebaseUser mUser = firebaseAuth.getCurrentUser();
        if(mUser!=null) {

            // Name, email address, and profile photo Url
            name = mUser.getDisplayName();
                /*if(name=="")
                {
                    val firstname =
                }*/
            email = mUser.getEmail();
            if (mUser.getPhotoUrl() != null) {
                photoUr = mUser.getPhotoUrl();
            }

            // Check if user's email is verified
            isEmailVerified = mUser.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
             uid = mUser.getUid();
        }


        tvEmail.setText(email);
        tvFirstName.setText(name);
        tvLastName.setText("");
        if(photoUr!=null){
            Glide.with((Fragment)this).load(this.photoUr).into(profileimage);


        }


            /*mUserReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    tvFirstName!!.text = snapshot.child("firstName").value as String
                    tvLastName!!.text = snapshot.child("lastName").value as String
                    tvEmail!!.text = snapshot.child("email").value as String
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })*/





        tvaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController controller = new NavController(getContext());
                controller.navigate(R.id.navigation_aboutus);
            }
        });
        tvsupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new Fragment(R.layout.fragment_faqsact);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.nav_host_fragment, frag);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });



        return view;

    }

    private void startProfileFragment(View view) {

        tvFirstName = view.findViewById(R.id.tvFirstName);
        tvLastName = view.findViewById(R.id.tvLastName);
        tvEmail = view.findViewById(R.id.tvEmail);
        //tveditProfile = view.findViewById<View>(R.id.edit_personal_tv);
        tvaboutus = view.findViewById(R.id.about_us);
        tvsupport = view.findViewById(R.id.supporthelp);
        favourite = view.findViewById(R.id.manage_vehcile) ;
        profileimage = view.findViewById(R.id.profileCircleImageView);
        btnSignout=view.findViewById(R.id.logout);
        btnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    AuthUI.getInstance()
                            .signOut(getContext())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                public void onComplete(@NonNull Task<Void> task) {
                                    // user is now signed out
                                    startActivity(new Intent(getActivity(), SignInActivity.class));
                                }
                            });

            }
        });
    }

    private void initializeFirebase() {
        fireabseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabaseReference = fireabseDatabase.getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();

    }
}