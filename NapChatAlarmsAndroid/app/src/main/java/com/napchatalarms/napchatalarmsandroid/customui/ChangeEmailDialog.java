package com.napchatalarms.napchatalarmsandroid.customui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Dialog which users enter a new email to be associated with their account.
 *
 * @author bbest
 */

public class ChangeEmailDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button confirm, cancel;
    public EditText emailEntry;
    public EditText passEntry;
    public EditText newemailEditText;

    /**
     * Public constructor taking in the <code>Activity</code> to appear over.
     *
     * @param a - Activity the dialog appears over
     */
    public ChangeEmailDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_email_dialog);
        confirm = (Button) findViewById(R.id.change_email_confirm_btn);
        cancel = (Button) findViewById(R.id.change_email_cancel_btn);
        emailEntry = (EditText) findViewById(R.id.change_email_email_edittext);
        passEntry = (EditText) findViewById(R.id.change_email_pass_editText);
        newemailEditText = (EditText) c.findViewById(R.id.change_email_edittext);
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

                            String newEmail = newemailEditText.getText().toString();
                            changeEmail(newEmail);

                        }
                    }
                });


    }

    /**
     * Updates the email associated with the account.
     *
     * @param newEmail - new email to associate that user account with.
     */
    //TODO:get new email entered from OptionsActivity
    public void changeEmail(String newEmail) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.i("ChangeEmailActivity:", "User email address updated.");
                            newemailEditText.setText("");
                        }
                    }
                });

    }
}
