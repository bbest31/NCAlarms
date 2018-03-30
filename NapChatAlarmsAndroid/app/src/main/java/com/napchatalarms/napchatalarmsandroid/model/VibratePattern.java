package com.napchatalarms.napchatalarmsandroid.model;

/**
 * Created by bbest on 10/03/18.
 */
public class VibratePattern {

    private Integer id;
    private String name;
    private long[] pattern;

    /**
     * Instantiates a new Vibrate pattern.
     *
     * @param i       the
     * @param title   the title
     * @param pattern the pattern
     */
    public VibratePattern(Integer i, String title, long[] pattern) {
        this.id = i;
        this.name = title;
        this.pattern = pattern;
    }


    /**
     * Get pattern long [ ].
     *
     * @return the long [ ]
     */
    public long[] getPattern() {
        return pattern;
    }

    /**
     * Sets pattern.
     *
     * @param pattern the pattern
     */
    public void setPattern(long[] pattern) {
        this.pattern = pattern;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
