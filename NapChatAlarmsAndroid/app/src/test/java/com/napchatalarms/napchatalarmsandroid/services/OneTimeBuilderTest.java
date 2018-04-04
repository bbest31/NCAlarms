package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.OneTimeAlarm;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by bbest on 11/02/18.
 */


@SuppressWarnings("DefaultFileTemplate")
public class OneTimeBuilderTest {
    private OneTimeAlarm alarm;
    private Long time;
    @Before
    public void init(){
        OneTimeBuilder builder = new OneTimeBuilder();
        time = System.currentTimeMillis();
        builder.setTime(time);
        builder.setSnooze(20);
        builder.setVibrate(-1);
        builder.setRingtoneURI("default");
        alarm = builder.build();
    }

    @Test
    public void properBuildTest(){
        assertEquals("default",alarm.getRingtoneURI());
        assertEquals(20,alarm.getSnoozeLength());
        assertTrue(time.equals(alarm.getTime()));

    }
}
