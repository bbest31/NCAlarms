package com.napchatalarms.napchatalarmsandroid.utility;

import android.util.Patterns;

import com.napchatalarms.napchatalarmsandroid.model.VibratePattern;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Class to hold all functions that may be useful by a multitude of classes.
 *
 * @author bbest
 */

public class UtilityFunctions {

    private static final long[] HEARTBEAT = {0,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500,200,100,200,500};
    private static final long[] BUZZSAW = {0,10000};
    private static final long[] LOCOMOTIVE = {0,500,100,500,600,500,100,500,600,500,100,500,600,500,100,500,600,500,100,500,600,500,100,500,600,500,100,500,600,500,100,500,600,500,100,500,600};
    private static final long[] TIPTOE = {0,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150};

    private static final VibratePattern LOCOMOTIVE_PATTERN = new VibratePattern(0,"Locomotive",LOCOMOTIVE);
    private static final VibratePattern HEARTBEAT_PATTERN = new VibratePattern(1,"Heartbeat",HEARTBEAT);
    private static final VibratePattern BUZZSAW_PATTERN = new VibratePattern(2,"Buzzsaw",BUZZSAW);
    private static final VibratePattern TIPTOE_PATTERN = new VibratePattern(3,"Tip-toe",TIPTOE);

    /**
     * This method compares a given input against the email pattern and returns a boolean
     * to indicate whether it is in email format or not.
     * SOURCE: https://stackoverflow.com/questions/9355899/android-email-edittext-validation?noredirect=1&lq=1
     */
    public final static boolean isValidEmail(CharSequence email) {
        if (email == null) {
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Returns a boolean to indicate whether the password has only alphabetic and numeric characters.
     * SOURCE: https://stackoverflow.com/questions/10344493/android-how-to-set-acceptable-numbers-and-characters-in-edittext
     */
    public final static boolean isValidPassword(String password) {

        if (password.length() < 8 || password.trim().isEmpty() || password.length() > 25) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param name
     * @return
     */
    public final static boolean isValidUsername(String name) {
        boolean valid = true;
        if (name.trim().isEmpty()) {
            valid = false;
        } else if(name.length() < 4){
            valid = false;
        }else if(name.length() > 15){
            valid = false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i))) {
                valid =  false;
            }
        }
        return valid;
    }

    /**
     * Take in the hour and minute of a time and make a UTC milliseconds such that the
     * returning value is the next occurrence of that time.
     **/
    public final static long UTCMilliseconds(int hour, int minute) {
        Long timeMilli = null;
        //Gets the current date/time
        Calendar cal = Calendar.getInstance();
        //If the time set is later that day
        if (hour > cal.get(Calendar.HOUR_OF_DAY)) {

            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            timeMilli = cal.getTimeInMillis();
        } else if (hour == cal.get(Calendar.HOUR_OF_DAY) && minute > cal.get(Calendar.MINUTE)) {
            //If the time is earlier than the current time we increase the day by 1.
            int day = cal.get(Calendar.DATE);

            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            timeMilli = cal.getTimeInMillis();
        } else if (hour == cal.get(Calendar.HOUR_OF_DAY) && minute < cal.get(Calendar.MINUTE)) {
            //If the time is earlier than the current time we increase the day by 1.
            int day = cal.get(Calendar.DATE);

            cal.set(Calendar.DATE, day + 1);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            timeMilli = cal.getTimeInMillis();
        } else if (hour < cal.get(Calendar.HOUR_OF_DAY)) {
            //If the time is earlier than the current time we increase the day by 1.
            int day = cal.get(Calendar.DATE);

            cal.set(Calendar.DATE, day + 1);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            timeMilli = cal.getTimeInMillis();
        } else if (hour == cal.get(Calendar.HOUR_OF_DAY) && minute == cal.get(Calendar.MINUTE)) {
            //If the time is exactly the current time we increase the day by 1.
            int day = cal.get(Calendar.DATE);

            cal.set(Calendar.DATE, day + 1);
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            timeMilli = cal.getTimeInMillis();
        }

        return timeMilli;
    }

