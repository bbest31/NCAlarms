package com.napchatalarms.napchatalarmsandroid.activities;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

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


    private static final String TAG = "LoginActivity";
    public static int RC_SIGN_IN = 100;
    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build(),
            new AuthUI.IdpConfig.FacebookBuilder().build());
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.splashScreenTheme);
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }


    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //Navigate to Home Activity if currentUser is already signed in.
        if (currentUser != null) {
            loginNavigationOnSuccess(currentUser, getApplicationContext());
            finish();
        } else {
            startSignInIntent();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            loginNavigationOnSuccess(auth.getCurrentUser(), getApplicationContext());
            finish();
        } else {
            startSignInIntent();
        }
    }


    //=======Methods======

    /**
     * This method is called if the user is successfully signed in and will navigate
     * to the Home Activity by confirming the <code>FirebaseUser</code> object for the current user
     * is a non-null object.
     *
     * @param currentUser the current user
     * @param context     the context
     * @see FirebaseUser
     */
    public void loginNavigationOnSuccess(FirebaseUser currentUser, final Context context) {

        if (currentUser != null) {
            User user = User.getInstance();
            NapChatController.getInstance().loadUserData(context);
            Log.i("LOGIN SUCCESS", User.getInstance().toString());
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);

            //Log event
            FirebaseAnalytics mAnalytics = FirebaseAnalytics.getInstance(this);
            Bundle event = new Bundle();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            event.putString(FirebaseAnalytics.Param.ACLID, user.getUid());
            event.putString("DATE", format.format(System.currentTimeMillis()));
            mAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, event);

            startActivity(homeIntent);
            finish();
        }

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                loginNavigationOnSuccess(user, getApplicationContext());
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }


    public void startSignInIntent() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setIsSmartLockEnabled(false)
                        .setLogo(R.drawable.ic_pillow)
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN);
    }



}




