package com.napchatalarms.napchatalarmsandroid.model;

import android.annotation.SuppressLint;

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
    private final int id;
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
     * Activate.
     */
    public void Activate() {
        this.isActive = Boolean.TRUE;
    }


    @Override
    public String toString() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd hh:mm aa ");

        return "Id: " + this.getId() +
                " Trigger: " + this.getTime() +
                " Time: " + sdf.format(new Date(this.getTime())) +
                " Interval:" + this.getInterval() +
                " Snooze Length: " + this.getSnoozeLength() +
                " Vibrate Pattern: " + this.getVibratePattern() +
                " RingtoneURI: " + this.getRingtoneURI() +
                " isActive: " + this.getStatus();
    }

    /**
     * Deactivate.
     */
    public void Deactivate() {
        this.isActive = Boolean.FALSE;
    }

    /**
     * Gets status.
     *
     * @return status
     */
    public Boolean getStatus() {
        return this.isActive;
    }

    /**
     * Gets id.
     *
     * @return id
     */
    public int getId() {
        return this.id;
    }

    //=====GETTERS=====

    /**
     * Gets time.
     *
     * @return time
     */
    public long getTime() {
        return this.triggerTime;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(long time) {
        this.triggerTime = time;
    }

    /**
     * Gets ringtone uri.
     *
     * @return ringtone uri
     */
    public String getRingtoneURI() {
        return this.ringtoneURI;
    }

    /**
     * Sets ringtone uri.
     *
     * @param uri the uri
     */
    public void setRingtoneURI(String uri) {
        this.ringtoneURI = uri;
    }


    //=====SETTERS=====

    /**
     * Gets snooze length.
     *
     * @return snooze length
     */
    public int getSnoozeLength() {
        return this.snoozeLength;
    }

    /**
     * Sets snooze length.
     *
     * @param length the length
     */
    public void setSnoozeLength(int length) {
        this.snoozeLength = length;
    }

    /**
     * Gets interval.
     *
     * @return interval
     */
    private long getInterval() {
        return this.interval;
    }

    /**
     * Sets interval.
     *
     * @param interval the interval
     */
    public void setInterval(long interval) {
        this.interval = interval;
    }

    /**
     * Gets vibrate pattern.
     *
     * @return the vibrate pattern
     */
    public Integer getVibratePattern() {
        return vibratePattern;
    }

    /**
     * Sets vibrate pattern.
     *
     * @param vibratePattern the vibrate pattern
     */
    public void setVibratePattern(Integer vibratePattern) {
        this.vibratePattern = vibratePattern;
    }
}
