package com.napchatalarms.napchatalarmsandroid.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.customui.AlarmAdapter;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * The activity that lists the current <code>User</code> <code>Alarms</code>.
 * <P>
 *     Bottom navigation bar holds:
 *     Home, Friends, Options, NapFacts.
 * </P>
 * @author bbest
 */
public class HomeActivity extends AppCompatActivity {

    //=====ATTRIBUTES=====
    User currentUser;
    ListView alarmListView;
    AlarmAdapter alarmAdapter;
    public static final int ALARM_PERMISSION_REQUEST = 31;

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
                    Intent createAlarmIntent = new Intent(HomeActivity.this,CreateAlarmActivity.class);
                    startActivity(createAlarmIntent);
                    return true;
                case R.id.navigation_notifications:
                    Intent optionsIntent = new Intent(HomeActivity.this,OptionsActivity.class);
                    startActivity(optionsIntent);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize();
        updateAlarmList();



    }

    public void initialize(){
        //Initialize User singleton
        currentUser = currentUser.getInstance();
        updateAlarmList();
        Log.d("HomeActivity:","Username: "+User.getInstance().getName().toString());
        Log.d("HomeActivity:","Email: "+User.getInstance().getEmail().toString());

        //Load stored alarms that match the current users credentials.


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /**
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {

        switch (requestCode) {
            case ALARM_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        ) {
                    // permission was granted, yay! Do the
                } else {
                    // permission denied, boo! Disable the
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
                }
            }
            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void updateAlarmList(){
        alarmListView = (ListView)findViewById(R.id.alarm_list_view);
        alarmAdapter = new AlarmAdapter(getApplicationContext(),currentUser.getAlarmList());
        alarmListView.setAdapter(alarmAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        updateAlarmList();

    }

    public void clearViews(){

    }


}
