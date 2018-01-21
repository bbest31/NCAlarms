package com.napchatalarms.napchatalarmsandroid.Services;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.Model.Alarm;
import com.napchatalarms.napchatalarmsandroid.Model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;


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

        String path = context.getFilesDir()+formatEmail(user.getEmail()) + "DIR\\";
        String filename = "SETT";
        File alarmFile = new File(path,filename);

    }


    public void createUserAlarmFile(Context context){

        User user  = User.getInstance();
        String path = context.getFilesDir()+formatEmail(user.getEmail()) + "DIR\\";
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

    public void saveUserAlarms(Context context){
        try {
            User user = User.getInstance();
            String userFile = formatEmail(user.getEmail()) + "ALARMS";
            //gets users directory
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(userFile, Context.MODE_PRIVATE);
            outputStream.flush();

            for(Alarm a: user.getAlarmList()) {

                outputStream.write(a.writeFormat().getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //TODO:load user file (if exists) and read the alarms listed line by line.
    public void loadUserAlarms(Context context){
        try{
            User user = User.getInstance();
            FileInputStream file = context.openFileInput(formatEmail(user.getEmail())+"DIR\\ALRM");

            //file.read();
            BufferedReader br = new BufferedReader(new FileReader(formatEmail(user.getEmail())+"DIR\\ALRM"));
            String line;
            StringBuilder text = new StringBuilder();
            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close() ;
            Log.d("User Alarm file: ",text.toString());
        } catch(Exception e){
            System.out.println("No directory with that user email or no file exists: "+e.getMessage());
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

    public void loadUser(Context context){
        loadUserSettings();
        loadUserAlarms(context);
        //loadUserFriends();
    }

    private String formatEmail(String email){
        String newEmail = email.replace("@","_");
        newEmail = newEmail.replace(".","_");
        return newEmail;
    }

}
