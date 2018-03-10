package com.napchatalarms.napchatalarmsandroid.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Base(Super) Alarm class holding the base attributes for an alarm.
 *
 * @author bbest
 */
public class Alarm implements Serializable {

    //=====ATTRIBUTES=====
    private long triggerTime;
    private String ringtoneURI;
    private Integer vibratePattern;
    private int snoozeLength;
    private int id;
    private Boolean isActive;
    /**
     * 604,800,000:per week, 86,400,000:everyday,
     */
    private long interval;

    /**
     * Public constructor initializing the Id using <code>hashCode()</code>
     */
    public Alarm() {
        this.id = this.hashCode();
    }


    //=====METHODS=====

    /**
     *
     */
    public void Activate() {
        this.isActive = Boolean.TRUE;
    }


    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm aa ");

        String alarm = "Id: " + this.getId() +
                " Trigger: " + this.getTime() +
                " Time: " + sdf.format(new Date(this.getTime())) +
                " Interval:" + this.getInterval() +
                " Snooze Length: " + this.getSnoozeLength() +
                " Vibrate Pattern: " + this.getVibratePattern() +
                " RingtoneURI: " + this.getRingtoneURI() +
                " isActive: " + this.getStatus();
        return alarm;
    }

    /**
     *
     */
    public void Deactivate() {
        this.isActive = Boolean.FALSE;
    }

    /**
     * @return
     */
    public Boolean getStatus() {
        return this.isActive;
    }

    /**
     * @return
     */
    public int getId() {
        return this.id;
    }

    //=====GETTERS=====

    /**
     * @return
     */
    public long getTime() {
        return this.triggerTime;
    }

    /**
     * @param time
     */
    public void setTime(long time) {
        this.triggerTime = time;
    }

    /**
     * @return
     */
    public String getRingtoneURI() {
        return this.ringtoneURI;
    }

    /**
     * @param uri
     */
    public void setRingtoneURI(String uri) {
        this.ringtoneURI = uri;
    }


    //=====SETTERS=====

    /**
     * @return
     */
    public int getSnoozeLength() {
        return this.snoozeLength;
    }

    /**
     * @param length
     */
    public void setSnoozeLength(int length) {
        this.snoozeLength = length;
    }

    /**
     * @return
     */
    public long getInterval() {
        return this.interval;
    }

    /**
     * @param interval
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    public Integer getVibratePattern() {
        return vibratePattern;
    }

    public void setVibratePattern(Integer vibratePattern) {
        this.vibratePattern = vibratePattern;
    }
}
