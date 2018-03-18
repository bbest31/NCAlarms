package com.napchatalarms.napchatalarmsandroid.dialog;

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
import com.google.firebase.auth.FirebaseAuth;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.utility.UtilityFunctions;


/**
 * Java class for the Forgot Password Dialog Box to send a password reset email, to the entered email address
 * if it indeed exists with an account.
 *
 * @author bbest
 */

public class ForgotPassDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button send, cancel;
    public EditText emailEntry;

    /**
     * Public constructor taking in the <code>Activity</code> to appear over.
     *
     * @param a - Activity to appear over.
     */
    public ForgotPassDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_forgot_password);
        send = (Button) findViewById(R.id.fgtpass_send_btn);
        cancel = (Button) findViewById(R.id.fgtpass_cancel_btn);
        emailEntry = (EditText) findViewById(R.id.password_reset_email_entry);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fgtpass_send_btn:
                sendResetPasswordEmail();
                break;
            case R.id.fgtpass_cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    /**
     * This method sends a password reset email entered into the edit text field.
     *
     * @see FirebaseAuth
     */
    public void sendResetPasswordEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String emailAddress = emailEntry.getText().toString();

        if(UtilityFunctions.isValidEmail(emailAddress)){
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("ForgotPassDialog", "Email sent.");
                                UtilityFunctions.createEmailSuccessToast(getOwnerActivity(),getLayoutInflater()).show();
                            } else {
                                //Email may not exists with an account so we should display some kind of error
                                Log.i("ForgotPassDialog", "Email could not be sent!");
                                //TODO Custom Toast
                            }
                        }
                    });
        } else {
            //TODO Custom Toast

        }


    }
}
