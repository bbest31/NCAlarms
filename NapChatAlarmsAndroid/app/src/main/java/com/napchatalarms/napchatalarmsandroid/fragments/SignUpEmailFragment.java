package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.SignUpActivity;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * Created by bbest on 12/03/18.
 */

public class SignUpEmailFragment extends android.support.v4.app.Fragment {

    Button nextButton;
    EditText emailEditText;
    EditText usernameEditText;

    public SignUpEmailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_email_usrnm, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        nextButton = (Button) view.findViewById(R.id.signup_email_next_btn);
        emailEditText = (EditText) view.findViewById(R.id.signup_email_edittext);
        usernameEditText = (EditText) view.findViewById(R.id.signup_username_edittext);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validCredentials = true;
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                int err = 0;

                if (!UtilityFunctions.isValidEmail(email) || email.trim().isEmpty()) {
                    err = 1;
                    validCredentials = false;

                } else if (!UtilityFunctions.isValidUsername(username) || username.trim().isEmpty()) {
                    err = 2;
                    validCredentials = false;
                }


                if (validCredentials == true) {
                    SignUpActivity activity = (SignUpActivity) getActivity();
                    activity.email = email;
                    activity.username = username;
                    activity.selectFragment(v);
                }
                if (validCredentials == false) {
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
                toast = UtilityFunctions.createInvalidEmailToast(getActivity(), getLayoutInflater());
                break;
            case 2:
                toast = UtilityFunctions.createInvalidUsernameToast(getActivity(), getLayoutInflater());
                break;
            default:
                break;

        }

        return toast;
    }
}
