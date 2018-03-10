package com.napchatalarms.napchatalarmsandroid.activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napchatalarms.napchatalarmsandroid.R;


public class SleepFactsFragment extends android.support.v4.app.Fragment {


    public SleepFactsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_facts, container, false);
        initialize(view);
        return  view;
    }

    private void initialize(View view){

    }

}
