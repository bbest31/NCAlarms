package com.napchatalarms.napchatalarmsandroid;


import java.util.Calendar;

/**
 * Alarm object that holds all the necessary information for a one-time alarm.
 * Created by bbest on 30/11/17.
 */

public class OneTimeAlarm extends Alarm{

    /**
     * Public constructor
     * */
    public OneTimeAlarm(){

        super();

    }

    @Override
    public String toString(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getTime());
        String alarm = "Alarm[Id: "+this.getId()+
                " Trigger: "+this.getTime()+
                " Snooze Length: "+this.getSnoozeLength()+
                " Vibrate: "+this.getVibrateOn()+
                "RingtoneURI: "+this.getRingtoneURI()+
                "isActive: "+this.getStatus();
        return alarm;
    }
}
