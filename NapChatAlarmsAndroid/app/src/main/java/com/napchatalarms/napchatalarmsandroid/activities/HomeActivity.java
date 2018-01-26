package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.User;

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
    private TextView mTextMessage;
    User currentUser;


    //TODO: Make icons for bottom nav items
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    Intent createAlarmIntent = new Intent(HomeActivity.this,CreateAlarmActivity.class);
                    startActivity(createAlarmIntent);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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

        //Initialize User singleton
        currentUser = currentUser.getInstance();

        Log.d("HomeActivity:","Username: "+User.getInstance().getName().toString());
        Log.d("HomeActivity:","Email: "+User.getInstance().getEmail().toString());

        //Load stored alarms that match the current users credentials.

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
