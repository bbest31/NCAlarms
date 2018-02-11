package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Activity shown when an alarm triggers.
 * @author bbest
 */
public class AlarmActivity extends AppCompatActivity {

    //=====ATTRIBUTES=====
    String timeDisplayString;
    int ID;
    int subID;
    int snoozeLength;
    Boolean vibrate;
    String ringtoneURI;
    String meridianDisplayString;
    Button dismissButton;
    Button snoozeButton;
    TextView timeDisplay;
    TextView meridianDisplay;


    /**
     * Initializing views from xml layout.
     * */
    public void initialize(){

        dismissButton = (Button) findViewById(R.id.dismiss_btn);
        snoozeButton = (Button) findViewById(R.id.snooze_btn);
        timeDisplay = (TextView) findViewById(R.id.time_display_text);
        meridianDisplay = (TextView) findViewById(R.id.meridan_display_text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Window window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        //window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
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
        ID = intent.getIntExtra("ID",0);
        vibrate = intent.getBooleanExtra("VIBRATE", false);
        ringtoneURI = intent.getStringExtra("URI");
        meridianDisplayString = intent.getStringExtra("MERIDIAN");
        timeDisplayString = intent.getStringExtra("TIME");
        subID = intent.getIntExtra("SUBID",0);


        timeDisplay.setText(timeDisplayString, TextView.BufferType.NORMAL);
        meridianDisplay.setText(meridianDisplayString, TextView.BufferType.NORMAL);


    }

    //=====METHODS=====

    /**
     *Calls the <code>AlarmController</code> and snoozes this alarm
     * based on the <code>snoozeLength</code> attribute. It also passes in the necessary information
     * for the follow up alarm to have the same behaviour.
     * @see AlarmController
     * */
    public void snoozeAlarm(){
        AlarmController alarmController = AlarmController.getInstance();
        alarmController.snoozeAlarm(this.getApplicationContext(),ID,subID,vibrate,snoozeLength,ringtoneURI);
        finish();

    }

    /**
     *Dismisses the current alarm from sounding off and closes the <code>AlarmActivity</code>
     * @see AlarmController
     * */
    public void dismissAlarm(){
        AlarmController alarmController = AlarmController.getInstance();
        alarmController.dismissAlarm(this.getApplicationContext(),ID,subID);
        finish();
    }
}
