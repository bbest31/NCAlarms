package com.napchatalarms.napchatalarmsandroid.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.AboutActivity;
import com.napchatalarms.napchatalarmsandroid.activities.CreditActivity;
import com.napchatalarms.napchatalarmsandroid.activities.LoginActivity;
import com.napchatalarms.napchatalarmsandroid.activities.OnboardingActivity;
import com.napchatalarms.napchatalarmsandroid.activities.OpenSrcLibActivity;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.dialog.ChangeUsernameDialog;
import com.napchatalarms.napchatalarmsandroid.dialog.DeleteAccountDialog;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.Toaster;
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

    private Button verifyEmailBtn;


    /**
     * Instantiates a new Options fragment.
     */
    public OptionsFragment() {

    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        initialize(view);
        return view;
    }

    /**
     * Initializes views to variables.
     */
    private void initialize(View view) {

        /*
      The Logout button.
     */
        Button logoutButton = view.findViewById(R.id.logout_btn);
        verifyEmailBtn = view.findViewById(R.id.verified_email_btn);
        //Button changeNameBtn = view.findViewById(R.id.change_username_btn);
        Button resetPassBtn = view.findViewById(R.id.opt_reset_pwd_btn);
        Button deleteAccountBtn = view.findViewById(R.id.delete_account_btn);
        Button aboutBtn = view.findViewById(R.id.about_btn);
        Button faqBtn = view.findViewById(R.id.faq_btn);
        Button upgradeBtn = view.findViewById(R.id.upgrade_btn);
        Button privacyPolicyBtn = view.findViewById(R.id.privacy_policy_btn);
        Button rateBtn = view.findViewById(R.id.rate_btn);
        Button shareBtn = view.findViewById(R.id.share_app_btn);
        Button submitFeedbackBtn = view.findViewById(R.id.feedback_btn);
        Button openSrcBtn = view.findViewById(R.id.open_src_btn);
        Button inviteBtn = view.findViewById(R.id.opt_inv_btn);
        Button languageBtn = view.findViewById(R.id.opt_lang_btn);
        Button creditsBtn = view.findViewById(R.id.credits_btn);
        Button tutorialBtn = view.findViewById(R.id.tutorial_btn);
        Button whatsNewBtn = view.findViewById(R.id.whats_new_btn);


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

//        changeNameBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                changeUsername();
//            }
//        });


        resetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

        upgradeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        faqBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        tutorialBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent onboard = new Intent(getActivity(), OnboardingActivity.class);
                getActivity().startActivity(onboard);
            }
        });

        whatsNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(),getLayoutInflater()).show();
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        privacyPolicyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });


        aboutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                //noinspection ConstantConditions
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
                //noinspection ConstantConditions
                getActivity().startActivity(intent);
            }
        });

        inviteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        languageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        creditsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CreditActivity.class);
                //noinspection ConstantConditions
                getActivity().startActivity(intent);
            }
        });

        submitFeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = getString(R.string.support_email);
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
                String timestamp = dateFormat.format(System.currentTimeMillis());
                String subject = "Napchat Feedback Android " + getString(R.string.version_number) + " " + timestamp;
                String header = "---------------\n" +
                        "APP VERSION: " + getString(R.string.version_number) + "\n" +
                        "DEVICE: " + Build.MANUFACTURER + " " + Build.MODEL + "\n" +
                        "ANDROID: " + Build.VERSION.RELEASE + " SDK: " + Build.VERSION.SDK_INT + "\n" +
                        "--------------- \n " +
                        "YOUR FEEDBACK BELOW";

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + recipient));
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
    private void logout() {
        ArrayList<Alarm> alarmArrayList = User.getInstance().getAlarmList();
        for (Alarm a : alarmArrayList) {
            AlarmController.getInstance().cancelAlarm(getContext(), a.getId());
        }
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        NapChatController.getInstance().uninitializeUser();
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        //noinspection ConstantConditions
        getActivity().finish();
    }

    /**
     * Gets the current user and resend a verification email to the user email address.
     *
     * @see FirebaseAuth
     */
    private void resendVerificationEmail() {
        //noinspection ConstantConditions
        FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Log.i("Options Fragment", "Resent Verification Email successfully");
                            Toaster.createEmailSuccessToast(getActivity(), getLayoutInflater()).show();
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
//    private void changeUsername() {
//        ChangeUsernameDialog newUsernameDialog = new ChangeUsernameDialog(getActivity());
//        newUsernameDialog.show();
//    }


    /**
     * This method sends a reset password email in order to change the users password.
     *
     * @see FirebaseAuth
     */
    private void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = User.getInstance().getEmail();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toaster.createEmailSuccessToast(getActivity(), getLayoutInflater()).show();

//                            Log.i("Options Activity", "Email sent.");
                        }
                    }
                });
    }

    private void checkEmailVerification() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Checks to see if the user has a verified email.
        //noinspection ConstantConditions
        if (!user.isEmailVerified()) {
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
            //noinspection ConstantConditions
            verifyEmailBtn.setTextColor(getContext().getColor(R.color.verified_green));
            verifyEmailBtn.setText(R.string.verified);
        }
    }


}
