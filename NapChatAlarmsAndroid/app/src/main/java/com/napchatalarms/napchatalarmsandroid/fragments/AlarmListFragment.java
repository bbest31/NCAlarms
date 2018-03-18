package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;

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


public class AlarmListFragment extends android.support.v4.app.Fragment implements View.OnTouchListener {

    //=====ATTRIBUTES=====
    SwipeMenuListView alarmListView;
    FloatingActionButton addAlarmButton;
    AlarmAdapter alarmAdapter;
    private final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.

    private float downRawX, downRawY;
    private float dX, dY;

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
                addAlarmButton.setBackgroundColor(getActivity().getColor(R.color.fab_blue_sel));
                Intent createAlarmIntent = new Intent(getActivity(), CreateAlarmActivity.class);
                createAlarmIntent.putExtra("ID", 0);
                startActivityForResult(createAlarmIntent,1);
            }
        });


        addAlarmButton.setOnTouchListener(this);

                SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getContext());
                // set item background
                openItem.setBackground(getActivity().getDrawable(R.drawable.circle_blue_btn));
                // set item width
                openItem.setWidth(300);
                // set item title
                openItem.setTitle(getActivity().getString(R.string.edit));
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
                deleteItem.setBackground(getActivity().getDrawable(R.drawable.circle_red_btn));
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int action = motionEvent.getAction();
        if (action == MotionEvent.ACTION_DOWN) {

            downRawX = motionEvent.getRawX();
            downRawY = motionEvent.getRawY();
            dX = view.getX() - downRawX;
            dY = view.getY() - downRawY;

            return true; // Consumed

        }
        else if (action == MotionEvent.ACTION_MOVE) {

            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();

            View viewParent = (View)view.getParent();
            int parentWidth = viewParent.getWidth();
            int parentHeight = viewParent.getHeight();

            float newX = motionEvent.getRawX() + dX;
            newX = Math.max(0, newX); // Don't allow the FAB past the left hand side of the parent
            newX = Math.min(parentWidth - viewWidth, newX); // Don't allow the FAB past the right hand side of the parent

            float newY = motionEvent.getRawY() + dY;
            newY = Math.max(0, newY); // Don't allow the FAB past the top of the parent
            newY = Math.min(parentHeight - viewHeight, newY); // Don't allow the FAB past the bottom of the parent

            view.animate()
                    .x(newX)
                    .y(newY)
                    .setDuration(0)
                    .start();

            return true; // Consumed

        }
        else if (action == MotionEvent.ACTION_UP) {

            float upRawX = motionEvent.getRawX();
            float upRawY = motionEvent.getRawY();

            float upDX = upRawX - downRawX;
            float upDY = upRawY - downRawY;

            if (Math.abs(upDX) < CLICK_DRAG_TOLERANCE && Math.abs(upDY) < CLICK_DRAG_TOLERANCE) { // A click
                return view.performClick();
            }
            else { // A drag
                return true; // Consumed
            }

        }
        else {
            return view.onTouchEvent(motionEvent);
        }
    }
}

