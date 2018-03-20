package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.LoginActivity;
import com.napchatalarms.napchatalarmsandroid.controller.AlarmController;
import com.napchatalarms.napchatalarmsandroid.controller.NapChatController;
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;
import com.napchatalarms.napchatalarmsandroid.model.Alarm;
import com.napchatalarms.napchatalarmsandroid.model.User;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Dialog Box to re-authenticate the User in order to confirm identity before deleting the account.
 *
 * @author bbest
 */
public class DeleteAccountDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button confirm, cancel;
    public EditText emailEntry;
    public EditText passEntry;
    private TextView errText;

    /**
     * Public constructor taking in the <code>Activity</code> to appear over.
     *
     * @param a - Activity to appear over.
     */
    public DeleteAccountDialog(Activity a) {
        super(a);
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_account);
        confirm = (Button) findViewById(R.id.delete_account_confirm_btn);
        cancel = (Button) findViewById(R.id.delete_account_cancel_btn);
        emailEntry = (EditText) findViewById(R.id.delete_account_email_editText);
        passEntry = (EditText) findViewById(R.id.delete_account_pass_editText);
        errText = (TextView) findViewById(R.id.del_acct_err_text);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_account_confirm_btn:
                reAuth();
                break;
            case R.id.delete_account_cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    /**
     * Re-authenticates the user before performing the delete account action.
     *
     * @see FirebaseUser
     * @see AuthCredential
     */
    public void reAuth() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = emailEntry.getText().toString();
        String pass = passEntry.getText().toString();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, pass);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.i("Re-Authentication", "User re-authenticated.");
                        if (task.isSuccessful()) {
                            deleteAccount();
                        }else{
                            errText.setVisibility(View.VISIBLE);
                        }
                    }
                });


    }

    /**
     * Deletes the user account and proceeds to the <code>LoginActivity</code>
     *
     * @see FirebaseAuth
     */
    public void deleteAccount() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        ArrayList<Alarm> alarmArrayList = User.getInstance().getAlarmList();
        for (Alarm a : alarmArrayList) {
            AlarmController.getInstance().cancelAlarm(getContext(), a.getId());
        }
        FirebaseDAO.getInstance().removeUser(User.getInstance().getUid());
        user.unlink(user.getProviderId());
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            try {
                                NapChatController.getInstance().deleteFiles(getContext());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            NapChatController.getInstance().uninitializeUser();
                            Log.i("reAuthDeleteAccount", "User account deleted.");
                            Intent intent = new Intent(c, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            c.startActivity(intent);
                            c.finish();
                        } else{
                            errText.setText("System Error");
                            errText.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
}
