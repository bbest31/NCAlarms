package com.napchatalarms.napchatalarmsandroid.model;

/**
 * Created by bbest on 06/05/18.
 */

public class Friend {

    public Friend(String displayName, String uid){
        this.name = displayName;
        this.uid = uid;
    }

    private String name;

    public String getUID() {
        return uid;
    }

    public void setUID(String uid) {
        this.uid = uid;
    }

    private String uid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
