package com.napchatalarms.napchatalarmsandroid.utility;

import android.content.Context;

import com.napchatalarms.napchatalarmsandroid.R;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Class to hold all functions that may be useful by a multitude of classes.
 *
 * @author bbest
 */
@SuppressWarnings("unused")
public class UtilityFunctions {

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

}
