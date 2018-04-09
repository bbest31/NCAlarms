package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.SignUpActivity;
import com.napchatalarms.napchatalarmsandroid.utility.Toaster;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/** Sign up flow fragment where User enters a valid password.
 * Created by bbest on 12/03/18.
 */
public class SignUpPasswordFragment extends Fragment {

    /**
     * The Pwd edit text.
     */
    private EditText pwdEditText;

    /**
     * Instantiates a new Sign up password fragment.
     */
    public SignUpPasswordFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_password, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        pwdEditText = view.findViewById(R.id.signup_pwd_edittext);
        /*
      The Sign up btn.
     */
        Button signUpBtn = view.findViewById(R.id.signup_pwd_next_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validCredentials = true;

                String password = pwdEditText.getText().toString();
                if (!UtilityFunctions.isValidPassword(password)) {
                    validCredentials = false;
                }

                if (validCredentials) {
                    SignUpActivity activity = (SignUpActivity) getActivity();
                    //noinspection ConstantConditions
                    activity.password = pwdEditText.getText().toString();
                    activity.createNewUser();
                }
                if (!validCredentials) {
                    //clears password for reentry.
                   Toaster.createInvalidCredentials(getActivity(),getLayoutInflater()).show();
                    pwdEditText.setText("");
                }
            }
        });
    }


}