    /**
     * @param trigger
     * @return
     */
    public final static Long validateOneTimeTrigger(Long trigger) {
        Calendar currTime = Calendar.getInstance();
        Calendar alarmTime = Calendar.getInstance();
        currTime.setTime(new Date(System.currentTimeMillis()));
        alarmTime.setTime(new Date(trigger));

        alarmTime.set(Calendar.YEAR, currTime.get(Calendar.YEAR));
        alarmTime.set(Calendar.MONTH, currTime.get(Calendar.MONTH));
        alarmTime.set(Calendar.DATE, currTime.get(Calendar.DATE));

        if (currTime.get(Calendar.HOUR_OF_DAY) > alarmTime.get(Calendar.HOUR_OF_DAY)) {
            //if the local alarm time var is for earlier this day we increase by a day
            alarmTime.add(Calendar.DATE, 1);
        } else if (currTime.get(Calendar.HOUR_OF_DAY) == alarmTime.get(Calendar.HOUR_OF_DAY)) {
            //if the local alarm time var is the same hour we check the minutes
            if (currTime.get(Calendar.MINUTE) >= alarmTime.get(Calendar.MINUTE)) {
                alarmTime.add(Calendar.DATE, 1);
            }
        }

        return alarmTime.getTimeInMillis();

    }

    /**
     * @param trigger
     * @return
     */
    public final static Long validateRepeatTrigger(Long trigger) {

        Calendar currTime = Calendar.getInstance();
        Calendar alarmTime = Calendar.getInstance();
        currTime.setTimeInMillis(System.currentTimeMillis());
        alarmTime.setTimeInMillis(trigger);

        int dayOfWeek = alarmTime.get(Calendar.DAY_OF_WEEK);
        alarmTime.set(Calendar.YEAR, currTime.get(Calendar.YEAR));
        alarmTime.set(Calendar.MONTH, currTime.get(Calendar.MONTH));
        alarmTime.set(Calendar.DATE, currTime.get(Calendar.DATE));

        //while our alarm is not for the proper day of the week we increase the day.
        while (alarmTime.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
            alarmTime.add(Calendar.DATE, 1);
        }

        if (alarmTime.get(Calendar.DATE) == currTime.get(Calendar.DATE)) {

            if (alarmTime.get(Calendar.HOUR_OF_DAY) < currTime.get(Calendar.HOUR_OF_DAY)) {
                alarmTime.add(Calendar.DATE, 7);
            } else if (alarmTime.get(Calendar.HOUR_OF_DAY) == currTime.get(Calendar.HOUR_OF_DAY)) {
                if (alarmTime.get(Calendar.MINUTE) <= currTime.get(Calendar.MINUTE)) {
                    alarmTime.add(Calendar.DATE, 7);
                }
            }
        }

        return alarmTime.getTimeInMillis();

    }


    //TODO: detect weekends, and weekdays settings.
    public final static String generateRepeatText(List<Integer> days) {
        String repeatText = "";
        if (days.size() != 0 && days.size() != 7) {
            for (Iterator<Integer> iterator = days.listIterator(); iterator.hasNext(); ) {
                switch (iterator.next()) {
                    case 1:
                        repeatText = repeatText.concat("Sun,");
                        break;
                    case 2:
                        repeatText = repeatText.concat("Mon,");
                        break;
                    case 3:
                        repeatText = repeatText.concat("Tues,");
                        break;
                    case 4:
                        repeatText = repeatText.concat("Wed,");
                        break;
                    case 5:
                        repeatText = repeatText.concat("Thurs,");
                        break;
                    case 6:
                        repeatText = repeatText.concat("Fri,");
                        break;
                    case 7:
                        repeatText = repeatText.concat("Sat,");
                        break;
                }
            }

            return repeatText;
        } else if (days.size() == 7) {
            repeatText = "Every Day";
            return repeatText;
        } else {
            return null;
        }
    }

       /**
     * Vibrate pattern is in milliseconds. First number indicates the time to wait
     * to start vibrating when notification fires. Second number is the time to vibrate
     * and then turn off. Subsequent numbers indicate times that the vibration is off,on,off,etc.
     **/
    public final static VibratePattern getVibratePattern(Integer i){
        VibratePattern pattern = null;
        switch(i){
            case 0:
                pattern = LOCOMOTIVE_PATTERN;
                break;
            case 1:
                pattern = HEARTBEAT_PATTERN;
                break;
            case 2:
                pattern = BUZZSAW_PATTERN;
                break;
            case 3:
                pattern = TIPTOE_PATTERN;
                break;
        }
        return  pattern;
    }
}
