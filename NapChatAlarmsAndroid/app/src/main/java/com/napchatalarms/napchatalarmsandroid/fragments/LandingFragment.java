package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.LoginActivity;
import com.napchatalarms.napchatalarmsandroid.activities.SignUpActivity;

/**
 * Created by bbest on 11/03/18.
 */

public class LandingFragment extends android.support.v4.app.Fragment {

    Button signUpButton;
    Button showLoginBtn;
    TextView alreadyHaveAcctText;
    Button fbBtn;
    TextView appName;
    TextView dividerText;

    public LandingFragment() {

    }

    @Override
    public void onResume(){super.onResume();}

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_landing, container, false);
        initialize(view);
        return view;
    }

    /**
     * Initializing function for the views.
     **/
    private void initialize(View view) {
        signUpButton = (Button) view.findViewById(R.id.signUp_btn);
        showLoginBtn = (Button) view.findViewById(R.id.show_login_btn);
        alreadyHaveAcctText = (TextView) view.findViewById(R.id.have_acct_text_view);
        fbBtn = (Button) view.findViewById(R.id.facebook_btn);
        appName = (TextView) view.findViewById(R.id.app_name_text);
        dividerText = (TextView) view.findViewById(R.id.or_text_view);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignUpActivity.class);
                startActivity(intent);
            }

        });

        showLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity activity = (LoginActivity) getActivity();
                activity.selectFragment(v);

            }
        });

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(),"",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP,0,100);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_construction_warn,
                        (ViewGroup) getActivity().findViewById(R.id.warning_toast_container));
                toast.setView(layout);
                toast.show();
            }
        });

    }


}
