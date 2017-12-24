package com.napchatalarms.napchatalarmsandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    AlarmController alarmController;
    String ringtone;
    boolean vibrate;
    int[] repeatDays;
    int snoozeLength;


    /**Declaration of view references.
     * */
    public void initialize(){
        alarmController = AlarmController.getInstance();
        timePicker = findViewById(R.id.timePicker);
        vibrateSwitch = findViewById(R.id.vibrate_switch);
        ringtoneButton = findViewById(R.id.ringtone_btn);
        repeatButton = findViewById(R.id.repeat_btn);
        snoozeSpinner = findViewById(R.id.snooze_spinner);
        createAlarmButton = findViewById(R.id.create_alarm_btn);
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
            Log.d("CREATE ALARM ACTIVITY:","TIME PICKER TIME:"+timePicker.getHour()+":"+timePicker.getMinute());
            Log.d("CREATE ALARM ACTIVITY:","Vibrate settings: "+vibrate);
            Log.d("CREATE ALARM ACTIVITY:","Ringtone: "+ringtone);
            Log.d("CREATE ALARM ACTIVITY:","Snooze:"+snoozeLength);
            builder.setTime(UtilityFunctions.UTCMilliseconds(timePicker.getHour(), timePicker.getMinute()))
                    .setVibrate(vibrate)
                    .setRingtoneURI(ringtone)
                    .setSnooze(snoozeLength);

            OneTimeAlarm alarm = builder.build();

            Log.d("Created ALARM:",alarm.toString());
            
            //add alarm to user list with the controller
            alarmController.addAlarm(alarm);
            //schedule alarm with the controller
            alarmController.scheduleAlarm(this,alarm);
        } else{

        }
    }


}
