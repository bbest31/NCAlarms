package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.dao.FirebaseDAO;

/**
 * Created by bbest on 30/06/18.
 */

public class AddFriendDialog extends Dialog implements View.OnClickListener {

    public Dialog dialog;
    private Activity parent;

    public AddFriendDialog(Activity a) {
        super(a);
        this.parent = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_friend);

        Button confirmButton = findViewById(R.id.add_friend_send_button);
        Button cancelButton = findViewById(R.id.add_friend_cancel_button);

        confirmButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_friend_send_button:
                String targetUsername = ((EditText) findViewById(R.id.add_friend_edit_text)).getText().toString();
                addFriend(targetUsername);
                break;
            case R.id.add_friend_cancel_button:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void addFriend(String target) {
        //TODO: add a confirmation toast.
        FirebaseDAO.getInstance().sendFriendRequest(target);
    }
}
