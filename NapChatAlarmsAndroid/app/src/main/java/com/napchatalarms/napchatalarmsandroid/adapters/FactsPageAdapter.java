package com.napchatalarms.napchatalarmsandroid.adapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.napchatalarms.napchatalarmsandroid.fragments.FactFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.FirstFactFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.SuggestFactLastFragment;

/**
 * Page Adapter class for {@link FactFragment}.
 * Created by bbest on 15/03/18.
 */

public class FactsPageAdapter extends FragmentStatePagerAdapter {


    private int count = 0;

    public FactsPageAdapter(FragmentManager fm, int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FactFragment.create(position);
        if (position == 0) {
            fragment = FirstFactFragment.create(position);
        } else if (position == count - 1) {
            fragment = SuggestFactLastFragment.create(position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }

}
