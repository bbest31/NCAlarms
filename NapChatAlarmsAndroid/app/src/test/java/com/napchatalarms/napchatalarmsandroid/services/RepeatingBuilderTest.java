package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.RepeatingAlarm;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by bbest on 11/02/18.
 */

public class RepeatingBuilderTest {
    RepeatingAlarm alarm1;
    RepeatingAlarm alarm2;

    @Before
    public void init(){
        List<Integer> days = Arrays.asList(1,2,3,4,5,6,7);
        RepeatingBuilder builder = new RepeatingBuilder();
        builder.initialize(days);
        builder.setSnooze(5);
        builder.setRingtoneURI("default");
        builder.setVibrate(true);
        builder.setInterval();
        builder.setTime(System.currentTimeMillis());
        alarm1 = builder.build();


        List<Integer> days2 = Arrays.asList(1,3,6,7);
        RepeatingBuilder secondBuilder = new RepeatingBuilder();
        secondBuilder.initialize(days2);
        secondBuilder.setInterval();
        secondBuilder.setVibrate(false);
        secondBuilder.setRingtoneURI("custom");
        secondBuilder.setSnooze(10);
        secondBuilder.setTime(System.currentTimeMillis()+600000);
        alarm2 = secondBuilder.build();
    }

    @Test
    public void properAlarmDateTimeTest(){
        System.out.println(alarm1.toString());

        System.out.println(alarm2.toString());

    }
}
