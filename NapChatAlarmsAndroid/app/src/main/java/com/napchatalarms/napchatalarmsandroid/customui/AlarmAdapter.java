package com.napchatalarms.napchatalarmsandroid.customui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * @author bbest
 */

public class AlarmAdapter extends ArrayAdapter<Alarm> {

    public AlarmAdapter(Context context, ArrayList<Alarm> alarmList){
        super(context, R.layout.alarm_layout, alarmList);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Get the data item for this position
        Alarm alarm = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.alarm_layout, parent, false);
        }
        //sets the widgets to correspond to variables

        //Set the time display string
        TextView timeText = (TextView) convertView.findViewById(R.id.time_display_text);
        SimpleDateFormat  sdf = new SimpleDateFormat("hh:mm a");
        timeText.setText(sdf.format(alarm.getTime()));

        //Set the status of the alarm
        Switch statusSwitch = (Switch) convertView.findViewById(R.id.activate_alarm_switch);
        statusSwitch.setChecked(alarm.getStatus());


        return convertView;
    }

}
