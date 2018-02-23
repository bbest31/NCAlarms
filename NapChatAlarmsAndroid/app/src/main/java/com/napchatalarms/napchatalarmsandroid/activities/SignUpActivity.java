package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.io.IOException;

/**
 * Activity that allows potential users to sign up using
 * email, password and username.
 *
 * @author bbest
 */
// SOURCES: https://firebase.google.com/docs/auth/android
public class SignUpActivity extends AppCompatActivity {

    Button createAccountButton;
    EditText UsernameEditText;
    EditText emailEditText;
    EditText passwordEditText;
    TextView UsernameErrorText;
    TextView emailErrorText;
    TextView passwordErrorText;
    //=====VIEWS=====
    private FirebaseAuth mAuth;

    /**
     * Initialize views for the activity.
     **/
    public void initialize() {
        mAuth = FirebaseAuth.getInstance();
        //Initialize views.
        createAccountButton = (Button) findViewById(R.id.create_account_btn);
        UsernameEditText = (EditText) findViewById(R.id.username_editText);

        emailEditText = (EditText) findViewById(R.id.email_editText);
        passwordEditText = (EditText) findViewById(R.id.password_editText);


        UsernameErrorText = (TextView) findViewById(R.id.username_error_text);

        emailErrorText = (TextView) findViewById(R.id.email_error_text);
        passwordErrorText = (TextView) findViewById(R.id.password_error_text);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        initialize();

        //=====ONCLICK METHODS=====
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }


    //=====METHODS=====

    /**
     * This method grabs the necessary credentials from the TextViews and passes them
     * to the <code>createNewUser()</code> method.
     *
     * @see UtilityFunctions
     */
    public void signUp() {
        Boolean validCredentials = true;

        String email = emailEditText.getText().toString();
        if (!UtilityFunctions.isValidEmail(email)) {
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_LONG).show();
            //emailErrorText.setVisibility(View.VISIBLE);
            validCredentials = false;
        }

        String password = passwordEditText.getText().toString();
        if (!UtilityFunctions.isValidPassword(password)) {
            Toast.makeText(this, "Invalid Password", Toast.LENGTH_LONG).show();
            //passwordErrorText.setVisibility(View.VISIBLE);
            validCredentials = false;

        }


        String username = UsernameEditText.getText().toString();
        if (!UtilityFunctions.isValidUsername(username)) {
            Toast.makeText(this, "Invalid Username", Toast.LENGTH_LONG).show();
            //UsernameErrorText.setVisibility(View.VISIBLE);
            validCredentials = false;

        }

        //calls the Firebase method to create the valid new user.
        if (validCredentials == true) {
            createNewUser(email, password, username);
        }
        if (validCredentials == false) {
            //clears password for reentry.
            passwordEditText.setText("");
        }

    }

    /**
     * This method takes in the new users email, password and username in order to create
     * a new account for them and log them in using their entered credentials.
     * <p>
     * If the sign up process is successful then the method sends a verification email to their inbox.
     * The <code>NapChatController</code> then is called to create a new file directory to store the
     * users local settings and alarm files. Finally the appropriate UI navigation upon success/failure.
     * </P>
     *
     * @see FirebaseAuth
     * @see NapChatController
     */
    public void createNewUser(String email, String password, final String username) {


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            initProfile(user, username);
                            sendEmailVerification();
                            try {
                                //Create new files directory for user in internal storage
                                NapChatController.getInstance().createUserFiles(getApplicationContext());
                                FirebaseDAO dao = FirebaseDAO.getInstance();
                                User newUser = User.getInstance();
                                dao.writeUserUID(newUser.getUid());
                                dao.writeUser(newUser);
//                                dao.writeFriendsList(newUser.getUid(), newUser.getFriendList());
//                                dao.writeAlerts(newUser.getUid(), newUser.getAlerts());
//                                dao.writeGroups(newUser.getUid(), newUser.getGroupMap());
//                                dao.writeFriendRequest(newUser.getUid(), newUser.getFriendRequests());
                            } catch (IOException e) {

                            }

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
     */
    public void signUpNavigationOnSuccess(FirebaseUser currentUser) {

        if (currentUser != null) {
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    /**
     * Firebase encapsulated method to send a verification email to the user's email.
     */
    public void sendEmailVerification() {
        mAuth.getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.w("SignUp Activity", "Email verification sent.");

                        }
                    }
                });
    }

    public void initProfile(FirebaseUser user, String username) {

        UserProfileChangeRequest initializeProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();
        user.updateProfile(initializeProfile)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Login Activity", "User Profile initialized.");
                        }
                    }
                });
    }
}
