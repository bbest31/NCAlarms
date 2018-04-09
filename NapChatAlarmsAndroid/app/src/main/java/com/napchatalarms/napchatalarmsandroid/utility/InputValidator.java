package com.napchatalarms.napchatalarmsandroid.utility;

import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * Created by bbest on 09/04/18.
 */

public class InputValidator {

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
        Pattern regex = Pattern.compile("[!@#$%^&*?]");
        for (int i = 0; i < password.length(); i++) {
            if (!Character.isLetterOrDigit(password.charAt(i)) && !regex.matcher(String.valueOf(password.charAt(i))).matches()) {
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
}
