package com.napchatalarms.napchatalarmsandroid.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.adapters.FactsPageAdapter;
import com.napchatalarms.napchatalarmsandroid.model.FactHolder;
import com.napchatalarms.napchatalarmsandroid.utility.DepthPageTransformer;


public class HealthFactsFragment extends android.support.v4.app.Fragment {

    private static int NUM_PAGES = 0;
    FactsPageAdapter pageAdapter;
    private ViewPager pager;
    private PagerAdapter pagerAdapter;

    public HealthFactsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facts, container, false);
        initialize(view);
        pageAdapter = new FactsPageAdapter(getActivity().getSupportFragmentManager(), NUM_PAGES);
        pager = (ViewPager) view.findViewById(R.id.facts_view_pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Hide the soft keyboard if still up
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);
                OnPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setOffscreenPageLimit(1);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(pageAdapter);
        return view;
    }


    private void initialize(View view) {
        NUM_PAGES = FactHolder.getInstance(getActivity()).getFacts().size() + 1;

    }

    public void OnPageSelected(final int i) {
        FactFragment fragment = (FactFragment) pageAdapter.instantiateItem(pager, i);
        if (fragment != null) {
            if (fragment.getClass() == SuggestFactLastFragment.class || fragment.getClass() == SuggestFactFragment.class) {
                fragment.onBecameVisible();

            }
        }
    }


}
