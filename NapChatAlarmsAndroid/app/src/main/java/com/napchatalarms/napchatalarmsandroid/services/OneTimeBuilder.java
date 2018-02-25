package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;

/**
 * Concrete Builder class for the OneTimeAlarm class.
 *
 * @author bbest
 */

public class OneTimeBuilder extends AlarmBuilder {

    private OneTimeAlarm alarm;

    /**
     * Public constructor than initializes a new OneTimeAlarm.
     **/
    public OneTimeBuilder() {
        alarm = new OneTimeAlarm();
    }

    /**
     * Returns the OneTimeAlarm object that has been built.
     **/
    @Override
    public OneTimeAlarm build() {
        return alarm;
    }

    /**
     * Sets the alarms trigger time.
     **/
    @Override
    public OneTimeBuilder setTime(final long triggerTime) {
        alarm.setTime(triggerTime);
        return this;
    }

    /**
     * Sets the alarms ringtone uri.
     **/
    @Override
    public OneTimeBuilder setRingtoneURI(final String uri) {

        alarm.setRingtoneURI(uri);
        return this;
    }

    /**
     * Sets the alarms vibrate settings to on or off.
     **/
    @Override
    public OneTimeBuilder setVibrate(final boolean vibrate) {
        alarm.setVibrate(vibrate);
        return this;
    }

    /**
     * Sets the alarms snooze length in minutes.
     **/
    @Override
    public OneTimeBuilder setSnooze(final int length) {
        alarm.setSnoozeLength(length);
        return this;
    }


}
