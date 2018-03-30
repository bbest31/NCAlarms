package com.napchatalarms.napchatalarmsandroid.services;

import com.napchatalarms.napchatalarmsandroid.model.Alarm;

/**
 * Builder pattern class to build the different types of alarms.
 *
 * @author bbest
 */
public class AlarmBuilder {

    private final Alarm alarm;

    /**
     * Instantiates a new Alarm builder.
     */
    AlarmBuilder() {
        alarm = new Alarm();
    }

}
