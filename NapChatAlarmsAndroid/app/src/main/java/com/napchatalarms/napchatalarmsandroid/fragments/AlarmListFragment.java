package com.napchatalarms.napchatalarmsandroid.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.CreateAlarmActivity;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.customui.AlarmAdapter;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;

import static android.app.Activity.RESULT_OK;


public class AlarmListFragment extends android.support.v4.app.Fragment {

    //=====ATTRIBUTES=====
    SwipeMenuListView alarmListView;
    FloatingActionButton addAlarmButton;
    AlarmAdapter alarmAdapter;

    public AlarmListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);
        initialize(view);
        Log.e("AlarmListFragment","onCreateView()");
        return  view;
    }

    @Override
    public void onResume(){
        super.onResume();
        updateAlarmList();
    }

    @Override
    public void onPause(){
        super.onPause();
    }
    private void initialize(View view) {
        alarmListView = (SwipeMenuListView) view.findViewById(R.id.alarm_list_view);



        addAlarmButton = (FloatingActionButton) view.findViewById(R.id.add_alarm_btn);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAlarmIntent = new Intent(getActivity(), CreateAlarmActivity.class);
                createAlarmIntent.putExtra("ID", 0);
                startActivityForResult(createAlarmIntent,1);
            }
        });
                SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0x00, 0xAB,
                        0xFF)));
                // set item width
                openItem.setWidth(300);
                // set item title
                openItem.setTitle("Edit");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(300);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_trashcan);
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        alarmListView.setMenuCreator(creator);

        alarmListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // Edit
                        Alarm editAlarm = alarmAdapter.getItem(position);
                        Intent intent = new Intent(getActivity(), CreateAlarmActivity.class);
                        try {
                            intent.putExtra("ID", editAlarm.getId());
                            startActivityForResult(intent,1);
                            updateAlarmList();
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        // delete
                        Alarm deleteAlarm = alarmAdapter.getItem(position);
                        AlarmController.getInstance().deleteAlarm(getContext(), deleteAlarm.getId());
                        updateAlarmList();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        alarmListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        alarmListView.setCloseInterpolator(new BounceInterpolator());
        updateAlarmList();
    }

    public void updateAlarmList() {
        User user = User.getInstance();
        alarmAdapter = new AlarmAdapter(getContext(), User.getInstance().getAlarmList());
        alarmListView.setAdapter(alarmAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    //Back from CreateAlarmActivity
                    updateAlarmList();
                    break;
                default:
                    break;
            }
        }
    }

}
