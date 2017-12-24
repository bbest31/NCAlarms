package com.napchatalarms.napchatalarmsandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Controller singleton that uses the AlarmManager to schedule, cancel and snooze alarms.
 * Created by bbest on 30/11/17.
 */

public class AlarmController {

    private static final AlarmController ourInstance = new AlarmController();

    private final AlarmReceiver alarmReceiver;

    public static AlarmController getInstance() {

        return ourInstance;
    }

    private AlarmController() {
        this.alarmReceiver = new AlarmReceiver();
    }

    //=====METHODS=====

    //TODO:save User alarm list to internal file storage.
    public void saveAlarms(){}
    //==HIGH-LVL==

    public Alarm getAlarmById(int Id){

        User user = User.getInstance();
        return user.getAlarmById(Id);
    }

    /**Adds the created alarm to the User list in order to be saved.
     * */
    public void addAlarm(Alarm alarm){
        User user = User.getInstance();
        user.addAlarm(alarm);
        saveAlarms();
    }

    /**Schedules an alarm to fire at its programmed time.
     * */
    public void scheduleAlarm(Context context,Alarm alarm){
        if(alarm.getClass() == OneTimeAlarm.class){
            Log.d("Schedule Alarm","Identified alarm as OneTimeAlarm.");
            scheduleOneTimeAlarm(context,(OneTimeAlarm) alarm);
        }else{
            scheduleRepeatingAlarm(context,(RepeatingAlarm)alarm);
        }
    }

    /**Delete an alarm based on its Id and de-schedule it, this method handles both OneTime and Repeating Alarms.
     * */
    public void deleteAlarm(Context context,int Id){
        Alarm alarm = this.getAlarmById(Id);

        //Delete based on OneTimeAlarm class
        if(alarm.getClass() == OneTimeAlarm.class){

            deleteOneTime(context,(OneTimeAlarm)alarm);

        } else {
            //Delete if Repeating type
            deleteRepeating(context,(RepeatingAlarm)alarm);
        }
        saveAlarms();
    }

    /**De-schedule and alarm and set its Active status to False.
     * */
    public void cancelAndDeactivate(Context context, int Id){
        Alarm alarm = this.getAlarmById(Id);
        if(alarm.getClass() == OneTimeAlarm.class){

            cancelOneTime(context,(OneTimeAlarm)alarm);
        }else{
            cancelAndDeactivateRepeating(context,(RepeatingAlarm)alarm);
        }
    }

    /**Update the attributes of an alarm.
     * */
    public void updateAlarm(Context context,Alarm alarm){

        if(alarm.getClass() == OneTimeAlarm.class){
            updateOneTimeAlarm(context,(OneTimeAlarm)alarm);
        }else{
            updateRepeatingAlarm(context,(RepeatingAlarm)alarm);
        }
        saveAlarms();
    }

    /**Stop the current sounding alarm from firing.
     * */
    public void dismissAlarm(Context context, int Id){
        Alarm alarm = this.getAlarmById(Id);

        if(alarm.getClass() == OneTimeAlarm.class){
            dismissOneTime(context,Id);
        }else{
            dismissRepeatingAlarm(context,Id);
        }
    }

    public PendingIntent AlarmPendingIntent(Context context,Alarm alarm){
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        //Provide Settings
        intent.putExtra("Vibrate", alarm.getVibrateOn());
        intent.putExtra("Id", alarm.getId());
        intent.putExtra("Snooze", alarm.getSnoozeLength());
        intent.putExtra("Time",timeString);
        intent.putExtra("Meridian",meridianString);
        intent.putExtra("Uri", alarm.getRingtoneURI());


        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }
    //==ONETIME METHODS==

