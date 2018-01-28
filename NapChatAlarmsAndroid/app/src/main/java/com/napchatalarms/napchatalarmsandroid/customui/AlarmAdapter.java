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
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.services.AlarmController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author bbest
 */

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    Context context;
    TextView alarmId;
    public AlarmAdapter(Context context, ArrayList<Alarm> alarmList){
        super(context, R.layout.alarm_layout, alarmList);
        this.context =context;
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

        //Set hidden id reference
        alarmId = (TextView)convertView.findViewById(R.id.hidden_alarm_id);

        alarmId.setText(String.valueOf(alarm.getId()));

        //Set the status of the alarm
        Switch statusSwitch = (Switch) convertView.findViewById(R.id.activate_alarm_switch);
        statusSwitch.setChecked(alarm.getStatus());

        statusSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    //Activate alarm
                    AlarmController.getInstance().activateAlarm(context,Integer.parseInt(alarmId.getText().toString()));
                } else{
                    //Deactivate alarm
                    AlarmController.getInstance().cancelAlarm(context,Integer.parseInt(alarmId.getText().toString()));
                }
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        return convertView;
    }



}
