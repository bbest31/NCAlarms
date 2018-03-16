package com.napchatalarms.napchatalarmsandroid.services;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.napchatalarms.napchatalarmsandroid.customui.FactFragment;

import java.util.List;

/**
 * Page Adapter class for {@link FactFragment}.
 * Created by bbest on 15/03/18.
 */

public class FactsPageAdapter extends FragmentPagerAdapter {


    private List<android.support.v4.app.Fragment> fragments;

    public FactsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new FactFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
