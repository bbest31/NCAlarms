package com.napchatalarms.napchatalarmsandroid.model;

import java.util.ArrayList;

/**
 * Created by bbest on 26/02/18.
 */

public class FriendList {

    private ArrayList<Friend> friends;

    private ArrayList<String> pending;
    public FriendList(){
        this.friends = new ArrayList<>();
        this.pending = new ArrayList<>();
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getPending() {
        return pending;
    }

    public void setPending(ArrayList<String> pending) {
        this.pending = pending;
    }
}
