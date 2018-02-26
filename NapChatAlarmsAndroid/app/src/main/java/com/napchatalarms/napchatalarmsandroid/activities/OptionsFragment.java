package com.napchatalarms.napchatalarmsandroid.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.customui.ChangeEmailDialog;
import com.napchatalarms.napchatalarmsandroid.customui.DeleteAccountDialog;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.util.ArrayList;

// SOURCES: https://firebase.google.com/docs/auth/android

/**
 * Activity where users can logout, verify email, change email/password/name, delete account
 * and upgrade to paid version.
 *
 * @author bbest
 */
public class OptionsFragment extends Fragment {

    //=====VIEWS=====
    public Button logoutButton;
    public Button resendEmailVerificationButton;
    public Button changeEmailButton;
    public Button changeNameButton;
    public EditText changeEmailEditText;
    public EditText changeUsernameEditText;
    public CheckedTextView verifiedEmailTextView;
    public Button resetPassButton;
    public Button deleteAccountButton;


    public OptionsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        initialize(view);
        return view;
    }

    /**
     * Initializes views to variables.
     */
    private void initialize(View view) {

        logoutButton = (Button) view.findViewById(R.id.logout_btn);
        resendEmailVerificationButton = (Button) view.findViewById(R.id.resend_verficiationemail_btn);
        verifiedEmailTextView = (CheckedTextView) view.findViewById(R.id.verified_email_check);
        changeEmailButton = (Button) view.findViewById(R.id.change_email_btn);
        changeNameButton = (Button) view.findViewById(R.id.change_name_btn);
        changeEmailEditText = (EditText) view.findViewById(R.id.change_email_edittext);
        changeUsernameEditText = (EditText) view.findViewById(R.id.change_username_edittext);
        resetPassButton = (Button) view.findViewById(R.id.reset_password_btn);
        deleteAccountButton = (Button) view.findViewById(R.id.delete_account_btn);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Checks to see if the user has a verified email.
        if (user.isEmailVerified() == false) {

            verifiedEmailTextView.setCheckMarkDrawable(R.drawable.ic_info_black_24dp);
            verifiedEmailTextView.setText("Email Unverified");
            verifiedEmailTextView.setBackgroundColor(getResources().getColor(R.color.unverified_red));
            verifiedEmailTextView.setVisibility(View.VISIBLE);

            resendEmailVerificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resendVerificationEmail();
                }
            });
        } else {
            resendEmailVerificationButton.setVisibility(View.GONE);
        }

        //=====ONCLICK METHODS=====

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        changeNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });

        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeEmailDialog changeEmailDialog = new ChangeEmailDialog(getActivity());
                changeEmailDialog.show();
            }
        });

        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog(getActivity());
                deleteAccountDialog.show();
            }
        });


    }
    

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {

            verifiedEmailTextView.setCheckMarkDrawable(R.drawable.ic_info_black_24dp);
            verifiedEmailTextView.setBackgroundColor(getResources().getColor(R.color.verified_green));
            verifiedEmailTextView.setText("Email Verified");
            resendEmailVerificationButton.setVisibility(View.GONE);
        } else {
            verifiedEmailTextView.setBackgroundColor(getResources().getColor(R.color.unverified_red));
            resendEmailVerificationButton.setVisibility(View.VISIBLE);
        }
    }
    //=====METHODS=====

    /**
     * Gets the current FirebaseAuth instance and logs the <code>User</code> out.
     * <p>
     * Before logging out the NapChatController saves the user alarms and settings.
     * </P>
     *
     * @see FirebaseAuth
     * @see NapChatController
     */
    public void logout() {
        ArrayList<Alarm> alarmArrayList = User.getInstance().getAlarmList();
        for (Alarm a : alarmArrayList) {
            AlarmController.getInstance().cancelAlarm(getContext(), a.getId());
        }
        FirebaseAuth.getInstance().signOut();
        NapChatController.getInstance().uninitializeUser();
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        getActivity().finish();
    }

    /**
     * Gets the current user and resend a verification email to the user email address.
     *
     * @see FirebaseAuth
     */
    public void resendVerificationEmail() {
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("Options Fragment", "Resent Verification Email successfully");
                        }
                    }
                });
    }


    /**
     * This method asserts the new first and surname are of correct format and updates the users name.
     *
     * @see UtilityFunctions
     */
    public void changeUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (!changeUsernameEditText.getText().toString().isEmpty() &&
                UtilityFunctions.isValidUsername(changeUsernameEditText.getText().toString())) {

            final String newName = changeUsernameEditText.getText().toString();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                User.getInstance().setName(newName);
                                changeUsernameEditText.setText("");
                                Toast.makeText(getActivity(), "Username successfully changed!", Toast.LENGTH_LONG).show();
                                Log.i("Options Activity", "The User's username has been updated successfully");
                            } else {
                                Toast.makeText(getActivity(), "Could not update username!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }


    /**
     * This method sends a reset password email in order to change the users password.
     *
     * @see FirebaseAuth
     */
    public void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = User.getInstance().getEmail();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Verification email sent!", Toast.LENGTH_LONG).show();
                            Log.i("Options Activity", "Email sent.");
                        }
                    }
                });
    }

}
