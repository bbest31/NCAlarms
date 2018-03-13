package com.napchatalarms.napchatalarmsandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class SignUpPasswordFragment extends Fragment {

    EditText pwdEditText;
    Button signUpBtn;

    public SignUpPasswordFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup_password, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view){
        pwdEditText = (EditText) view.findViewById(R.id.signup_pwd_edittext);
        signUpBtn = (Button) view.findViewById(R.id.signup_pwd_next_btn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean validCredentials = true;
                String errMsg = "Invalid Entry";

                String password = pwdEditText.getText().toString();
                if (!UtilityFunctions.isValidPassword(password)) {
                    validCredentials = false;
                    errMsg = "Invalid Password!";
                }


                if (validCredentials == true) {
                    SignUpActivity activity = (SignUpActivity)getActivity();
                    activity.password = pwdEditText.getText().toString();
                    activity.createNewUser();
                }
                if (validCredentials == false) {
                    //clears password for reentry.
                    Toast.makeText(getActivity(),errMsg, Toast.LENGTH_LONG).show();
                    pwdEditText.setText("");
                }
            }
        });
    }

}
