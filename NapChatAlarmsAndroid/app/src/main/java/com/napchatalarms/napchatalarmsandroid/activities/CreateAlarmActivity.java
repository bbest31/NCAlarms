package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.media.MediaActionSound;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;

import com.napchatalarms.napchatalarmsandroid.customui.RepeatDaysDialog;
import com.napchatalarms.napchatalarmsandroid.customui.RingtoneDialog;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.services.AlarmController;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.services.OneTimeBuilder;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.services.RepeatingBuilder;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Activity used to create new alarms.
 *
 * @author bbest
 */
public class CreateAlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    Spinner snoozeSelector;
    /**
     * The Create alarm button.
     */
    Button createAlarmButton;
    Button editAlarmButton;
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
    List<Integer> repeatDays;
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
        repeatButton = (Button) findViewById(R.id.repeat_btn);

        snoozeSelector = (Spinner)findViewById(R.id.snooze_spinner);
        ArrayAdapter<CharSequence> snoozeAdapter = ArrayAdapter.createFromResource(this
                ,R.array.snooze_array
                ,R.layout.support_simple_spinner_dropdown_item);
        snoozeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        snoozeSelector.setAdapter(snoozeAdapter);
        snoozeSelector.setOnItemSelectedListener(this);

        //Get id indicator to see if this is a brand new alarm or if we are editing one.
        Intent intent = this.getIntent();
        int id = intent.getIntExtra("ID",0);

        //If we are getting an alarm Id we grab the alarm and populate the views with its attributes.
        if (id !=0) {
            final Alarm alarm = User.getInstance().getAlarmById(id);
            editAlarmButton = (Button)findViewById(R.id.edit_alarm_btn);
            editAlarmButton.setVisibility(View.VISIBLE);

            //Set TimePicker time.
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(alarm.getTime());
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));

            //Set ringtone name
            Uri uri = Uri.parse(alarm.getRingtoneURI());
            if(uri.toString().equals(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString())){
                ringtoneButton.setText("Ringtone: Default");
            } else {
                String uriName = RingtoneManager.getRingtone(getApplicationContext(), uri).getTitle(getApplicationContext());
                ringtoneButton.setText("Ringtone: " + uriName);
            }
            ringtone = alarm.getRingtoneURI();

            //Set vibrate
            vibrateSwitch.setChecked(alarm.getVibrateOn());
            vibrate = vibrateSwitch.isChecked();

            // set snooze
            int pos = snoozeAdapter.getPosition(String.valueOf(alarm.getSnoozeLength()));
            snoozeSelector.setSelection(pos);
            snoozeLength = Integer.valueOf(snoozeSelector.getSelectedItem().toString());

            //Set repeat day selection
            if(alarm.getClass() == RepeatingAlarm.class){

                RepeatingAlarm repeatingAlarm = (RepeatingAlarm)alarm;
                repeatDays = repeatingAlarm.getRepeatDays();
                setRepeatText(repeatDays);

            }

            editAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    editAlarm(alarm.getId(),vibrate,timePicker.getHour(),timePicker.getMinute(),ringtone,snoozeLength,repeatDays);
                    finish();
                }
            });

        } else {
            createAlarmButton = (Button) findViewById(R.id.create_alarm_btn);
            createAlarmButton.setVisibility(View.VISIBLE);

            ringtoneButton.setText("Ringtone: Default");
            ringtone = String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));

            vibrate = vibrateSwitch.isChecked();

            repeatDays = new ArrayList<Integer>();

            createAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createAlarm();
                    finish();
                }
            });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
        initialize();

        //=====ONCLICK METHODS=====

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

        //TODO pass in repeatDays
        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepeatDaysDialog repeatDaysDialog = new RepeatDaysDialog(CreateAlarmActivity.this);
                repeatDaysDialog.show();
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
    public void createAlarm(){
        if(repeatDays.size() == 0) {
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
            RepeatingBuilder builder = new RepeatingBuilder();
            Long trigger = UtilityFunctions.UTCMilliseconds(timePicker.getHour(),timePicker.getMinute());
            builder.initialize(repeatDays)
                    .setTime(trigger)
                    .setVibrate(vibrate)
                    .setRingtoneURI(ringtone)
                    .setSnooze(snoozeLength)
                    .setInterval();

            RepeatingAlarm alarm = builder.build();

            alarmController.createAlarm(getApplicationContext(),alarm);

        }
    }

    public void editAlarm(int id, Boolean vibrate, int hour, int minute ,String ringtone,int snooze,  List<Integer> repeat ){
        alarmController.editAlarm(getApplicationContext(),id,vibrate,hour,minute,ringtone,snooze,repeat);
        alarmController.saveAlarms(getApplicationContext());
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

    /**
     *
     * @param newDays
     */
    public void setRepeatDays(List<Integer> newDays){
        repeatDays = newDays;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    //Return from ringtone dialog
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    String name = RingtoneManager.getRingtone(getApplicationContext(),uri).getTitle(getApplicationContext());
                    setRingtone(String.valueOf(uri),name);
                    break;
                case 2:
                    //Return from Music

                    break;
                default:
                    break;
            }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        try {
            snoozeLength = Integer.valueOf(String.valueOf(parent.getItemAtPosition(pos)));
        }catch(NumberFormatException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        snoozeLength = 5;
    }


    public void setRepeatText(List<Integer> repeatDays){
        //Set the repeat_btn view to reflect the days selected

        String text = UtilityFunctions.generateRepeatText(repeatDays);
        if(text !=null){
            repeatButton.setText("Repeat: "+text);
        } else {
            repeatButton.setText("Repeat");
        }
    }

}
