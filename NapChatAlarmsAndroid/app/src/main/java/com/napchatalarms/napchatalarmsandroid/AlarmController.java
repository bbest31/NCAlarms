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

    /**Delete an alarm based on its Id, this method handles both OneTime and Repeating Alarms.
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
    }

    /***/
    public void cancelAlarm(Context context, int Id){
        Alarm alarm = this.getAlarmById(Id);
        if(alarm.getClass() == OneTimeAlarm.class){

            cancelOneTimeAlarm(context,Id);
        }else{
            cancelRepeatingAlarm(context,Id);
        }
    }

    /***/
    public void cancelAndDeactivate(Context context, int Id){
        Alarm alarm = this.getAlarmById(Id);
        if(alarm.getClass() == OneTimeAlarm.class){

            cancelAndDeactivateOneTime(context,Id);
        }else{
            cancelAndDeactivateRepeating(context,Id);
        }
    }

    /***/
    public void updateAlarm(Context context,Alarm alarm){

        if(alarm.getClass() == OneTimeAlarm.class){
            updateOneTimeAlarm(context,(OneTimeAlarm)alarm);
        }else{
            updateRepeatingAlarm(context,(RepeatingAlarm)alarm);
        }

    }




    //==ONETIME METHODS==

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
        //intent.putExtra("Interval", 0);

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


    public void cancelOneTimeAlarm(Context context, int ID){
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.Cancel(context,ID);
    }


    public void cancelAndDeactivateOneTime(Context context, int Id){
        AlarmReceiver alarmReceiver = new AlarmReceiver();
        alarmReceiver.Cancel(context,Id);

        Alarm alarm = User.getInstance().getAlarmById(Id);
        alarm.Deactivate();
    }

    public void updateOneTimeAlarm(Context context,OneTimeAlarm alarm){
        deleteOneTime(context,alarm.getId());
        this.addAlarm(alarm);
    }

    //TODO:May override the repeat if we snooze for a repeating alarm.
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

    //TODO: implement methods below

    //==REPEATING METHODS==
    public void scheduleRepeatingAlarm(Context context, RepeatingAlarm alarm){

    }

    public void cancelRepeatingAlarm(Context context, int ID){}


    public void cancelAndDeactivateRepeating(Context context,int ID){}

    public void deleteRepeating(Context context,int Id){
        User user = User.getInstance();
        //int[] Ids = ((RepeatingAlarm)alarm).getSubIds();
        //TODO:write a for loop that will use the alarm receiver to delete each sub-alarm by id.
        //...
        user.deleteAlarm(Id);
    }

    public void updateRepeatingAlarm(Context context, RepeatingAlarm alarm){}

    public void changeOneTimeToRepeat(){}

    public void changeRepeatingToOneTime(){}

    //May not need this at all
    public void snoozeRepeatingAlarm(){}

}
