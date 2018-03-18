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

    public FactFragment(){

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
}
