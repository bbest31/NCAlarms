package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Created by bbest on 18/03/18.
 */

public class SuggestFactFragment extends FactFragment {
    private Button submitBtn;
    private EditText descriptionField;
    private EditText link;
    private View view;
    private int pageNumber;
    private static final String ARG_PAGE = "submitFact";

    public SuggestFactFragment(){

    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static SuggestFactFragment create(int pageNumber) {
        SuggestFactFragment fragment = new SuggestFactFragment();
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
        View view = inflater.inflate(R.layout.fragment_suggest_fact, container, false);
        this.view = view;

        submitBtn = (Button) view.findViewById(R.id.submit_fact_btn);
        descriptionField = (EditText) view.findViewById(R.id.submit_fact_descrip_edittext);
        link = (EditText) view.findViewById(R.id.link_src_edittext);

        return view;

    }


    @Override
    public int getPageNumber() {
        return pageNumber;
    }
}
