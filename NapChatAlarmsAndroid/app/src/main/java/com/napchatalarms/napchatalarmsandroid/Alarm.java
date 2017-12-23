package com.napchatalarms.napchatalarmsandroid;

import java.util.Calendar;

/**Base(Super) Alarm Class.
 * Created by bbest on 01/12/17.
 */

public class Alarm {

    //=====ATTRIBUTES=====
    private long triggerTime;
    private String ringtoneURI;
    private Boolean vibrateOn;
    private int snoozeLength;
    private int id;
    private Boolean isActive;
    /**604,800,000:per week, 86,400,000:everyday,*/
    private long interval;

    public Alarm(){

        this.id = this.hashCode();
    }


    //=====METHODS=====
    public void Activate(){
        this.isActive = true;
    }

    @Override
    public String toString(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getTime());
        String alarm = "Alarm[Id: "+this.getId()+
                " Trigger: "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+
                " Interval:"+this.getInterval()+
                " Snooze Length: "+this.getSnoozeLength()+
                " Vibrate: "+this.getVibrateOn()+
                "RingtoneURI: "+this.getRingtoneURI()+
                "isActive: "+this.getStatus();
        return alarm;
    }

    public void Deactivate(){
        this.isActive = false;
    }

    public Boolean getStatus(){
        return this.isActive;
    }

    public int getId(){
        return this.id;
    }

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

    public long getInterval(){return this.interval;}

    //=====SETTERS=====
    public void setTime(long time){
        this.triggerTime = time;
    }

    public void setRingtoneURI(String uri){
        this.ringtoneURI = uri;
    }

    public void setVibrate(Boolean vib){
        this.vibrateOn = vib;
    }

    public void setSnoozeLength(int length){
        this.snoozeLength = length;
    }

    public void setInterval(long interval){ this.interval = interval;}
}
