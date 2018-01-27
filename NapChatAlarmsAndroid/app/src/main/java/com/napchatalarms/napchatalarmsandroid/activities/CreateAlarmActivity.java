package com.napchatalarms.napchatalarmsandroid.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.customui.RingtoneDialog;
import com.napchatalarms.napchatalarmsandroid.services.AlarmController;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.services.NapChatController;
import com.napchatalarms.napchatalarmsandroid.services.OneTimeBuilder;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * Activity used to create new alarms.
 * @author bbest
 */
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
        ringtoneButton.setText("Ringtone: Default");
        repeatButton = (Button) findViewById(R.id.repeat_btn);
        snoozeSpinner = (Spinner) findViewById(R.id.snooze_spinner);
        createAlarmButton = (Button) findViewById(R.id.create_alarm_btn);
        ringtone = String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
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
                RingtoneDialog ringtoneDialog = new RingtoneDialog(CreateAlarmActivity.this);
                ringtoneDialog.show();

            }
        });

    }

    //=====METHODS=====

    /**Calls the <code>AlarmBuilder</code> class depending on whether there are repeating days or not.
     * If the <code>repeatDays</code> equals null then it identifies it as a <Code>OneTimeAlarm</Code>.
     * @see com.napchatalarms.napchatalarmsandroid.services.AlarmBuilder
     * @see OneTimeBuilder
     * @see com.napchatalarms.napchatalarmsandroid.services.RepeatingBuilder
     * */
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
            alarmController.addAlarm(alarm, getApplicationContext());
            //schedule alarm with the controller
            alarmController.scheduleAlarm(getApplicationContext(),alarm);

        } else{
            //build repeating alarm

        }
    }



}
