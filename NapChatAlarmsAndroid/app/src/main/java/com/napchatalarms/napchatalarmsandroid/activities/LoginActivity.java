package com.napchatalarms.napchatalarmsandroid.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.fragments.LandingFragment;
import com.napchatalarms.napchatalarmsandroid.fragments.LoginFragment;
import com.napchatalarms.napchatalarmsandroid.model.User;

// SOURCES: https://firebase.google.com/docs/auth/android

/**
 * Activity that allows the users to sign in using email and password.
 * <p>
 * There are also options to reset password as well sign up.
 * </P>
 *
 * @author bbest
 */
public class LoginActivity extends AppCompatActivity {

    //Views
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            selectFragment(new View(getApplicationContext()));
        }


    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
       selectFragment(new View(getApplicationContext()));
        //Navigate to Home Activity if currentUser is already signed in.
        loginNavigationOnSuccess(currentUser, getApplicationContext());

    }


    //=======Methods======

    /**
     * This method is called if the user is successfully signed in and will navigate
     * to the Home Activity by confirming the <code>FirebaseUser</code> object for the current user
     * is a non-null object.
     *
     * @see FirebaseUser
     */

    public void loginNavigationOnSuccess(FirebaseUser currentUser, final Context context) {

        if (currentUser != null) {
            User user = User.getInstance();
            NapChatController.getInstance().loadUserData(context);
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }

    }

    public void selectFragment(View view) {
        android.support.v4.app.Fragment fragment;
        if (view == findViewById(R.id.show_login_btn)) {
            fragment = new LoginFragment();
        } else {
            fragment = new LandingFragment();
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.login_frame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();

    }


}




