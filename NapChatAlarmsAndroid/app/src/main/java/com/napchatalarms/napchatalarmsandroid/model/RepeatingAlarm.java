package com.napchatalarms.napchatalarmsandroid.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bbest
 */

public class RepeatingAlarm extends Alarm implements Serializable {

    //=====ATTRIBUTES=====
    private List<Integer> repeatDays;

    /**
     * Map carrying the different sub alarms for each day that needs a repeating alarm.
     * The key integer is the Id of the sub-Alarm.
     */
    private Map<Integer, Alarm> subAlarms;

    /**
     * Constructor determines the number of sub-Alarms it needs to make based on
     * the integer list days, each integer in days corresponds to what day of the week (1-7).
     */
    public RepeatingAlarm() {
        super();
        subAlarms = new HashMap<Integer, Alarm>();
    }
    //=====METHODS=====

    /**
     * <p>
     * Takes an integer to indicate the day of the week and time in milliseconds we need to set the
     * next trigger time for.</p>
     * <p>
     * If the day of the week we need is the same as the calendar day of the week we get from
     * the trigger time then we don't need to adjust anything. If the day of the week of the
     * calendar date is not the same as the desired day then we changed the day of the week of the calendar
     * until they match.
     * </p>
     *
     * @see Calendar
     */
    public long calculateTrigger(int day, long triggerTime) {
        Calendar cal = Calendar.getInstance();
        //Sets the calendar to the trigger time of the alarm.
        cal.setTimeInMillis(triggerTime);
        int alarmDay = cal.get(Calendar.DAY_OF_WEEK);

        //The alarm is for the proper day of the week.
        if (alarmDay == day) {
            return triggerTime;
        } else
        //Alarm is for a day other than the intended day.
        {

            while (cal.get(Calendar.DAY_OF_WEEK) != day) {
                cal.add(Calendar.DATE, 1);
            }
            return cal.getTimeInMillis();
        }

    }

    /**
     * @return
     */
    @Override
    public String toString() {
        String alarmStr = "ID: " + this.getId() + " Active: " + this.getStatus() + "\n <Sub-Alarms> \n";

        for (Map.Entry<Integer, Alarm> entry : subAlarms.entrySet()) {
            alarmStr = alarmStr + entry.getValue().toString() + " \n ";
        }

        alarmStr = alarmStr.concat("</Sub-Alarms>\n" + repeatDays);

        return alarmStr;
    }

    //=====GETTERS=====

    /**
     * @return
     */
    public Map<Integer, Alarm> getSubAlarms() {
        return this.subAlarms;
    }

    /**
     * @param subAlarms
     */
    public void setSubAlarms(Map<Integer, Alarm> subAlarms) {
        this.subAlarms = subAlarms;
    }

    /**
     * @param Id
     * @return
     */
    public Alarm getSubAlarmById(int Id) {
        return this.subAlarms.get(Id);
    }

    //=====SETTERS=====

    /**
     * @return
     */
    public List<Integer> getRepeatDays() {
        return repeatDays;
    }

    /**
     * @param repeatDays
     */
    public void setRepeatDays(List<Integer> repeatDays) {
        this.repeatDays = repeatDays;
    }

    /**
     * @param alarm
     */
    public void addSubAlarm(Alarm alarm) {
        this.subAlarms.put(alarm.getId(), alarm);
    }

    /**
     * @param alarm
     */
    public void removeSubAlarm(Alarm alarm) {
        this.subAlarms.remove(alarm.getId());
    }

}
