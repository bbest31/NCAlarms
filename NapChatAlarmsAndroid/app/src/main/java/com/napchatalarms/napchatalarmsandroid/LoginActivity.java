package com.napchatalarms.napchatalarmsandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Navigate to Home Activity if currentUser is already signed in.
        loginNavigationOnSuccess(currentUser);


    }

    /**
     * This method is called if the user is succesfully signed in and will navigate
     * to the Home Activity by confirming the FirebaseUser object for the current user
     * is a non-null object.**/
    public void loginNavigationOnSuccess(FirebaseUser currentUser){

        if(currentUser != null){
            
        }

    }
}
