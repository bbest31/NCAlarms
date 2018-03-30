package com.napchatalarms.napchatalarmsandroid.abstractions;

/**
 * Interface for Fact Fragments
 * Created by bbest on 25/03/18.
 */
public interface IFactFragment {
    /**
     * Void method indicating when a Fact Fragment became visible.
     */
    void onBecameVisible();

    /**
     * Void method indicating when a Fact Fragment became invisible.
     */
    void onBecameInvisible();
}
