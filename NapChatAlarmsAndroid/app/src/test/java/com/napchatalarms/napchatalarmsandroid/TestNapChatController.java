package com.napchatalarms.napchatalarmsandroid;

import android.app.Application;
import android.content.Context;

import com.napchatalarms.napchatalarmsandroid.Model.Alarm;
import com.napchatalarms.napchatalarmsandroid.Model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.Model.User;
import com.napchatalarms.napchatalarmsandroid.Services.NapChatController;

import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by bbest on 20/01/18.
 */

public class TestNapChatController {
    @Test
    public void directoryCreation() throws Exception{

        ArrayList<Alarm> alarmArrayList = new ArrayList<Alarm>();

        Alarm alarm1 = new Alarm();
        alarm1.setTime(1000);
        alarm1.setVibrate(true);
        alarm1.setInterval(1000);
        alarm1.setRingtoneURI("ringtone1");
        alarm1.setSnoozeLength(1);

        Alarm alarm2 = new Alarm();
        alarm2.setTime(2000);
        alarm2.setVibrate(true);
        alarm2.setInterval(2000);
        alarm2.setRingtoneURI("ringtone2");
        alarm2.setSnoozeLength(2);

        Alarm alarm3 = new Alarm();
        alarm3.setTime(3000);
        alarm3.setVibrate(true);
        alarm3.setInterval(3000);
        alarm3.setRingtoneURI("ringtone3");
        alarm3.setSnoozeLength(3);

        Alarm alarm4 = new Alarm();
        alarm4.setTime(4000);
        alarm4.setVibrate(true);
        alarm4.setInterval(4000);
        alarm4.setRingtoneURI("ringtone4");
        alarm4.setSnoozeLength(4);

        OneTimeAlarm alarm5 = new OneTimeAlarm();
        alarm5.setTime(5000);
        alarm5.setVibrate(false);
        alarm5.setRingtoneURI("ringtone5");
        alarm5.setSnoozeLength(5);

        alarmArrayList.add(alarm1);
        alarmArrayList.add(alarm2);
        alarmArrayList.add(alarm3);
        alarmArrayList.add(alarm4);
        alarmArrayList.add(alarm5);

        for(Alarm a : alarmArrayList) {
            System.out.print(a.writeFormat()+"\n");
        }
    }
}
