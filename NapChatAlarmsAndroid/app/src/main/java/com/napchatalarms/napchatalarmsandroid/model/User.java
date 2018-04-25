package com.napchatalarms.napchatalarmsandroid.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Singleton class for the current user.
 *
 * @author bbest
 */
public class User {

    private static User instance = null;

    //=====ATTRIBUTES=====
    private String email;
    private ArrayList<Alarm> alarmList;
    private String uid;
//    private FriendList friendList;
//    private Map<String, Group> groupList;
//    private ArrayList<NapAlerts> alerts;
//    private ArrayList<FriendRequest> friendRequests;

    /**
     * Private Constructor
     */
    private User() {
        try {
            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
            //noinspection ConstantConditions
            this.email = fUser.getEmail();
            this.uid = fUser.getUid();
        } catch (NullPointerException e) {
            e.printStackTrace();
//            Log.e("User", "Failed to init User");
        }

        this.alarmList = new ArrayList<>();
    }

    /**
     * Instance method
     *
     * @return the instance
     */
    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    //=====METHODS=====

    /**
     * Add alarm.
     *
     * @param alarm the alarm
     */
    public void addAlarm(Alarm alarm) {
        this.alarmList.add(alarm);
    }

    /**
     * This method will return an Alarm if an alarm with the provided Id exists, otherwise return null.
     *
     * @param Id the id
     * @return the alarm by id
     */
    public Alarm getAlarmById(int Id) {

        for (int i = 0; i < this.alarmList.size(); i++) {
            Alarm alarm = this.alarmList.get(i);
            if (alarm.getId() == Id) {
                return alarm;
            }
        }
//        Log.e("User.getAlarmById", "Could not retrieve alarm with ID = " + Id);
//        Log.e("User", "Current User Alarms:" + User.getInstance().getAlarmList());
//        Log.e("User", "User Info: " + User.getInstance().toString());
        return null;
    }

    /**
     * The alarm in the User's alarm list is deleted that matches the given Id.
     *
     * @param id the id
     */
    public void deleteAlarm(int id) {

        for (int i = 0; i < this.alarmList.size(); i++) {
            Alarm alarm = this.alarmList.get(i);
            if (alarm.getId() == id) {
                this.alarmList.remove(i);

            }
        }

    }

//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("uid", uid);
//        result.put("friends", friendList);
//        result.put("groups", groupList);
//        result.put("alerts", alerts);
//        result.put("requests", friendRequests);
//
//        return result;
//    }


    //=====GETTERS & SETTERS=====


    /**
     * Gets email.
     *
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets email.
     *
     * @param newEmail the new email
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    /**
     * Gets alarm list.
     *
     * @return alarm list
     */
    public ArrayList<Alarm> getAlarmList() {
        return alarmList;
    }

    /**
     * Sets alarm list.
     *
     * @param list the list
     */
    public void setAlarmList(ArrayList<Alarm> list) {
        this.alarmList = list;
    }

    /**
     * Gets uid.
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets uid.
     *
     * @param uid the uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

//    public FriendList getFriendList() {
//        return friendList;
//    }
//
//    public void setFriendList(FriendList friendList) {
//        this.friendList = friendList;
//    }
//
//    public Map<String, Group> getGroupMap() {
//        return groupList;
//    }
//
//    public void setGroupMap(Map<String, Group> groupList) {
//        this.groupList = groupList;
//    }
//
//    public ArrayList<NapAlerts> getAlerts() {
//        return alerts;
//    }
//
//    public void setAlerts(ArrayList<NapAlerts> alerts) {
//        this.alerts = alerts;
//    }
//
//    public ArrayList<FriendRequest> getFriendRequests() {
//        return friendRequests;
//    }
//
//    public void setFriendRequests(ArrayList<FriendRequest> friendRequests) {
//        this.friendRequests = friendRequests;
//    }

    @Override
    public String toString() {
        return "UID: " + this.getUid() + ", E: " + this.getEmail();
    }
}
