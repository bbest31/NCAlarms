package com.napchatalarms.napchatalarmsandroid.model;

/**
 * Created by bbest on 15/03/18.
 */
public class Fact {
    private String factDescription;
    private String citation;
    private final int id;
    //private URL link;
    //gif or image.

    /**
     * Instantiates a new Fact.
     *
     * @param fact     the fact
     * @param citation the citation
     */
    public Fact(String fact, String citation) {
        this.citation = citation;
        this.factDescription = fact;
        this.id = this.hashCode();

    }

    /**
     * Gets fact description.
     *
     * @return the fact description
     */
    public String getFactDescription() {
        return factDescription;
    }

    /**
     * Sets fact description.
     *
     * @param factDescription the fact description
     */
    public void setFactDescription(String factDescription) {
        this.factDescription = factDescription;
    }

    /**
     * Gets citation.
     *
     * @return the citation
     */
    public String getCitation() {
        return citation;
    }

    /**
     * Sets citation.
     *
     * @param citation the citation
     */
    public void setCitation(String citation) {
        this.citation = citation;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

}
