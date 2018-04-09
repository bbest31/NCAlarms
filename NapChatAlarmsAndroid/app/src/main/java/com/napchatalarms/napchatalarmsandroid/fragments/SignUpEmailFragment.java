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
import com.napchatalarms.napchatalarmsandroid.utility.Toaster;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/** Fragment in signup process where the User enters there desired email and username.
 * Created by bbest on 12/03/18.
 */
public class SignUpEmailFragment extends android.support.v4.app.Fragment {

    /**
     * The Email edit text.
     */
    private EditText emailEditText;
    /**
     * The Username edit text.
     */
    private EditText usernameEditText;

    /**
     * Instantiates a new Sign up email fragment.
     */
    public SignUpEmailFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_email_usrnm, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        /*
      The Next button.
     */
        Button nextButton = view.findViewById(R.id.signup_email_next_btn);
        emailEditText = view.findViewById(R.id.signup_email_edittext);
        usernameEditText = view.findViewById(R.id.signup_username_edittext);

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


                if (validCredentials) {
                    SignUpActivity activity = (SignUpActivity) getActivity();
                    //noinspection ConstantConditions
                    activity.email = email;
                    activity.username = username;
                    activity.selectFragment(v);
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
                toast = Toaster.createInvalidUsernameToast(getActivity(), getLayoutInflater());
                break;
            default:
                break;

        }

        return toast;
    }
}
