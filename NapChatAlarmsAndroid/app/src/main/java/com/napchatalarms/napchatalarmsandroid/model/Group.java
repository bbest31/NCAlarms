package com.napchatalarms.napchatalarmsandroid.model;

import java.util.ArrayList;

/**
 * Created by bbest on 10/02/18.
 */

public class Group {

    private ArrayList<Friend> group;
    private String groupName;

    public Group() {

    }

    public Group(ArrayList<Friend> list, String name) {
        this.setGroupName(name);
        this.setGroup(list);
    }

    public void removeFriend(String uid) {
        for (Friend friend : group) {
            if (uid.equals(friend.getUid())) {
                group.remove(friend);
            }
        }

    }

    public void addFriend(Friend friend) {
        getGroup().add(friend);
    }

    public ArrayList<Friend> getGroup() {
        return group;
    }

    public void setGroup(ArrayList<Friend> group) {
        this.group = group;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
