package com.napchatalarms.napchatalarmsandroid.model;

/**
 * Created by bbest on 06/05/18.
 */

public class Contact {

    public Contact(String displayName, String phoneNumber){
        this.name = displayName;
        this.number = phoneNumber;
    }
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
