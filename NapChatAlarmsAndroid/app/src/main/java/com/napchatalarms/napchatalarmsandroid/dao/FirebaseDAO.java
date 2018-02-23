package com.napchatalarms.napchatalarmsandroid.dao;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.napchatalarms.napchatalarmsandroid.model.Friend;
import com.napchatalarms.napchatalarmsandroid.model.FriendRequest;
import com.napchatalarms.napchatalarmsandroid.model.Group;
import com.napchatalarms.napchatalarmsandroid.model.NapAlerts;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Data access object that connects and reads/writes to the Firebase Realtime Database.
 * Created by bbest on 10/02/18.
 */

public class FirebaseDAO {

    private static FirebaseDAO instance = null;

    public static FirebaseDAO getInstance() {
        if (instance == null) {
            instance = new FirebaseDAO();
        }
        return instance;
    }

    /**
     * This constructor initializes all the event listeners for the different children of the user
     * in the database. We seperate them in order to properly construct the objects store inside once we read them.
     */
    private FirebaseDAO() {

//        ValueEventListener groupEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                ArrayList<Group> groups = dataSnapshot.getValue(ArrayList.class);
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        ValueEventListener friendEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        ValueEventListener alertEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        ValueEventListener requestsEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        dbRef.addValueEventListener(groupEventListener);
//        dbRef.addValueEventListener(friendEventListener);
//        dbRef.addValueEventListener(alertEventListener);
//        dbRef.addValueEventListener(requestsEventListener);
    }

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private static DatabaseReference dbRef = database.getReference();

    public void writeFriendsList(String uid,ArrayList<Friend> friends) {
        dbRef.child("users").child(uid).child("friends").setValue(friends);
    }

    public void writeGroups(String uid,Map<String, Group> groupMap) {

        dbRef.child("users").child(uid).child("groups").setValue(groupMap);
    }

    public void writeUser(User user) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + user.getUid(), user.toMap());


        dbRef.updateChildren(childUpdates);

    }

    public void writeUserUID(String uid) {
        dbRef.child("users").setValue(uid);
    }

    public void writeAlerts(String uid,ArrayList<NapAlerts> alerts) {
        //
        dbRef.child("users").child(uid).child("alerts").setValue(alerts);
    }

    public void writeFriendRequest(String uid,ArrayList<FriendRequest> requests) {
        //
        dbRef.child("users").child(uid).child("requests").setValue(requests);
    }


    public FirebaseDatabase getDatabase() {
        return database;
    }

    public DatabaseReference getDbRef() {
        dbRef.keepSynced(true);
        return dbRef;
    }

    public void loadUser() {

    }

    public void readUserUID() {

    }


}
