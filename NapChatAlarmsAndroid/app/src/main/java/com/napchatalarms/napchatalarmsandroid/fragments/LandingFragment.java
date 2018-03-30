package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.LoginActivity;
import com.napchatalarms.napchatalarmsandroid.activities.SignUpActivity;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

/**
 * Created by bbest on 11/03/18.
 */
public class LandingFragment extends android.support.v4.app.Fragment {

    /**
     * Instantiates a new Landing fragment.
     */
    public LandingFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
        /*
      The Sign up button.
     */
        Button signUpButton = view.findViewById(R.id.signUp_btn);
        /*
      The Show login btn.
     */
        Button showLoginBtn = view.findViewById(R.id.show_login_btn);
        /*
      The Already have acct text.
     */
        TextView alreadyHaveAcctText = view.findViewById(R.id.have_acct_text_view);
        /*
      The Fb btn.
     */
        Button fbBtn = view.findViewById(R.id.facebook_btn);
        /*
      The App name.
     */
        TextView appName = view.findViewById(R.id.app_name_text);
        /*
      The Divider text.
     */
        TextView dividerText = view.findViewById(R.id.or_text_view);


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
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

    }


}
