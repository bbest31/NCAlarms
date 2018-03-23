package com.napchatalarms.napchatalarmsandroid.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
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
import com.napchatalarms.napchatalarmsandroid.activities.OpenSrcLibActivity;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.dialog.ChangeUsernameDialog;
import com.napchatalarms.napchatalarmsandroid.dialog.DeleteAccountDialog;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

// SOURCES: https://firebase.google.com/docs/auth/android

/**
 * Activity where users can logout, verify email, change email/password/name, delete account
 * and upgrade to paid version.
 *
 * @author bbest
 */
public class OptionsFragment extends android.support.v4.app.Fragment {

    //=====VIEWS=====
    Button logoutButton;
    private Button verifyEmailBtn;
    private Button changeNameBtn;
    private Button resetPassBtn;
    private Button shareBtn;
    private Button aboutBtn;
    private Button deleteAccountBtn;
    private Button faqBtn;
    private Button upgradeBtn;
    private Button privacyPolicyBtn;
    private Button rateBtn;
    private Button openSrcBtn;
    private Button submitFeedbackBtn;
    private Button languageBtn;
    private Button inviteBtn;


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
        resetPassBtn = (Button) view.findViewById(R.id.opt_reset_pwd_btn);
        deleteAccountBtn = (Button) view.findViewById(R.id.delete_account_btn);
        aboutBtn = (Button) view.findViewById(R.id.about_btn);
        faqBtn = (Button) view.findViewById(R.id.faq_btn);
        upgradeBtn = (Button) view.findViewById(R.id.upgrade_btn);
        privacyPolicyBtn = (Button) view.findViewById(R.id.privacy_policy_btn);
        rateBtn = (Button) view.findViewById(R.id.rate_btn);
        shareBtn = (Button) view.findViewById(R.id.share_app_btn);
        submitFeedbackBtn = (Button) view.findViewById(R.id.feedback_btn);
        openSrcBtn = (Button) view.findViewById(R.id.open_src_btn);
        inviteBtn = (Button) view.findViewById(R.id.opt_inv_btn);
        languageBtn = (Button) view.findViewById(R.id.opt_lang_btn);


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


        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        upgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        privacyPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });


        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                getActivity().startActivity(intent);
            }
        });

        deleteAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteAccountDialog deleteAccountDialog = new DeleteAccountDialog(getActivity());
                deleteAccountDialog.show();
            }
        });

        openSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OpenSrcLibActivity.class);
                getActivity().startActivity(intent);
            }
        });

        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UtilityFunctions.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        submitFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recepient = getString(R.string.support_email);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY hh:mm aa");
                String timestamp = dateFormat.format(System.currentTimeMillis());
                String subject = "Napchat Feedback Android " + getString(R.string.version_number) + " " + timestamp;
                String header = "---------------\n" +
                        "APP VERSION: " + getString(R.string.version_number) + "\n" +
                        "DEVICE: " + Build.MANUFACTURER + " " + Build.MODEL + "\n" +
                        "ANDROID: " + Build.VERSION.RELEASE + " SDK: " + Build.VERSION.SDK_INT + "\n" +
                        "--------------- \n " +
                        "YOUR FEEDBACK BELOW";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + recepient));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
                emailIntent.putExtra(Intent.EXTRA_TEXT, header);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
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
                            UtilityFunctions.createEmailSuccessToast(getActivity(), getLayoutInflater()).show();
                            verifyEmailBtn.setText(R.string.sent);
                            verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
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
                            UtilityFunctions.createEmailSuccessToast(getActivity(), getLayoutInflater()).show();

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
            verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            verifyEmailBtn.setTextColor(getResources().getColor(R.color.verified_green));
            verifyEmailBtn.setText(R.string.verified);
        }
    }


}
