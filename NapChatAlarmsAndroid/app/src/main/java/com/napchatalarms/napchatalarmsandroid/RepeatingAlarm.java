package com.napchatalarms.napchatalarmsandroid;

import java.util.ArrayList;

/**
 * Created by bbest on 01/12/17.
 */

public class RepeatingAlarm extends Alarm {

    //=====ATTRIBUTES=====
    private long triggerTime;
    private String ringtoneURI;
    private Boolean vibrateOn;
    private int snoozeLength;
    private int interval;
    private ArrayList<Alarm> subAlarms;

    //TODO: constructor needs to apply the attributes to very sub alarm but the subalarms need their own Id and interval needs to be 168hr(604800000 ms)
    public RepeatingAlarm(){}

    //=====GETTERS=====
    public long getTime(){
        return this.triggerTime;
    }

    public String getRingtoneURI(){
        return this.ringtoneURI;
    }

    public Boolean getVibrateOn(){
        return this.vibrateOn;
    }

    public int getSnoozeLength(){
        return this.snoozeLength;
    }

    public int getInterval(){return this.interval;}

    public ArrayList<Alarm> getSubAlarms(){return this.subAlarms;}

    //=====SETTERS=====
    public void setTime(long time){
        this.triggerTime = time;
    }

    public void setInterval(int interval){ this.interval = interval;}

    public void setRingtoneURI(String uri){
        this.ringtoneURI = uri;
    }

    public void setVibrate(Boolean vib){
        this.vibrateOn = vib;
    }

    public void setSnoozeLength(int length){
        this.snoozeLength = length;
    }

    public void addSubAlarm(Alarm alarm){this.subAlarms.add(alarm);}

    public void removeSubAlarm(Alarm alarm){this.subAlarms.remove(alarm);}
}
