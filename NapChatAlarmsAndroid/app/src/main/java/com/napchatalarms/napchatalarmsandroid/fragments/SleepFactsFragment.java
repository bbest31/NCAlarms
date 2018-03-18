package com.napchatalarms.napchatalarmsandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.Fact;
import com.napchatalarms.napchatalarmsandroid.model.FactHolder;
import com.napchatalarms.napchatalarmsandroid.services.FactsPageAdapter;
import com.napchatalarms.napchatalarmsandroid.utility.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class SleepFactsFragment extends android.support.v4.app.Fragment {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private static int NUM_PAGES = 0;
    FactsPageAdapter pageAdapter;

    public SleepFactsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_facts, container, false);
        initialize(view);
        List<Fragment> fragments = getFragments();
        pageAdapter = new FactsPageAdapter(getActivity().getSupportFragmentManager(),NUM_PAGES);
        pager = (ViewPager)view.findViewById(R.id.facts_view_pager);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(pageAdapter);
        return  view;
    }



    private void initialize(View view){


    }

    /**
     *
     * @return
     */
    private List<Fragment> getFragments(){
        ArrayList<Fact> facts = FactHolder.getInstance(getActivity()).getFacts();
        List<Fragment> fragments = new ArrayList<>();

        // Add first fact without left chevron
        FirstFactFragment firstFactFragment = new FirstFactFragment();
        firstFactFragment.setFact(facts.get(0));
        fragments.add(new FirstFactFragment());

        // Add the rest of the facts
        for(int i = 1; i < facts.size(); i++){
            FactFragment factFragment = new FactFragment();
            factFragment.setFact(facts.get(i));
            fragments.add(factFragment);
        }

        // Add SubmitFactFragment
        SubmitFactFragment fragment = new SubmitFactFragment();
        fragment.isLast(true);

        fragments.add(fragment);

        NUM_PAGES = fragments.size();

        return fragments;
    }


}
