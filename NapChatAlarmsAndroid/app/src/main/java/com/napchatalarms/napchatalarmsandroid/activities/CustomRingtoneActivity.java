package com.napchatalarms.napchatalarmsandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.adapters.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CustomRingtoneActivity extends AppCompatActivity {

    private Intent returnIntent;
    private String uri;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ringtone);
        initialize();
    }

    private void initialize(){
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.custom_tone_listview);

        // preparing list data
        prepareListData();

        // setting list header icons
        prepareHeaderIcons();


        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // on child clicked listener.
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                setUri(groupPosition,childPosition);
                String name = getToneName(groupPosition, childPosition);
                returnIntent = new Intent();
                returnIntent.putExtra("URI",String.valueOf(uri));
                returnIntent.putExtra("NAME", name);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                return false;
            }
        });

    }


    /**
     *Prepares the selection of  custom ring-tones.
     */
    private void prepareListData(){
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<>();

        // Adding child data
        listDataHeader.add("Soft");
        listDataHeader.add("Mild");
        listDataHeader.add("Thunderous");

        // Adding child data
        List<String> softTones = new ArrayList<>();
        softTones.add(getString(R.string.bamboo));


        List<String> mildTones = new ArrayList<>();
        mildTones.add(getString(R.string.alleycat));

        List<String> thunderousTones = new ArrayList<>();
        thunderousTones.add(getString(R.string.steampunk));

        listDataChild.put(listDataHeader.get(0), softTones); // Header, Child data
        listDataChild.put(listDataHeader.get(1), mildTones);
        listDataChild.put(listDataHeader.get(2), thunderousTones);
    }

    /**
     * Sets the proper list header icons as their drawableLeft attribute.
     */
    private void prepareHeaderIcons(){

    }

    /**
     * Gets the proper uri of the selected ringtone based on the position of the header group and the child view.
     * @param groupPosition
     * @param childPosition
     */
    private void setUri(int groupPosition, int childPosition){

        switch (groupPosition){
            case 0:
                // Soft section
                // determine the child selection
                switch (childPosition){
                    case 0:
                        uri = "android.resource://"+getPackageName()+"/"+R.raw.bamboo_forest;
                        break;
                }
                break;
            case 1:
                // Mild section
                // determine the child selection
                switch (childPosition){
                    case 0:
                        uri = "android.resource://"+getPackageName()+"/"+R.raw.alley_cat;
                        break;
                }
                break;
            case 3:
                // Thunderous section
                // determine the child selection
                switch (childPosition){
                    case 0:
                        uri = "android.resource://"+getPackageName()+"/"+R.raw.steampunk;
                        break;
                }
                break;
        }
    }

    /**
     * Gets the name of the selected ringtone and returns it for display.
     * @param groupPosition
     * @param childPosition
     * @return
     */
    public String getToneName(int groupPosition, int childPosition){
        String name = "";
        switch (groupPosition){
            case 0:
                // Soft section
                // determine the child selection
                switch (childPosition){
                    case 0:
                        name = getString(R.string.bamboo);
                        break;
                }
                break;
            case 1:
                // Mild section
                // determine the child selection
                switch (childPosition){
                    case 0:
                        name = getString(R.string.alleycat);
                        break;
                }
                break;
            case 2:
                // Thunderous section
                // determine the child selection
                switch (childPosition){
                    case 0:
                        name = getString(R.string.steampunk);
                        break;
                }
                break;
        }
        return name;
    }

}
