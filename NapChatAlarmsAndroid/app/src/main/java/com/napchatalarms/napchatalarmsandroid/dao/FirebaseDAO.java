package com.napchatalarms.napchatalarmsandroid.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.napchatalarms.napchatalarmsandroid.model.Friend;
import com.napchatalarms.napchatalarmsandroid.model.FriendRequest;
import com.napchatalarms.napchatalarmsandroid.model.Group;
import com.napchatalarms.napchatalarmsandroid.model.NapAlerts;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.util.ArrayList;
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
        dbRef.child("users").child(uid).child("friends").setValue(friends).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                } else{
                    Log.e("FirebaseDAO","Failure to write user friend list: "+task.getException().getMessage());
                }
            }
        });
    }

    public void writeGroups(String uid,Map<String, Group> groupMap) {

        dbRef.child("users").child(uid).child("groups").setValue(groupMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                } else{
                    Log.e("FirebaseDAO","Failure to write user groups: "+task.getException().getMessage());
                }
            }
        });
    }

    public void initUserToDB(User user) {

        dbRef.child("users").child(user.getUid()).child("email").setValue(user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("FirebaseDAO","Success to write email: ");
                } else{
                    Log.e("FirebaseDAO","Failure to write user email: "+task.getException().getMessage());
                }
            }
        });
        dbRef.child("users").child(user.getUid()).child("username").setValue(user.getName()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("FirebaseDAO","Success to write username: ");
                } else{
                    Log.e("FirebaseDAO","Failure to write username: "+task.getException().getMessage());
                }
            }
        });

    }

    public void writeAlerts(String uid,ArrayList<NapAlerts> alerts) {
        //
        dbRef.child("users").child(uid).child("alerts").setValue(alerts).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                } else{
                    Log.e("FirebaseDAO","Failure to write user alerts: "+task.getException().getMessage());
                }
            }
        });
    }

    public void writeFriendRequest(String uid,ArrayList<FriendRequest> requests) {
        //
        dbRef.child("users").child(uid).child("requests").setValue(requests).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                } else{
                    Log.e("FirebaseDAO","Failure to write user requests: "+task.getException().getMessage());
                }
            }
        });
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

    public void removeUser(String uid){

        dbRef.child("users").child(uid).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.i("FirebaseDAO","Succesfully removed user.");
                } else{
                    Log.e("FirebaseDAO","Failure to remove User: "+task.getException().getMessage());
                }
            }
        });
    }



}
