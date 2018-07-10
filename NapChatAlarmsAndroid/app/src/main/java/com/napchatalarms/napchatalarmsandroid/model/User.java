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
    private ArrayList<Friend> friendList;
    private ArrayList<Alert> alerts;
    private ArrayList<Friend> friendRequests;
    private String username;

    /**
     * Private Constructor
     */
    private User() {
        try {
            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
            //noinspection ConstantConditions
            this.username = fUser.getDisplayName();
            this.email = fUser.getEmail();
            this.uid = fUser.getUid();

        } catch (NullPointerException e) {
            e.printStackTrace();
//            Log.e("User", "Failed to init User");
        }

        this.alarmList = new ArrayList<>();
        this.friendList = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.alerts = new ArrayList<>();
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

    public void addFriend(Friend newFriend) {
        this.friendList.add(newFriend);
    }

    public Friend getFriend(String uid) {
        for (Friend f : friendList) {
            if (f.getUID().equals(uid)) {
                return f;
            }
        }
        return null;
    }

    public void removeFriend(String uid) {
        for (Friend f : friendList) {
            if (f.getUID().equals(uid)) {
                friendList.remove(f);
            }
        }
    }


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


    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    public void setFriendList(ArrayList<Friend> friendList) {
        this.friendList = friendList;
    }

    public ArrayList<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.alerts = alerts;
    }

    public void clearAlerts() {
        getAlerts().clear();
    }

    public void recievedAlert(Alert alert) {
        this.alerts.add(alert);
    }

    public ArrayList<Friend> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<Friend> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public void removeRequest(String uid) {
        for (Friend f : this.friendRequests) {
            if (f.getUID().equals(uid)) {
                this.friendRequests.remove(f);
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUserInfo(User user) {
        setEmail(user.getEmail());
        setUsername(user.getUsername());
        setFriendList(user.getFriendList());
        setFriendRequests(user.getFriendRequests());
        setAlerts(user.getAlerts());
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", alarmList=" + alarmList +
                ", uid='" + uid + '\'' +
                ", friendList=" + friendList +
                ", alerts=" + alerts +
                ", friendRequests=" + friendRequests +
                ", username='" + username + '\'' +
                '}';
    }
}
