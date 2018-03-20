package com.napchatalarms.napchatalarmsandroid.services;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.napchatalarms.napchatalarmsandroid.fragments.FactFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.FirstFactFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.SubmitFactFragment;
import com.napchatalarms.napchatalarmsandroid.model.Fact;
import com.napchatalarms.napchatalarmsandroid.model.FactHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Page Adapter class for {@link FactFragment}.
 * Created by bbest on 15/03/18.
 */

public class FactsPageAdapter extends FragmentPagerAdapter {



    private int count = 0;

    public FactsPageAdapter(FragmentManager fm,int count) {
        super(fm);
        this.count = count;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = FactFragment.create(position);
        if(position == 0){
            fragment = FirstFactFragment.create(position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return count;
    }

    /**
     *
     * @return
     */
    private List<Fragment> getFragments(Context context){
        ArrayList<Fact> facts = FactHolder.getInstance(context).getFacts();
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



        return fragments;
    }
}
