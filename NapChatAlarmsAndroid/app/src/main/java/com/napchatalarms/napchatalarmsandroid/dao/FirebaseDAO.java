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
import java.util.List;

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

    public void writeFriendRequests(String uid, ArrayList<Friend> requests) {
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

    /**
     * Adds the Friend object representing the sending user to the receiving users friend request list.
     * @param targetUID
     */
    public void sendFriendRequest(String targetUID) {
        Friend sendingUser = new Friend(User.getInstance().getUsername(), User.getInstance().getUid());
        dbRef.child("users").child(targetUID).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User targetRequests = mutableData.getValue(User.class);
                if(targetRequests != null) {
                    if (!targetRequests.getFriendRequests().contains(sendingUser)) {
                        //Add the new request
                        targetRequests.getFriendRequests().add(sendingUser);
                        return Transaction.success(mutableData);
                    } else {
                        // Already contains a request from this user.
                        return Transaction.success(mutableData);
                    }
                }
                else {
                    // came up null so some issue arose.
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d("FirebaseDAO", "requestTransaction:onComplete:" + databaseError);
                //TODO: if error is null then we send back a confirmation toast to the activity. (may have to have it as a param)
                // otherwise we send back a failed toast.
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

    public void setUserListener() {
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

    public void loadUserInfo() {
        //noinspection ConstantConditions
        User.getInstance().setUsername(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        //noinspection ConstantConditions
        User.getInstance().setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        //noinspection ConstantConditions
        User.getInstance().setUid(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getFriends();
        getAlerts();
        getRequests();
    }

    public void getFriends() {
        dbRef.child("users").child(User.getInstance().getUid()).child("friendList").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ArrayList<Friend> friends = mutableData.getValue(ArrayList.class);
                if (friends != null) {
                    User.getInstance().setFriendList(friends);
                    return Transaction.success(mutableData);
                } else {
                    Log.e("DAO", "Failed to load user friends list.");
                    User.getInstance().setFriendList(new ArrayList<>());
                    return Transaction.abort();
                }

            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    public void getRequests() {
        dbRef.child("users").child(User.getInstance().getUid()).child("friendRequests").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ArrayList<Friend> requests = mutableData.getValue(ArrayList.class);
                if (requests != null) {
                    User.getInstance().setFriendRequests(requests);
                    return Transaction.success(mutableData);
                } else {
                    Log.e("DAO", "Failed to load user friends requests.");
                    User.getInstance().setFriendRequests(new ArrayList<>());
                    return Transaction.abort();
                }

            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    public void getAlerts() {
        dbRef.child("users").child(User.getInstance().getUid()).child("alerts").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                ArrayList<Alert> alerts = mutableData.getValue(ArrayList.class);
                if (alerts != null) {
                    User.getInstance().setAlerts(alerts);
                    return Transaction.success(mutableData);
                } else {
                    Log.e("DAO", "Failed to load user alerts.");
                    User.getInstance().setAlerts(new ArrayList<>());
                    return Transaction.abort();
                }

            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
    }

    public void uniqueUsername() {
        dbRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = snapshot.getValue(User.class);

                    System.out.print(user.getUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
