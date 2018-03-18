package com.napchatalarms.napchatalarmsandroid.services;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.napchatalarms.napchatalarmsandroid.fragments.FactFragment;

import java.util.List;

/**
 * Page Adapter class for {@link FactFragment}.
 * Created by bbest on 15/03/18.
 */

public class FactsPageAdapter extends FragmentPagerAdapter {


    private List<android.support.v4.app.Fragment> fragments;
    private int count = 0;

    public FactsPageAdapter(FragmentManager fm,int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        return new FactFragment();
    }

    @Override
    public int getCount() {
        return count;
    }
}
