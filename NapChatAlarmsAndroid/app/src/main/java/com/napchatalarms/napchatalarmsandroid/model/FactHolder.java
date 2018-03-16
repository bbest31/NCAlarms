package com.napchatalarms.napchatalarmsandroid.model;

import android.content.Context;

import com.napchatalarms.napchatalarmsandroid.R;

import java.util.ArrayList;
import java.util.List;

/** Static class to hold all the current {@link SleepFact} contained in this app version.
 * Created by bbest on 15/03/18.
 */

public class FactHolder {

    static private List<SleepFact> facts = new ArrayList<>();
    static private FactHolder instance = null;

    private FactHolder(Context context){
        facts.add(new SleepFact(context.getString(R.string.sleep_fact1_description),context.getString(R.string.sleep_fact1_citation)));
        facts.add(new SleepFact(context.getString(R.string.sleep_fact2_description),context.getString(R.string.sleep_fact2_citation)));
        facts.add(new SleepFact(context.getString(R.string.sleep_fact3_description),context.getString(R.string.sleep_fact3_citation)));
    }

    public static FactHolder getInstance(Context context){
        if(instance == null){
            instance = new FactHolder(context);
        }
        return instance;
    }

    public static List<SleepFact> getFacts() {
        return facts;
    }

    public static void setFacts(List<SleepFact> facts) {
        FactHolder.facts = facts;
    }
}
