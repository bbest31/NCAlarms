package com.napchatalarms.napchatalarmsandroid.model;

/**
 * Created by bbest on 26/06/18.
 */

public class Alert {
     private String senderName;
     private String timeStamp;
     private String message;

    public Alert(String sender, String ts, String msg){
        setSenderName(sender);
        setTimeStamp(ts);
        setMessage(msg);
    }

    public Alert(String sender, String ts) {
        setSenderName(sender);
        setTimeStamp(ts);
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
