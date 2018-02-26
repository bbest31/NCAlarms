package com.napchatalarms.napchatalarmsandroid.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.model.User;

/**
 * The activity that lists the current <code>User</code> <code>Alarms</code>.
 * <p>
 * Bottom navigation bar holds:
 * Home, Friends, Options, NapFacts, Alerts.
 * </P>
 *
 * @author bbest
 * @todo order alarms from earliest to latest time.
 */
public class HomeActivity extends AppCompatActivity {

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
                    selectFragment(findViewById(R.id.navigation_home));
                    return true;
                case R.id.navigation_social:
                    selectFragment(findViewById(R.id.navigation_social));
                    return true;
                case R.id.navigation_options:
                    selectFragment(findViewById(R.id.navigation_options));
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            initialize();
            getFragmentManager().beginTransaction().replace(R.id.fragment, new AlarmListFragment());
        }
        Log.i("User Info", User.getInstance().toString());

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void initialize() {
        NapChatController.getInstance().initNotificationChannel(getApplicationContext());
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.inflateMenu(R.menu.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void selectFragment(View view) {
        Fragment fragment;
        if (view == findViewById(R.id.navigation_home)) {
            fragment = new AlarmListFragment();
        } else if (view == findViewById(R.id.navigation_social)) {
            fragment = new SocialFragment();
        } else {
            fragment = new OptionsFragment();
        }

        getFragmentManager().beginTransaction().replace(R.id.fragment, fragment);
    }


}


