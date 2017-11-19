package com.napchatalarms.napchatalarmsandroid;

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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by brand on 11/19/2017.
 */

public class DeleteAccountDialog extends Dialog implements android.view.View.OnClickListener{

    public Activity c;
    public Dialog d;
    public Button confirm, cancel;
    public EditText emailEntry;
    public EditText passEntry;

    public DeleteAccountDialog(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.delete_account_dialog);
        confirm = (Button) findViewById(R.id.del_accnt_confirm_btn);
        cancel = (Button) findViewById(R.id.del_accnt_cancel_btn);
        emailEntry = (EditText)findViewById(R.id.del_accnt_email_edittext);
        passEntry = (EditText)findViewById(R.id.del_accnt_pass_edittext);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.del_accnt_confirm_btn:

                //TODO: need some indicator that the deletion worked to then go to Login screen.
               // reAuth();
                //Intent intent = new Intent(c,LoginActivity.class);
                //c.startActivity(intent);
                c.finish();
                break;
            case R.id.del_accnt_cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public void reAuth(){
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
                        Log.d("Re-Authentication", "User re-authenticated.");
                        deleteAccount();
                    }
                });


    }

    public void deleteAccount(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("deleteAccount", "User account deleted.");
                        }
                    }
                });
    }
}
