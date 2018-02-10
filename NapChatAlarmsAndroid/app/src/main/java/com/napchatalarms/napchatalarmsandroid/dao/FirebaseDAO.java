package com.napchatalarms.napchatalarmsandroid.dao;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.napchatalarms.napchatalarmsandroid.model.Friend;
import com.napchatalarms.napchatalarmsandroid.model.Group;
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
        dbRef.child("users").child(user.getUid()).child("friends").setValue(user.getFriendList());
        dbRef.child("users").child(user.getUid()).child("groups").setValue(user.getGroupMap());
    }


    public static FirebaseDatabase getDatabase() {
        return database;
    }

    public static DatabaseReference getDbRef() {
        dbRef.keepSynced(true);
        return dbRef;
    }


}
