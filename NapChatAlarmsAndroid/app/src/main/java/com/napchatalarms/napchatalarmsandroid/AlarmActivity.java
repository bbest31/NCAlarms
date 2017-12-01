package com.napchatalarms.napchatalarmsandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


public class AlarmActivity extends AppCompatActivity {

    //=====ATTRIBUTES=====
    String timeDisplayString;
    int ID;
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

        //Reference xml layout views


        //=====ONCLICK METHODS=====


        //Get alarm attributes for snooze reschedule
        Intent intent = this.getIntent();
        snoozeLength = intent.getIntExtra("SNOOZE", 5);
        ID = intent.getIntExtra("ID",0);
        vibrate = intent.getBooleanExtra("VIBRATE", false);
        ringtoneURI = intent.getStringExtra("URI");
        meridianDisplayString = intent.getStringExtra("MERIDIAN");
        timeDisplayString = intent.getStringExtra("TIME");


        timeDisplay.setText(timeDisplayString, TextView.BufferType.NORMAL);
        meridianDisplay.setText(meridianDisplayString, TextView.BufferType.NORMAL);


    }

    //=====METHODS=====

    /**
     *
     * */
    public void snoozeAlarm(){
        AlarmController alarmController = AlarmController.getInstance();
        alarmController.cancelAlarm(this.getApplicationContext(),ID);
        alarmController.snoozeAlarm(this.getApplicationContext(),ID,vibrate,snoozeLength,ringtoneURI);
        finish();

    }

    /**
     *
     * */
    public void dismissAlarm(){
        AlarmController alarmController = AlarmController.getInstance();
        alarmController.cancelAlarm(this.getApplicationContext(),ID);
        finish();
    }
}
