package com.napchatalarms.napchatalarmsandroid.utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Created by bbest on 08/04/18.
 */

public class Toaster {
    /**
     * Constructs a custom toast near the top of the activity of an amber color with the message
     * "We're working on it!"
     * Used as a placeholder for an onclick method.
     *
     * @param activity the activity
     * @param inflater the inflater
     * @return toast
     */
    public static Toast createWarningToast(Activity activity, LayoutInflater inflater) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        View layout = inflater.inflate(R.layout.toast_construction_warn,
                (ViewGroup) activity.findViewById(R.id.warning_toast_container));
        toast.setView(layout);
        return toast;
    }

    /**
     * Create email success toast toast.
     *
     * @param activity the activity
     * @param inflater the inflater
     * @return the toast
     */
    public static Toast createEmailSuccessToast(Activity activity, LayoutInflater inflater) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        View layout = inflater.inflate(R.layout.toast_email_success,
                (ViewGroup) activity.findViewById(R.id.email_success_toast_container));
        toast.setView(layout);
        return toast;
    }

    /**
     * Create invalid username toast toast.
     *
     * @param activity the activity
     * @param inflater the inflater
     * @return the toast
     */
    public static Toast createInvalidUsernameToast(Activity activity, LayoutInflater inflater) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        View layout = inflater.inflate(R.layout.toast_signup_user_err,
                (ViewGroup) activity.findViewById(R.id.signup_user_err_container));
        toast.setView(layout);
        return toast;
    }

    /**
     * Create alarm created toast toast.
     *
     * @param activity the activity
     * @param inflater the inflater
     * @return the toast
     */
    public static Toast createAlarmCreatedToast(Activity activity, LayoutInflater inflater) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity, "", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 100);
        View layout = inflater.inflate(R.layout.toast_alarm_created,
                (ViewGroup) activity.findViewById(R.id.alarm_created_toast_container));
        toast.setView(layout);
        return toast;
    }

    /**
     * Create invalid credentials toast.
     *
     * @param activity the activity
     * @param inflater the inflater
     * @return the toast
     */
    @SuppressWarnings("unused")
    public static Toast createInvalidCredentials(Activity activity, LayoutInflater inflater) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        View layout = inflater.inflate(R.layout.toast_invalid_cred,
                (ViewGroup) activity.findViewById(R.id.login_toast_container));
        toast.setView(layout);
        return toast;
    }

    /**
     * Create invalid email toast toast.
     *
     * @param activity the activity
     * @param inflater the inflater
     * @return the toast
     */
    public static Toast createInvalidEmailToast(Activity activity, LayoutInflater inflater) {
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity, "", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 100);
        View layout = inflater.inflate(R.layout.toast_invalid_email,
                (ViewGroup) activity.findViewById(R.id.invalid_email_layout));
        toast.setView(layout);
        return toast;
    }

    public static Toast createConnectionErrorToast(Activity activity, LayoutInflater inflater){
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity,"",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,100);
        View layout = inflater.inflate(R.layout.toast_conn_error,
                (ViewGroup) activity.findViewById(R.id.conn_error_layout));
        toast.setView(layout);
        return toast;
    }

    public static Toast createLoginFailToast(Activity activity, LayoutInflater inflater){
        @SuppressLint("ShowToast") Toast toast = Toast.makeText(activity,"",Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,100);
        View layout = inflater.inflate(R.layout.toast_login_error,
                (ViewGroup) activity.findViewById(R.id.login_error_layout));
        toast.setView(layout);
        return toast;
    }
}
