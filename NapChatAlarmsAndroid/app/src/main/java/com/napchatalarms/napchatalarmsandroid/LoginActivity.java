package com.napchatalarms.napchatalarmsandroid;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String email;
    private String password;

    /**Button used to navigate to the SignUpActivity.**/
    Button signUpButton;
    EditText emailEditText;
    EditText passwordEditText;

    public void initialize()
    {
        signUpButton = (Button) findViewById(R.id.signUp_btn);
        emailEditText = (EditText)findViewById(R.id.login_emailEditText);
        passwordEditText = (EditText)findViewById(R.id.login_passwordEditText);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        initialize();

        signUpButton.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View v){
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
        }

        });


    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //Navigate to Home Activity if currentUser is already signed in.
        loginNavigationOnSuccess(currentUser);


    }



    /**
     * This method is called if the user is successfully signed in and will navigate
     * to the Home Activity by confirming the FirebaseUser object for the current user
     * is a non-null object.**/
    //TODO: Should pass some information about the user to the HomeActivity, in order to load necessary information.
    public void loginNavigationOnSuccess(FirebaseUser currentUser){

        if(currentUser != null){
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(homeIntent);
        }

    }

    /**
     * This method will grab the credentials entered into the TextViews and assign their values to the
     * local strings. Method then passes those strings in to the Firebase method signInWithEmailAndPassword().
     */
    //TODO:Reinforce email format.
    public void login(){

        Boolean validcreds = Boolean.TRUE;

        email = emailEditText.getText().toString();
        if(email.isEmpty()){
            //Handle Error
            validcreds = Boolean.FALSE;
        }

        password = passwordEditText.getText().toString();
        if(password.isEmpty()){
            //Handle Error
            validcreds = Boolean.FALSE;
        }

        if(validcreds = Boolean.TRUE)
        {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                // Log.d( "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                loginNavigationOnSuccess(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w("signInWithEmail:failure", task.getException());
                                Toast.makeText(LoginActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                loginNavigationOnSuccess(null);
                            }

                            // ...
                        }
                });
        }
        else{
            //HANDLE ERROR
        }
    }
}




