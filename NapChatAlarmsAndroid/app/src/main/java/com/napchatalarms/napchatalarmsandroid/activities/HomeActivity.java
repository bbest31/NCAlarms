package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.customui.AlarmAdapter;
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;

/**
 * The activity that lists the current <code>User</code> <code>Alarms</code>.
 * <P>
 *     Bottom navigation bar holds:
 *     Home, Friends, Options, NapFacts.
 * </P>
 * @todo order alarms from earliest to latest time.
 * @author bbest
 */
public class HomeActivity extends AppCompatActivity {

    //=====ATTRIBUTES=====
    User currentUser;
    SwipeMenuListView alarmListView;
    Button addAlarmButton;
    AlarmAdapter alarmAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
        updateAlarmList();

    }

    @Override
    protected void onStart(){
        super.onStart();
        updateAlarmList();
        Log.d("User Info",User.getInstance().toString());
        Log.d("User Alarms","Alarm List: "+User.getInstance().getAlarmList());

    }

    public void initialize(){

        //Initialize User singleton
        currentUser = currentUser.getInstance();
        alarmListView = (SwipeMenuListView)findViewById(R.id.alarm_list_view);
        updateAlarmList();


        addAlarmButton = (Button)findViewById(R.id.add_alarm_btn);
        addAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAlarmIntent = new Intent(HomeActivity.this,CreateAlarmActivity.class);
                createAlarmIntent.putExtra("ID",0);
                startActivity(createAlarmIntent);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
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
                        getApplicationContext());
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
                        Intent intent = new Intent(HomeActivity.this, CreateAlarmActivity.class);
                        try {
                            intent.putExtra("ID", editAlarm.getId());
                            startActivity(intent);
                            updateAlarmList();
                        } catch (NullPointerException e){
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        // delete
                        Alarm deleteAlarm = alarmAdapter.getItem(position);
                        AlarmController.getInstance().deleteAlarm(getApplicationContext(),deleteAlarm.getId());
                        updateAlarmList();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        alarmListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);

        alarmListView.setCloseInterpolator(new BounceInterpolator());

    }

    public void updateAlarmList(){
        alarmAdapter = new AlarmAdapter(getApplicationContext(),currentUser.getAlarmList());
        alarmListView.setAdapter(alarmAdapter);
    }

    /**
     *
     */
    //TODO: Make icons for bottom nav items, turn views visible and gone by
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    updateAlarmList();
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    Intent optionsIntent = new Intent(HomeActivity.this,OptionsActivity.class);
                    startActivity(optionsIntent);
                    return true;
            }
            return false;
        }

    };


}


