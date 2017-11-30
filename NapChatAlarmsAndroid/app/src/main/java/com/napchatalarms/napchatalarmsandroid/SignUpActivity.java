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
import com.google.firebase.auth.UserProfileChangeRequest;

// SOURCES: https://firebase.google.com/docs/auth/android
public class SignUpActivity extends AppCompatActivity {

    //=====VIEWS=====
    private FirebaseAuth mAuth;
    Button createAccountButton;
    EditText UsernameEditText;
    EditText emailEditText;
    EditText passwordEditText;


    TextView UsernameErrorText;
    TextView emailErrorText;
    TextView passwordErrorText;


    /**
     * Initialize views for the activity.
    **/
    public void initialize(){
        mAuth = FirebaseAuth.getInstance();
        //Initialize views.
        createAccountButton = (Button)findViewById(R.id.createaccount_btn);
        UsernameEditText = (EditText)findViewById(R.id.username_editText);

        emailEditText = (EditText)findViewById(R.id.email_editText);
        passwordEditText = (EditText)findViewById(R.id.password_editText);


        UsernameErrorText = (TextView)findViewById(R.id.firstNameErrorText);

        emailErrorText = (TextView)findViewById(R.id.emailErrorText);
        passwordErrorText = (TextView)findViewById(R.id.passwordErrorText);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initialize();

        //=====ONCLICK METHODS=====
        createAccountButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                signUp();
            }
        });

    }


    //=====METHODS=====
    /**
     * This method grabs the necessary credentials from the TextViews and passes them
     * to the createNewUser() method.
     */
    //TODO:Password Re-entry field.
    public void signUp(){
        Boolean validCredentials = true;

        String email = emailEditText.getText().toString();
        if(!UtilityFunctions.isValidEmail(email)){
            emailErrorText.setVisibility(View.VISIBLE);
            validCredentials = false;
        }

        String password = passwordEditText.getText().toString();
        if(password.isEmpty() | password.length() < 8 | !UtilityFunctions.isValidPassword(password)){
            passwordErrorText.setVisibility(View.VISIBLE);
            validCredentials = false;

        }


        String username = UsernameEditText.getText().toString();
        if(username.isEmpty() | !UtilityFunctions.isValidUsername(username)){
            UsernameErrorText.setVisibility(View.VISIBLE);
            validCredentials = false;

        }

        //calls the Firebase method to create the valid new user.
        if(validCredentials == true){
            //gets rid of previously shown error texts.
            //Might be irrelevant since we navigate to another activity and this one is destroyed.
            //TODO:May be able to remove this block.
            emailErrorText.setVisibility(View.GONE);
            passwordErrorText.setVisibility(View.GONE);
            //surNameErrorText.setVisibility(View.GONE);
            UsernameErrorText.setVisibility(View.GONE);

            createNewUser(email,password,username);
        }
        if(validCredentials == false){
            //clears password for reentry.
            passwordEditText.setText("");
        }

    }
    /**
     * This method takes in the new users email, password, surname and first name in order to create
     * a new account for them and log them in using their entered credentials. If the sign up process
     * is successful then the method to send a verification email to their account is called
     * and appropriate UI navigation upon success/failure.
     * **/
    public void createNewUser(String email, String password, final String username){


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Signup Activity: signup", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            initProfile(user,username);
                            sendEmailVerification();
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

    /**
     * Navigation on successfuly sign up to the HomeActivity.
     * */
    public void signUpNavigationOnSuccess(FirebaseUser currentUser){

        if(currentUser != null){
            Intent intent = new Intent(SignUpActivity.this,HomeActivity.class);
            startActivity(intent);
        }

    }

    /**Firebase encapsulated method to send a verification email to the user's email.
     * */
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

    public void initProfile(FirebaseUser user, String username){

        UserProfileChangeRequest initializeProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        user.updateProfile(initializeProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("Login Activity","User Profile initialized.");
                        }
                    }
                });
    }
}
