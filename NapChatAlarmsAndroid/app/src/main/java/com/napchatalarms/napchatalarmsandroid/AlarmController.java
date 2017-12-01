package com.napchatalarms.napchatalarmsandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Controller singleton that uses the AlarmManager to schedule, cancel and snooze alarms.
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
    public void scheduleOneTimeAlarm(SingleAlarm singleAlarm, Context context){
        singleAlarm.Activate();

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(singleAlarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(singleAlarm.getTime()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Provide Settings
        intent.putExtra("Vibrate", singleAlarm.getVibrateOn());
        intent.putExtra("Id", singleAlarm.getId());
        intent.putExtra("Snooze", singleAlarm.getSnoozeLength());
        intent.putExtra("Time",timeString);
        intent.putExtra("Meridian",meridianString);
        intent.putExtra("Uri", singleAlarm.getRingtoneURI());
        //intent.putExtra("Interval", 0);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, singleAlarm.getTime(),pendingIntent);

        Toast.makeText(context,"Alarm Created!",Toast.LENGTH_SHORT).show();
    }

    public void scheduleRepeatingAlarm(){

    }

    public void cancelOneTimeAlarm(Context context, int ID){
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.Cancel(context,ID);
    }

    public void cancelAndDeactivateOneTime(Context context, int ID){
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.Cancel(context,ID);

        //TODO:Get alarm by ID and deactivate.
    }

    //TODO: implement methods
    public void getAlarmById(int Id){
        User user = User.getInstance();
    }

    public void cancelRepeating(Context context, int ID){}

    public void cancelAndDeactivateRepeating(Context context,int ID){}

    public void addSingleAlarm(SingleAlarm SingleAlarm){
        User user = User.getInstance();
        user.addAlarm(SingleAlarm);
    }

    public void deleteAlarm(int Id){
        User user = User.getInstance();
    }

    public void updateAlarm(){
        User user = User.getInstance();
    }

    public void saveAlarm(){}

    public void snoozeAlarm(Context context,int ID, boolean vibrate,int snooze, String ringtone){

        long currentTime = System.currentTimeMillis();
        long newTriggerTime = currentTime + snooze * 1000;

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(newTriggerTime));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(newTriggerTime));


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra("Vibrate",vibrate);
        intent.putExtra("Id",ID);
        intent.putExtra("Snooze",snooze);
        intent.putExtra("Time",timeString);
        intent.putExtra("Meridian",meridianString);
        intent.putExtra("Uri", ringtone);

        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, newTriggerTime, pendingIntent);
    }
    //TODO:implement
    public void addRepeatingAlarm(){}

    public void deleteRepeatingAlarm(){}

    public void updateRepeatingAlarm(){}

    public void saveRepeatingAlarm(){}

    //May not need this at all
    public void snoozeRepeatingAlarm(){}
}
