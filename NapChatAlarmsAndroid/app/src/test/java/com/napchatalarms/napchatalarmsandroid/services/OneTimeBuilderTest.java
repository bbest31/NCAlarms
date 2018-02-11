package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by bbest on 11/02/18.
 */


public class OneTimeBuilderTest {
    OneTimeAlarm alarm;
    Long time;
    @Before
    public void init(){
        OneTimeBuilder builder = new OneTimeBuilder();
        time = System.currentTimeMillis();
        builder.setTime(time);
        builder.setSnooze(20);
        builder.setVibrate(false);
        builder.setRingtoneURI("default");
        alarm = builder.build();
    }

    @Test
    public void properBuildTest(){
        assertEquals("default",alarm.getRingtoneURI());
        assertEquals(20,alarm.getSnoozeLength());
        assertEquals(false,alarm.getVibrateOn());
        assertTrue(time.equals(alarm.getTime()));

    }
}
