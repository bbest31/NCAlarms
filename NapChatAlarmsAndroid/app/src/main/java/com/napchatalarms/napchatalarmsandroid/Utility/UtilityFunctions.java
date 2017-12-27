package com.napchatalarms.napchatalarmsandroid.Utility;

import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Patterns;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;
import java.util.Date;

/**Class to hold all functions that may be useful by a multitude of classes.
 * Created by bbest on 17/11/17.
 */

public class UtilityFunctions {

    /**
     * This method compares a given input against the email pattern and returns a boolean
     * to indicate whether it is in email format or not.
     * SOURCE: https://stackoverflow.com/questions/9355899/android-email-edittext-validation?noredirect=1&lq=1
     * */
    public final static boolean isValidEmail(CharSequence email){
        if(email == null){
            return false;
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }


    /**
     * Returns a boolean to indicate whether the password has only alphabetic and numeric characters.
     * SOURCE: https://stackoverflow.com/questions/10344493/android-how-to-set-acceptable-numbers-and-characters-in-edittext
     * */
    public final static boolean isValidPassword(String password){

        for (int i = 0; i < password.length();i++){
            if(!Character.isLetterOrDigit(password.charAt(i))){
                return false;
            }
        }

        return true;
    }

    public final static boolean isValidUsername(String name){
        for(int i = 0; i< name.length();i++){
            if(!Character.isLetterOrDigit(name.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * Take in the hour and minute of a time and make a UTC milliseconds such that the
     * returning value is in the future.
     * **/
    public final static long UTCMilliseconds(int hour,int minute){

        //Gets the current date/time
        Calendar cal = Calendar.getInstance();
        //If the time set is later that day
        if(hour > cal.get(Calendar.HOUR_OF_DAY) && minute > cal.get(Calendar.MINUTE)){

            cal.set(Calendar.HOUR_OF_DAY,hour);
            cal.set(Calendar.MINUTE,minute);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);

            return cal.getTimeInMillis();
        } else {
            //If the time is earlier than the current time we increase the day by 1.
            int day = cal.get(Calendar.DATE);

            cal.set(Calendar.DATE, day+1);
            cal.set(Calendar.HOUR_OF_DAY,hour);
            cal.set(Calendar.MINUTE,minute);
            cal.set(Calendar.SECOND,0);
            cal.set(Calendar.MILLISECOND,0);

            return cal.getTimeInMillis();
        }


    }

    //TODO:custom Vibration patterns.
}
