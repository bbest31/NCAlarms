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
    public EditText changeUsernameEditText;
    public CheckedTextView verifiedEmailTextView;
    public Button resetPassButton;
    public Button deleteAccountButton;
    User currentUser;


    /**
     * Initializes views/variables/objects for the activity.
     * */
    public void initialize(){

        logoutButton = (Button)findViewById(R.id.logout_btn);
        resendEmailVerificationButton = (Button)findViewById(R.id.resend_verficiationemail_btn);
        verifiedEmailTextView = (CheckedTextView)findViewById(R.id.verified_email_check);
        changeEmailButton = (Button)findViewById(R.id.change_email_btn);
        changeNameButton = (Button)findViewById(R.id.change_name_btn);
        changeEmailEditText = (EditText)findViewById(R.id.change_email_edittext);
        changeUsernameEditText = (EditText)findViewById(R.id.change_username_edittext);
        resetPassButton = (Button)findViewById(R.id.reset_password_btn);
        deleteAccountButton = (Button)findViewById(R.id.delete_account_btn);
        currentUser = User.getInstance();
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
                changeUsername();
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUserEmail();
            }
        });

        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    //=====METHODS=====
    /**
     * Gets the current FirebaseAuth instance and signs the user out and returns to the Login Activity.
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
    public void changeUsername(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(!changeUsernameEditText.getText().toString().isEmpty() &&
                UtilityFunctions.isValidUsername(changeUsernameEditText.getText().toString())) {

            final String newName = changeUsernameEditText.getText().toString();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                currentUser.setName(newName);
                                Log.d("Options Activity", "The User's username has been updated successfully");
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

        final String newEmail = changeEmailEditText.getText().toString();

        if(UtilityFunctions.isValidEmail(newEmail)) {
            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("Options Activity", "User email address updated.");
                                currentUser.setEmail(newEmail);
                            }
                        }
                    });
        } else{
            //Display Error Text
        }
    }

    /**This method sends a reset password email in order to change the users password.
     * */
    public void resetPassword(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = currentUser.getEmail();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Options Activity", "Email sent.");
                        }
                    }
                });
    }

}
