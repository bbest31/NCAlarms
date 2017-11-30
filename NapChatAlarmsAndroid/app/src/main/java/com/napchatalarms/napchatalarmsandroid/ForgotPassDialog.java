package com.napchatalarms.napchatalarmsandroid;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;


/**Java class for the Forgot Password Dialog Box to send a password reset email, to the entered email address
 * if it indeed exists with an account.
 * SOURCE:
 * Created by brandon best on 11/16/2017.
 */

public class ForgotPassDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button send, cancel;
    public EditText emailEntry;

    public ForgotPassDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forgot_password_dialog);
        send = (Button) findViewById(R.id.fgtpass_send_btn);
        cancel = (Button) findViewById(R.id.fgtpass_cancel_btn);
        emailEntry = (EditText)findViewById(R.id.password_reset_email_entry);
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
     * */
    public void sendResetPasswordEmail(){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        String emailAddress = emailEntry.getText().toString();

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("ForgotPassDialog", "Email sent.");

                        }else{
                            //Email may not exists with an account so we should display some kind of error
                            Log.d("ForgotPassDialog","Email could not be sent!");
                        }
                    }
                });
    }
}
