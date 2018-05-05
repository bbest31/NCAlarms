package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.LoginActivity;
import com.napchatalarms.napchatalarmsandroid.activities.SignUpActivity;

/** Fragment users see when opening app that prompts either login options or sign up.
 * Created by bbest on 11/03/18.
 */
@SuppressWarnings("unused")
public class LandingFragment extends android.support.v4.app.Fragment {

    private static final String TAG = "LandingFragment";
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
    @SuppressWarnings("unused")
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
        @SuppressWarnings("unused") TextView alreadyHaveAcctText = view.findViewById(R.id.have_acct_text_view);

        /*
        Google Signin Button
        */
        SignInButton googleSignIn = view.findViewById(R.id.google_sign_in_button);
        /*
      The App name.
     */
        @SuppressWarnings("unused") TextView appName = view.findViewById(R.id.app_name_text);
        /*
      The Divider text.
     */
        @SuppressWarnings("unused") TextView dividerText = view.findViewById(R.id.or_text_view);


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
                //noinspection ConstantConditions
                activity.selectFragment(v);

            }
        });

        LoginActivity activity = (LoginActivity) getActivity();



        googleSignIn.setOnClickListener(new View.OnClickListener() {
            LoginActivity activity = (LoginActivity) getActivity();

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.google_sign_in_button:
                        signIn();
                        break;
                    // ...
                }
            }

            private void signIn() {
                // Configure Google Sign In
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
                        .requestIdToken(getActivity().getResources().getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();

                // Build a GoogleSignInClient with the options specified by gso.
                GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                getActivity().startActivityForResult(signInIntent, activity.RC_SIGN_IN);
            }
        });


        // Initialize Facebook Login button
//        mCallbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = view.findViewById(R.id.face_login);
        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.setFragment(this);
        loginButton.registerCallback(activity.mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                LoginActivity activity = (LoginActivity) getActivity();
                activity.handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });



    }


}
