package com.dal.carbonfootprint.profile;

import android.content.Context;
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
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.dal.carbonfootprint.EditFragment;
import com.dal.carbonfootprint.R;


public class FAQFragment extends Fragment {

    Context mcontext = null;
    private ImageView backBtn;
    private String TAG = "AboutusFragment";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mcontext = getContext();
        View view = inflater.inflate(R.layout.fragment_faqsact, container, false);
        backBtn = view.findViewById(R.id.back_arrow);
        return view;
    }




}