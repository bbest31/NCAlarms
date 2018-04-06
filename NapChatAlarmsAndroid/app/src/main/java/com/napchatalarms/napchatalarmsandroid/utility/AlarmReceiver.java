package com.napchatalarms.napchatalarmsandroid.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.napchatalarms.napchatalarmsandroid.activities.AlarmActivity;
import com.napchatalarms.napchatalarmsandroid.model.VibratePattern;

/**
 * AlarmReceiver builds the local notifications and creates the AlarmActivity Intent that will launch
 * when the alarm goes off.
 *
 * @author bbest
 */
public class AlarmReceiver extends BroadcastReceiver {
    /**
     * The Previous filter.
     */
    private int previousFilter;


    @Override
    public void onReceive(final Context context, Intent intent) {

//        final String action = intent.getAction();
//        //This is an auto reception.
//        if(action != null){
//            int id = intent.getIntExtra("ID", -1);
//            int sub = intent.getIntExtra("SUBID",0);
//            int vib = intent.getIntExtra("VIBRATE",-1);
//            int snooze = intent.getIntExtra("SNOOZE",1);
//            String ring = intent.getStringExtra("URI");
//            if (id != -1) {
//                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.cancel(id);
//                AlarmController.getInstance().snoozeAlarm(context,id,sub,vib,snooze,ring);
//            }
//        } else {
        //Regular reception of an alarm
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //noinspection ConstantConditions
        if (manager.isNotificationPolicyAccessGranted()) {
            previousFilter = manager.getCurrentInterruptionFilter();
            manager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
        }

        //Get values for alarm
        final int vibrate = intent.getIntExtra("Vibrate", -1);
        final String ringtoneURI = intent.getStringExtra("Uri");
        final int id = intent.getIntExtra("Id", 0);
        final int subId = intent.getIntExtra("subID", 0);
        final int snoozeLength = intent.getIntExtra("Snooze", 5);
        String displayTime = intent.getStringExtra("Time");
        String displayMeridian = intent.getStringExtra("Meridian");

        //If this is an update cancel the original
        Intent cancel = new Intent("Dismiss");
        PendingIntent cancelPending;
        if (subId == 0) {
            //noinspection UnusedAssignment
            cancelPending = PendingIntent.getBroadcast(context, id, cancel, PendingIntent.FLAG_CANCEL_CURRENT);
        } else {
            //noinspection UnusedAssignment
            cancelPending = PendingIntent.getBroadcast(context, subId, cancel, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        //Pass parameters to AlarmActivity so it can have same settings for snooze re-fire.
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra("SNOOZE", snoozeLength);
        alarmIntent.putExtra("VIBRATE", vibrate);
        alarmIntent.putExtra("URI", ringtoneURI);
        alarmIntent.putExtra("ID", id);
        alarmIntent.putExtra("TIME", displayTime);
        alarmIntent.putExtra("MERIDIAN", displayMeridian);
        alarmIntent.putExtra("SUBID", subId);
        alarmIntent.putExtra("FILTER", previousFilter);

        PendingIntent pendingAlarmIntent;
        if (subId != 0) {
            pendingAlarmIntent = PendingIntent.getActivity(context, subId, alarmIntent, PendingIntent.FLAG_ONE_SHOT);

        } else {

            pendingAlarmIntent = PendingIntent.getActivity(context, id, alarmIntent, PendingIntent.FLAG_ONE_SHOT);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "alarm");

        //Build Notification
        builder.setCategory(Notification.CATEGORY_ALARM)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setFullScreenIntent(pendingAlarmIntent, true)
                .setContentIntent(pendingAlarmIntent)
                .setContentTitle("NapChat Alarm")
                .setContentText("Open Alarm")
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setPriority(Notification.PRIORITY_MAX)
                .setOnlyAlertOnce(false);

        //Setting Alarm Ringtone
        if (Uri.parse(ringtoneURI).equals(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))) {
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), AudioManager.STREAM_ALARM);
        } else if (ringtoneURI.split(":")[0].equals("android.resource")) {

            builder.setSound(Uri.parse(ringtoneURI), AudioManager.STREAM_ALARM);
        } else {

            Uri uri = Uri.parse(ringtoneURI);

            //Assert the existence of the ringtone.
            //Check device audio files
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = cr.query(uri, null, null, null, null);

            if (cursor != null) {
                builder.setSound(uri, AudioManager.STREAM_ALARM);
            } else {
                builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM), AudioManager.STREAM_ALARM);
            }
            assert cursor != null;
            cursor.close();

        }

        //Setting vibrate settings

        if (vibrate != -1) {

            VibratePattern pattern = UtilityFunctions.getVibratePattern(vibrate);
            builder.setVibrate(pattern.getPattern());
        }


        if (subId == 0) {
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_INSISTENT;
            manager.notify(id, notification);
        } else {
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_INSISTENT;
            manager.notify(subId, notification);
        }

        //Schedule a timeout for the alarm.
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            Intent timeoutIntent = new Intent(context, AlarmReceiver.class);
//            timeoutIntent.setAction("CANCEL_NOTIFICATION");
//            timeoutIntent.putExtra("ID", id);
//            timeoutIntent.putExtra("SNOOZE", snoozeLength);
//            timeoutIntent.putExtra("VIBRATE", vibrate);
//            timeoutIntent.putExtra("URI", ringtoneURI);
//            timeoutIntent.putExtra("SUBID", subId);
//
//            PendingIntent pi = PendingIntent.getBroadcast(context, 0, timeoutIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+25000L,pi);
//        }
    }

    /**
     * Cancels a current notification alarm from continuing to fire. Used to dismiss as well/.
     *
     * @param context the context
     * @param Id      the id
     */
    public void Cancel(Context context, int Id) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        //noinspection ConstantConditions
        manager.cancel(Id);
    }
}
