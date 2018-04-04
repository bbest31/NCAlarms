package com.napchatalarms.napchatalarmsandroid.controller;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

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
 * The type Nap chat controller.
 *
 * @author bbest
 */
@SuppressWarnings("unused")
public class NapChatController {

    private static final NapChatController instance = new NapChatController();

    private NapChatController() {

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static NapChatController getInstance() {
        return instance;
    }

    //=====METHODS=====

    /**
     * Create user files.
     *
     * @param context the context
     * @throws IOException the io exception
     */
    public void createUserFiles(Context context) throws IOException {
        try {
            createUserSettingsFile(context);
            createUserAlarmFile(context);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    /**
     * Create user settings file.
     *
     * @param context the context
     */
    @SuppressWarnings("unused")
    private void createUserSettingsFile(Context context) {

        @SuppressWarnings("unused") User user = User.getInstance();

        String filename = formatEmail(User.getInstance().getEmail()) + "SETT.ser";
        @SuppressWarnings("unused") File settingsFile = new File(context.getFilesDir().getAbsolutePath(), filename);
        //settingsFile.createNewFile();

    }

    /**
     * Create user alarm file.
     *
     * @param context the context
     * @throws IOException the io exception
     */
    private void createUserAlarmFile(Context context) throws IOException {

        String filename = formatEmail(User.getInstance().getEmail()) + "ALRM.ser";
        File alarmFile = new File(context.getFilesDir().getAbsolutePath(), filename);
        //noinspection ResultOfMethodCallIgnored
        alarmFile.createNewFile();
//        Log.i("NapChatController", "Successfully created user alarm file");

    }

    /**
     * Save user alarms.
     *
     * @param context the context
     */
    public void saveUserAlarms(Context context) {
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
     * Load user alarms.
     *
     * @param context the context
     * @throws IOException the io exception
     */
    public void loadUserAlarms(Context context) throws IOException {
        try {
            User user = User.getInstance();
            FileInputStream file = context.openFileInput(formatEmail(user.getEmail()) + "ALRM.ser");
            ObjectInputStream inputStream = new ObjectInputStream(file);
            @SuppressWarnings("unchecked") ArrayList<Alarm> list = (ArrayList<Alarm>) inputStream.readObject();
            user.setAlarmList(list);
            inputStream.close();
            file.close();

        } catch (IOException e) {
//            Log.e("NapChatController", "No alarm file exists or no object present: " + e.getMessage());
            throw new IOException();
        } catch (ClassNotFoundException c) {
//            Log.e("NapChatController", "Class not found exception " + c.getMessage());
            c.printStackTrace();
            throw new IOException();
        }
    }

    /**
     * Delete files.
     *
     * @param context the context
     */
    public void deleteFiles(Context context) {

        File dir = context.getFilesDir();
        File alarmFile = new File(dir, formatEmail(User.getInstance().getEmail()) + "ALRM.ser");
        //File settingsFile = new File(dir, formatEmail(User.getInstance().getEmail()) + "SETT.ser");
        //noinspection ResultOfMethodCallIgnored
        alarmFile.delete();
        //settingsFile.delete();
    }

    /**
     * Load user data.
     *
     * @param context the context
     */
    public void loadUserData(Context context) {
        loadUserInfo();
        try {
            loadUserAlarms(context);
        } catch (IOException e) {
            //noinspection EmptyCatchBlock
            try {
                createUserFiles(context);
            } catch (IOException e1) {

            }
//            Log.e("NapChatController", "Fail to load user alarms.");
        }
    }

    /**
     * Init notification channel.
     *
     * @param context the context
     */
    public void initNotificationChannel(Context context) {
        int buildVersion = Build.VERSION.SDK_INT;
        if (buildVersion >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                    NOTIFICATION_SERVICE);
            try {
                //noinspection ConstantConditions
                notificationManager.getNotificationChannel("alarm");
            } catch (RuntimeException e) {
                // Create the NotificationChannel, but only on API 26+ because
                // the NotificationChannel class is new and not in the support library
//                Log.i("Init Notify Channel", "Initializing the alarm Notification Channel");
                CharSequence name = "alarm channel";
                String description = "application alarms";
                NotificationChannel channel = new NotificationChannel("alarm", name, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription(description);
                //noinspection ConstantConditions
                notificationManager.createNotificationChannel(channel);
            }
        }

    }

    /**
     * @param email - the user email.
     * @return newly formatted email which has @ and . replaced with underscore
     */
    private String formatEmail(String email) {
        String newEmail = email.replace("@", "_");
        newEmail = newEmail.replace(".", "_");
        return newEmail;
    }

    /**
     * Load user info.
     */
    private void loadUserInfo() {
        //noinspection ConstantConditions
        User.getInstance().setName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        //noinspection ConstantConditions
        User.getInstance().setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //noinspection ConstantConditions
        User.getInstance().setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());
    }

    /**
     * Uninitialized user.
     */
    public void uninitializeUser() {
        User user = User.getInstance();
        user.setUid(null);
        user.setEmail(null);
        user.setName(null);
        user.setAlarmList(new ArrayList<Alarm>());
//        user.setAlerts(new ArrayList<NapAlerts>());
//        user.setFriendList(new FriendList());
//        user.setGroupMap(new HashMap<String, Group>());
    }


}
