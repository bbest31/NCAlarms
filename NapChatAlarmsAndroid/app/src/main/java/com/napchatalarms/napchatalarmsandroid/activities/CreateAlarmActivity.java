package com.napchatalarms.napchatalarmsandroid.activities;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.dialog.DnDOverrideDialog;
import com.napchatalarms.napchatalarmsandroid.dialog.RepeatDaysDialog;
import com.napchatalarms.napchatalarmsandroid.dialog.RingtoneDialog;
import com.napchatalarms.napchatalarmsandroid.dialog.VibrateDialog;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.services.OneTimeBuilder;
import com.napchatalarms.napchatalarmsandroid.services.RepeatingBuilder;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Activity used to create new alarms.
 *
 * @author bbest
 */
public class CreateAlarmActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    final int EXTERNAL_STORAGE_REQUEST = 42;
    final int CUSTOM_RINGTONE_RESULT_CODE = 80;
    final int DEVICE_RINGTONE_RESULT_CODE = 14;
    final int MUSIC_RINGTONE_RESULT_CODE = 19;
    /**
     * The Time picker.
     */
//=====ATTRIBUTES=====
    TimePicker timePicker;
    /**
     * The Vibrate switch.
     */
    Button vibrateBtn;
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
     * Vibrate Pattern
     */
    Integer vibratePattern;
    /**
     * The Repeat days.
     */
    List<Integer> repeatDays;
    /**
     * The Snooze length.
     */
    Integer snoozeLength;
    Boolean readPermission;

    /**
     * Declaration of view references.
     */
    private void initialize() {
        alarmController = AlarmController.getInstance();

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        vibrateBtn = (Button) findViewById(R.id.vibrate_btn);
        ringtoneButton = (Button) findViewById(R.id.ringtone_btn);
        repeatButton = (Button) findViewById(R.id.repeat_btn);

        snoozeSelector = (Spinner) findViewById(R.id.snooze_spinner);
        ArrayAdapter<CharSequence> snoozeAdapter = ArrayAdapter.createFromResource(this
                , R.array.snooze_array
                , R.layout.support_simple_spinner_dropdown_item);
        snoozeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        snoozeSelector.setAdapter(snoozeAdapter);
        snoozeSelector.setOnItemSelectedListener(this);

        //Get id indicator to see if this is a brand new alarm or if we are editing one.
        Intent intent = this.getIntent();
        int id = intent.getIntExtra("ID", 0);

        //If we are getting an alarm Id we grab the alarm and populate the views with its attributes.
        if (id != 0) {
            final Alarm alarm = User.getInstance().getAlarmById(id);
            editAlarmButton = (Button) findViewById(R.id.edit_alarm_btn);
            editAlarmButton.setVisibility(View.VISIBLE);

            //Set TimePicker time.
            final Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(alarm.getTime());
            timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
            timePicker.setMinute(calendar.get(Calendar.MINUTE));

            //Set ringtone name
            Uri uri = Uri.parse(alarm.getRingtoneURI());
            if (uri.toString().equals(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM).toString())) {
                ringtoneButton.setText("Ringtone: Default");
            } else {
                String uriName = RingtoneManager.getRingtone(getApplicationContext(), uri).getTitle(getApplicationContext());
                ringtoneButton.setText("Ringtone: " + uriName);
            }
            ringtone = alarm.getRingtoneURI();

            //Set vibrate
            switch (alarm.getVibratePattern()) {
                case -1:

                    vibrateBtn.setText("Vibrate: Off");
                    break;
                case 0:

                    vibrateBtn.setText("Vibrate: Heartbeat");
                    break;
                case 1:

                    vibrateBtn.setText("Vibrate: Buzzsaw");
                    break;
                case 2:

                    vibrateBtn.setText("Vibrate: Locomotive");
                    break;
                case 3:

                    vibrateBtn.setText("Vibrate: Tip-toe");
                    break;

            }

            // set snooze
            int pos = snoozeAdapter.getPosition(String.valueOf(alarm.getSnoozeLength()));
            snoozeSelector.setSelection(pos);
            snoozeLength = Integer.valueOf(snoozeSelector.getSelectedItem().toString());

            //Set repeat day selection
            if (alarm.getClass() == RepeatingAlarm.class) {

                RepeatingAlarm repeatingAlarm = (RepeatingAlarm) alarm;
                repeatDays = repeatingAlarm.getRepeatDays();
                setRepeatText(repeatDays);

            } else {
                repeatDays = new ArrayList<>();
            }

            editAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    editAlarm(alarm.getId(), vibratePattern, timePicker.getHour(), timePicker.getMinute(), ringtone, snoozeLength, repeatDays);
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            });

        } else {
            createAlarmButton = (Button) findViewById(R.id.create_alarm_btn);
            createAlarmButton.setVisibility(View.VISIBLE);

            ringtoneButton.setText("Ringtone: Default");
            ringtone = String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));


            vibratePattern = -1;
            vibrateBtn.setText("Vibrate: Off");

            repeatDays = new ArrayList<>();

            createAlarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    createAlarm();
                    Intent returnIntent = new Intent();
                    setResult(Activity.RESULT_OK, returnIntent);
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
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            readPermission = true;
        } else {
            readPermission = false;
        }

        //=====ONCLICK METHODS=====

        ringtoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RingtoneDialog ringtoneDialog = new RingtoneDialog(CreateAlarmActivity.this, readPermission);
                ringtoneDialog.show();

            }
        });

        vibrateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VibrateDialog vibrateDialog = new VibrateDialog(CreateAlarmActivity.this);
                vibrateDialog.show();
            }
        });

        repeatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RepeatDaysDialog repeatDaysDialog = new RepeatDaysDialog(CreateAlarmActivity.this, repeatDays);
                repeatDaysDialog.show();
            }
        });

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Log.e("DND PERMISSION", String.valueOf(mNotificationManager.isNotificationPolicyAccessGranted()));
        if (!mNotificationManager.isNotificationPolicyAccessGranted()) {
            DnDOverrideDialog overrideDialog = new DnDOverrideDialog(CreateAlarmActivity.this);
            overrideDialog.show();
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Explain to the user why we need to read the contacts
            // Should we show an explanation?
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

            }

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQUEST);

            // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
            // app-defined int constant that should be quite unique

            return;
        }

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
    public void createAlarm() {
        if (repeatDays.size() == 0) {
            OneTimeBuilder builder = new OneTimeBuilder();
            Long trigger = UtilityFunctions.UTCMilliseconds(timePicker.getHour(), timePicker.getMinute());
            builder.setTime(trigger)
                    .setVibrate(vibratePattern)
                    .setRingtoneURI(ringtone)
                    .setSnooze(snoozeLength);

            OneTimeAlarm alarm = builder.build();

            alarmController.createAlarm(getApplicationContext(), alarm);
            alarmController.scheduleAlarm(getApplicationContext(), alarm);

        } else {
            //build repeating alarm
            RepeatingBuilder builder = new RepeatingBuilder();
            Long trigger = UtilityFunctions.UTCMilliseconds(timePicker.getHour(), timePicker.getMinute());
            builder.initialize(repeatDays)
                    .setTime(trigger)
                    .setVibrate(vibratePattern)
                    .setRingtoneURI(ringtone)
                    .setSnooze(snoozeLength)
                    .setInterval();

            RepeatingAlarm alarm = builder.build();

            alarmController.createAlarm(getApplicationContext(), alarm);
            alarmController.scheduleAlarm(getApplicationContext(), alarm);

        }
        alarmController.saveAlarms(getApplicationContext());

    }

    public void editAlarm(int id, Integer vibrate, int hour, int minute, String ringtone, int snooze, List<Integer> repeat) {
        alarmController.editAlarm(getApplicationContext(), id, vibrate, hour, minute, ringtone, snooze, repeat);
        alarmController.saveAlarms(getApplicationContext());
    }

    /**
     * Set ringtone.
     *
     * @param uri  the uri
     * @param name the name
     */
    public void setRingtone(String uri, String name) {
        ringtone = uri;
        ringtoneButton.setText("Ringtone: " + name);
    }

    /**
     * @param newDays
     */
    public void setRepeatDays(List<Integer> newDays) {
        repeatDays = newDays;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case DEVICE_RINGTONE_RESULT_CODE:
                    //Return from ringtone dialog
                    Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                    String name = RingtoneManager.getRingtone(getApplicationContext(), uri).getTitle(getApplicationContext());
                    setRingtone(String.valueOf(uri), name);
                    break;
                case MUSIC_RINGTONE_RESULT_CODE:
                    //Return from Music
                    Uri musicUri = data.getData();
                    String title = "Music";

                    //get the song title
                    ContentResolver cr = this.getContentResolver();
                    String[] projection = {MediaStore.Audio.Media.TITLE};
                    Cursor cursor = cr.query(musicUri, projection, null, null, null);

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
                            title = cursor.getString(titleIndex);
                        }
                    }

                    setRingtone(String.valueOf(musicUri), title);
                    break;
                case CUSTOM_RINGTONE_RESULT_CODE:
                    String customUri = data.getStringExtra("URI");
                    String trackName = data.getStringExtra("NAME");
                    setRingtone(customUri,trackName);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        switch (resultCode) {
            case RESULT_OK:
                int pattern = data.getIntExtra("PATTERN", -1);
                vibratePattern = pattern;
                if (pattern == -1) {
                    vibrateBtn.setText("Vibrate: Off");
                } else {
                    vibrateBtn.setText("Vibrate: " + UtilityFunctions.getVibratePattern(pattern).getName());
                }
                break;

        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        try {
            snoozeLength = Integer.valueOf(String.valueOf(parent.getItemAtPosition(pos)));
        } catch (NumberFormatException e) {
            Log.e("CreateAlarmActivity", e.getMessage());
            e.printStackTrace();
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        snoozeLength = 5;
    }


    public void setRepeatText(List<Integer> repeatDays) {
        //Set the repeat_btn view to reflect the days selected

        String text = UtilityFunctions.generateRepeatText(repeatDays);
        if (text != null) {
            repeatButton.setText("Repeat: " + text);
        } else {
            repeatButton.setText("Repeat");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case EXTERNAL_STORAGE_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay!
                    readPermission = true;
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    readPermission = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }


}
