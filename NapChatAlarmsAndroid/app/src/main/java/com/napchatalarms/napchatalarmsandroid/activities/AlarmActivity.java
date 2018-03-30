package com.napchatalarms.napchatalarmsandroid.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;

import java.io.IOException;

/**
 * Activity shown when an alarm triggers.
 *
 * @author bbest
 */
public class AlarmActivity extends AppCompatActivity {

    /**
     * The Id.
     */
    private int ID;
    /**
     * The Sub id.
     */
    private int subID;
    /**
     * The Snooze length.
     */
    private int snoozeLength;
    /**
     * The Previous filter.
     */
    private int previousFilter;
    /**
     * The Vibrate.
     */
    private int vibrate;
    /**
     * The Ringtone uri.
     */
    private String ringtoneURI;
    /**
     * The Dismiss button.
     */
    private Button dismissButton;
    /**
     * The Snooze button.
     */
    private Button snoozeButton;
    /**
     * The Time display.
     */
    private TextView timeDisplay;
    /**
     * The Meridian display.
     */
    private TextView meridianDisplay;


    /**
     * Initializing views from xml layout.
     */
    private void initialize() {

        dismissButton = findViewById(R.id.dismiss_btn);
        snoozeButton = findViewById(R.id.snooze_btn);
        timeDisplay = findViewById(R.id.time_display_text);
        meridianDisplay = findViewById(R.id.meridan_display_text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);


        setContentView(R.layout.activity_alarm);


        initialize();


        //=====ONCLICK METHODS=====
        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAlarm();
            }
        });

        snoozeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snoozeAlarm();
            }
        });

        //Get alarm attributes for snooze reschedule
        Intent intent = this.getIntent();
        snoozeLength = intent.getIntExtra("SNOOZE", 5);
        ID = intent.getIntExtra("ID", 0);
        vibrate = intent.getIntExtra("VIBRATE", -1);
        ringtoneURI = intent.getStringExtra("URI");
        /*
      The Meridian display string.
     */
        String meridianDisplayString = intent.getStringExtra("MERIDIAN");
        /*
      The Time display string.
     */
        String timeDisplayString = intent.getStringExtra("TIME");
        subID = intent.getIntExtra("SUBID", 0);
        previousFilter = intent.getIntExtra("FILTER", 2);


        timeDisplay.setText(timeDisplayString, TextView.BufferType.NORMAL);
        meridianDisplay.setText(meridianDisplayString, TextView.BufferType.NORMAL);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager.isNotificationPolicyAccessGranted()) {
            notificationManager.setInterruptionFilter(previousFilter);
        }
    }
    //=====METHODS=====

    /**
     * Calls the <code>AlarmController</code> and snoozes this alarm
     * based on the <code>snoozeLength</code> attribute. It also passes in the necessary information
     * for the follow up alarm to have the same behaviour.
     *
     * @see AlarmController
     */
    private void snoozeAlarm() {
        AlarmController.getInstance().snoozeAlarm(this.getApplicationContext(), ID, subID, vibrate, snoozeLength, ringtoneURI);
        finish();

    }

    /**
     * Dismisses the current alarm from sounding off and closes the <code>AlarmActivity</code>
     *
     * @see AlarmController
     */
    private void dismissAlarm() {
        try {
            NapChatController.getInstance().loadUserAlarms(this.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
            // Log.e("AlarmActivity", "Could not load user alarms");
        }
        AlarmController.getInstance().dismissAlarm(this.getApplicationContext(), ID, subID);
        finish();
    }
}
