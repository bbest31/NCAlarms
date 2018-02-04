package com.napchatalarms.napchatalarmsandroid.services;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.napchatalarms.napchatalarmsandroid.Manifest;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Permission;
import java.util.ArrayList;


/**
 * @author bbest
 */

public class NapChatController {

    private static final NapChatController instance = new NapChatController();

    public static NapChatController getInstance(){
        return instance;
    }


    private NapChatController(){}

    //=====METHODS=====

    /**
     *
     * @param context
     * @throws IOException
     */
    public void createUserFiles(Context context) throws IOException{

        createUserSettingsFile(context);
        createUserAlarmFile(context);
    }

    /**
     *
     * @param context
     * @throws IOException
     */
    public void createUserSettingsFile(Context context) throws IOException{

        User user  = User.getInstance();

        String filename = formatEmail(User.getInstance().getEmail())+"SETT.ser";
        File settingsFile = new File(context.getFilesDir().getAbsolutePath(),filename);


    }

    /**
     *
     * @param context
     * @throws IOException
     */
    public void createUserAlarmFile(Context context) throws IOException{

        String filename = formatEmail(User.getInstance().getEmail())+"ALRM.ser";
        File alarmFile = new File(context.getFilesDir().getAbsolutePath(),filename);
        alarmFile.createNewFile();

    }
    /**Loads the file which contains the user settings
     * **/
    public void loadUserSettings() throws IOException{
        //loads file and looks for settings for the matching user name.
        //if none exists we add a space for it.
    }

    /**
     *
     */
    public void saveUserSettings(){}

    /**
     *
     * @param context
     * @throws IOException
     */
    public void saveUserAlarms(Context context) throws IOException{
        try {
            User user = User.getInstance();
            //gets users directory
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(formatEmail(user.getEmail())+"ALRM.ser", Context.MODE_PRIVATE);
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
     *
     * @param context
     * @throws IOException
     */
    public void loadUserAlarms(Context context) throws IOException{
        try{
            User user = User.getInstance();
            FileInputStream file = context.openFileInput(formatEmail(user.getEmail())+"ALRM.ser");
            ObjectInputStream inputStream = new ObjectInputStream(file);
            ArrayList<Alarm> list = (ArrayList<Alarm>) inputStream.readObject();
            user.setAlarmList(list);
            inputStream.close();
            file.close();
            for(Alarm a:list){

                System.out.println(a.toString());
            }
        } catch(IOException e){
            System.err.println("No alarm file exists or no object present: "+e.getMessage());
        } catch (ClassNotFoundException c){
            System.err.println("Class not found exception "+ c.getMessage());
            c.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @throws IOException
     */
    public void deleteFiles(Context context) throws IOException{

        File dir = context.getFilesDir();
        File alarmFile = new File(dir,formatEmail(User.getInstance().getEmail())+"ALRM.ser");
        File settingsFile = new File(dir,formatEmail(User.getInstance().getEmail())+"SETT.ser");
        alarmFile.delete();
        settingsFile.delete();
    }

    /**
     *
     * @param context
     */
    public void loadUserData(Context context){
        try {
            //loadUserSettings();
            loadUserAlarms(context);
            //loadUserFriends();
        } catch (IOException e){
            try {
                createUserFiles(context);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     *
     * @param email
     * @return
     */
    private String formatEmail(String email){
        String newEmail = email.replace("@","_");
        newEmail = newEmail.replace(".","_");
        return newEmail;
    }


}
