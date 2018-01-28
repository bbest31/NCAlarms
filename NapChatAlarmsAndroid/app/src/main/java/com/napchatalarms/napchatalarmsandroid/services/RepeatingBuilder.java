package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;

import java.util.Map;

/**
 * @author bbest
 */

public class RepeatingBuilder extends AlarmBuilder {

    private RepeatingAlarm alarm;

    /**
     *
     */
    public RepeatingBuilder(){ alarm = new RepeatingAlarm();}

    @Override
    public RepeatingAlarm build(){ return alarm;}

    /**This method needs to be called first when building Repeating alarms.**/
    public RepeatingBuilder initialize(final int[] days){
        alarm.setRepeatDays(days);
        //Initialize the individual alarms for each day.
        for (int i = 0; i < days.length; i++) {
            alarm.addSubAlarm(new Alarm());
            }
        return this;
    }

    @Override
    public RepeatingBuilder setTime(final long triggerTime){

        Map<Integer,Alarm> subAlarms = alarm.getSubList();
        int i = 0;
        int[] repeatDays = alarm.getRepeatDays();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            long trig = alarm.calculateTrigger(repeatDays[i],triggerTime);
            a.setTime(trig);
            i++;
        }
        return this;
    }

    @Override
    public RepeatingBuilder setRingtoneURI(final String uri){
        Map<Integer,Alarm> subAlarms = alarm.getSubList();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            a.setRingtoneURI(uri);
        }
        return this;
    }

    @Override
    public RepeatingBuilder setVibrate(final boolean vibrate){
        Map<Integer,Alarm> subAlarms = alarm.getSubList();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            a.setVibrate(vibrate);
        }
        return this;
    }

    @Override
    public RepeatingBuilder setSnooze(final int length){
        Map<Integer,Alarm> subAlarms = alarm.getSubList();
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            Alarm a = entry.getValue();
            a.setSnoozeLength(length);
        }
        return this;
    }

    /**
     *
     * @return
     */
    public RepeatingBuilder setInterval(){
        Map<Integer,Alarm> subAlarms = alarm.getSubList();
        int[] repeatDays = alarm.getRepeatDays();
        //If the interval should be every week.
        if(repeatDays[0] != 0) {
            for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet()) {
                Alarm a = entry.getValue();
                a.setInterval(604800000);
            }
        }
        else{
            //Repeat days indicator is for every day so the interval is different.
            for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet()) {
                Alarm a = entry.getValue();
                a.setInterval(86400000);
            }
        }
        return this;
    }

}
