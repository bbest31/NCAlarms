package com.napchatalarms.napchatalarmsandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

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

            deleteOneTime(context,Id);

        } else {

            //Delete if Repeating type
            deleteRepeating(context,Id);
        }
        saveAlarms();
    }

    /**De-schedule and alarm and set its Active status to False.
     * */
    public void cancelAndDeactivate(Context context, int Id){
        Alarm alarm = this.getAlarmById(Id);
        if(alarm.getClass() == OneTimeAlarm.class){

            cancelAndDeactivateOneTime(context,Id);
        }else{
            cancelAndDeactivateRepeating(context,Id);
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
            cancelAndDeactivateOneTime(context,Id);
        }else{
            cancelRepeatingAlarm(context,Id);
        }
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


        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneTimeAlarm.getTime(),pendingIntent);

        Toast.makeText(context,"Alarm Created!",Toast.LENGTH_SHORT).show();
    }

    /**Delete one-time alarm from user list and the current scheduling.
     * */
    public void deleteOneTime(Context context, int Id){
        User user = User.getInstance();
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.Cancel(context,Id);
        user.deleteAlarm(Id);
    }

    /**De-schedule the alarm and set its Active status to False.
     * */
    public void cancelAndDeactivateOneTime(Context context, int Id){
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.Cancel(context,Id);

        Alarm alarm = User.getInstance().getAlarmById(Id);
        alarm.Deactivate();
    }

    /**Removes the old version of the alarm from the User list and the current scheduling.
     * Then we add the new version to the User list.
     * */
    public void updateOneTimeAlarm(Context context,OneTimeAlarm alarm){
        deleteAlarm(context,alarm.getId());
        this.addAlarm(alarm);
    }
    //TODO: May need one for repeating alarms as well.
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
    //TODO: implement some methods below
    /***/
    public void scheduleRepeatingAlarm(Context context, RepeatingAlarm alarm){

    }
    /**Stop the current sounding sub-Alarm from firing.
     * */
    public void cancelRepeatingAlarm(Context context, int id){}

    /**De-schedules the sub-Alarms and sets the status of Active
     *  to False for the RepeatingAlarm.isActive attribute.
     * */
    public void cancelAndDeactivateRepeating(Context context,int id){}

    /**Uses the Id's of all the sub-Alarms to cancels their scheduling with the AlarmReceiver.
     * Then removes the RepeatingAlarm from the User list.
     * */
    public void deleteRepeating(Context context,int Id){
        User user = User.getInstance();
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        Alarm alarm = user.getAlarmById(Id);
        alarm = (RepeatingAlarm)alarm;

        Set<Integer> IDs = ((RepeatingAlarm) alarm).getSubList().keySet();
        for (int id : IDs) {
            alarmReceiver.Cancel(context,id);
        }

        user.deleteAlarm(Id);
    }
    /**Takes in a new instance of the repeating alarm with the same Id. We delete
     * the old version from the User list and de-schedule it before we add the new version
     * to the User list.
     * */
    public void updateRepeatingAlarm(Context context, RepeatingAlarm alarm){
        deleteAlarm(context,alarm.getId());
        this.addAlarm(alarm);
    }

    //May not need this at all
    public void snoozeRepeatingAlarm(){}

}
