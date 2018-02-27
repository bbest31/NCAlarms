package com.napchatalarms.napchatalarmsandroid.activities;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napchatalarms.napchatalarmsandroid.R;


public class SocialFragment extends android.support.v4.app.Fragment {


    public SocialFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_social, container, false);
        initialize(view);
        return  view;
    }

    private void initialize(View view){

    }

}
