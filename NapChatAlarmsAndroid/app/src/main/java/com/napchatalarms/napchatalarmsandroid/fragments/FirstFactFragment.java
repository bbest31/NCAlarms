package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Created by bbest on 18/03/18.
 */

public class FirstFactFragment extends FactFragment {
    private TextView description;
    private TextView citation;
    private Button yesBtn;
    private Button noBtn;
    private int pageNumber;
    private static final String ARG_PAGE = "fact";


    public FirstFactFragment(){

    }

    public static FirstFactFragment create(int pageNumber){
        FirstFactFragment fragment = new FirstFactFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARG_PAGE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first_fact, container, false);
        description = (TextView) view.findViewById(R.id.fact_description);
        citation = (TextView) view.findViewById(R.id.fact_citation);
        yesBtn = (Button) view.findViewById(R.id.dyk_yes_btn);
        noBtn = (Button) view.findViewById(R.id.dyk_no_btn);

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }


    @Override
    public int getPageNumber() {
        return pageNumber;
    }
}
