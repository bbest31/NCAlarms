package com.napchatalarms.napchatalarmsandroid.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.customui.FactFragment;
import com.napchatalarms.napchatalarmsandroid.services.FactsPageAdapter;
import com.napchatalarms.napchatalarmsandroid.utility.DepthPageTransformer;

import java.util.ArrayList;
import java.util.List;


public class SleepFactsFragment extends android.support.v4.app.Fragment {

    private ViewPager pager;
    private PagerAdapter pagerAdapter;
    private final static int NUM_PAGES = 3;
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
        pageAdapter = new FactsPageAdapter(getActivity().getSupportFragmentManager());

        pager = (ViewPager)view.findViewById(R.id.facts_view_pager);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(pageAdapter);
        return  view;
    }



    private void initialize(View view){


    }

    private List<Fragment> getFragments(){
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FactFragment());
        fragments.add(new FactFragment());
        fragments.add(new FactFragment());

        return fragments;
    }


}
