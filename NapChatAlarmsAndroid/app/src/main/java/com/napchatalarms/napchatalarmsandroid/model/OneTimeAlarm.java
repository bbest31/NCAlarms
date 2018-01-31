package com.napchatalarms.napchatalarmsandroid.model;


import java.io.Serializable;

/**
 * Alarm object that holds all the necessary information for a one-time alarm.
 * @author bbest
 */

public class OneTimeAlarm extends Alarm implements Serializable {

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

}
