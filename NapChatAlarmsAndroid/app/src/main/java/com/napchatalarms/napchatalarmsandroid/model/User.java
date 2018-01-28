package com.napchatalarms.napchatalarmsandroid.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**Singleton class for the current user.
 * @author bbest
 */
public class User {

    private static User instance = null;

    //=====ATTRIBUTES=====
    private String name;
    private String email;
    private ArrayList<Alarm> alarmList;

    /**Private Constructor
     * */
    private User() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        this.name =  fUser.getDisplayName();
        this.email = fUser.getEmail();
        this.alarmList = new ArrayList<>();
    }

    /**Instance method*/
    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    //=====METHODS=====

    /**
     *
     * @param alarm
     */
    public void addAlarm(Alarm alarm){ this.alarmList.add(alarm);}

    /**
     * This method will return an Alarm if an alarm with the provided Id exists, otherwise return null.
     * */
    public Alarm getAlarmById(int Id){

        for(int i = 0; i < this.alarmList.size();i++){
            Alarm alarm = this.alarmList.get(i);
            if(alarm.getId() == Id){
                return alarm;
            }
        }
        return null;
    }

    /**The alarm in the User's alarm list is deleted that matches the given Id.
     * */
    public void deleteAlarm(int id){

        for(int i = 0; i < this.alarmList.size();i++){
            Alarm alarm = this.alarmList.get(i);
            if(alarm.getId() == id){
                this.alarmList.remove(i);

            }
        }

    }


    //=====GETTERS & SETTERS=====

    /**
     *
     * @return
     */
    public String getName(){ return this.name;}

    /**
     *
     * @return
     */
    public String getEmail(){return this.email;}

    /**
     *
     * @param newName
     */
    public void setName(String newName){this.name = newName;}

    /**
     *
     * @param newEmail
     */
    public void setEmail(String newEmail){this.email = newEmail;}

    /**
     *
     * @return
     */
    public ArrayList<Alarm> getAlarmList() {return alarmList;}
}
