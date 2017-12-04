package com.napchatalarms.napchatalarmsandroid;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by bbest on 01/12/17.
 */

public class RepeatingAlarm extends Alarm {

    //=====ATTRIBUTES=====
    private long triggerTime;
    private String ringtoneURI;
    private Boolean vibrateOn;
    private int snoozeLength;
    private int[] repeatDays;

    /**Map carrying the different sub alarms for each day that needs a repeating alarm.
     * The key integer is the Id of the sub-Alarm. */
    private Map<Integer,Alarm> subAlarms;

    /**Contructor determines the number of sub-Alarms it needs to make based on
     * the integer list days, each integer in days corresponds to what day of the week (1-7, 0:everyday).
     * */
    public RepeatingAlarm(long trigger, int snooze, Boolean vibrate, String ringtone,int[] days){

        super(trigger,snooze,vibrate,ringtone);
        setRepeatDays(days);
        //Apply the alarm attributes to each sub alarm and make the key corresponding to its day tp fire.
        if(days[0] != 0) {
            for (int i = 0; i < days.length; i++) {
                //Computes the trigger to correspond to the correct time on the correct day.
                long trig = calculateTrigger(days[i], trigger);
                Alarm alarm = new Alarm(trig, snooze, vibrate, ringtone);
                alarm.setInterval(604800000);

                addSubAlarm(alarm);
            }

        }else{
            //Makes one repeating alarm that repeats everyday at the same time.
            Alarm alarm = new Alarm(trigger,snooze,vibrate,ringtone);
            alarm.setInterval(86400000);
            addSubAlarm(alarm);
        }
    }
    //=====METHODS=====

    /**Takes an integer to indicate the day of the week and time in milliseconds we need to set the
     * next trigger time for.
     * */

    public long calculateTrigger(int day, long triggerTime){
        Calendar cal = Calendar.getInstance();
        //Sets the calendar to the trigger time of the alarm.
        cal.setTimeInMillis(triggerTime);
        int alarmDay = cal.get(Calendar.DAY_OF_WEEK);

        //The alarm is for the proper day of the week.
        if(alarmDay == day ){
            return triggerTime;
        } else
        //Alarm is for a day other than the intended day.
        {
            while(cal.get(Calendar.DAY_OF_WEEK) != day){
                cal.set(Calendar.DAY_OF_WEEK,alarmDay+1);
            }
            return cal.getTimeInMillis();
        }

    }

    //TODO: Do an override of the print function in order to verify the constructor is working.

    //=====GETTERS=====

    public Map<Integer,Alarm> getSubList(){return this.subAlarms;}

    public Alarm getSubAlarmById(int Id){
        return this.subAlarms.get(Id);
    }

    public int[] getRepeatDays() {
        return repeatDays;
    }

    //=====SETTERS=====


    public void setRepeatDays(int[] repeatDays) {
        this.repeatDays = repeatDays;
    }


    public void addSubAlarm(Alarm alarm){this.subAlarms.put(alarm.getId(),alarm);}

    public void removeSubAlarm(Alarm alarm){this.subAlarms.remove(alarm.getId());}
}
