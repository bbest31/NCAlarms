package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.Fact;

/**
 * Fragment holding a singular {@link Fact}.
 * <p>
 * Which consists of a description, citation, and DYK yes or no buttons.
 * </p>
 * Created by bbest on 15/03/18.
 */

public class FactFragment extends Fragment {
    private TextView description;
    private TextView citation;
    private Button yesBtn;
    private Button noBtn;
    private int pageNumber;
    private static final String ARG_PAGE = "fact";



    public FactFragment(){

    }
    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
    public static FactFragment create(int pageNumber) {
        FactFragment fragment = new FactFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fact, container, false);
        description = (TextView) view.findViewById(R.id.fact_description);
        citation = (TextView) view.findViewById(R.id.fact_citation);
        yesBtn = (Button) view.findViewById(R.id.dyk_yes_btn);
        noBtn = (Button) view.findViewById(R.id.dyk_no_btn);

        return view;
    }

    public void setFact(Fact fact) {
        //description.setText(fact.getFactDescription());
        //citation.setText(fact.getCitation());
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
