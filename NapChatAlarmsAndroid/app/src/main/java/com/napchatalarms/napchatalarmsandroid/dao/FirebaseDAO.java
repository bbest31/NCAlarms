package com.napchatalarms.napchatalarmsandroid.dao;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.napchatalarms.napchatalarmsandroid.model.Friend;
import com.napchatalarms.napchatalarmsandroid.model.FriendRequest;
import com.napchatalarms.napchatalarmsandroid.model.Group;
import com.napchatalarms.napchatalarmsandroid.model.NapAlerts;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.util.ArrayList;
import java.util.Map;

/**Data access object that connects and reads/writes to the Firebase Realtime Database.
 * Created by bbest on 10/02/18.
 */

public class FirebaseDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static DatabaseReference dbRef = database.getReference();

    public static void writeFriendsList(ArrayList<Friend> friends){
        dbRef.child("users").child(User.getInstance().getUid()).child("friends").setValue(friends);
    }

    public static void writeGroups(Map<String,Group> groupMap){
        dbRef.child("users").child(User.getInstance().getUid()).child("groups").setValue(groupMap);
    }

    public static  void writeUser(User user){
        dbRef.child("users").child(user.getUid());
        writeFriendsList(user.getFriendList());
        writeGroups(user.getGroupMap());
        writeFriendRequest(user.getFriendRequests());
        writeAlerts(user.getAlerts());
    }

    public static void writeAlerts(ArrayList<NapAlerts> alerts){
        //
        dbRef.child("users").child(User.getInstance().getUid()).child("alerts").setValue(alerts);
    }

    public static void writeFriendRequest(ArrayList<FriendRequest> requests){
        //
        dbRef.child("users").child(User.getInstance().getUid()).child("requests").setValue(requests);
    }


    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static DatabaseReference getDbRef() {
        dbRef.keepSynced(true);
        return dbRef;
    }


}
