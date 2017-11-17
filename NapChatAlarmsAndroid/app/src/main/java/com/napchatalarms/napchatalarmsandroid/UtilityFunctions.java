package com.napchatalarms.napchatalarmsandroid;

import android.util.Patterns;

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
}
