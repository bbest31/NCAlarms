package com.napchatalarms.napchatalarmsandroid.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.napchatalarms.napchatalarmsandroid.fragments.OnboardingFragment;

/**
 * Created by bbest on 07/04/18.
 */

public class OnboardingPageAdapter extends FragmentStatePagerAdapter {

    private int count = 0;

    public OnboardingPageAdapter(FragmentManager fm, int count){
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = OnboardingFragment.create(position);

        return fragment;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
