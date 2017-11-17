package com.napchatalarms.napchatalarmsandroid;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    Button createAccountButton;
    EditText firstNameEditText;
    EditText surNameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordRentryEditText;

    TextView firstnameErrorText;
    TextView surNameErrorText;
    TextView emailErrorText;
    TextView passwordErrorText;
    TextView passwordReentryErrorText;

    /**
     * Initialize views for the activity.
    **/
    public void initialize(){
        mAuth = FirebaseAuth.getInstance();
        //Initialize views.
        createAccountButton = (Button)findViewById(R.id.createaccount_btn);
        firstNameEditText = (EditText)findViewById(R.id.firstname_editText);
        surNameEditText = (EditText)findViewById(R.id.surname_editText);
        emailEditText = (EditText)findViewById(R.id.email_editText);
        passwordEditText = (EditText)findViewById(R.id.password_editText);
        //passwordReentryEditText = (EditText)findViewById(R.id.pass_rentry_editText);

        firstnameErrorText = (TextView)findViewById(R.id.firstNameErrorText);
        surNameErrorText = (TextView)findViewById(R.id.surnameErrorText);
        emailErrorText = (TextView)findViewById(R.id.emailErrorText);
        passwordErrorText = (TextView)findViewById(R.id.passwordErrorText);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initialize();

        //========onClick methods===============
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUp();
            }
        });

    }


    /**
     * This method grabs the necessary credentials from the TextViews and passes them
     * to the createNewUser() method.
     */
    //TODO:Password Re-entry field.
    public void signUp(){

        Boolean validCredentails = Boolean.TRUE;

        String email = emailEditText.getText().toString();
        if(!UtilityFunctions.isValidEmail(email)){
            emailErrorText.setVisibility(View.VISIBLE);
            validCredentails = Boolean.FALSE;
        }

        String password = passwordEditText.getText().toString();
        if(password.isEmpty() | password.length() < 8 | !UtilityFunctions.isValidPassword(password)){
            passwordErrorText.setVisibility(View.VISIBLE);
            validCredentails = Boolean.FALSE;

        }

        String surname = surNameEditText.getText().toString();
        if(surname.isEmpty()){
            surNameErrorText.setVisibility(View.VISIBLE);
            validCredentails = Boolean.FALSE;

        }

        String firstname = firstNameEditText.getText().toString();
        if(firstname.isEmpty()){
            firstnameErrorText.setVisibility(View.VISIBLE);
            validCredentails = Boolean.FALSE;

        }

        //calls the Firebase method to create the valid new user.
        if(validCredentails = Boolean.TRUE){
            //gets rid of previously shown error texts.
            //Might be irrelevant since we navigate to another activity and this one is destroyed.
            //TODO:May be able to remove this block.
            emailErrorText.setVisibility(View.GONE);
            passwordErrorText.setVisibility(View.GONE);
            surNameErrorText.setVisibility(View.GONE);
            firstnameErrorText.setVisibility(View.GONE);

            createNewUser(email,password,surname,firstname);
        } else{
            //clears password for reentry.
            passwordEditText.setText(' ');
        }

    }
    /**
     * This method takes in the new users email, password, surname and first name in order to create
     * a new account for them and log them in using their entered credentials.
     * **/
    public void createNewUser(String email, String password, String surname, String firstName){


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            signUpNavigationOnSuccess(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignUp Activity", "createUserWithEmail:failure", task.getException());
                            signUpNavigationOnSuccess(null);
                        }

                        // ...
                    }
                });

    }

    public void signUpNavigationOnSuccess(FirebaseUser currentUser){

        if(currentUser != null){
            sendEmailVerification();
            Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
            startActivity(intent);
        }

    }

    public void sendEmailVerification(){
        mAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.w("SignUp Activity","Email verification sent.");
                            
                        }
                    }
                });
    }

}
