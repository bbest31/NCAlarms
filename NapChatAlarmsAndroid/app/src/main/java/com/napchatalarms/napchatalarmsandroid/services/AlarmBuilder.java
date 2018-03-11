package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;

/**
 * Builder pattern class to build the different types of alarms.
 *
 * @author bbest
 */

public class AlarmBuilder {

    private Alarm alarm;

    /**
     *
     */
    public AlarmBuilder() {
        alarm = new Alarm();
    }

    /**
     * @return
     */
    public Alarm build() {
        return alarm;
    }

    /**
     * @param triggerTime
     * @return
     */
    public AlarmBuilder setTime(final long triggerTime) {
        alarm.setTime(triggerTime);
        return this;
    }

    /**
     * @param uri
     * @return
     */
    public AlarmBuilder setRingtoneURI(final String uri) {
        alarm.setRingtoneURI(uri);
        return this;
    }

    /**
     * @param vibrate
     * @return
     */
    public AlarmBuilder setVibrate(final int vibrate) {
        alarm.setVibratePattern(vibrate);
        return this;
    }

    /**
     * @param length
     * @return
     */
    public AlarmBuilder setSnooze(final int length) {
        alarm.setSnoozeLength(length);
        return this;
    }

    /**
     * @param interval
     * @return
     */
    public AlarmBuilder setInterval(final long interval) {
        alarm.setInterval(interval);
        return this;
    }
}
