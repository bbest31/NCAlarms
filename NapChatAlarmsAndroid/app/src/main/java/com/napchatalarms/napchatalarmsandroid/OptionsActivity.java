package com.napchatalarms.napchatalarmsandroid;

import android.content.Intent;
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

public class OptionsActivity extends AppCompatActivity {

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

        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            logout();
            }
        });
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent loginIntent = new Intent(OptionsActivity.this,LoginActivity.class);
        startActivity(loginIntent);
        //finish();
    }

    public void resendVerificationEmail(){
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(OptionsActivity.this,"Verification email sent.",Toast.LENGTH_SHORT);
                        }
                    }
                });
    }

    //TODO Finish
    public void changeUserName(){

    }

    //TODO Finish; Grab entered email.
    public void changeUserEmail(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail("user@example.com")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            //Log.d(TAG, "User email address updated.");
                        }
                    }
                });
    }
}
