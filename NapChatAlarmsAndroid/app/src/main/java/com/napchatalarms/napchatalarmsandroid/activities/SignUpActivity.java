package com.napchatalarms.napchatalarmsandroid.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;
import com.napchatalarms.napchatalarmsandroid.fragments.SignUpFragment;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.text.SimpleDateFormat;

/**
 * Activity that allows potential users to sign up using
 * email, password and username.
 *
 * @author bbest
 */
// SOURCES: https://firebase.google.com/docs/auth/android
public class SignUpActivity extends AppCompatActivity {

    /**
     * The Email.
     */
    public String email;
    /**
     * The Password.
     */
    public String password;

    public String username;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email = null;
        password = null;
        username = null;
        initFragment(new View(getApplicationContext()));

    }


    //=====METHODS=====


    /**
     * This method takes in the new users email, password and username in order to create
     * a new account for them and log them in using their entered credentials.
     * <p>
     * If the sign up process is successful then the method sends a verification email to their inbox.
     * The <code>NapChatController</code> then is called to create a new file directory to store the
     * users local settings and alarm files. Finally the appropriate UI navigation upon success/failure.
     * </P>
     *
     * @see FirebaseAuth
     * @see NapChatController
     */
    public void createNewUser() {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            initProfile(user);
                            sendEmailVerification();
                            signUpNavigationOnSuccess(user, 0);

                        } else {
                            // If sign in fails, display a message to the user.
//                            Log.w("SignUp Activity", "createUserWithEmail:failure", task.getException());
                            signUpNavigationOnSuccess(null, 0);
                            Toast.makeText(SignUpActivity.this, "Failed. Account with email may already exist.", Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });

    }

    /**
     * Navigation on successfully sign up to the HomeActivity.
     *
     * @param currentUser the current user
     * @param method      the method
     */
    private void signUpNavigationOnSuccess(FirebaseUser currentUser, @SuppressWarnings("SameParameterValue") int method) {

        if (currentUser != null) {
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            FirebaseAnalytics mAnalytics = FirebaseAnalytics.getInstance(this);
            switch (method) {
                case 0:
                    //Log event for email sign up
                    Bundle event = new Bundle();
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    event.putString(FirebaseAnalytics.Param.ACLID, currentUser.getUid());
                    event.putString("DATE", format.format(System.currentTimeMillis()));
                    event.putString("METHOD", "EMAIL");
                    mAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, event);
                    break;
                case 1:
                    //Facebook sign up
                    break;
            }
            startActivity(intent);
            finish();
        }

    }

    /**
     * Firebase encapsulated method to send a verification email to the user's email.
     */
    private void sendEmailVerification() {
        //noinspection ConstantConditions
        mAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //noinspection StatementWithEmptyBody
                        if (task.isSuccessful()) {
//                            Log.w("SignUp Activity", "Email verification sent.");

                        }
                    }
                });
    }

    /**
     * Init profile.
     *
     * @param user the user
     */
    private void initProfile(final FirebaseUser user) {

        UserProfileChangeRequest initializeProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        user.updateProfile(initializeProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            try {
                                //Create new files directory for user in internal storage
                                NapChatController.getInstance().loadUserData(getApplicationContext());
                                NapChatController.getInstance().initUserInfo();
                                FirebaseDAO dao = FirebaseDAO.getInstance();
                                User newUser = User.getInstance();
                                dao.initUserToDB(newUser);

                            } catch (Exception e) {
//                                Log.e("SignUpActivity", "Error initializing user files or db index " + e.getMessage());
                            }
                        }
                    }
                });
    }

    /**
     * Select fragment.
     *
     * @param view the view
     */
    public void initFragment(View view) {
        android.support.v4.app.Fragment fragment;
        fragment = new SignUpFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.signup_frame, fragment)
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left).commit();

    }
}
