package com.napchatalarms.napchatalarmsandroid;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**Singleton Class for the current user.
 * Created by brand on 11/17/2017.
 */

public class User {

    private static User instance = null;

    //=====ATTRIBUTES=====
    private String name;
    private String email;
    private ArrayList<Alarm> alarmList;

    /**Private Constructor
     * */
    //TODO: read stored alarms from file and if their username matches current user then add to AlarmList
    private User() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        this.name =  fUser.getDisplayName();
        this.email = fUser.getEmail();
    }

    /**Instance method*/
    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    //=====METHODS=====
    public void addAlarm(Alarm alarm){ this.alarmList.add(alarm);}

    //public Alarm getAlarmById(int Id){}

    public void deleteAlarm(int Id){}


    public void updateAlarm(Alarm alarm){}


    //=====GETTERS & SETTERS=====
    public String getName(){ return this.name;}
    public String getEmail(){return this.email;}
    public void setName(String newName){this.name = newName;}
    public void setEmail(String newEmail){this.email = newEmail;}
}
