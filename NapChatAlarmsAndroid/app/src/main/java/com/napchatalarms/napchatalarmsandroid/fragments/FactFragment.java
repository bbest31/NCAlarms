package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.abstractions.IFactFragment;
import com.napchatalarms.napchatalarmsandroid.model.Fact;
import com.napchatalarms.napchatalarmsandroid.model.FactHolder;
import com.napchatalarms.napchatalarmsandroid.model.User;

/**
 * Fragment holding a singular {@link Fact}.
 * <p>
 * Which consists of a description, citation, and DYK yes or no buttons.
 * </p>
 * Created by bbest on 15/03/18.
 */
@SuppressWarnings("unused")
public class FactFragment extends Fragment implements IFactFragment {
    private static final String ARG_PAGE = "fact";
    private TextView description;
    private TextView citation;
    private ScrollView scrollView;
    private int pageNumber;
    private FirebaseAnalytics mAnalytics;


    /**
     * Instantiates a new Fact fragment.
     */
    public FactFragment() {

    }

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     *
     * @param pageNumber the page number
     * @return the fact fragment
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
        //noinspection ConstantConditions
        pageNumber = getArguments().getInt(ARG_PAGE);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fact, container, false);
        description = view.findViewById(R.id.fact_description);
        citation = view.findViewById(R.id.fact_citation);
        scrollView = view.findViewById(R.id.fact_scrollview);

        scrollView.setScrollbarFadingEnabled(false);

        this.setFact(pageNumber);

        return view;
    }

    /**
     * Sets fact.
     *
     * @param factNumber the fact number
     */
    void setFact(int factNumber) {
        @SuppressWarnings("AccessStaticViaInstance") Fact fact = FactHolder.getInstance(getActivity()).getFacts().get(factNumber);
        description.setText(fact.getFactDescription());
        citation.setText(fact.getCitation());
    }

    /**
     * Gets page number.
     *
     * @return the page number
     */
    @SuppressWarnings("unused")
    public int getPageNumber() {
        return pageNumber;
    }

    @Override
    public void onBecameVisible() {

    }

    /**
     * When this fragment becomes invisible then we disable the views again.
     */
    @Override
    public void onBecameInvisible() {

    }
}
