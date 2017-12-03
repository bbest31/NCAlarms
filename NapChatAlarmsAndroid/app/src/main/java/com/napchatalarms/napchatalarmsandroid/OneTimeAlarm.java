package com.napchatalarms.napchatalarmsandroid;



/**
 * Alarm object that holds all the necessary information for a one-time alarm.
 * Created by bbest on 30/11/17.
 */

public class OneTimeAlarm extends Alarm{

    /**
     * Public constructor
     * */
    public OneTimeAlarm(long time, int snooze, Boolean vibrate, String ringtone){

        super(time,0,snooze,vibrate,ringtone);
    }

}
