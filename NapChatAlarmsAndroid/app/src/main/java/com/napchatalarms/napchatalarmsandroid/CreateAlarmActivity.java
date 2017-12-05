package com.napchatalarms.napchatalarmsandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

public class CreateAlarmActivity extends AppCompatActivity {

    //=====ATTRIBUTES=====
    TimePicker timePicker;
    Switch vibrateSwitch;
    Button ringtoneButton;
    Button repeatButton;
    Spinner snoozeSpinner;
    Button createAlarmButton;
    Button createActivateButton;
    AlarmController alarmController;
    AlarmBuilder alarmBuilder;

    /**Declaration of view references.
     * */
    public void initialize(){
        alarmController = AlarmController.getInstance();
        alarmBuilder = AlarmBuilder.getInstance();
        timePicker = findViewById(R.id.timePicker);
        vibrateSwitch = findViewById(R.id.vibrate_switch);
        ringtoneButton = findViewById(R.id.ringtone_btn);
        repeatButton = findViewById(R.id.repeat_btn);
        snoozeSpinner = findViewById(R.id.snooze_spinner);
        createAlarmButton = findViewById(R.id.create_alarm_btn);
        createActivateButton = findViewById(R.id.create_activate_btn);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        initialize();

        //=====ONCLICK METHODS=====


    }

    //=====METHODS=====

    //build alarm with the builder
    //add alarm to user list with the controller
    //schedule alarm with the controller
}
