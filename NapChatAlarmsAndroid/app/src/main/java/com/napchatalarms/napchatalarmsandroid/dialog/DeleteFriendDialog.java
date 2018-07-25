package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;
import com.napchatalarms.napchatalarmsandroid.model.User;

/**
 * Created by bbest on 30/06/18.
 */

public class DeleteFriendDialog extends Dialog implements android.view.View.OnClickListener {
    /**
     * The parent activity.
     */
    private final Activity parent;
    /**
     * This dialog refence.
     */
    public Dialog d;

    private String targetUID;


    public DeleteFriendDialog(Activity a, String uid) {
        super(a);
        this.parent = a;
        this.targetUID = uid;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_delete_friend);

        Button confirmButton = findViewById(R.id.delete_friend_confirm_btn);
        Button cancelButton = findViewById(R.id.delete_friend_cancel_btn);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.delete_friend_confirm_btn:
                removeFriend();
                break;
            case R.id.delete_friend_cancel_btn:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void removeFriend() {
        FirebaseDAO.getInstance().removeFriend(User.getInstance().getUid(), targetUID);
    }
}
