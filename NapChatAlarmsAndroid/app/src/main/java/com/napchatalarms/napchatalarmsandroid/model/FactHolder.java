package com.napchatalarms.napchatalarmsandroid.model;

import android.content.Context;

import com.napchatalarms.napchatalarmsandroid.R;

import java.util.ArrayList;

/**
 * Static class to hold all the current {@link Fact} contained in this app version.
 * Created by bbest on 15/03/18.
 */
@SuppressWarnings("unused")
public class FactHolder {

    static private ArrayList<Fact> facts = new ArrayList<>();
    static private FactHolder instance = null;

    private FactHolder(Context context) {
        facts.add(new Fact(context.getString(R.string.sleep_fact1_description), context.getString(R.string.sleep_fact1_citation)));
        facts.add(new Fact(context.getString(R.string.sleep_fact2_description), context.getString(R.string.sleep_fact2_citation)));
        facts.add(new Fact(context.getString(R.string.sleep_fact3_description), context.getString(R.string.sleep_fact3_citation)));
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static FactHolder getInstance(Context context) {
        if (instance == null) {
            instance = new FactHolder(context);
        }
        return instance;
    }

    /**
     * Gets facts.
     *
     * @return the facts
     */
    public static ArrayList<Fact> getFacts() {
        return facts;
    }

    /**
     * Sets facts.
     *
     * @param facts the facts
     */
    @SuppressWarnings("unused")
    public static void setFacts(ArrayList<Fact> facts) {
        FactHolder.facts = facts;
    }
}
