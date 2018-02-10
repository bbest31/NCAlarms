package com.napchatalarms.napchatalarmsandroid.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.services.OneTimeBuilder;
import com.napchatalarms.napchatalarmsandroid.services.RepeatingBuilder;
import com.napchatalarms.napchatalarmsandroid.utility.AlarmReceiver;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        }else if(alarm.getClass() == RepeatingAlarm.class){
            scheduleRepeatingAlarm(context,(RepeatingAlarm)alarm);
        } else {
            rescheduleSubAlarm(context,alarm);
        }
    }

    /**
     *
     * @param context
     * @param id
     */
    public void activateAlarm(Context context,int id){
        validateTriggerTime(id);
        Alarm alarm = User.getInstance().getAlarmById(id);
        scheduleAlarm(context,alarm);
        alarm.Activate();
        saveAlarms(context);
    }

    /**
     * When an alarm is activated from the home screen we need to
     * adjust the alarms trigger time to ensure its current one isn't a time in the past.
     * @param id
     */
    public void validateTriggerTime(int id){
        Alarm alarm = User.getInstance().getAlarmById(id);
        if(alarm.getClass() == OneTimeAlarm.class){

            Long newTrigger = UtilityFunctions.validateOneTimeTrigger(alarm.getTime());
            alarm.setTime(newTrigger);

        } else if (alarm.getClass() == RepeatingAlarm.class) {

            for (Map.Entry<Integer, Alarm> entry : ((RepeatingAlarm)alarm).getSubAlarms().entrySet())
            {
                Long newTrigger = UtilityFunctions.validateRepeatTrigger(entry.getValue().getTime());
                entry.getValue().setTime(newTrigger);
            }


        }
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
        saveAlarms(context);
    }

    /**Update the attributes of an alarm.
     * */
    public void editAlarm(Context context, int id, Boolean vibrate,int hour, int min, String ringtone, int snooze, List<Integer> repeatDays){
        Alarm alarm = User.getInstance().getAlarmById(id);

        if(alarm.getClass() == OneTimeAlarm.class && repeatDays.size() != 0){
            //onetime to repeating alarm conversion
            RepeatingBuilder builder = new RepeatingBuilder();
            builder.initialize(repeatDays)
            .setVibrate(vibrate)
            .setRingtoneURI(ringtone)
            .setSnooze(snooze)
            .setInterval();
            Long trig = UtilityFunctions.UTCMilliseconds(hour,min);
            builder.setTime(trig);

            RepeatingAlarm newAlarm = builder.build();
            deleteAlarm(context,id);
            createAlarm(context,newAlarm);


        } else if(alarm.getClass()==RepeatingAlarm.class && repeatDays.size() == 0){
            //Change repeating alarm to onetime
            OneTimeBuilder builder = new OneTimeBuilder();
            builder.setRingtoneURI(ringtone)
                    .setVibrate(vibrate)
                    .setSnooze(snooze);
            Long trig = UtilityFunctions.UTCMilliseconds(hour,min);
            builder.setTime(trig);

            OneTimeAlarm newAlarm = builder.build();
            deleteAlarm(context,id);
            createAlarm(context,newAlarm);

        } else  if(alarm.getClass() == OneTimeAlarm.class && repeatDays.size() == 0){
            //Onetime alarm staying the same type
            alarm.setRingtoneURI(ringtone);
            alarm.setSnoozeLength(snooze);
            alarm.setVibrate(vibrate);
            Long trig = UtilityFunctions.UTCMilliseconds(hour, min);
            alarm.setTime(trig);
            cancelAlarm(context, id);
            scheduleAlarm(context,alarm);

        } else{
            //repeating stays repeating
            RepeatingBuilder builder = new RepeatingBuilder();
            builder.initialize(repeatDays)
                    .setSnooze(snooze)
                    .setRingtoneURI(ringtone)
                    .setVibrate(vibrate)
                    .setInterval();
            Long trigger = UtilityFunctions.UTCMilliseconds(hour, min);
            builder.setTime(trigger);
            RepeatingAlarm newAlarm = builder.build();

            deleteAlarm(context,id);
            createAlarm(context,newAlarm);
        }

    }

    /**Stop the current sounding alarm from firing.
     * */
    public void dismissAlarm(Context context, int Id, int subId){
        Alarm alarm = User.getInstance().getAlarmById(Id);

        if(alarm.getClass() == OneTimeAlarm.class){
            dismissOneTime(context,Id);
            alarm.Deactivate();
            saveAlarms(context);
        }else{
            dismissRepeatingAlarm(context,Id,subId);
            saveAlarms(context);
        }
    }

    /**
     *
     * @param context
     * @param alarm
     * @return
     */
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
        pendingIntent = PendingIntent.getBroadcast(context,alarm.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

    /**
     *
     * @param context
     * @param alarm
     */
    public void rescheduleSubAlarm(Context context, Alarm alarm){
        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Provide Settings
        intent.putExtra("Vibrate", alarm.getVibrateOn());
        intent.putExtra("Id", alarm.getId());
        intent.putExtra("Snooze", alarm.getSnoozeLength());
        intent.putExtra("Time",timeString);
        intent.putExtra("Meridian",meridianString);
        intent.putExtra("Uri", alarm.getRingtoneURI());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,alarm.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);


        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getTime(),pendingIntent);
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

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,oneTimeAlarm.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);


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
        pendingIntent = PendingIntent.getBroadcast(context,alarm.getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);

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
    public void snoozeAlarm(Context context,int ID,int subId, boolean vibrate,int snooze, String ringtone){

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
        intent.putExtra("subID",subId);

        PendingIntent pendingIntent;
        if(subId == 0){
            pendingIntent = PendingIntent.getBroadcast(context,ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context,subId,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        }

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
        for(Map.Entry<Integer,Alarm> entry : alarm.getSubAlarms().entrySet()){

            Intent intent = new Intent(context, AlarmReceiver.class);

            //Provide Settings
            intent.putExtra("Vibrate", alarm.getVibrateOn());
            intent.putExtra("Id", alarm.getId());
            intent.putExtra("Snooze", alarm.getSnoozeLength());
            intent.putExtra("Time",timeString);
            intent.putExtra("Meridian",meridianString);
            intent.putExtra("Uri", entry.getValue().getRingtoneURI());
            intent.putExtra("subID",entry.getValue().getId());


            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.getBroadcast(context,entry.getValue().getId(),intent,PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, entry.getValue().getTime(),pendingIntent);
        }

        Toast.makeText(context,"Alarm Created!",Toast.LENGTH_SHORT).show();

    }

    /**Stop the current sounding sub-Alarm from firing.
     * */
    public void dismissRepeatingAlarm(Context context, int id, int subId){

        alarmReceiver.Cancel(context,subId);
        //reschedule for next week.
        Alarm alarm = User.getInstance().getAlarmById(id);
        Alarm subAlarm = ((RepeatingAlarm)alarm).getSubAlarmById(subId);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(subAlarm.getTime());
        int day = calendar.get(Calendar.DATE);
        calendar.add(Calendar.DATE,day+7);
        subAlarm.setTime(calendar.getTimeInMillis());
        AlarmController.getInstance().scheduleAlarm(context,alarm);
    }


    /**De-schedules the sub-Alarms
     * */
    public void cancelRepeating(Context context, RepeatingAlarm alarm){

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        for (Map.Entry<Integer,Alarm> entry : alarm.getSubAlarms().entrySet()) {
            alarmManager.cancel(AlarmPendingIntent(context,entry.getValue()));
        }


    }

}
