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

import com.firebase.ui.auth.AuthUI;
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
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.Toaster;

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
        Button deleteAccountBtn = view.findViewById(R.id.delete_account_btn);
        Button aboutBtn = view.findViewById(R.id.about_btn);
        Button faqBtn = view.findViewById(R.id.faq_btn);
        Button upgradeBtn = view.findViewById(R.id.upgrade_btn);
        Button privacyPolicyBtn = view.findViewById(R.id.privacy_policy_btn);
        Button rateBtn = view.findViewById(R.id.rate_btn);
        Button submitFeedbackBtn = view.findViewById(R.id.feedback_btn);
        Button openSrcBtn = view.findViewById(R.id.open_src_btn);
        Button creditsBtn = view.findViewById(R.id.credits_btn);
        Button tutorialBtn = view.findViewById(R.id.tutorial_btn);
        Button whatsNewBtn = view.findViewById(R.id.whats_new_btn);



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
                Toaster.createWarningToast(getActivity(), getLayoutInflater()).show();
            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
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
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.warning)
                        .setMessage(R.string.delete_account_warning)
                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteAccount();
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

        openSrcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OpenSrcLibActivity.class);
                //noinspection ConstantConditions
                getActivity().startActivity(intent);
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

        AuthUI.getInstance()
                .signOut(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        if(task.isSuccessful()){
                            NapChatController.getInstance().uninitializeUser();
                            Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(loginIntent);
                            //noinspection ConstantConditions
                            getActivity().finish();
                        } else {

                        }
                    }
                });

    }


    /**
     * Deletes the user account and proceeds to the <code>LoginActivity</code>
     *
     * @see FirebaseAuth
     */
    private void deleteAccount() {

        ArrayList<Alarm> alarmArrayList = User.getInstance().getAlarmList();
        for (Alarm a : alarmArrayList) {
            AlarmController.getInstance().cancelAlarm(getContext(), a.getId());
        }
        FirebaseDAO.getInstance().removeUser(User.getInstance().getUid());

        AuthUI.getInstance()
                .delete(getActivity())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        if (task.isSuccessful()) {
                            try {
                                NapChatController.getInstance().deleteFiles(getContext());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            NapChatController.getInstance().uninitializeUser();
//                            Log.i("reAuthDeleteAccount", "User account deleted.");
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                        } else {
                            //TODO: error toast
                        }
                    }
                });
    }


}
