package com.napchatalarms.napchatalarmsandroid.model;


/**
 * Alarm object that holds all the necessary information for a one-time alarm.
 * @author bbest
 */

public class OneTimeAlarm extends Alarm {

    /**
     * Public constructor
     * */
    public OneTimeAlarm(){
        super();
    }

    @Override
    public String toString(){

        String alarm = "Id: "+this.getId()+
                " Trigger: "+this.getTime()+
                " Snooze Length: "+this.getSnoozeLength()+
                " Vibrate: "+this.getVibrateOn()+
                "RingtoneURI: "+this.getRingtoneURI()+
                "isActive: "+this.getStatus();
        return alarm;
    }

    @Override
    public String writeFormat(){
        String alarm = this.getId()+
                "|"+this.getTime()+
                "|null"+ //interval = null indicates it is a OneTime alarm.
                "|"+this.getSnoozeLength()+
                "|"+this.getVibrateOn()+
                "|"+this.getRingtoneURI()+
                "|"+this.getStatus();
        return alarm;    }
}
