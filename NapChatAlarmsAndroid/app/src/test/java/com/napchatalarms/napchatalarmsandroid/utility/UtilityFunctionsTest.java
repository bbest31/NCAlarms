package com.napchatalarms.napchatalarmsandroid.utility;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilityFunctionsTest {
    @Test
    @Ignore
    public void addition_isCorrect() throws Exception {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.set(Calendar.DAY_OF_WEEK,day+7);
        assertEquals(day, cal.get(Calendar.DAY_OF_WEEK));
    }

    @Test
    public void validUsernameTest(){
        assertTrue(UtilityFunctions.isValidUsername("bbest31"));
        assertFalse(UtilityFunctions.isValidUsername("bbest.devgmail.com"));
        assertFalse(UtilityFunctions.isValidUsername("bbest$%^dev@gm__ail.com"));
        assertFalse(UtilityFunctions.isValidUsername(""));
        assertFalse(UtilityFunctions.isValidUsername(" "));
        assertFalse(UtilityFunctions.isValidUsername("abc"));
        assertFalse(UtilityFunctions.isValidUsername("brandonbest1234567890"));
    }

    @Test
    public void validPasswordTest(){
        assertTrue(UtilityFunctions.isValidPassword("pwd1234556789"));
        assertFalse(UtilityFunctions.isValidPassword(""));
        assertFalse(UtilityFunctions.isValidPassword("!#(%&$^(*!)%*!%&"));
        assertFalse(UtilityFunctions.isValidPassword("1234567890abcenfelknfoffffffffffffffffffihgaeroihaoirjfthstb1"));


    }

    @Test
    public void utcMilliTest(){
        Calendar calendar = Calendar.getInstance();
        assertTrue(calendar.getTimeInMillis() < UtilityFunctions.UTCMilliseconds(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)));

    }

    @Test
    public void validOneTimeTriggerTest(){
        //Trigger that is already in the future
        assertTrue(UtilityFunctions.validateOneTimeTrigger(System.currentTimeMillis()+60000) > System.currentTimeMillis());
        //Trigger that is in the past
        //behind by one day
        assertTrue(UtilityFunctions.validateOneTimeTrigger(System.currentTimeMillis()-86400000) > System.currentTimeMillis());
        //behind by one week
        assertTrue(UtilityFunctions.validateOneTimeTrigger(System.currentTimeMillis()-604800000) > System.currentTimeMillis());
        //behind by one month
        assertTrue(UtilityFunctions.validateOneTimeTrigger(System.currentTimeMillis()-(86400000*30)) > System.currentTimeMillis());
        //Trigger that is the same time as current.
        assertTrue(UtilityFunctions.validateOneTimeTrigger(System.currentTimeMillis()) > System.currentTimeMillis());


    }

    @Test
    public void validateRepeatingTriggerTest(){
        //Trigger that is already in the future
        assertTrue(UtilityFunctions.validateRepeatTrigger(System.currentTimeMillis()+60000) > System.currentTimeMillis());
        //Trigger that is in the past
        //behind by one day
        assertTrue(UtilityFunctions.validateRepeatTrigger(System.currentTimeMillis()-86400000) > System.currentTimeMillis());
        //behind by one week
        assertTrue(UtilityFunctions.validateRepeatTrigger(System.currentTimeMillis()-604800000) > System.currentTimeMillis());
        //behind by one month
        assertTrue(UtilityFunctions.validateRepeatTrigger(System.currentTimeMillis()-(86400000*30)) > System.currentTimeMillis());
        //Trigger that is the same time as current.
        assertTrue(UtilityFunctions.validateRepeatTrigger(System.currentTimeMillis()) > System.currentTimeMillis());

    }

    @Test
    public void stringSplitTest(){
        String path = "android.resource//:com.napchatalarms.napchatalarmsandroid/raw/mysound.m4a";
        assertTrue(path.split("/")[0].equals("android.resource"));
    }
}