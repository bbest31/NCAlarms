package com.napchatalarms.napchatalarmsandroid.model;

import java.net.URL;

/**
 * Created by bbest on 15/03/18.
 */

public class SleepFact {
    private String factDescription;
    private String citation;
    //private URL link;
    //gif or image.

    public SleepFact(String fact, String citation){
        this.citation = citation;
        this.factDescription = fact;

    }

    public String getFactDescription() {
        return factDescription;
    }

    public void setFactDescription(String factDescription) {
        this.factDescription = factDescription;
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
    }
}
