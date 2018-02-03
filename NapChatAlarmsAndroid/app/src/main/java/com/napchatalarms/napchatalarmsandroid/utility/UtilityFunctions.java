package com.napchatalarms.napchatalarmsandroid.utility;

import android.Manifest;
import android.app.Activity;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**Class to hold all functions that may be useful by a multitude of classes.
 * @author bbest
 */

public class UtilityFunctions {

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
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetterOrDigit(name.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Take in the hour and minute of a time and make a UTC milliseconds such that the
     * returning value is in the future.
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
     *
     * @param trigger
     * @return
     */
    public final static Long validateOneTimeTrigger(Long trigger){
        Calendar currTime = Calendar.getInstance();
        Calendar alarmTime = Calendar.getInstance();
        currTime.setTimeInMillis(System.currentTimeMillis());
        alarmTime.setTimeInMillis(trigger);

        if(currTime.get(Calendar.DATE) > alarmTime.get(Calendar.DATE)){
            //The day is a day in the past we so increment until it is the same day
            while(currTime.get(Calendar.DATE) > alarmTime.get(Calendar.DATE)){
                alarmTime.add(Calendar.DATE,1);
            }
            if(currTime.get(Calendar.HOUR) > alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is for earlier this day we increase by a day
                alarmTime.add(Calendar.DATE,1);
            } else if(currTime.get(Calendar.HOUR) == alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is the same day and hour we check the minutes
                if(currTime.get(Calendar.MINUTE) >= alarmTime.get(Calendar.MINUTE)){
                    alarmTime.add(Calendar.DATE,1);
                }
            }
        }  else if(currTime.get(Calendar.DATE) == alarmTime.get(Calendar.DATE)){
            if(currTime.get(Calendar.HOUR) > alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is for earlier this day we increase by a day
                alarmTime.add(Calendar.DATE,1);

            } else if(currTime.get(Calendar.HOUR) == alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is the same day and hour we check the minutes
                if(currTime.get(Calendar.MINUTE) >= alarmTime.get(Calendar.MINUTE)){
                    alarmTime.add(Calendar.DATE,1);
                }
            }
        }

        return alarmTime.getTimeInMillis();

    }

    public final static Long validateRepeatTrigger(Long trigger){

        Calendar currTime = Calendar.getInstance();
        Calendar alarmTime = Calendar.getInstance();
        currTime.setTimeInMillis(System.currentTimeMillis());
        alarmTime.setTimeInMillis(trigger);

        if(currTime.get(Calendar.DATE) > alarmTime.get(Calendar.DATE)){
            while(currTime.get(Calendar.DATE) > alarmTime.get(Calendar.DATE)){
                alarmTime.add(Calendar.WEEK_OF_YEAR,1);
            }

            if(currTime.get(Calendar.HOUR) > alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is for earlier this day we increase by a day
                alarmTime.add(Calendar.WEEK_OF_YEAR,1);

            } else if(currTime.get(Calendar.HOUR) == alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is the same day and hour we check the minutes
                if(currTime.get(Calendar.MINUTE) >= alarmTime.get(Calendar.MINUTE)){
                    alarmTime.add(Calendar.WEEK_OF_YEAR,1);
                }
            }

        } else if(currTime.get(Calendar.DATE) == alarmTime.get(Calendar.DATE)){
            if(currTime.get(Calendar.HOUR) > alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is for earlier this day we increase by a day
                alarmTime.add(Calendar.WEEK_OF_YEAR,1);

            } else if(currTime.get(Calendar.HOUR) == alarmTime.get(Calendar.HOUR)){
                //if the local alarm time var is the same day and hour we check the minutes
                if(currTime.get(Calendar.MINUTE) >= alarmTime.get(Calendar.MINUTE)){
                    alarmTime.add(Calendar.WEEK_OF_YEAR,1);
                }
            }
        }

        return alarmTime.getTimeInMillis();

    }


    //TODO: detect weekends, and weekdays settings.
    public final static String generateRepeatText(List<Integer> days){
        String repeatText = "";
        if(days.size() != 0 && days.size() != 7) {
            for (Iterator<Integer> iterator = days.listIterator(); iterator.hasNext();) {
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
        } else if(days.size() == 7) {
            repeatText = "Every Day";
            return repeatText;
        } else {
            return null;
        }
    }




    //TODO:custom Vibration patterns.
}
