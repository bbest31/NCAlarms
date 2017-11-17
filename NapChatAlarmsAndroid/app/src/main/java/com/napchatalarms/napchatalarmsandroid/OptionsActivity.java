package com.napchatalarms.napchatalarmsandroid;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

// SOURCES: https://firebase.google.com/docs/auth/android

public class OptionsActivity extends AppCompatActivity {

    //=====VIEWS=====
    public Button logoutButton;
    public Button resendEmailVerificationButton;
    public Button changeEmailButton;
    public Button changeNameButton;
    public EditText changeEmailEditText;
    public EditText changeFirstNameEditText;
    public EditText changeSurnameEditText;
    public CheckedTextView verifiedEmailTextView;


    /**
     * Initializes views for the activity.
     * */
    public void initialize(){

        logoutButton = (Button)findViewById(R.id.logout_btn);
        resendEmailVerificationButton = (Button)findViewById(R.id.resend_verficiationemail_btn);
        verifiedEmailTextView = (CheckedTextView)findViewById(R.id.verified_email_check);
        changeEmailButton = (Button)findViewById(R.id.change_email_btn);
        changeNameButton = (Button)findViewById(R.id.change_name_btn);
        changeEmailEditText = (EditText)findViewById(R.id.change_email_edittext);
        changeFirstNameEditText = (EditText)findViewById(R.id.change_firstname_edittext);
        changeSurnameEditText = (EditText)findViewById(R.id.change_surname_edittext);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        initialize();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //Checks to see if the user has a verified email.
        if(!user.isEmailVerified()){

            verifiedEmailTextView.setCheckMarkDrawable(R.drawable.ic_info_black_24dp);
            verifiedEmailTextView.setBackgroundColor(getResources().getColor(R.color.red));

            resendEmailVerificationButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                resendVerificationEmail();
                }
            });
        } else{
            resendEmailVerificationButton.setVisibility(View.GONE);
        }

        //=====ONCLICK METHODS=====

        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            logout();
            }
        });

        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserName();
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserEmail();
            }
        });


    }

    //=====METHODS=====
    /**
     * Gets the current FirbaseAuth instance and signs the user out and returns to the Login Activity.
     * */
    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent loginIntent = new Intent(OptionsActivity.this,LoginActivity.class);
        startActivity(loginIntent);
        //finish();
    }


    /**
     * Gets the current user and resend a verification email to the user email address.
     * */
    public void resendVerificationEmail(){
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("Options Activity","Resent Verification Email successfully");
                        }
                    }
                });
    }


    /**
     * This method asserts the new first and surname are of correct format and updates the users name.
     * */
    public void changeUserName(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(!changeSurnameEditText.getText().toString().isEmpty()
                && !changeFirstNameEditText.getText().toString().isEmpty() &&
                UtilityFunctions.isValidName(changeFirstNameEditText.getText().toString()) &&
                UtilityFunctions.isValidName(changeSurnameEditText.getText().toString())
                ) {

            String newName = changeFirstNameEditText.getText().toString();
            newName.concat(" ");
            newName.concat(changeSurnameEditText.getText().toString());

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Options Activity", "The User's name has been updated successfully");
                            }
                        }
                    });
        }
    }

    /**
     * This method updates the user's email given the new email entered in the corresponding
     * EditText.
     * */
    public void changeUserEmail(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        String newEmail = changeEmailEditText.getText().toString();

        if(UtilityFunctions.isValidEmail(newEmail)) {
            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Options Activity", "User email address updated.");
                            }
                        }
                    });
        } else{
            //Display Error Text
        }
    }


}
