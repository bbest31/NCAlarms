package com.napchatalarms.napchatalarmsandroid.utility;

import com.napchatalarms.napchatalarmsandroid.model.VibratePattern;

/**
 * Created by bbest on 09/04/18.
 */

public class VibrateLibrary {
    private static final long[] HEARTBEAT = {0, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500, 200, 100, 200, 500};
    private static final VibratePattern HEARTBEAT_PATTERN = new VibratePattern(2, "Heartbeat", HEARTBEAT);
    private static final long[] BUZZSAW = {0, 10000};
    private static final VibratePattern BUZZSAW_PATTERN = new VibratePattern(1, "Buzzsaw", BUZZSAW);
    private static final long[] LOCOMOTIVE = {0, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600, 500, 100, 500, 600};
    private static final VibratePattern LOCOMOTIVE_PATTERN = new VibratePattern(0, "Locomotive", LOCOMOTIVE);

    /**
     * Vibrate pattern is in milliseconds. First number indicates the time to wait
     * to start vibrating when notification fires. Second number is the time to vibrate
     * and then turn off. Subsequent numbers indicate times that the vibration is off,on,off,etc.
     *
     * @param i the
     * @return the vibrate pattern
     */
    public static VibratePattern getVibratePattern(Integer i) {
        VibratePattern pattern = null;
        switch (i) {
            case 0:
                pattern = LOCOMOTIVE_PATTERN;
                break;
            case 1:
                pattern = BUZZSAW_PATTERN;
                break;
            case 2:
                pattern = HEARTBEAT_PATTERN;
                break;
        }
        return pattern;
    }
}