    /**Schedule a one-time-alarm and set its status to Active.
     * */
    public void scheduleOneTimeAlarm(Context context, OneTimeAlarm oneTimeAlarm){
        oneTimeAlarm.Activate();

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(oneTimeAlarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(oneTimeAlarm.getTime()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Provide Settings
        intent.putExtra("Vibrate", oneTimeAlarm.getVibrateOn());
        intent.putExtra("Id", oneTimeAlarm.getId());
        intent.putExtra("Snooze", oneTimeAlarm.getSnoozeLength());
        intent.putExtra("Time",timeString);
        intent.putExtra("Meridian",meridianString);
        intent.putExtra("Uri", oneTimeAlarm.getRingtoneURI());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneTimeAlarm.getTime(),pendingIntent);

        Toast.makeText(context,"Alarm Created!",Toast.LENGTH_SHORT).show();
    }

    /**Delete one-time alarm from user list and the current scheduling.
     * */
    public void deleteOneTime(Context context, OneTimeAlarm alarm){
        User user = User.getInstance();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(oneTimePendingIntent(context,alarm));
        user.deleteAlarm(alarm.getId());
    }

    /**Dismiss the alarm and set its Active status to False.
     * */
    public void dismissOneTime(Context context, int Id){

        alarmReceiver.Cancel(context,Id);

        Alarm alarm = User.getInstance().getAlarmById(Id);
        alarm.Deactivate();
    }

    /**This method is used when an active one-time-alarm is set to de-active
     * from the Home Activity and we must cancel the pending alarm.
     * */
    public void cancelOneTime(Context context,OneTimeAlarm alarm){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(oneTimePendingIntent(context,alarm));
        alarm.Deactivate();
    }

    /**Removes the old version of the alarm from the User list and the current scheduling.
     * Then we add the new version to the User list.
     * */
    public void updateOneTimeAlarm(Context context,OneTimeAlarm alarm){
        deleteAlarm(context,alarm.getId());
        this.addAlarm(alarm);
    }

    public PendingIntent oneTimePendingIntent(Context context,OneTimeAlarm alarm){
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        //Provide Settings
        intent.putExtra("Vibrate", alarm.getVibrateOn());
        intent.putExtra("Id", alarm.getId());
        intent.putExtra("Snooze", alarm.getSnoozeLength());
        intent.putExtra("Time",timeString);
        intent.putExtra("Meridian",meridianString);
        intent.putExtra("Uri", alarm.getRingtoneURI());


        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

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


    //==REPEATING METHODS==
    /***/
    public void scheduleRepeatingAlarm(Context context, RepeatingAlarm alarm){

        alarm.Activate();

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //For each sub-Alarm schedule it.
        for(Map.Entry<Integer,Alarm> entry : alarm.getSubList().entrySet()){

            Intent intent = new Intent(context, AlarmReceiver.class);

            //Provide Settings
            intent.putExtra("Vibrate", alarm.getVibrateOn());
            intent.putExtra("Id", entry.getValue().getId());
            intent.putExtra("Snooze", alarm.getSnoozeLength());
            intent.putExtra("Time",timeString);
            intent.putExtra("Meridian",meridianString);
            intent.putExtra("Uri", alarm.getRingtoneURI());


            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, entry.getValue().getTime(),entry.getValue().getInterval(),pendingIntent);
        }

        Toast.makeText(context,"Alarm Created!",Toast.LENGTH_SHORT).show();

    }

    /**Stop the current sounding sub-Alarm from firing.
     * */
    public void dismissRepeatingAlarm(Context context, int id){
        alarmReceiver.Cancel(context,id);
    }


    /**De-schedules the sub-Alarms and sets the status of Active
     *  to False for the RepeatingAlarm.isActive attribute.
     * */
    public void cancelAndDeactivateRepeating(Context context,RepeatingAlarm alarm){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        for (Map.Entry<Integer,Alarm> entry : alarm.getSubList().entrySet()) {
            alarmManager.cancel(AlarmPendingIntent(context,entry.getValue()));
        }

        alarm.Deactivate();
    }

    /**Uses the Id's of all the sub-Alarms to cancels their scheduling with the AlarmReceiver.
     * Then removes the RepeatingAlarm from the User list.
     * */
    public void deleteRepeating(Context context,RepeatingAlarm alarm){
        User user = User.getInstance();
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        for (Map.Entry<Integer,Alarm> entry : alarm.getSubList().entrySet()) {
            alarmManager.cancel(AlarmPendingIntent(context,entry.getValue()));
        }

        user.deleteAlarm(alarm.getId());
    }
    /**Takes in a new instance of the repeating alarm with the same Id. We delete
     * the old version from the User list and de-schedule it before we add the new version
     * to the User list.
     * */
    public void updateRepeatingAlarm(Context context, RepeatingAlarm alarm){
        deleteAlarm(context,alarm.getId());
        this.addAlarm(alarm);
    }

}
