package com.napchatalarms.napchatalarmsandroid.model;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Exclude;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class for the current user.
 *
 * @author bbest
 */
public class User {

    private static User instance = null;

    //=====ATTRIBUTES=====
    private String name;
    private String email;
    private ArrayList<Alarm> alarmList;
    private String uid;
    private ArrayList<Friend> friendList;
    private Map<String, Group> groupList;
    private ArrayList<NapAlerts> alerts;
    private ArrayList<FriendRequest> friendRequests;

    /**
     * Private Constructor
     */
    private User() {
        try {
            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
            this.name = fUser.getDisplayName();
            this.email = fUser.getEmail();
            this.uid = fUser.getUid();
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e("Alarm Controller", "Failed to get current User");
        }

        this.alarmList = new ArrayList<>();
    }

    /**
     * Instance method
     */
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    //=====METHODS=====

    /**
     * @param alarm
     */
    public void addAlarm(Alarm alarm) {
        this.alarmList.add(alarm);
    }

    /**
     * This method will return an Alarm if an alarm with the provided Id exists, otherwise return null.
     */
    public Alarm getAlarmById(int Id) {

        for (int i = 0; i < this.alarmList.size(); i++) {
            Alarm alarm = this.alarmList.get(i);
            if (alarm.getId() == Id) {
                return alarm;
            }
        }
        Log.e("User.getAlarmById", "Could not retrieve alarm with ID = " + Id);
        Log.e("User", "Current User Alarms:" + User.getInstance().getAlarmList());
        Log.e("User", "User Info: " + User.getInstance().toString());
        return null;
    }

    /**
     * The alarm in the User's alarm list is deleted that matches the given Id.
     */
    public void deleteAlarm(int id) {

        for (int i = 0; i < this.alarmList.size(); i++) {
            Alarm alarm = this.alarmList.get(i);
            if (alarm.getId() == id) {
                this.alarmList.remove(i);

            }
        }

    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("friends", friendList);
        result.put("groups", groupList);
        result.put("alerts", alerts);
        result.put("requests", friendRequests);

        return result;
    }


    //=====GETTERS & SETTERS=====

    /**
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * @param newName
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * @return
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param newEmail
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    /**
     * @return
     */
    public ArrayList<Alarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(ArrayList<Alarm> list) {
        this.alarmList = list;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }

    public Map<String, Group> getGroupMap() {
        return groupList;
    }

    public void setGroupMap(Map<String, Group> groupList) {
        this.groupList = groupList;
    }

    public ArrayList<NapAlerts> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<NapAlerts> alerts) {
        this.alerts = alerts;
    }

    public ArrayList<FriendRequest> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<FriendRequest> friendRequests) {
        this.friendRequests = friendRequests;
    }

    @Override
    public String toString() {
        String user = "UID: " + this.getUid() + ", E: " + this.getEmail() + ", username: " + this.getName();
        return user;
    }
}
