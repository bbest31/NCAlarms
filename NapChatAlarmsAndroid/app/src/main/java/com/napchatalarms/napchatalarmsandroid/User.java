package com.napchatalarms.napchatalarmsandroid;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**Singleton Class for the current user.
 * Created by brand on 11/17/2017.
 */

public class User {

    private static User instance = null;

    //=====ATTRIBUTES=====
    private String name;
    private String email;

    /**Private Constructor
     * */
    private User() {
        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
        this.name =  fUser.getDisplayName();
        this.email = fUser.getEmail();
    }

    /**Instance method*/
    public static User getInstance() {
        if(instance == null) {
            instance = new User();
        }
        return instance;
    }

    //TODO:Could replace all getters and setters with a single method called update that just reattains the info from the FirebaseUser object
    //=====GETTERS & SETTERS=====
    public String getName(){ return this.name;}
    public String getEmail(){return this.email;}
    public void setName(String newName){this.name = newName;}
    public void setEmail(String newEmail){this.email = newEmail;}
}
