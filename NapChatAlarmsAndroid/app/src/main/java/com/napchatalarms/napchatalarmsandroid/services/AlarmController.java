package com.napchatalarms.napchatalarmsandroid.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.utility.AlarmReceiver;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Controller singleton that uses the AlarmManager to schedule, cancel and snooze alarms.
 * @author bbest
 */

public class AlarmController {

    private static final AlarmController ourInstance = new AlarmController();

    private final AlarmReceiver alarmReceiver;

    /**
     *
     * @return
     */
    public static AlarmController getInstance() {

        return ourInstance;
    }

    /**
     *
     */
    private AlarmController() {
        this.alarmReceiver = new AlarmReceiver();
    }

    //=====METHODS=====
    public void createAlarm(Context context, Alarm alarm){
        alarm.Activate();
        addAlarmToUser(alarm,context);
        scheduleAlarm(context,alarm);
    }
    /**
     *
     * @param context
     */
    public void saveAlarms(Context context){
        try {
            NapChatController.getInstance().saveUserAlarms(context);
        } catch(IOException e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    //==HIGH-LVL==

    /**Adds the created alarm to the User list in order to be saved.
     * */
    public void addAlarmToUser(Alarm alarm, Context context){
        User user = User.getInstance();
        user.addAlarm(alarm);
        saveAlarms(context);
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

    /**
     *
     * @param context
     * @param Id
     */
    public void activateAlarm(Context context,int Id){
        Alarm alarm = User.getInstance().getAlarmById(Id);
        scheduleAlarm(context,alarm);
        alarm.Activate();
        saveAlarms(context);
    }

    /**Delete an alarm based on its Id and de-schedule it, this method handles both OneTime and Repeating Alarms.
     * */
    public void deleteAlarm(Context context,int id){

        Alarm alarm = User.getInstance().getAlarmById(id);

        //Alarm is currently Active
        if(alarm.getStatus()){
           cancelAlarm(context,id);
        } else {

        }

        User.getInstance().deleteAlarm(id);
        saveAlarms(context);
    }

    /**De-schedule and alarm and set its Active status to False.
     * */
    public void cancelAlarm(Context context, int id){
        Alarm alarm = User.getInstance().getAlarmById(id);
        if(alarm.getClass() == OneTimeAlarm.class){

            cancelOneTime(context,(OneTimeAlarm)alarm);
        }else{
            cancelRepeating(context,(RepeatingAlarm)alarm);
        }
        alarm.Deactivate();
    }

    /**Update the attributes of an alarm.
     * */
    public void editAlarm(Context context, Alarm alarm){

        if(alarm.getClass() == OneTimeAlarm.class){
            editOneTime(context,(OneTimeAlarm)alarm);
        }else{
            editRepeatingAlarm(context,(RepeatingAlarm)alarm);
        }
        saveAlarms(context);
    }

    /**Stop the current sounding alarm from firing.
     * */
    public void dismissAlarm(Context context, int Id){
        Alarm alarm = User.getInstance().getAlarmById(Id);

        if(alarm.getClass() == OneTimeAlarm.class){
            dismissOneTime(context,Id);
            saveAlarms(context);
        }else{
            dismissRepeatingAlarm(context,Id);
        }
        alarm.Deactivate();
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

    /**Schedule a one-time-alarm with the system.
     * */
    public void scheduleOneTimeAlarm(Context context, OneTimeAlarm oneTimeAlarm){

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

        Log.d("AlarmController","Intent made and alarm about to be scheduled with time "+oneTimeAlarm.getTime());
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneTimeAlarm.getTime(),pendingIntent);

        Toast.makeText(context,"Alarm Created!",Toast.LENGTH_LONG).show();
    }

    /**Dismiss the alarm and set its Active status to False.
     * */
    public void dismissOneTime(Context context, int Id){

        alarmReceiver.Cancel(context,Id);
    }

    /**This method is used when an active one-time-alarm is set to de-active
     * from the Home Activity and we must cancel the pending alarm.
     * */
    public void cancelOneTime(Context context,OneTimeAlarm alarm){

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.cancel(oneTimePendingIntent(context,alarm));
    }

    /**Removes the old version of the alarm from the User list and the current scheduling.
     * Then we add the new version to the User list.
     * */
    public void editOneTime(Context context, OneTimeAlarm alarm){
        deleteAlarm(context,alarm.getId());
        this.addAlarmToUser(alarm,context);
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

    /**
     *
     * @param context
     * @param ID
     * @param vibrate
     * @param snooze
     * @param ringtone
     */
    public void snoozeAlarm(Context context,int ID, boolean vibrate,int snooze, String ringtone){

        long currentTime = System.currentTimeMillis();
        long newTriggerTime = currentTime + snooze * 60000;

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
    /**
     * */
    public void scheduleRepeatingAlarm(Context context, RepeatingAlarm alarm){

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


    /**De-schedules the sub-Alarms
     * */
    public void cancelRepeating(Context context, RepeatingAlarm alarm){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        for (Map.Entry<Integer,Alarm> entry : alarm.getSubList().entrySet()) {
            alarmManager.cancel(AlarmPendingIntent(context,entry.getValue()));
        }


    }

    /**Takes in a new instance of the repeating alarm with the same Id. We delete
     * the old version from the User list and de-schedule it before we add the new version
     * to the User list.
     * */
    public void editRepeatingAlarm(Context context, RepeatingAlarm alarm){
        deleteAlarm(context,alarm.getId());
        this.addAlarmToUser(alarm,context);
    }

}
