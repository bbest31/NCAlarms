package com.napchatalarms.napchatalarmsandroid.model;

/**Base(Super) Alarm class holding the base attributes for an alarm.
 * @author bbest
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

    /**
     * Public constructor initializing the Id using <code>hashCode()</code>
     */
    public Alarm(){
        this.id = this.hashCode();
    }


    //=====METHODS=====

    /**
     *
     */
    public void Activate(){
        this.isActive = Boolean.TRUE;
    }


    @Override
    public String toString(){

        String alarm = "Id: "+this.getId()+
                " Trigger: "+this.getTime()+
                " Interval:"+this.getInterval()+
                " Snooze Length: "+this.getSnoozeLength()+
                " Vibrate: "+this.getVibrateOn()+
                " RingtoneURI: "+this.getRingtoneURI()+
                " isActive: "+this.getStatus();
        return alarm;
    }

    /**
     *
     * @return
     */
    public String writeFormat(){
        String alarm = this.getId()+
                "|"+this.getTime()+
                "|"+this.getInterval()+
                "|"+this.getSnoozeLength()+
                "|"+this.getVibrateOn()+
                "|"+this.getRingtoneURI()+
                "|"+this.getStatus();
        return alarm;    }

    /**
     *
     */
    public void Deactivate(){
        this.isActive = Boolean.FALSE;
    }

    /**
     *
     * @return
     */
    public Boolean getStatus(){
        return this.isActive;
    }

    /**
     *
     * @return
     */
    public int getId(){
        return this.id;
    }

    //=====GETTERS=====

    /**
     *
     * @return
     */
    public long getTime(){
        return this.triggerTime;
    }

    /**
     *
     * @return
     */
    public String getRingtoneURI(){
        return this.ringtoneURI;
    }

    /**
     *
     * @return
     */
    public Boolean getVibrateOn(){
        return this.vibrateOn;
    }

    /**
     *
     * @return
     */
    public int getSnoozeLength(){
        return this.snoozeLength;
    }

    /**
     *
     * @return
     */
    public long getInterval(){return this.interval;}

    //=====SETTERS=====

    /**
     *
     * @param time
     */
    public void setTime(long time){
        this.triggerTime = time;
    }

    /**
     *
     * @param uri
     */
    public void setRingtoneURI(String uri){
        this.ringtoneURI = uri;
    }

    /**
     *
     * @param vib
     */
    public void setVibrate(Boolean vib){
        this.vibrateOn = vib;
    }

    /**
     *
     * @param length
     */
    public void setSnoozeLength(int length){
        this.snoozeLength = length;
    }

    /**
     *
     * @param interval
     */
    public void setInterval(long interval){ this.interval = interval;}
}
