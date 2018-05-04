package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.SignUpActivity;
import com.napchatalarms.napchatalarmsandroid.utility.InputValidator;
import com.napchatalarms.napchatalarmsandroid.utility.Toaster;

/** Fragment in signup process where the User enters there desired email and username.
 * Created by bbest on 12/03/18.
 */
public class SignUpFragment extends android.support.v4.app.Fragment {

    /**
     * The Email edit text.
     */
    private EditText emailEditText;
    /**
     * The Password edit text.
     */
    private EditText passwordEditText;

    /**
     * Instantiates a new Sign up email fragment.
     */
    public SignUpFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        /*
      The Next button.
     */
        Button confirmButton = view.findViewById(R.id.signup_confirm_btn);
        emailEditText = view.findViewById(R.id.signup_email_edittext);
        passwordEditText = view.findViewById(R.id.signup_pwd_edittext);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validCredentials = true;
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                int err = 0;

                if (!InputValidator.isValidEmail(email) || email.trim().isEmpty()) {
                    err = 1;
                    validCredentials = false;

                } else if (!InputValidator.isValidPassword(password) || password.trim().isEmpty()) {
                    err = 2;
                    validCredentials = false;
                }


                if (validCredentials) {
                    SignUpActivity activity = (SignUpActivity) getActivity();
                    //noinspection ConstantConditions
                    activity.email = email;
                    activity.password = password;
                    activity.createNewUser();
                }
                if (!validCredentials) {
                    Toast toast = errorToast(err);
                    toast.show();

                }


            }
        });
    }

    private Toast errorToast(int err) {
        Toast toast = null;
        switch (err) {
            case 1:
                toast = Toaster.createInvalidEmailToast(getActivity(), getLayoutInflater());
                break;
            case 2:
                toast = Toaster.createInvalidPasswordToast(getActivity(), getLayoutInflater());
                break;
            default:
                break;

        }

        return toast;
    }
}
