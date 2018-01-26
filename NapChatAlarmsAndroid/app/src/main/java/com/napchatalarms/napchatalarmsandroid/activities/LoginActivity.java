package com.napchatalarms.napchatalarmsandroid.activities;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.customui.ForgotPassDialog;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.services.NapChatController;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

// SOURCES: https://firebase.google.com/docs/auth/android

/**
 * Activity that allows the users to sign in using email and password.
 * <P>
 *     There are also options to reset password as well sign up.
 * </P>
 * @author bbest
 */
public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private String email;
    private String password;

    //Views
    Button signUpButton;
    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    Button forgotPasswordButton;
    TextView errorText;

    /**
     * Initializing function for the views.
     * **/
    public void initialize()
    {
        loginButton = (Button)findViewById(R.id.login_btn);
        signUpButton = (Button) findViewById(R.id.signUp_btn);
        emailEditText = (EditText)findViewById(R.id.login_email_editText);
        passwordEditText = (EditText)findViewById(R.id.login_password_editText);
        forgotPasswordButton = (Button)findViewById(R.id.forgotPass_btn);
        errorText = (TextView)findViewById(R.id.login_error_text);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        initialize();

        //===============OnClick methods====================

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                login(getApplicationContext());
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View v){
            Intent intent = new Intent(LoginActivity.this,SignUpActivity.class);
            startActivity(intent);
        }

        });

        forgotPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            ForgotPassDialog fgtpassDialog = new ForgotPassDialog(LoginActivity.this);
            fgtpassDialog.show();
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


    //=======Methods======

    /**
     * This method is called if the user is successfully signed in and will navigate
     * to the Home Activity by confirming the <code>FirebaseUser</code> object for the current user
     * is a non-null object.
     * @see FirebaseUser
     * */

    public void loginNavigationOnSuccess(FirebaseUser currentUser){

        if(currentUser != null){
            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(homeIntent);
            finish();
        }

    }

    /**
     * This method will grab the credentials entered into the TextViews and assign their values to the
     * local strings. Method then passes those strings in to the Firebase method signInWithEmailAndPassword().
     * @see UtilityFunctions
     */
    public void login(final Context context){

        Boolean validcreds = Boolean.TRUE;

        email = emailEditText.getText().toString();
        if(!UtilityFunctions.isValidEmail(email)){
            validcreds = Boolean.FALSE;
        }

        password = passwordEditText.getText().toString();
        if(password.isEmpty()){
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
                                 Log.d( "Login Activity","signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //Load user data.
//                                NapChatController controller = NapChatController.getInstance();
//                                controller.loadUser(context);

                                loginNavigationOnSuccess(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signInWithEmail:failure", task.getException());
                                errorText.setVisibility(View.VISIBLE);
                                loginNavigationOnSuccess(null);
                            }

                            // ...
                        }
                });
        }
        else{
            errorText.setVisibility(View.VISIBLE);
        }
    }
}




