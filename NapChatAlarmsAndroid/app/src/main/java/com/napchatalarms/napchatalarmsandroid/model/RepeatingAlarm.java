package com.napchatalarms.napchatalarmsandroid.model;

import java.util.Calendar;
import java.util.Map;

/**
 * @author bbest
 */

public class RepeatingAlarm extends Alarm {

    //=====ATTRIBUTES=====
    private int[] repeatDays;

    /**Map carrying the different sub alarms for each day that needs a repeating alarm.
     * The key integer is the Id of the sub-Alarm. */
    private Map<Integer,Alarm> subAlarms;

    /**Contructor determines the number of sub-Alarms it needs to make based on
     * the integer list days, each integer in days corresponds to what day of the week (1-7, 0:everyday).
     * */
    public RepeatingAlarm(){
        super();
    }
    //=====METHODS=====

    /**Takes an integer to indicate the day of the week and time in milliseconds we need to set the
     * next trigger time for.
     * @see Calendar
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
                cal.set(Calendar.DAY_OF_WEEK,(alarmDay+1)%7);
            }
            return cal.getTimeInMillis();
        }

    }

    @Override
    public String toString(){
        String alarm = "ID: "+this.getId() + " <Sub-Alarms> ";
        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet())
        {
            alarm = alarm + entry.getValue().toString();
        }
        alarm = alarm.concat("</Sub-Alarms>");

        return  alarm;
    }

    @Override
    public String writeFormat(){
        String alarm = "";

        return alarm;
    }

    //=====GETTERS=====

    /**
     *
     * @return
     */
    public Map<Integer,Alarm> getSubList(){return this.subAlarms;}

    /**
     *
     * @param Id
     * @return
     */
    public Alarm getSubAlarmById(int Id){
        return this.subAlarms.get(Id);
    }

    /**
     *
     * @return
     */
    public int[] getRepeatDays() {
        return repeatDays;
    }

    //=====SETTERS=====

    /**
     *
     * @param repeatDays
     */
    public void setRepeatDays(int[] repeatDays) {
        this.repeatDays = repeatDays;
    }

    /**
     *
     * @param alarm
     */
    public void addSubAlarm(Alarm alarm){this.subAlarms.put(alarm.getId(),alarm);}

    /**
     *
     * @param alarm
     */
    public void removeSubAlarm(Alarm alarm){this.subAlarms.remove(alarm.getId());}
}
