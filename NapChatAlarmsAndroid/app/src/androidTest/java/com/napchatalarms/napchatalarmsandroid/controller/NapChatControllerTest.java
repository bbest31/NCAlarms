package com.napchatalarms.napchatalarmsandroid.controller;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NapChatControllerTest {

    private NapChatController controller;
    private Context instrumentationCtx;

    @Before
    public void initializeVarTest() throws Exception{
        @SuppressWarnings("MismatchedQueryAndUpdateOfCollection") ArrayList<Alarm> alarmArrayList = new ArrayList<>();
        controller = NapChatController.getInstance();
        instrumentationCtx = InstrumentationRegistry.getContext();
        User user = User.getInstance();
        user.setEmail("bbest@email.ca");
        user.setName("Brandon");

        Alarm alarm1 = new Alarm();
        alarm1.setTime(1000);
//        alarm1.setVibrate(true);
        alarm1.setInterval(1000);
        alarm1.setRingtoneURI("ringtone1");
        alarm1.setSnoozeLength(1);

        Alarm alarm2 = new Alarm();
        alarm2.setTime(2000);
//        alarm2.setVibrate(true);
        alarm2.setInterval(2000);
        alarm2.setRingtoneURI("ringtone2");
        alarm2.setSnoozeLength(2);

        Alarm alarm3 = new Alarm();
        alarm3.setTime(3000);
//        alarm3.setVibrate(true);
        alarm3.setInterval(3000);
        alarm3.setRingtoneURI("ringtone3");
        alarm3.setSnoozeLength(3);

        Alarm alarm4 = new Alarm();
        alarm4.setTime(4000);
//        alarm4.setVibrate(true);
        alarm4.setInterval(4000);
        alarm4.setRingtoneURI("ringtone4");
        alarm4.setSnoozeLength(4);

        OneTimeAlarm alarm5 = new OneTimeAlarm();
        alarm5.setTime(5000);
//        alarm5.setVibrate(false);
        alarm5.setRingtoneURI("ringtone5");
        alarm5.setSnoozeLength(5);

        alarmArrayList.add(alarm1);
        alarmArrayList.add(alarm2);
        alarmArrayList.add(alarm3);
        alarmArrayList.add(alarm4);
        alarmArrayList.add(alarm5);
    }

    @Test
    public void createDirectory(){
        try {
            controller.createUserFiles(instrumentationCtx);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //noinspection StatementWithEmptyBody
        for(String f : instrumentationCtx.fileList()){

//            Log.i("TAG","File: "+f);
        }

        File alarmFile = instrumentationCtx.getFileStreamPath("ALRM.ser");
        assertTrue(alarmFile.exists());

        File settFile = instrumentationCtx.getFileStreamPath("data/user/0/com.napchatalarms.napchatalarmsandroid.test/filesbbest_email_caDIR/SETT.ser");
        assertTrue(settFile.exists());
    }


    }


