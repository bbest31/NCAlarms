package com.napchatalarms.napchatalarmsandroid.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.AboutActivity;
import com.napchatalarms.napchatalarmsandroid.activities.LoginActivity;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.customui.ChangeUsernameDialog;
import com.napchatalarms.napchatalarmsandroid.customui.DeleteAccountDialog;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.util.ArrayList;

// SOURCES: https://firebase.google.com/docs/auth/android

/**
 * Activity where users can logout, verify email, change email/password/name, delete account
 * and upgrade to paid version.
 *@todo Upgrade Button , Rate button method, FAQ, Privacy Policy,
 * @author bbest
 */
public class OptionsFragment extends android.support.v4.app.Fragment {

    //=====VIEWS=====
    Button logoutButton;
    private Button verifyEmailBtn;
    private Button changeNameBtn;
    private Button resetPassButton;
    private Button aboutBtn;
    private Button deleteAccountButton;


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
        verifyEmailBtn = (Button) view.findViewById(R.id.verified_email_btn);
        changeNameBtn = (Button) view.findViewById(R.id.change_username_btn);
        resetPassButton = (Button) view.findViewById(R.id.opt_reset_pwd_btn);
        deleteAccountButton = (Button) view.findViewById(R.id.delete_account_btn);
        aboutBtn = (Button)view.findViewById(R.id.about_btn);

        checkEmailVerification();

        //=====ONCLICK METHODS=====

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.warning)
                .setMessage(R.string.logout_warning)
                .setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                builder.setNegativeButton(R.string.Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    //Cancel
                    }
                });

                 builder.create().show();

            }
        });

        changeNameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });


        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(intent);
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

        checkEmailVerification();
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
        ChangeUsernameDialog newUsernameDialog = new ChangeUsernameDialog(getActivity());
        newUsernameDialog.show();
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
                            Toast.makeText(getActivity(), "Reset email sent!", Toast.LENGTH_LONG).show();
                            Log.i("Options Activity", "Email sent.");
                        }
                    }
                });
    }

    private void checkEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Checks to see if the user has a verified email.
        if (user.isEmailVerified() == false) {
            verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resendVerificationEmail();
                }
            });
        } else {
            verifyEmailBtn.setTextColor(getResources().getColor(R.color.dark_grey));
        }
    }

}
