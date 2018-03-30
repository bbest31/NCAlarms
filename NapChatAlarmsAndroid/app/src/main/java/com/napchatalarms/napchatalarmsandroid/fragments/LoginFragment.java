package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
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
import com.napchatalarms.napchatalarmsandroid.activities.LoginActivity;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.dialog.ForgotPassDialog;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * Created by bbest on 11/03/18.
 */
public class LoginFragment extends android.support.v4.app.Fragment {

    /**
     * The Email edit text.
     */
    private EditText emailEditText;
    /**
     * The Password edit text.
     */
    private EditText passwordEditText;

    /**
     * Instantiates a new Login fragment.
     */
    public LoginFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View v) {
        emailEditText = v.findViewById(R.id.login_email_editText);
        passwordEditText = v.findViewById(R.id.login_password_editText);
        /*
      The Login button.
     */
        Button loginButton = v.findViewById(R.id.login_btn);
        /*
      The Forgot password btn.
     */
        Button forgotPasswordBtn = v.findViewById(R.id.forgotPass_btn);
        /*
      The Back btn.
     */
        Button backBtn = v.findViewById(R.id.back_btn);

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
                ForgotPassDialog forgotPassDialog = new ForgotPassDialog(getActivity());
                forgotPassDialog.show();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity activity = (LoginActivity) getActivity();
                activity.selectFragment(v);
            }
        });

    }


    /**
     * This method will grab the credentials entered into the TextViews and assign their values to the
     * local strings. Method then passes those strings in to the Firebase method signInWithEmailAndPassword().
     *
     * @param context the context
     * @see UtilityFunctions
     */
    private void login(final Context context) {

        Boolean validCredentials = Boolean.TRUE;

        String email = emailEditText.getText().toString();
        if (!UtilityFunctions.isValidEmail(email)) {
            validCredentials = Boolean.FALSE;
        }

        String password = passwordEditText.getText().toString();
        if (password.isEmpty()) {
            validCredentials = Boolean.FALSE;
        }

        if (validCredentials == Boolean.TRUE) {
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
//                                Log.i("Login Activity", "signInWithEmail:success");
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                loginNavigationOnSuccess(user, context);
                            } else {
                                // If sign in fails, display a message to the user.
//                                Log.w("signInWithEmail:failure", task.getException());
                                Toast toast = Toast.makeText(getActivity(), getActivity().getString(R.string.invalid_credentials), Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.TOP, 0, 80);
                                toast.setText(getActivity().getString(R.string.failed_login));

                                LayoutInflater inflater = getLayoutInflater();
                                View layout = inflater.inflate(R.layout.toast_login_err,
                                        (ViewGroup) getActivity().findViewById(R.id.login_toast_container));

                                toast.setView(layout);
                                toast.show();
                                loginNavigationOnSuccess(null, context);
                            }

                            // ...
                        }
                    });
        } else {

            loginErrToast().show();
        }
    }

    /**
     * This method is called if the user is successfully signed in and will navigate
     * to the Home Activity by confirming the <code>FirebaseUser</code> object for the current user
     * is a non-null object.
     *
     * @param currentUser the current user
     * @param context     the context
     * @see FirebaseUser
     */
    private void loginNavigationOnSuccess(FirebaseUser currentUser, final Context context) {

        if (currentUser != null) {
            //Load user data.
            NapChatController.getInstance().loadUserData(context);
            LoginActivity activity = (LoginActivity) getActivity();
            activity.loginNavigationOnSuccess(currentUser, context);
        }

    }

    private Toast loginErrToast() {
        Toast toast = Toast.makeText(getActivity(), getActivity().getString(R.string.invalid_credentials), Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, 80);
        toast.setText(getActivity().getString(R.string.invalid_credentials));

        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_login_err,
                (ViewGroup) getActivity().findViewById(R.id.login_toast_container));

        toast.setView(layout);
        return toast;
    }
}
