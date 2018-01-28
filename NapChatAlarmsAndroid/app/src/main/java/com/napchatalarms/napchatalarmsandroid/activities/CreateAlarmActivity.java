package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TimePicker;

import com.napchatalarms.napchatalarmsandroid.customui.RingtoneDialog;
import com.napchatalarms.napchatalarmsandroid.services.AlarmController;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.services.OneTimeBuilder;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * Activity used to create new alarms.
 *
 * @author bbest
 */
public class CreateAlarmActivity extends AppCompatActivity  {

    /**
     * The Time picker.
     */
//=====ATTRIBUTES=====
    TimePicker timePicker;
    /**
     * The Vibrate switch.
     */
    Switch vibrateSwitch;
    /**
     * The Ringtone button.
     */
    Button ringtoneButton;
    /**
     * The Repeat button.
     */
    Button repeatButton;
    /**
     * The Snooze spinner.
     */
    NumberPicker snoozeSelector;
    /**
     * The Create alarm button.
     */
    Button createAlarmButton;
    /**
     * The Alarm controller.
     */
    AlarmController alarmController;
    /**
     * The Ringtone.
     */
    String ringtone;
    /**
     * The Vibrate.
     */
    Boolean vibrate;
    /**
     * The Repeat days.
     */
    Integer[] repeatDays;
    /**
     * The Snooze length.
     */
    Integer snoozeLength;


    /**
     * Declaration of view references.
     */
    public void initialize(){
        alarmController = AlarmController.getInstance();
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        vibrateSwitch = (Switch) findViewById(R.id.vibrate_switch);
        ringtoneButton = (Button) findViewById(R.id.ringtone_btn);
        ringtoneButton.setText("Ringtone: Default");
        repeatButton = (Button) findViewById(R.id.repeat_btn);
        snoozeSelector = (NumberPicker) findViewById(R.id.snooze_length_picker);
        snoozeSelector.setMaxValue(60);
        snoozeSelector.setMinValue(1);
        snoozeSelector.canScrollHorizontally(1);
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

        vibrateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                vibrate = isChecked;
            }
        });


    }

    //=====METHODS=====

    /**
     * Calls the <code>AlarmBuilder</code> class depending on whether there are repeating days or not.
     * If the <code>repeatDays</code> equals null then it identifies it as a <Code>OneTimeAlarm</Code>.
     *
     * @see com.napchatalarms.napchatalarmsandroid.services.AlarmBuilder
     * @see OneTimeBuilder
     * @see com.napchatalarms.napchatalarmsandroid.services.RepeatingBuilder
     */
//TODO:finish repeating alarm creation.
    public void createAlarm(){
        if(repeatDays == null) {
            OneTimeBuilder builder = new OneTimeBuilder();
            Long trigger = UtilityFunctions.UTCMilliseconds(timePicker.getHour(), timePicker.getMinute());
            builder.setTime(trigger)
                    .setVibrate(vibrate)
                    .setRingtoneURI(ringtone)
                    .setSnooze(snoozeLength);

            OneTimeAlarm alarm = builder.build();

            alarmController.createAlarm(getApplicationContext(),alarm);

        } else{
            //build repeating alarm

        }
    }

    /**
     * Set ringtone.
     *
     * @param uri  the uri
     * @param name the name
     */
    public void setRingtone(String uri, String name){
        ringtone = uri;
        ringtoneButton.setText("Ringtone: "+name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    String name = RingtoneManager.getRingtone(getApplicationContext(),uri).getTitle(getApplicationContext());
                    setRingtone(String.valueOf(uri),name);
                    break;
                default:
                    break;
            }
        }
    }


}
