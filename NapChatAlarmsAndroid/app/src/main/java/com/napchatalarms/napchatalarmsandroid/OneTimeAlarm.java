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
    public OneTimeAlarm(long time, int snooze, Boolean vibrate, String ringtone){

        super(time,snooze,vibrate,ringtone);
    }

    @Override
    public String toString(){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(this.getTime());
        String alarm = "Alarm[Id: "+this.getId()+
                " Trigger: "+cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE)+
                " Snooze Length: "+this.getSnoozeLength()+
                " Vibrate: "+this.getVibrateOn()+
                "RingtoneURI: "+this.getRingtoneURI()+
                "isActive: "+this.getStatus();
        return alarm;
    }
}
