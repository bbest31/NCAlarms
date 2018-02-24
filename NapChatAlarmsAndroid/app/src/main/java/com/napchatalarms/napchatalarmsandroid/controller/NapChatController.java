package com.napchatalarms.napchatalarmsandroid.controller;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * @author bbest
 */

public class NapChatController {

    private static final NapChatController instance = new NapChatController();

    private NapChatController() {
    }

    public static NapChatController getInstance() {
        return instance;
    }

    //=====METHODS=====

    /**
     * @param context
     * @throws IOException
     */
    public void createUserFiles(Context context) throws IOException {
        try {
            //createUserSettingsFile(context);
            createUserAlarmFile(context);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * @param context
     * @throws IOException
     */
    public void createUserSettingsFile(Context context) throws IOException {

        User user = User.getInstance();

        String filename = formatEmail(User.getInstance().getEmail()) + "SETT.ser";
        File settingsFile = new File(context.getFilesDir().getAbsolutePath(), filename);


    }

    /**
     * @param context
     * @throws IOException
     */
    public void createUserAlarmFile(Context context) throws IOException {

        String filename = formatEmail(User.getInstance().getEmail()) + "ALRM.ser";
        File alarmFile = new File(context.getFilesDir().getAbsolutePath(), filename);
        alarmFile.createNewFile();
        Log.i("NapChatController", "Sucessfull created user alarm file");

    }

    /**
     * Loads the file which contains the user settings
     **/
    public void loadUserSettings() throws IOException {
        //loads file and looks for settings for the matching user name.
        //if none exists we add a space for it.
    }

    /**
     *
     */
    public void saveUserSettings() {
    }

    /**
     * @param context
     * @throws IOException
     */
    public void saveUserAlarms(Context context) throws IOException {
        try {
            User user = User.getInstance();
            //gets users directory
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(formatEmail(user.getEmail()) + "ALRM.ser", Context.MODE_PRIVATE);
            outputStream.flush();

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

            objectOutputStream.writeObject(user.getAlarmList());

            objectOutputStream.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param context
     * @throws IOException
     */
    public void loadUserAlarms(Context context) throws IOException {
        try {
            User user = User.getInstance();
            FileInputStream file = context.openFileInput(formatEmail(user.getEmail()) + "ALRM.ser");
            ObjectInputStream inputStream = new ObjectInputStream(file);
            ArrayList<Alarm> list = (ArrayList<Alarm>) inputStream.readObject();
            user.setAlarmList(list);
            inputStream.close();
            file.close();

        } catch (IOException e) {
            Log.e("NapChatController", "No alarm file exists or no object present: " + e.getMessage());
            throw new IOException();
        } catch (ClassNotFoundException c) {
            Log.e("NapChatController", "Class not found exception " + c.getMessage());
            c.printStackTrace();
            throw new IOException();
        }
    }

    /**
     * @param context
     * @throws IOException
     */
    public void deleteFiles(Context context) throws IOException {

        File dir = context.getFilesDir();
        File alarmFile = new File(dir, formatEmail(User.getInstance().getEmail()) + "ALRM.ser");
       // File settingsFile = new File(dir, formatEmail(User.getInstance().getEmail()) + "SETT.ser");
        alarmFile.delete();
        //settingsFile.delete();
    }

    /**
     * @param context
     */
    public void loadUserData(Context context) {
        loadUserInfo();
        try {
            loadUserAlarms(context);
        } catch (IOException e) {
            try {
                createUserFiles(context);
            } catch (IOException e1) {
            }
            Log.e("NapChatController", "Fail to load user alarms.");
        }
    }

    public void initNotificationChannel(Context context) {
        int buildVersion = Build.VERSION.SDK_INT;
        if (buildVersion >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                    NOTIFICATION_SERVICE);
            try {
                notificationManager.getNotificationChannel("alarm");
            } catch (RuntimeException e) {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
                Log.i("Init Notify Channel", "Initializing the alarm Notification Channel");
                CharSequence name = "alarm channel";
                String description = "application alarms";
                NotificationChannel channel = new NotificationChannel("alarm", name, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription(description);
                notificationManager.createNotificationChannel(channel);
            }
        }

    }

    /**
     * @param email
     * @return
     */
    private String formatEmail(String email) {
        String newEmail = email.replace("@", "_");
        newEmail = newEmail.replace(".", "_");
        return newEmail;
    }

    public void loadUserInfo() {
        User.getInstance().setName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        User.getInstance().setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        User.getInstance().setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }


}
