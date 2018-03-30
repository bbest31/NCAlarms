package com.napchatalarms.napchatalarmsandroid.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.services.OneTimeBuilder;
import com.napchatalarms.napchatalarmsandroid.services.RepeatingBuilder;
import com.napchatalarms.napchatalarmsandroid.utility.AlarmReceiver;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller singleton that uses the AlarmManager to schedule, cancel and snooze alarms.
 *
 * @author bbest
 */
public class AlarmController {

    private static final AlarmController ourInstance = new AlarmController();

    private final AlarmReceiver alarmReceiver;

    /**
     *
     */
    private AlarmController() {
        this.alarmReceiver = new AlarmReceiver();
    }

    /**
     * Gets instance.
     *
     * @return instance instance
     */
    public static AlarmController getInstance() {

        return ourInstance;
    }

    //=====METHODS=====

    /**
     * Sets alarm status to active
     * Adds to the user list and schedules alarm.
     *
     * @param context the context
     * @param alarm   the alarm
     */
    public void createAlarm(Context context, Alarm alarm) {
        alarm.Activate();
        addAlarmToUser(alarm, context);
//        Log.w("New Alarm ID", String.valueOf(alarm.getId()));
    }

    /**
     * Save alarms.
     *
     * @param context the context
     */
    public void saveAlarms(Context context) {
        try {
            NapChatController.getInstance().saveUserAlarms(context);
        } catch (Exception e) {
            //Log.e("ALRMCNTRL.saveAlarms", e.getMessage());
            e.printStackTrace();
        }
    }
    //==HIGH-LVL==

    /**
     * Adds the created alarm to the User list in order to be saved.
     *
     * @param alarm   the alarm
     * @param context the context
     */
    private void addAlarmToUser(Alarm alarm, Context context) {
        User user = User.getInstance();
        user.addAlarm(alarm);
        saveAlarms(context);
    }

    /**
     * Schedules an alarm to fire at its programmed time.
     *
     * @param context the context
     * @param alarm   the alarm
     */
    public void scheduleAlarm(Context context, Alarm alarm) {
        if (alarm.getClass() == OneTimeAlarm.class) {
            scheduleOneTimeAlarm(context, (OneTimeAlarm) alarm);
        } else if (alarm.getClass() == RepeatingAlarm.class) {
            scheduleRepeatingAlarm(context, (RepeatingAlarm) alarm);
        } else {
            rescheduleSubAlarm(context, alarm);
        }
    }

    /**
     * Activate alarm.
     *
     * @param context the context
     * @param id      the id
     */
    public void activateAlarm(Context context, int id) {
        validateTriggerTime(id);
        Alarm alarm = User.getInstance().getAlarmById(id);
        scheduleAlarm(context, alarm);
        alarm.Activate();
        saveAlarms(context);
    }

    /**
     * When an alarm is activated from the home screen we need to
     * adjust the alarms trigger time to ensure its current one isn't a time in the past.
     *
     * @param id the id
     */
    private void validateTriggerTime(int id) {
        Alarm alarm = User.getInstance().getAlarmById(id);
        if (alarm.getClass() == OneTimeAlarm.class) {

            Long newTrigger = UtilityFunctions.validateOneTimeTrigger(alarm.getTime());
            alarm.setTime(newTrigger);

        } else if (alarm.getClass() == RepeatingAlarm.class) {

            for (Map.Entry<Integer, Alarm> entry : ((RepeatingAlarm) alarm).getSubAlarms().entrySet()) {
                Long newTrigger = UtilityFunctions.validateRepeatTrigger(entry.getValue().getTime());
                entry.getValue().setTime(newTrigger);
            }


        }
    }

    /**
     * Delete an alarm based on its Id and de-schedule it, this method handles both OneTime and Repeating Alarms.
     *
     * @param context the context
     * @param id      the id
     */
    public void deleteAlarm(Context context, int id) {

        Alarm alarm = User.getInstance().getAlarmById(id);

        //Alarm is currently Active
        //noinspection StatementWithEmptyBody
        if (alarm.getStatus()) {
            cancelAlarm(context, id);
        } else {

        }
        User.getInstance().deleteAlarm(id);
        saveAlarms(context);
    }

