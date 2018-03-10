package com.napchatalarms.napchatalarmsandroid.utility;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.napchatalarms.napchatalarmsandroid.activities.AlarmActivity;

/**
 * AlarmReceiver builds the local notifications and creates the AlarmActivity Intent that will launch
 * when the alarm goes off.
 *
 * @author bbest
 */

public class AlarmReceiver extends BroadcastReceiver {
    int previousFilter;
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        previousFilter = manager.getCurrentInterruptionFilter();
        manager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);

        //Get values for alarm
        Boolean vibrate = intent.getBooleanExtra("Vibrate", false);
        String ringtoneURI = intent.getStringExtra("Uri");
        int id = intent.getIntExtra("Id", 0);
        int subId = intent.getIntExtra("subID", 0);
        int snoozeLength = intent.getIntExtra("Snooze", 5);
        String displayTime = intent.getStringExtra("Time");
        String displayMeridian = intent.getStringExtra("Meridian");

        //If this is an update cancel the original
        Intent cancel = new Intent("Dismiss");
        PendingIntent cancelPending;
        if (subId == 0) {
            cancelPending = PendingIntent.getBroadcast(context, id, cancel, PendingIntent.FLAG_CANCEL_CURRENT);
        } else {
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
        alarmIntent.putExtra("FILTER",previousFilter);

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
        if (Uri.parse(ringtoneURI) == RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)) {
            builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        } else {

            Uri uri = Uri.parse(ringtoneURI);
            builder.setSound(uri);
        }

        //Setting vibrate settings

        if (vibrate == true) {

            long[] pattern = UtilityFunctions.getVibratePattern(0);
            if (pattern.length == 0) {
                builder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS);
            } else {
                builder.setVibrate(pattern);
            }
        }


        if (subId == 0) {
            manager.notify(id, builder.build());
        } else {
            manager.notify(subId, builder.build());
        }

    }

    /**
     * Cancels a current notification alarm from continuing to fire. Used to dismiss as well/.
     */
    public void Cancel(Context context, int Id) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(Id);
    }
}
