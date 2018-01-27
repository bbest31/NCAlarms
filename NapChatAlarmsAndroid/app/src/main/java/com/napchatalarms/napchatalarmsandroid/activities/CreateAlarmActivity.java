package com.napchatalarms.napchatalarmsandroid.activities;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
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
    public static final int SET_ALARM_PERMISSION = 1;



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

         checkAlarmPermissions(CreateAlarmActivity.this);

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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {

        switch (requestCode) {
            case SET_ALARM_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length == 1
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the


                } else {
                    // permission denied, boo! Disable the
                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
                }

            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    /**
     *
     */
    public void checkAlarmPermissions(Activity a){
        int vibratePermission = checkVibratePermission(a);
        int alarmPermission = checkAlarmPermission(a);
        int wakeLockPermission = checkWakeLockPermission(a);

        String[] permissions = {""};
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED){


        } else{
            requestAlarmPermissions();
        }



        /*else if(wakeLockPermission != PackageManager.PERMISSION_GRANTED){
            permissions[2] = android.Manifest.permission.WAKE_LOCK;
        }else if(vibratePermission != PackageManager.PERMISSION_GRANTED){
        permissions[0] = android.Manifest.permission.VIBRATE;
            } */

    }


    private int checkVibratePermission(Activity a){
        // Assume thisActivity is the current activity
        return  ContextCompat.checkSelfPermission(a,
                android.Manifest.permission.VIBRATE);
    }

    private int checkAlarmPermission(Activity a){
        // Assume thisActivity is the current activity
        return  ContextCompat.checkSelfPermission(a,
                android.Manifest.permission.SET_ALARM);
    }

    private int checkWakeLockPermission(Activity a){
        // Assume thisActivity is the current activity
        return  ContextCompat.checkSelfPermission(a,
                android.Manifest.permission.WAKE_LOCK);
    }

    private void requestAlarmPermissions(){
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.SET_ALARM)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(findViewById(R.layout.activity_create_alarm), "Alarm access is required to set Alarms.",
                    Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(CreateAlarmActivity.this,
                            new String[]{Manifest.permission.SET_ALARM},
                            SET_ALARM_PERMISSION);
                }
            }).show();

        } else {
            Snackbar.make(findViewById(R.id.timePicker),
                    "Permission is not available. Requesting Alarm permission.",
                    Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SET_ALARM},
                    SET_ALARM_PERMISSION);
        }
    }

}
