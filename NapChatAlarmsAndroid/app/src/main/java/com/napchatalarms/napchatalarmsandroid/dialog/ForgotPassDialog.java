package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.utility.InputValidator;


/**
 * Java class for the Forgot Password Dialog Box to send a password reset email, to the entered email address
 * if it indeed exists with an account.
 *
 * @author bbest
 */
@SuppressWarnings("unused")
public class ForgotPassDialog extends Dialog implements android.view.View.OnClickListener {

    /**
     * The D.
     */
    @SuppressWarnings("unused")
    public Dialog d;
    /**
     * The Email entry.
     */
    private EditText emailEntry;

    private TextView errText;
    /**
     * Public constructor taking in the <code>Activity</code> to appear over.
     *
     * @param a - Activity to appear over.
     */
    public ForgotPassDialog(Activity a) {
        super(a);
        /*
      The C.
     */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_forgot_password);
        /*
      The Send.
     */
        Button send = findViewById(R.id.forgot_pass_send_btn);
        Button cancel = findViewById(R.id.forgot_pass_cancel_btn);
        emailEntry = findViewById(R.id.password_reset_email_entry);
        errText = findViewById(R.id.forgot_password_error_text);
        send.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_pass_send_btn:
                sendResetPasswordEmail();
                break;
            case R.id.forgot_pass_cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
    }

    /**
     * This method sends a password reset email entered into the edit text field.
     *
     * @see FirebaseAuth
     */
    private void sendResetPasswordEmail() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String emailAddress = emailEntry.getText().toString();

        if (InputValidator.isValidEmail(emailAddress)) {
            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
//                                Log.i("ForgotPassDialog", "Email sent.");
                                //UtilityFunctions.createEmailSuccessToast(getOwnerActivity(), getLayoutInflater()).show();
                                dismiss();
                            } else {
                                //Email may not exists with an account so we should display some kind of error
//                                Log.i("ForgotPassDialog", "Email could not be sent!");
                               // UtilityFunctions.createConnectionErrorToast(getOwnerActivity(), getLayoutInflater()).show();
                                errText.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } else {
            errText.setVisibility(View.VISIBLE);
        }


    }
}
