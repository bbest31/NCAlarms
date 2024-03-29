package com.napchatalarms.napchatalarmsandroid.model;


import android.annotation.SuppressLint;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Alarm object that holds all the necessary information for a one-time alarm.
 *
 * @author bbest
 */
public class OneTimeAlarm extends Alarm implements Serializable {

    /**
     * Public constructor
     */
    public OneTimeAlarm() {
        super();
    }

    @Override
    public String toString() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm aa ");

        return "Id: " + this.getId() +
                " Trigger: " + this.getTime() +
                " Time: " + sdf.format(new Date(this.getTime())) +
                " Snooze Length: " + this.getSnoozeLength() +
                " Vibrate Pattern: " + this.getVibratePattern() +
                " RingtoneURI: " + this.getRingtoneURI() +
                " isActive: " + this.getStatus();
    }

}
