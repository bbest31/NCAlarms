package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;

/**
 * Concrete Builder class for the OneTimeAlarm class.
 *
 * @author bbest
 */
public class OneTimeBuilder extends AlarmBuilder {

    private final OneTimeAlarm alarm;

    /**
     * Public constructor than initializes a new OneTimeAlarm.
     */
    public OneTimeBuilder() {
        alarm = new OneTimeAlarm();
    }

    /**
     * Returns the OneTimeAlarm object that has been built.
     **/
    public OneTimeAlarm build() {
        return alarm;
    }

    /**
     * Sets the alarms trigger time.
     **/
    public OneTimeBuilder setTime(final long triggerTime) {
        alarm.setTime(triggerTime);
        return this;
    }

    /**
     * Sets the alarms ringtone uri.
     **/
    public OneTimeBuilder setRingtoneURI(final String uri) {

        alarm.setRingtoneURI(uri);
        return this;
    }

    /**
     * Sets the alarms vibrate settings to on or off.
     **/
    public OneTimeBuilder setVibrate(final int vibrate) {
        alarm.setVibratePattern(vibrate);
        return this;
    }

    /**
     * Sets the alarms snooze length in minutes.
     **/
    @SuppressWarnings("UnusedReturnValue")
    public OneTimeBuilder setSnooze(final int length) {
        alarm.setSnoozeLength(length);
        return this;
    }


}
