package com.napchatalarms.napchatalarmsandroid;


//TODO: Shift any shared attributes between this and RepeatingAlarm to Alarm abstraction
/**
 * Alarm object that holds all the necessary information for an alarm.
 * Created by bbest on 30/11/17.
 */

public class OneTimeAlarm extends Alarm{

    //=====ATTRIBUTES=====
    /**
     * Trigger time of alarm needs to be in UTC milliseconds.
     * */
    private long triggerTime;
    private String ringtoneURI;
    private Boolean vibrateOn;
    private int snoozeLength;
    private int interval;

    //=====METHODS=====
    /**
     * Public constructor
     * */
    public OneTimeAlarm(long time, int snooze, Boolean vibrate, String ringtone){

        super();
        setTime(time);
        setRingtoneURI(ringtone);
        setVibrate(vibrate);
        setSnoozeLength(snooze);
        setInterval(0);
        Activate();
    }


    //=====METHODS=====



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

}
