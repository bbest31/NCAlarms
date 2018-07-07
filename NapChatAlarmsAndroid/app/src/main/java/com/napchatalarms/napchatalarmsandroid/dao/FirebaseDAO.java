package com.napchatalarms.napchatalarmsandroid.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
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
    private static final DatabaseReference dbRef = database.getReference();
    private static FirebaseDAO instance = null;
    private ValueEventListener userListener;

    /**
     * This constructor initializes all the event listeners for the different children of the user
     * in the database. We separate them in order to properly construct the objects store inside once we read them.
     */
    private FirebaseDAO() {
        userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                User currentUser = User.getInstance();

                //Update the user info.
                currentUser.setUserInfo(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("FirebaseDAO", "userListener:onCancelled", databaseError.toException());
            }
        };
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
        dbRef.child("users").child(user.getUid()).setValue(user);
        dbRef.child("users").child(user.getUid()).addValueEventListener(userListener);
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
        dbRef.child("users").child(uid).child("friendList").setValue(friends).addOnCompleteListener(new OnCompleteListener<Void>() {
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
        dbRef.child("users").child(uid).child("friendRequests").setValue(requests).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    public void removeFriend(String uid, String friendUID) {
        ArrayList<Friend> friends = User.getInstance().getFriendList();

        for (Friend friend : friends) {
            if (friend.getUID().equals(friendUID)) {
                User.getInstance().removeFriend(friendUID);
                break;
            }
        }
        dbRef.child("users").child(uid).child("friends").setValue(friends).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //Log.i("FirebaseDAO", "Successfully removed user.");
                } else {
                    //Log.i("FirebaseDAO", "Failed to remove user.");
                }
            }
        });
    }

    public void sendFriendRequest(String targetUID) {
        Friend sendingUser = new Friend(User.getInstance().getUsername(), User.getInstance().getUid());
        dbRef.child("users").child(targetUID).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User targetUser = mutableData.getValue(User.class);
                if (!targetUser.getFriendRequests().contains(sendingUser)) {
                    targetUser.getFriendRequests().add(sendingUser);
                    return Transaction.success(mutableData);
                } else {
                    return Transaction.success(mutableData);
                }
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("FirebaseDAO", "requestTransaction:onComplete:" + databaseError);
            }
        });

    }

    public void confirmRequest() {

    }

    public void denyRequest() {
        dbRef.child("users").child(User.getInstance().getUid()).child("friendRequests").setValue(User.getInstance().getFriendRequests());
    }

    public void sendAlert() {

    }

    public void scrubAlerts() {
        dbRef.child("users").child(User.getInstance().getUid()).child("alerts").setValue(new ArrayList<Friend>());
    }

    public void loadUserInfo() {
        dbRef.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = User.getInstance();
                User savedUser = dataSnapshot.getValue(User.class);
                currentUser.setUserInfo(savedUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
