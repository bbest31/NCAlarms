package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;
import com.napchatalarms.napchatalarmsandroid.fragments.LandingFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.LoginFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.SignUpEmailFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.SignUpPasswordFragment;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * Activity that allows potential users to sign up using
 * email, password and username.
 *
 * @author bbest
 */
// SOURCES: https://firebase.google.com/docs/auth/android
public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public String email;
    public String password;
    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_Light_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        email = null;
        username = null;
        password = null;
        selectFragment(new View(getApplicationContext()));

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
                            signUpNavigationOnSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUp Activity", "createUserWithEmail:failure", task.getException());
                            signUpNavigationOnSuccess(null);
                            Toast.makeText(SignUpActivity.this, "Failed. Account with email may already exist.", Toast.LENGTH_LONG).show();
                            ;
                        }

                        // ...
                    }
                });

    }

    /**
     * Navigation on successfuly sign up to the HomeActivity.
     */
    public void signUpNavigationOnSuccess(FirebaseUser currentUser) {

        if (currentUser != null) {
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * Firebase encapsulated method to send a verification email to the user's email.
     */
    public void sendEmailVerification() {
        mAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.w("SignUp Activity", "Email verification sent.");

                        }
                    }
                });
    }

    public void initProfile(final FirebaseUser user) {

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
                                FirebaseDAO dao = FirebaseDAO.getInstance();
                                User newUser = User.getInstance();
                                dao.initUserToDB(newUser);

                            } catch (Exception e) {
                                Log.e("SignUpActivity", "Error initializing user files or db index " + e.getMessage());
                            }
                        }
                    }
                });
    }

    public void selectFragment(View view) {
        android.support.v4.app.Fragment fragment;
        if (view == findViewById(R.id.signup_email_next_btn)) {
            fragment = new SignUpPasswordFragment();
        } else {
            fragment = new SignUpEmailFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.signup_frame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK).commit();

    }
}
