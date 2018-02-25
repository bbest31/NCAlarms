package com.napchatalarms.napchatalarmsandroid.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author bbest
 */

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    Context context;
    TextView alarmId;
    TextView repeatDaysText;

    public AlarmAdapter(Context context, ArrayList<Alarm> alarmList) {
        super(context, R.layout.alarm_layout, alarmList);
        this.context = context;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        //Get the data item for this position
        Alarm alarm = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_layout, parent, false);
        }
        //sets the widgets to correspond to variables

        //Set hidden id reference
        alarmId = (TextView) convertView.findViewById(R.id.hidden_alarm_id);
        alarmId.setText(String.valueOf(alarm.getId()));

        //Set the time display string
        TextView timeText = (TextView) convertView.findViewById(R.id.time_display_text);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        timeText.setText(sdf.format(alarm.getTime()));

        //Set the days repeating on
        repeatDaysText = (TextView) convertView.findViewById(R.id.repeat_days_text);
        if (alarm.getClass() == RepeatingAlarm.class) {
            String repeatText = UtilityFunctions.generateRepeatText(((RepeatingAlarm) alarm).getRepeatDays());
            if (repeatText != null) {
                repeatDaysText.setText(repeatText);
                repeatDaysText.setVisibility(View.VISIBLE);
            }
        } else {
            repeatDaysText.setVisibility(View.GONE);
        }

        //Set the status of the alarm
        Switch statusSwitch = (Switch) convertView.findViewById(R.id.activate_alarm_switch);
        statusSwitch.setChecked(alarm.getStatus());

        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Alarm checkedAlarm = getItem(position);

                if (isChecked) {
                    //Activate alarm
                    AlarmController.getInstance().activateAlarm(context, checkedAlarm.getId());
                } else {
                    //Deactivate alarm
                    AlarmController.getInstance().cancelAlarm(context, checkedAlarm.getId());
                }
            }
        });

        return convertView;
    }

}

