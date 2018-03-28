package com.napchatalarms.napchatalarmsandroid.model;

/**
 * Created by bbest on 15/03/18.
 */

public class Fact {
    private String factDescription;
    private String citation;
    private int id;
    //private URL link;
    //gif or image.

    public Fact(String fact, String citation) {
        this.citation = citation;
        this.factDescription = fact;
        this.id = this.hashCode();

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

    public int getId() {
        return id;
    }

}
