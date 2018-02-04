package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;

import java.util.List;
import java.util.Map;

/**
 * The type Repeating builder.
 *
 * @author bbest
 */
public class RepeatingBuilder extends AlarmBuilder {

    private RepeatingAlarm alarm;

    /**
     * Instantiates a new Repeating builder.
     */
    public RepeatingBuilder(){ alarm = new RepeatingAlarm();}

    @Override
    public RepeatingAlarm build(){ return alarm;}

    /**
     * This method needs to be called first when building Repeating alarms. @param days the days
     *
     * @return the repeating builder
     */
    public RepeatingBuilder initialize(final List<Integer> days){
        alarm.setRepeatDays(days);
        //Initialize the individual alarms for each day.
        for (Integer i : days) {
            alarm.addSubAlarm(new Alarm());
            }
        return this;
    }

    @Override
    public RepeatingBuilder setTime(final long triggerTime){
        alarm.setTime(triggerTime);
        Map<Integer,Alarm> subAlarms = alarm.getSubAlarms();
        int i = 0;
        List<Integer> repeatDays = alarm.getRepeatDays();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            long trig = alarm.calculateTrigger(repeatDays.get(i),triggerTime);
            a.setTime(trig);
            i++;
        }
        return this;
    }

    @Override
    public RepeatingBuilder setRingtoneURI(final String uri){
        alarm.setRingtoneURI(uri);
        Map<Integer,Alarm> subAlarms = alarm.getSubAlarms();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            a.setRingtoneURI(uri);
        }
        return this;
    }

    @Override
    public RepeatingBuilder setVibrate(final boolean vibrate){
        alarm.setVibrate(vibrate);
        Map<Integer,Alarm> subAlarms = alarm.getSubAlarms();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            a.setVibrate(vibrate);
        }
        return this;
    }

    @Override
    public RepeatingBuilder setSnooze(final int length){
        alarm.setSnoozeLength(length);
        Map<Integer,Alarm> subAlarms = alarm.getSubAlarms();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            a.setSnoozeLength(length);
        }
        return this;
    }

    /**
     * Set interval repeating builder.
     *
     * @return repeating builder
     */
    public RepeatingBuilder setInterval(){
        Map<Integer,Alarm> subAlarms = alarm.getSubAlarms();
            for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet()) {
                Alarm a = entry.getValue();
                a.setInterval(604800000);
            }

        return this;
    }

}
