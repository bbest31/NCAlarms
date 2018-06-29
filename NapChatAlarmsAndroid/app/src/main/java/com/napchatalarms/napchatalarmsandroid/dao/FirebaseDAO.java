package com.napchatalarms.napchatalarmsandroid.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.napchatalarms.napchatalarmsandroid.model.Alert;
import com.napchatalarms.napchatalarmsandroid.model.Friend;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.util.ArrayList;

/**
 * Data access object that connects and reads/writes to the Firebase Realtime Database.
 * Created by bbest on 10/02/18.
 */
@SuppressWarnings("unused")
public class FirebaseDAO {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseDAO instance = null;
    private static final DatabaseReference dbRef = database.getReference();

    /**
     * This constructor initializes all the event listeners for the different children of the user
     * in the database. We separate them in order to properly construct the objects store inside once we read them.
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

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FirebaseDAO getInstance() {
        if (instance == null) {
            instance = new FirebaseDAO();
        }
        return instance;
    }

    /**
     * Init user to db.
     *
     * @param user the user
     */
    public void initUserToDB(User user) {

        dbRef.child("users").child(user.getUid()).child("email").setValue(user.getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //noinspection StatementWithEmptyBody
                if (task.isSuccessful()) {
//                    Log.i("FirebaseDAO", "Success to write email: ");
                } else {
//                    Log.e("FirebaseDAO", "Failure to write user email: " + task.getException().getMessage());
                }
            }
        });

        dbRef.child("users").child(user.getUid()).child("username").setValue(user.getUsername()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {

                } else {

                }
            }
        });

        dbRef.child("users").child(user.getUid()).child("friends").setValue(user.getFriendList()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                } else {

                }
            }
        });

        dbRef.child("users").child(user.getUid()).child("alerts").setValue(user.getAlerts()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                } else {

                }
            }
        });

        dbRef.child("users").child(user.getUid()).child("requests").setValue(user.getFriendRequests()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){

                } else {

                }
            }
        });

    }

    public void writeAlerts(String uid, ArrayList<Alert> alerts) {
        //
        dbRef.child("users").child(uid).child("alerts").setValue(alerts).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    Log.e("FirebaseDAO", "Failure to write user alerts: " + task.getException().getMessage());
                }
            }
        });
    }

    public void writeFriendsList(String uid, ArrayList<Friend> friends) {
        dbRef.child("users").child(uid).child("friends").setValue(friends).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    Log.e("FirebaseDAO", "Failure to write user friend list: " + task.getException().getMessage());
                }
            }
        });
    }

    public void writeFriendRequest(String uid, ArrayList<Friend> requests) {
        //
        dbRef.child("users").child(uid).child("requests").setValue(requests).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {

                } else {
                    Log.e("FirebaseDAO", "Failure to write user requests: " + task.getException().getMessage());
                }
            }
        });
    }


    /**
     * Gets database.
     *
     * @return the database
     */
    @SuppressWarnings("unused")
    public FirebaseDatabase getDatabase() {
        return database;
    }

    /**
     * Gets db ref.
     *
     * @return the db ref
     */
    @SuppressWarnings("unused")
    public DatabaseReference getDbRef() {
        dbRef.keepSynced(true);
        return dbRef;
    }

    /**
     * Remove user.
     *
     * @param uid the uid
     */
    public void removeUser(String uid) {

        dbRef.child("users").child(uid).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @SuppressWarnings("StatementWithEmptyBody")
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                //noinspection StatementWithEmptyBody
                if (task.isSuccessful()) {
//                    Log.i("FirebaseDAO", "Successfully removed user.");
                } else {
//                    Log.e("FirebaseDAO", "Failure to remove User: " + task.getException().getMessage());
                }
            }
        });
    }


}
