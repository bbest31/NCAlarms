package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Created by bbest on 18/03/18.
 */

public class SubmitFactFragment extends FactFragment {
    private Button submitBtn;
    private EditText descriptionField;
    private EditText link;
    private View view;
    private int pageNumber;
    private static final String ARG_PAGE = "submitFact";

    public SubmitFactFragment(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_submit_fact, container, false);
        this.view = view;

        submitBtn = (Button) view.findViewById(R.id.submit_fact_btn);
        descriptionField = (EditText) view.findViewById(R.id.submit_fact_descrip_edittext);
        link = (EditText) view.findViewById(R.id.link_src_edittext);

        return view;

    }

    public void isLast(Boolean last){
        if(last){
           // view.findViewById(R.id.submit_chevron_rt).setVisibility(View.INVISIBLE);
        }
    }

}
