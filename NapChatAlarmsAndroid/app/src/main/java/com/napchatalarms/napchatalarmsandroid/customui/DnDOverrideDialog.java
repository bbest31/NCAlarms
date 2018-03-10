package com.napchatalarms.napchatalarmsandroid.customui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Created by bbest on 10/03/18.
 */

public class DnDOverrideDialog extends Dialog implements android.view.View.OnClickListener {

    public Activity c;
    public Button allow;
    public Button deny;
    /**
     * Creates a dialog window that uses the default dialog theme.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     * @param a the context in which the dialog should run
     */
    public DnDOverrideDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dnd_permission_dialog);
        allow = (Button) findViewById(R.id.allow_btn);
        deny = (Button) findViewById(R.id.deny_btn);
        allow.setOnClickListener(this);
        deny.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.allow_btn:
                Intent intent = new Intent(android.provider.Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
                c.startActivity(intent);
                break;
            case R.id.deny_btn:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

}
