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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.User;
import com.napchatalarms.napchatalarmsandroid.utility.InputValidator;

/** Dialog box used to change the User's username.
 * Created by bbest on 15/03/18.
 */
public class ChangeUsernameDialog extends Dialog {

    private EditText changeNameEditText;
    private Button submitBtn;
    private Button cancelBtn;
    private TextView errText;


    /**
     * Instantiates a new Change username dialog.
     *
     * @param a the a
     */
    public ChangeUsernameDialog(Activity a) {
        super(a);

    }

    /**
     * @param savedInstanceState - instance saved from previous creation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_change_username);
        initialize();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeUsername();
            }
        });
    }


    /**
     * Initializes view components for {@link com.napchatalarms.napchatalarmsandroid.activities.AboutActivity}.
     */
    private void initialize() {
        changeNameEditText = findViewById(R.id.new_name_edittext);
        submitBtn = findViewById(R.id.submit_new_name_btn);
        cancelBtn = findViewById(R.id.new_name_cancel_btn);
        errText = findViewById(R.id.change_usrnm_err_text);

    }

    /**
     *
     */
    private void changeUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (!changeNameEditText.getText().toString().isEmpty() &&
                InputValidator.isValidUsername(changeNameEditText.getText().toString())) {

            final String newName = changeNameEditText.getText().toString();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(newName)
                    .build();
            //noinspection ConstantConditions
            user.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                User.getInstance().setName(newName);
                                changeNameEditText.setText("");
//                                Toast.makeText(parentActivity, "Username successfully changed!", Toast.LENGTH_LONG).show();
//                                Log.i("Options Activity", "The User's username has been updated successfully");
                            } else {
                                errText.setVisibility(View.VISIBLE);
                            }
                        }
                    });
        } else {
            errText.setVisibility(View.VISIBLE);
        }
    }
}
