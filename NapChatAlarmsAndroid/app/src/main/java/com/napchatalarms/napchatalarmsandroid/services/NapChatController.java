package com.napchatalarms.napchatalarmsandroid.services;

import android.content.Context;
import android.util.Log;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


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
    public void createUserDirectory(Context context) throws IOException{
        User user = User.getInstance();
        String userDir = formatEmail(user.getEmail()) + "DIR";
        context.getDir(userDir,Context.MODE_PRIVATE);
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

        String path = context.getFilesDir()+formatEmail(user.getEmail()) + "DIR\\";
        String filename = "SETT";
        File alarmFile = new File(path,filename);

    }

    /**
     *
     * @param context
     * @throws IOException
     */
    public void createUserAlarmFile(Context context) throws IOException{

        User user  = User.getInstance();
        String path = context.getFilesDir()+formatEmail(user.getEmail()) + "DIR\\";
        String filename = "ALRM";
        File alarmFile = new File(path,filename);

    }
    /**Loads the file which contains the user settings
     * **/
    public void loadUserSettings() throws IOException{
        User user = User.getInstance();
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
            String userFile = formatEmail(user.getEmail()) + "ALARMS";
            //gets users directory
            FileOutputStream outputStream;
            outputStream = context.openFileOutput(userFile, Context.MODE_PRIVATE);
            outputStream.flush();

            for(Alarm a: user.getAlarmList()) {

                outputStream.write(a.writeFormat().getBytes());
            }
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
    //TODO:load user file (if exists) and read the alarms listed line by line.
    public void loadUserAlarms(Context context) throws IOException{
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
        } catch(IOException e){
            System.out.println("No directory with that user email or no file exists: "+e.getMessage());
        }
    }

    /**
     *
     * @param context
     * @throws IOException
     */
    public void deleteFiles(Context context) throws IOException{

        User user  = User.getInstance();
        String path = formatEmail(user.getEmail()) + "DIR";
        File[] dir = context.getDir(path,Context.MODE_PRIVATE).listFiles();
        for(File file : dir){
            file.delete();
        }
        context.deleteFile(path);

    }

    /**
     *
     * @param context
     */
    public void loadUser(Context context){
        try {
            loadUserSettings();
            loadUserAlarms(context);
            //loadUserFriends();
        } catch (IOException e){
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

    /**
     *
     */
    private void checkPermissions(){

    }

}
