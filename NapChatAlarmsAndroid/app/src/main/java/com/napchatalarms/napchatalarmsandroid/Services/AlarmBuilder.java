package com.napchatalarms.napchatalarmsandroid.Services;

import com.napchatalarms.napchatalarmsandroid.Model.Alarm;

/**Builder pattern class to build the different types of alarms.
 * Created by brand on 12/3/2017.
 */

public class AlarmBuilder {

    private Alarm alarm;

    public AlarmBuilder(){
        alarm = new Alarm();
    }

    public Alarm build(){
        return alarm;
    }

    public AlarmBuilder setTime(final long triggerTime){
        alarm.setTime(triggerTime);
        return this;
    }

    public AlarmBuilder setRingtoneURI(final String uri){
        alarm.setRingtoneURI(uri);
        return this;
    }

    public AlarmBuilder setVibrate(final boolean vibrate){
        alarm.setVibrate(vibrate);
        return this;
    }

    public AlarmBuilder setSnooze(final int length){
        alarm.setSnoozeLength(length);
        return this;
    }

    public AlarmBuilder setInterval(final long interval){
        alarm.setInterval(interval);
        return this;
    }
}
