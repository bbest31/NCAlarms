package com.napchatalarms.napchatalarmsandroid;

/**Abstract Alarm Class.
 * Created by bbest on 01/12/17.
 */

public abstract class Alarm {

    private int id;
    private Boolean isActive;

    public Alarm(){
        this.id = this.hashCode();
    }

    public void Activate(){
        this.isActive = true;
    }

    public void Deactivate(){
        this.isActive = false;
    }

    public Boolean getStatus(){
        return this.isActive;
    }

    public int getId(){
        return this.id;
    }
}
