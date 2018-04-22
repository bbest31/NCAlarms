package com.napchatalarms.napchatalarmsandroid.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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


/**
 * The type Health facts fragment.
 */
@SuppressWarnings("unused")
public class HealthFactsFragment extends android.support.v4.app.Fragment {

    private static int NUM_PAGES = 0;
    /**
     * The Page adapter.
     */
    private FactsPageAdapter pageAdapter;
    private ViewPager pager;

    /**
     * Instantiates a new Health facts fragment.
     */
    public HealthFactsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facts, container, false);
        initialize();
        //noinspection ConstantConditions
        pageAdapter = new FactsPageAdapter(getActivity().getSupportFragmentManager(), NUM_PAGES);
        pager = view.findViewById(R.id.facts_view_pager);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Hide the soft keyboard if still up
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                //noinspection ConstantConditions
                imm.hideSoftInputFromWindow(pager.getWindowToken(), 0);
                OnPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        pager.setOffscreenPageLimit(4);
        pager.setPageTransformer(true, new DepthPageTransformer());
        pager.setAdapter(pageAdapter);
        return view;
    }


    @SuppressWarnings("unused")
    private void initialize() {
        //noinspection AccessStaticViaInstance
        NUM_PAGES = FactHolder.getInstance(getActivity()).getFacts().size() + 1;

    }

    /**
     * On page selected.
     *
     * @param i the
     */
    private void OnPageSelected(final int i) {
        FactFragment fragment = (FactFragment) pageAdapter.instantiateItem(pager, i);
        // Get the next fragment that has been instantiated and tell it that it is invisible.
        if (i != NUM_PAGES - 1) {
            FactFragment nextFragment = (FactFragment) pageAdapter.instantiateItem(pager, i + 1);
            //noinspection ConstantConditions
            if (nextFragment != null) {
                nextFragment.onBecameInvisible();
            }
        }
        //noinspection ConstantConditions
        if (fragment != null) {
            fragment.onBecameVisible();

        }
    }

}
