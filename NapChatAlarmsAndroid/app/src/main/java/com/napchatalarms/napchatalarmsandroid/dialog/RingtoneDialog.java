package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Dialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.CreateAlarmActivity;
import com.napchatalarms.napchatalarmsandroid.activities.CustomRingtoneActivity;

/**
 * The type Ringtone dialog.
 *
 * @author bbest
 */
@SuppressWarnings("unused")
public class RingtoneDialog extends Dialog implements android.view.View.OnClickListener {
    @SuppressWarnings("FieldCanBeLocal")
    private final int CUSTOM_RINGTONE_RESULT_CODE = 80;
    @SuppressWarnings("FieldCanBeLocal")
    private final int DEVICE_RINGTONE_RESULT_CODE = 14;
    @SuppressWarnings("FieldCanBeLocal")
    private final int MUSIC_RINGTONE_RESULT_CODE = 19;
    /**
     * The C.
     */
    private final CreateAlarmActivity c;
    /**
     * The D.
     */
    @SuppressWarnings("unused")
    public Dialog d;

    private final Boolean readPermission;

    /**
     * Public constructor taking in the <code>Activity</code> to appear over.
     *
     * @param a                      - Activity to appear over.
     * @param externalReadPermission the external read permission
     */
    public RingtoneDialog(CreateAlarmActivity a, Boolean externalReadPermission) {
        super(a);
        this.c = a;
        this.readPermission = externalReadPermission;
    }

    /**
     * Initialize.
     */
    private void initialize() {
        /*
      The Cancel.
     */
        Button cancel = findViewById(R.id.cancel_ringtone_btn);
        cancel.setOnClickListener(this);
        /*
      The Default btn.
     */
        Button defaultBtn = findViewById(R.id.defaultRingtoneButton);
        defaultBtn.setOnClickListener(this);
        Button deviceBtn = findViewById(R.id.deviceRingtoneButton);
        deviceBtn.setOnClickListener(this);
        Button musicBtn = findViewById(R.id.musicRingtoneButton);
        musicBtn.setOnClickListener(this);
        Button ncBtn = findViewById(R.id.napchatRingtoneButton);
        ncBtn.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_ringtone_options);
        initialize();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.defaultRingtoneButton:
                c.setRingtone(String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)), "Default");
                break;
            case R.id.cancel_ringtone_btn:
                dismiss();
                break;
            case R.id.deviceRingtoneButton:
                //Open device alarm page
                Intent deviceToneIntent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                deviceToneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");
                deviceToneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                deviceToneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
                deviceToneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM);
                c.startActivityForResult(deviceToneIntent, DEVICE_RINGTONE_RESULT_CODE);
                break;
            case R.id.musicRingtoneButton:
                if (readPermission) {
                    Intent musicIntent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                    c.startActivityForResult(musicIntent, MUSIC_RINGTONE_RESULT_CODE);
                }
                break;
            case R.id.napchatRingtoneButton:
                Intent customRingtoneIntent = new Intent(c, CustomRingtoneActivity.class);
                c.startActivityForResult(customRingtoneIntent, CUSTOM_RINGTONE_RESULT_CODE);
                break;
            default:
                break;
        }
        dismiss();
    }


}