    /**
     * De-schedule and alarm and set its Active status to False.
     *
     * @param context the context
     * @param id      the id
     */
    public void cancelAlarm(Context context, int id) {
        Alarm alarm = User.getInstance().getAlarmById(id);
        if (alarm.getClass() == OneTimeAlarm.class) {

            cancelOneTime(context, (OneTimeAlarm) alarm);
        } else {
            cancelRepeating(context, (RepeatingAlarm) alarm);
        }
        alarm.Deactivate();
        saveAlarms(context);
    }

    /**
     * Update the attributes of an alarm.
     *
     * @param context    the context
     * @param id         the id
     * @param vibrate    the vibrate
     * @param hour       the hour
     * @param min        the min
     * @param ringtone   the ringtone
     * @param snooze     the snooze
     * @param repeatDays the repeat days
     */
    public void editAlarm(Context context, int id, int vibrate, int hour, int min, String ringtone, int snooze, List<Integer> repeatDays) {
        Alarm alarm = User.getInstance().getAlarmById(id);
        Boolean wasActive = alarm.getStatus();
        //noinspection ConstantConditions
        if (alarm != null) {
            if (alarm.getClass() == OneTimeAlarm.class && repeatDays.size() != 0) {
                //onetime to repeating alarm conversion
                RepeatingBuilder builder = new RepeatingBuilder();
                builder.initialize(repeatDays)
                        .setVibrate(vibrate)
                        .setRingtoneURI(ringtone)
                        .setSnooze(snooze)
                        .setInterval();
                Long trig = UtilityFunctions.UTCMilliseconds(hour, min);
                builder.setTime(trig);

                RepeatingAlarm newAlarm = builder.build();
                deleteAlarm(context, id);
                createAlarm(context, newAlarm);
                if (wasActive) {
                    scheduleAlarm(context, newAlarm);
                } else {
                    newAlarm.Deactivate();
                }
                saveAlarms(context);
//                Log.w("Edit Alarm(1T->R)", newAlarm.toString());


            } else if (alarm.getClass() == RepeatingAlarm.class && repeatDays.size() == 0) {
                //Change repeating alarm to onetime
                OneTimeBuilder builder = new OneTimeBuilder();
                builder.setRingtoneURI(ringtone)
                        .setVibrate(vibrate)
                        .setSnooze(snooze);
                Long trig = UtilityFunctions.UTCMilliseconds(hour, min);
                builder.setTime(trig);

                OneTimeAlarm newAlarm = builder.build();
                deleteAlarm(context, id);
                createAlarm(context, newAlarm);
                if (wasActive) {
                    scheduleAlarm(context, newAlarm);
                } else {
                    newAlarm.Deactivate();
                }
                saveAlarms(context);
//                Log.w("Edit Alarm(R->1T)", newAlarm.toString());

            } else if (alarm.getClass() == OneTimeAlarm.class && repeatDays.size() == 0) {
                //Onetime alarm staying the same type
                cancelAlarm(context, id);
                alarm.setRingtoneURI(ringtone);
                alarm.setSnoozeLength(snooze);
                alarm.setVibratePattern(vibrate);
                Long trig = UtilityFunctions.UTCMilliseconds(hour, min);
                alarm.setTime(trig);
                if (wasActive) {
                    alarm.Activate();
                    scheduleAlarm(context, alarm);
                } else {
                    alarm.Deactivate();
                }
                saveAlarms(context);
//                Log.w("Edit Alarm(1T->1T)", alarm.toString());

            } else {
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
                deleteAlarm(context, id);
                createAlarm(context, newAlarm);
                if (wasActive) {
                    scheduleAlarm(context, newAlarm);
                } else {
                    newAlarm.Deactivate();
                }
                saveAlarms(context);
//                Log.w("Edit Alarm(R->R)", newAlarm.toString());
            }
        } else {
            Toast.makeText(context, "Error editing alarm", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Stop the current sounding alarm from firing.
     *
     * @param context the context
     * @param Id      the id
     * @param subId   the sub id
     */
    public void dismissAlarm(Context context, int Id, int subId) {
        Alarm alarm = User.getInstance().getAlarmById(Id);

        if (alarm.getClass() == OneTimeAlarm.class) {
            dismissOneTime(context, Id);
            alarm.Deactivate();
            saveAlarms(context);
        } else {
            dismissRepeatingAlarm(context, Id, subId);
            saveAlarms(context);
        }

    }

    /**
     * Alarm pending intent pending intent.
     *
     * @param context the context
     * @param alarm   the alarm
     * @return pending intent
     */
    private PendingIntent AlarmPendingIntent(Context context, Alarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        //Provide Settings
        intent.putExtra("Vibrate", alarm.getVibratePattern());
        intent.putExtra("Id", alarm.getId());
        intent.putExtra("Snooze", alarm.getSnoozeLength());
        intent.putExtra("Time", timeString);
        intent.putExtra("Meridian", meridianString);
        intent.putExtra("Uri", alarm.getRingtoneURI());


        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

    /**
     * Reschedule sub alarm.
     *
     * @param context the context
     * @param alarm   the alarm
     */
    private void rescheduleSubAlarm(Context context, Alarm alarm) {
        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Provide Settings
        intent.putExtra("Vibrate", alarm.getVibratePattern());
        intent.putExtra("Id", alarm.getId());
        intent.putExtra("Snooze", alarm.getSnoozeLength());
        intent.putExtra("Time", timeString);
        intent.putExtra("Meridian", meridianString);
        intent.putExtra("Uri", alarm.getRingtoneURI());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //noinspection ConstantConditions
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarm.getTime(), pendingIntent);
    }


    //==ONETIME METHODS==

    /**
     * Schedule a one-time-alarm with the system.
     *
     * @param context      the context
     * @param oneTimeAlarm the one time alarm
     */
    private void scheduleOneTimeAlarm(Context context, OneTimeAlarm oneTimeAlarm) {

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(oneTimeAlarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(oneTimeAlarm.getTime()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Provide Settings
        intent.putExtra("Vibrate", oneTimeAlarm.getVibratePattern());
        intent.putExtra("Id", oneTimeAlarm.getId());
        intent.putExtra("Snooze", oneTimeAlarm.getSnoozeLength());
        intent.putExtra("Time", timeString);
        intent.putExtra("Meridian", meridianString);
        intent.putExtra("Uri", oneTimeAlarm.getRingtoneURI());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, oneTimeAlarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);


        //noinspection ConstantConditions
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, oneTimeAlarm.getTime(), pendingIntent);

//        Log.w("Controller Sched 1Time", oneTimeAlarm.toString());
    }

    /**
     * Dismiss the alarm and set its Active status to False.
     *
     * @param context the context
     * @param Id      the id
     */
    private void dismissOneTime(Context context, int Id) {

        alarmReceiver.Cancel(context, Id);
    }

    /**
     * This method is used when an active one-time-alarm is set to de-active
     * from the Home Activity and we must cancel the pending alarm.
     *
     * @param context the context
     * @param alarm   the alarm
     */
    private void cancelOneTime(Context context, OneTimeAlarm alarm) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //noinspection ConstantConditions
        alarmManager.cancel(oneTimePendingIntent(context, alarm));
    }

    /**
     * One time pending intent pending intent.
     *
     * @param context the context
     * @param alarm   the alarm
     * @return the pending intent
     */
    private PendingIntent oneTimePendingIntent(Context context, OneTimeAlarm alarm) {
        Intent intent = new Intent(context, AlarmReceiver.class);

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        //Provide Settings
        intent.putExtra("Vibrate", alarm.getVibratePattern());
        intent.putExtra("Id", alarm.getId());
        intent.putExtra("Snooze", alarm.getSnoozeLength());
        intent.putExtra("Time", timeString);
        intent.putExtra("Meridian", meridianString);
        intent.putExtra("Uri", alarm.getRingtoneURI());


        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }

    /**
     * Snooze alarm.
     *
     * @param context  the context
     * @param ID       the id
     * @param subId    the sub id
     * @param vibrate  the vibrate
     * @param snooze   the snooze
     * @param ringtone the ringtone
     */
    public void snoozeAlarm(Context context, int ID, int subId, int vibrate, int snooze, String ringtone) {

        long currentTime = System.currentTimeMillis();
        long newTriggerTime = currentTime + snooze * 60000;

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(newTriggerTime));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(newTriggerTime));


        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra("Vibrate", vibrate);
        intent.putExtra("Id", ID);
        intent.putExtra("Snooze", snooze);
        intent.putExtra("Time", timeString);
        intent.putExtra("Meridian", meridianString);
        intent.putExtra("Uri", ringtone);
        intent.putExtra("subID", subId);

        PendingIntent pendingIntent;
        if (subId == 0) {
            pendingIntent = PendingIntent.getBroadcast(context, ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getBroadcast(context, subId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }

        //noinspection ConstantConditions
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, newTriggerTime, pendingIntent);
    }


    //==REPEATING METHODS==

    /**
     * Schedule repeating alarm.
     *
     * @param context the context
     * @param alarm   the alarm
     */
    private void scheduleRepeatingAlarm(Context context, RepeatingAlarm alarm) {

        //Get the time in string format with the meridian
        SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm");
        String timeString = timeFormatter.format(new Date(alarm.getTime()));
        SimpleDateFormat meridianFormatter = new SimpleDateFormat("a");
        String meridianString = meridianFormatter.format(new Date(alarm.getTime()));

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        //For each sub-Alarm schedule it.
        for (Map.Entry<Integer, Alarm> entry : alarm.getSubAlarms().entrySet()) {

            Intent intent = new Intent(context, AlarmReceiver.class);

            //Provide Settings
            intent.putExtra("Vibrate", alarm.getVibratePattern());
            intent.putExtra("Id", alarm.getId());
            intent.putExtra("Snooze", alarm.getSnoozeLength());
            intent.putExtra("Time", timeString);
            intent.putExtra("Meridian", meridianString);
            intent.putExtra("Uri", entry.getValue().getRingtoneURI());
            intent.putExtra("subID", entry.getValue().getId());


            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.getBroadcast(context, entry.getValue().getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

            //noinspection ConstantConditions
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, entry.getValue().getTime(), pendingIntent);
        }

//        Log.w("Controller Sched Repeat", alarm.toString());

    }

    /**
     * Stop the current sounding sub-Alarm from firing.
     *
     * @param context the context
     * @param id      the id
     * @param subId   the sub id
     */
    private void dismissRepeatingAlarm(Context context, int id, int subId) {

        alarmReceiver.Cancel(context, subId);
        //reschedule for next week.
        Alarm alarm = User.getInstance().getAlarmById(id);
        Alarm subAlarm = ((RepeatingAlarm) alarm).getSubAlarmById(subId);
        Long newTrigger = UtilityFunctions.validateRepeatTrigger(subAlarm.getTime());
        subAlarm.setTime(newTrigger);
        AlarmController.getInstance().scheduleAlarm(context, subAlarm);
        saveAlarms(context);
    }


    /**
     * De-schedules the sub-Alarms
     *
     * @param context the context
     * @param alarm   the alarm
     */
    private void cancelRepeating(Context context, RepeatingAlarm alarm) {

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        for (Map.Entry<Integer, Alarm> entry : alarm.getSubAlarms().entrySet()) {
            //noinspection ConstantConditions
            alarmManager.cancel(AlarmPendingIntent(context, entry.getValue()));
        }


    }

}
