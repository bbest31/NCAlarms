package com.napchatalarms.napchatalarmsandroid;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

/**
 * Created by bbest on 01/12/17.
 */

public class RepeatingAlarm extends Alarm {

    //=====ATTRIBUTES=====
    private long triggerTime;
    private String ringtoneURI;
    private Boolean vibrateOn;
    private int snoozeLength;
    /**Dictionary carrying the seperate sub alarms for each day that needs a repeating alarm.
     * The key integer corresponds to what day of the week (1-7, 0:everyday, 8:weekdays, 9:weekends)*/
    private Dictionary<Integer,Alarm> subAlarms;

    public RepeatingAlarm(long trigger,long interval, int snooze, Boolean vibrate, String ringtone,int[] days){
        super(trigger,interval,snooze,vibrate,ringtone);

        //Apply the alarm attributes to each sub alarm and make the key corresponding to its day tp fire.

        for(int i = 0; i < days.length;i++){
            //Computes the trigger to correspond to the correct time on the correct day.
            long trig = calculateTrigger(days[i],trigger);
            Alarm alarm = new Alarm(trig,interval,snooze,vibrate,ringtone);

            addSubAlarm(days[i],alarm);
        }
    }

    //=====METHODS=====
    /**Takes an integer to indicate the day of the week and time in milliseconds we need to set the
     * next trigger time for.
     * */
    //TODO: Finish this.
    public long calculateTrigger(int day, long triggerTime){

        return triggerTime;
    }
    //=====GETTERS=====

    public Dictionary<Integer,Alarm> getSubList(){return this.subAlarms;}
    //TODO: Need to look up how to get an element by key.
    //public Alarm getSubAlarmById(int Id){}

    //=====SETTERS=====


    public void addSubAlarm(Integer k,Alarm alarm){this.subAlarms.put(k,alarm);}

    public void removeSubAlarm(Alarm alarm){this.subAlarms.remove(alarm);}
}
