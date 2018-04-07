package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.napchatalarms.napchatalarmsandroid.BuildConfig;
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
        navigation.setItemTextColor(getColorStateList(R.color.bottom_nav_colors));
        selectFragment(findViewById(R.id.navigation_home));
        this.currentFragment = R.id.navigation_home;
        checkFirstRun();

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

    private void checkFirstRun(){
        final String PREFS_NAME = getPackageName();
        final String PREF_VERSION_CODE_KEY = getString(R.string.version_code);
        final int DOESNT_EXIST = -1;

        // Get current version code
        int currentVersionCode = BuildConfig.VERSION_CODE;

        // Get saved version code
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST);

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {

            // This is just a normal run
           return;


        } else if (savedVersionCode == DOESNT_EXIST) {

            // TODO This is a new install (or the user cleared the shared preferences)
            //Show tutorial
          //  Log.w("HOME","This is a new install");
            Intent onboardIntent = new Intent(this,OnboardingActivity.class);
            startActivity(onboardIntent);

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
      //      Log.w("HOME","This is a upgrade");
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();

    }


}


