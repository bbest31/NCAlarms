package com.napchatalarms.napchatalarmsandroid.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.VibratePattern;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to hold all functions that may be useful by a multitude of classes.
 *
 * @author bbest
 */
@SuppressWarnings("unused")
public class UtilityFunctions {

    private static final long[] HEARTBEAT = {0, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500};
    private static final long[] BUZZSAW = {0, 10000};
    private static final long[] LOCOMOTIVE = {0, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600};

    //TODO need to have Vibrate Pattern names from strings.xml
    private static final VibratePattern LOCOMOTIVE_PATTERN = new VibratePattern(0, "Locomotive", LOCOMOTIVE);
    private static final VibratePattern HEARTBEAT_PATTERN = new VibratePattern(2, "Heartbeat", HEARTBEAT);
    private static final VibratePattern BUZZSAW_PATTERN = new VibratePattern(1, "Buzzsaw", BUZZSAW);

    /**
     * This method compares a given input against the email pattern and returns a boolean
     * to indicate whether it is in email format or not.
     * SOURCE: https://stackoverflow.com/questions/9355899/android-email-edittext-validation?noredirect=1&lq=1
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();

    }

    /**
     * Returns a boolean to indicate whether the password has only alphabetic and numeric characters.
     * SOURCE: https://stackoverflow.com/questions/10344493/android-how-to-set-acceptable-numbers-and-characters-in-edittext
     *
     * @param password the password
     * @return the boolean
     */
    public static boolean isValidPassword(String password) {

        if (password.length() < 8 || password.trim().isEmpty() || password.length() > 25) {
            return false;
        }
        Pattern regex = Pattern.compile("[!@#$%^&*]");
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i)) && !regex.matcher(String.valueOf(password.charAt(i))).matches() ) {
                return false;
            }
        }

        return true;
    }

    /**
     * Is valid username boolean.
     *
     * @param name the name
     * @return boolean
     */
    public static boolean isValidUsername(String name) {
        boolean valid = true;
        if (name.trim().isEmpty()) {
            valid = false;
        } else if (name.length() < 4) {
            valid = false;
        } else if (name.length() > 15) {
            valid = false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i))) {
                valid = false;
            }
        }
        return valid;
    }

    /**
     * Take in the hour and minute of a time and make a UTC milliseconds such that the
     * returning value is the next occurrence of that time.
     *
     * @param hour   the hour
     * @param minute the minute
     * @return the long
     */
    public static long UTCMilliseconds(int hour, int minute) {
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

            //If the time is later than the current time by minutes.
            cal.set(Calendar.HOUR_OF_DAY, hour);
            cal.set(Calendar.MINUTE, minute);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            timeMilli = cal.getTimeInMillis();

        } else if (hour == cal.get(Calendar.HOUR_OF_DAY) && minute < cal.get(Calendar.MINUTE)) {

            //If the time is earlier than the current time by minutes we increase the day by 1.
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

        //noinspection ConstantConditions
        return timeMilli;
    }

    /**
     * Validate one time trigger long.
     *
     * @param trigger the trigger
     * @return long
     */
    public static Long validateOneTimeTrigger(Long trigger) {
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
     * Validate repeat trigger long.
     *
     * @param trigger the trigger
     * @return long
     */
    public static Long validateRepeatTrigger(Long trigger) {

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

    /**
     * Generate repeat text string.
     *
     * @param days the days
     * @return the string
     */
    public static String generateRepeatText(List<Integer> days, Context context) {
        Collections.sort(days);
        String repeatText = "";
        if (days.size() != 0 && days.size() != 7) {
            if (days.contains(1) && days.contains(7) && days.size() == 2) {
                repeatText = context.getString(R.string.weekends);
            } else if (!days.contains(1) && !days.contains(7) && days.size() == 5) {
                repeatText = context.getString(R.string.weekdays);
            } else {

                for (Integer day : days) {
                    switch (day) {
                        case 1:
                            repeatText = repeatText.concat(context.getString(R.string.sunday_short));
                            repeatText = repeatText.concat(" ");
                            break;
                        case 2:
                            repeatText = repeatText.concat(context.getString(R.string.monday_short));
                            repeatText = repeatText.concat(" ");
                            break;
                        case 3:
                            repeatText = repeatText.concat(context.getString(R.string.tuesday_short));
                            repeatText = repeatText.concat(" ");
                            break;
                        case 4:
                            repeatText = repeatText.concat(context.getString(R.string.wednesday_short));
                            repeatText = repeatText.concat(" ");
                            break;
                        case 5:
                            repeatText = repeatText.concat(context.getString(R.string.thursday_short));
                            repeatText = repeatText.concat(" ");
                            break;
                        case 6:
                            repeatText = repeatText.concat(context.getString(R.string.friday_short));
                            repeatText = repeatText.concat(" ");
                            break;
                        case 7:
                            repeatText = repeatText.concat(context.getString(R.string.saturday_short));
                            break;
                    }
                }
            }

            return repeatText;
        } else if (days.size() == 7) {
            repeatText = context.getString(R.string.everyday);
            return repeatText;
        } else {
            return null;
        }
    }

    /**
     * Vibrate pattern is in milliseconds. First number indicates the time to wait
     * to start vibrating when notification fires. Second number is the time to vibrate
     * and then turn off. Subsequent numbers indicate times that the vibration is off,on,off,etc.
     *
     * @param i the
     * @return the vibrate pattern
     */
    public static VibratePattern getVibratePattern(Integer i) {
        VibratePattern pattern = null;
        switch (i) {
            case 0:
                pattern = LOCOMOTIVE_PATTERN;
                break;
            case 1:
                pattern = BUZZSAW_PATTERN;
                break;
            case 2:
                pattern = HEARTBEAT_PATTERN;
                break;
        }
        return pattern;
    }

}
