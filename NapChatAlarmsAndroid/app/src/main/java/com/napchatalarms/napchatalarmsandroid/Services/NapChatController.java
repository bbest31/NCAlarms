package com.napchatalarms.napchatalarmsandroid.Services;

import android.app.Application;
import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.Model.Alarm;
import com.napchatalarms.napchatalarmsandroid.Model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


/**
 * Created by bbest on 20/01/18.
 */

public class NapChatController {

    private static final NapChatController instance = new NapChatController();

    public static NapChatController getInstance(){
        return instance;
    }

    private NapChatController(){}

    //=====METHODS=====

    public void createUserDirectory(Context context){
        User user = User.getInstance();
        String userDir = formatEmail(user.getEmail()) + "DIR";
        context.getDir(userDir,Context.MODE_PRIVATE);
        createUserSettingsFile(context);
        createUserAlarmFile(context);
    }

    public void createUserSettingsFile(Context context){

        User user  = User.getInstance();
        String path = formatEmail(user.getEmail()) + "DIR\\";
        String filename = "SETT";
        File alarmFile = new File(path,filename);

    }


    public void createUserAlarmFile(Context context){

        User user  = User.getInstance();
        String path = formatEmail(user.getEmail()) + "DIR\\";
        String filename = "ALRM";
        File alarmFile = new File(path,filename);

    }
    /**Loads the file which contains the user settings
     * **/
    public void loadUserSettings(){
        User user = User.getInstance();
        //loads file and looks for settings for the matching user name.
        //if none exists we add a space for it.
    }

    public void saveUserSettings(){}

    public void loadUserAlarms(){}

    public void saveUserAlarms(Context context){
        try {
            User user = User.getInstance();
            String userFile = formatEmail(user.getEmail()) + "ALARMS";
            //TODO:Get the alarm attributes in string format and write each to file
            String alarms = "ALARMS";
            //gets users directory
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(userFile, Context.MODE_PRIVATE);
            outputStream.flush();

            for(Alarm a: user.getAlarmList()) {

                outputStream.write(a.toString().getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFiles(Context context){

        User user  = User.getInstance();
        String path = formatEmail(user.getEmail()) + "DIR";
        File[] dir = context.getDir(path,Context.MODE_PRIVATE).listFiles();
        for(File file : dir){
            file.delete();
        }
        context.deleteFile(path);

    }

    private String formatEmail(String email){
        String newEmail = email.replace("@","_");
        newEmail = newEmail.replace(".","_");
        return newEmail;
    }

}
