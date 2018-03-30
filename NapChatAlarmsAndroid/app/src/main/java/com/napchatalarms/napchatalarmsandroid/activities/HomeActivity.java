package com.napchatalarms.napchatalarmsandroid.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.fragments.AlarmListFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.HealthFactsFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.OptionsFragment;

/**
 * The activity that lists the current <code>User</code> <code>Alarms</code>.
 * <p>
 * Bottom navigation bar holds:
 * Home, Friends, Options, NapFacts, Alerts.
 * </P>
 *
 * @author bbest
 */
public class HomeActivity extends AppCompatActivity {
    private int currentFragment;
    /**
     *
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectFragment(findViewById(R.id.navigation_home));
                    return true;
                case R.id.navigation_facts:
                    selectFragment(findViewById(R.id.navigation_facts));
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            initialize();
        }
//        Log.i("User Info", User.getInstance().toString());

    }

    /**
     * Initialize.
     */
    private void initialize() {
        NapChatController.getInstance().initNotificationChannel(getApplicationContext());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.inflateMenu(R.menu.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home);
        navigation.setItemIconTintList(null);
        navigation.setItemTextColor(getResources().getColorStateList(R.color.bottom_nav_colors));
        selectFragment(findViewById(R.id.navigation_home));
        this.currentFragment = R.id.navigation_home;
    }

    /**
     * Changes the fragment occupying the layout based on the bottom navigation selection.
     *
     * @param view - Bottom Navigation Bar view selection.
     */
    private void selectFragment(View view) {
        android.support.v4.app.Fragment fragment = null;
        if (view == findViewById(R.id.navigation_home) && currentFragment != R.id.navigation_home) {
            fragment = new AlarmListFragment();
            currentFragment = R.id.navigation_home;
        } else if (view == findViewById(R.id.navigation_facts) && currentFragment != R.id.navigation_facts) {
            fragment = new HealthFactsFragment();
            currentFragment = R.id.navigation_facts;
        } else if (view == findViewById(R.id.navigation_options) && currentFragment != R.id.navigation_options) {
            fragment = new OptionsFragment();
            currentFragment = R.id.navigation_options;
        }

        //noinspection StatementWithEmptyBody
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
        } else {
//            Log.w("Home Activity","Already on that fragment.");
        }


    }


}


