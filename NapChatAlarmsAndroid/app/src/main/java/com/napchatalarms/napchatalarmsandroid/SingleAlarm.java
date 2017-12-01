package com.napchatalarms.napchatalarmsandroid;



/**
 * Alarm object that holds all the necessary information for an alarm.
 * Created by bbest on 30/11/17.
 */

public class SingleAlarm extends Alarm{

    //=====ATTRIBUTES=====
    /**
     * Trigger time of alarm needs to be in UTC milliseconds.
     * */
    private int triggerTime;
    private String ringtoneURI;
    private Boolean vibrateOn;
    private Boolean isActive;
    private int snoozeLength;
    private int id;
    private int interval;

    //=====METHODS=====
    /**
     * Public constructor
     * */
    public SingleAlarm(int time, int interval, int snooze, Boolean vibrate, String ringtone){

        //TODO:May need to format string depending on what we get from the clock view.

        this.id = this.hashCode();
        setTime(time);
        setRingtoneURI(ringtone);
        setVibrate(vibrate);
        setSnoozeLength(snooze);
        setInterval(interval);
        Activate();
    }


    //=====METHODS=====

    public void Activate(){
        this.isActive = true;
    }

    public void Deactivate(){
        this.isActive = false;
    }

    //=====GETTERS=====
    public int getTime(){
        return this.triggerTime;
    }

    public String getRingtoneURI(){
        return this.ringtoneURI;
    }

    public Boolean getVibrateOn(){
        return this.vibrateOn;
    }

    public Boolean getStatus(){
        return this.isActive;
    }

    public int getSnoozeLength(){
        return this.snoozeLength;
    }

    public int getId(){
        return this.id;
    }

    public int getInterval(){return this.interval;}

    //=====SETTERS=====
    public void setTime(int time){
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

}
