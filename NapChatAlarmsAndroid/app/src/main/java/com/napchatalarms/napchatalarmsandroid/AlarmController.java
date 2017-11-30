package com.napchatalarms.napchatalarmsandroid;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.time.LocalDateTime;

/**
 * Created by bbest on 30/11/17.
 */

public class AlarmController {

    private static final AlarmController ourInstance = new AlarmController();

    public static AlarmController getInstance() {

        return ourInstance;
    }

    private AlarmController() {

    }

    //=====METHODS=====
    public void scheduleAlarm(Alarm alarm, Context context){
        alarm.Activate();

        //TODO: store trigger time as a string to display to the AlarmActivty


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Provide Settings
        intent.putExtra("Vibrate",alarm.getVibrateOn());
        intent.putExtra("Id",alarm.getId());
        intent.putExtra("Snooze",alarm.getSnoozeLength());
        //intent.putExtra("Time",time[0]);
        //intent.putExtra("Meridian",time[1]);
        intent.putExtra("Uri",alarm.getRingtoneURI());

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,alarm.getTime(),pendingIntent);

        Toast.makeText(context,"Alarm Created!",Toast.LENGTH_SHORT).show();
    }

    public void scheduleRepeatingAlarm(){

    }

    public void cancelAlarm(Context context,int ID){
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.Cancel(context,ID);
    }

    public void snoozeAlarm(Context context,int ID, boolean vibrate,int snooze, String ringtone){

        //TODO:add the desired minutes to the current time

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra("Vibrate",vibrate);
        intent.putExtra("Id",ID);
        intent.putExtra("Snooze",snooze);
        //intent.putExtra("Time",time[0]);
        //intent.putExtra("Meridian",time[1]);
        intent.putExtra("Uri", ringtone);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, newTriggerTime, pendingIntent);
    }
}
