package com.napchatalarms.napchatalarmsandroid.model;

/**
 * Created by bbest on 10/03/18.
 */

public class VibratePattern {

    private Integer id;
    private String name;
    private long[] pattern;

    public VibratePattern(Integer i, String title, long[] pattern) {
        this.id = i;
        this.name = title;
        this.pattern = pattern;
    }


    public long[] getPattern() {
        return pattern;
    }

    public void setPattern(long[] pattern) {
        this.pattern = pattern;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
