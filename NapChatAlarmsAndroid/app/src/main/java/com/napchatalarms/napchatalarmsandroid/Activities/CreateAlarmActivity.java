package com.napchatalarms.napchatalarmsandroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.napchatalarms.napchatalarmsandroid.Services.AlarmController;
import com.napchatalarms.napchatalarmsandroid.Model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.Services.OneTimeBuilder;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.Utility.UtilityFunctions;

public class CreateAlarmActivity extends AppCompatActivity {

    //=====ATTRIBUTES=====
    TimePicker timePicker;
    Switch vibrateSwitch;
    Button ringtoneButton;
    Button repeatButton;
    Spinner snoozeSpinner;
    Button createAlarmButton;
    AlarmController alarmController;
    String ringtone;
    boolean vibrate;
    int[] repeatDays;
    int snoozeLength;


    /**Declaration of view references.
     * */
    public void initialize(){
        alarmController = AlarmController.getInstance();
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        vibrateSwitch = (Switch) findViewById(R.id.vibrate_switch);
        ringtoneButton = (Button) findViewById(R.id.ringtone_btn);
        repeatButton = (Button) findViewById(R.id.repeat_btn);
        snoozeSpinner = (Spinner) findViewById(R.id.snooze_spinner);
        createAlarmButton = (Button) findViewById(R.id.create_alarm_btn);
        ringtone = "default";
        vibrate = vibrateSwitch.isChecked();
        repeatDays = null;
        snoozeLength = 5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        initialize();

        //=====ONCLICK METHODS=====
        createAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlarm();
                //TODO:May need to create this intent with set result so Home knows to refresh alarm list.
                finish();
            }
        });

        ringtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO:need a custom dialog box for ringtone options (Music, Device Ringtones, NapChat Ringtones)
            }
        });

    }

    //=====METHODS=====

    /**Uses the Alarm building classes to build the alarm.**/
    //TODO:finish repeating alarm creation.
    public void createAlarm(){
        if(repeatDays == null) {
            OneTimeBuilder builder = new OneTimeBuilder();
            long trigger = UtilityFunctions.UTCMilliseconds(timePicker.getHour(), timePicker.getMinute());
            builder.setTime(trigger)
                    .setVibrate(vibrate)
                    .setRingtoneURI(ringtone)
                    .setSnooze(snoozeLength);

            OneTimeAlarm alarm = builder.build();

            //Affirm attributes of alarm.
            Log.d("Created ALARM",alarm.toString());

            //add alarm to user list with the controller
            alarmController.addAlarm(alarm);
            //schedule alarm with the controller
            alarmController.scheduleAlarm(getApplicationContext(),alarm);

        } else{

        }
    }


}
