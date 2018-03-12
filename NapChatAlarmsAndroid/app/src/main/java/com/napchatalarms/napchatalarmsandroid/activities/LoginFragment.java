package com.napchatalarms.napchatalarmsandroid.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.customui.ForgotPassDialog;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * Created by bbest on 11/03/18.
 */

public class LoginFragment extends android.support.v4.app.Fragment {

    EditText emailEditText;
    EditText passwordEditText;
    Button loginButton;
    Button forgotPasswordBtn;
    Button backBtn;
    private String email;
    private String password;

    public LoginFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View v) {
        emailEditText = (EditText) v.findViewById(R.id.login_email_editText);
        passwordEditText = (EditText) v.findViewById(R.id.login_password_editText);
        loginButton = (Button) v.findViewById(R.id.login_btn);
        forgotPasswordBtn = (Button) v.findViewById(R.id.forgotPass_btn);
        backBtn = (Button) v.findViewById(R.id.back_btn);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if no view has focus:
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                login(getContext());
            }
        });


        forgotPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPassDialog fgtpassDialog = new ForgotPassDialog(getActivity());
                fgtpassDialog.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity activity = (LoginActivity)getActivity();
                activity.selectFragment(v);
            }
        });

    }


    /**
     * This method will grab the credentials entered into the TextViews and assign their values to the
     * local strings. Method then passes those strings in to the Firebase method signInWithEmailAndPassword().
     *
     * @see UtilityFunctions
     */
    public void login(final Context context) {

        Boolean validcreds = Boolean.TRUE;

        email = emailEditText.getText().toString();
        if (!UtilityFunctions.isValidEmail(email)) {
            validcreds = Boolean.FALSE;
        }

        password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            validcreds = Boolean.FALSE;
        }

        if (validcreds == Boolean.TRUE) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.i("Login Activity", "signInWithEmail:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                loginNavigationOnSuccess(user, context);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("signInWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_LONG).show();
                                loginNavigationOnSuccess(null, context);
                            }

                            // ...
                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is called if the user is successfully signed in and will navigate
     * to the Home Activity by confirming the <code>FirebaseUser</code> object for the current user
     * is a non-null object.
     *
     * @see FirebaseUser
     */

    public void loginNavigationOnSuccess(FirebaseUser currentUser, final Context context) {

        if (currentUser != null) {
            //Load user data.
            NapChatController.getInstance().loadUserData(context);
            LoginActivity activity = (LoginActivity) getActivity();
            activity.loginNavigationOnSuccess(currentUser, context);
        }

    }
}
