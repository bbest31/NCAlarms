package com.napchatalarms.napchatalarmsandroid.customui;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.CreateAlarmActivity;

/**
 * The type Ringtone dialog.
 *
 * @author bbest
 */
public class RingtoneDialog extends Dialog implements android.view.View.OnClickListener{

    /**
     * The C.
     */
    public CreateAlarmActivity c;
    /**
     * The D.
     */
    public Dialog d;
    /**
     * The Cancel.
     */
    public Button cancel;
    /**
     * The Default btn.
     */
    public Button defaultBtn, /**
     * The Device btn.
     */
    deviceBtn, /**
     * The Music btn.
     */
    musicBtn, /**
     * The Nc btn.
     */
    ncBtn;

    /**
     * Public constructor taking in the <code>Activity</code> to appear over.
     *
     * @param a - Activity to appear over.
     */
    public RingtoneDialog(CreateAlarmActivity a){
            super(a);
            this.c=a;
            }

    /**
     * Initialize.
     */
    public void initialize(){
        cancel=(Button)findViewById(R.id.cancel_ringtone_btn);
        cancel.setOnClickListener(this);
        defaultBtn = (Button) findViewById(R.id.defaultRingtoneButton);
        defaultBtn.setOnClickListener(this);
        deviceBtn = (Button) findViewById(R.id.deviceRingtoneButton);
        deviceBtn.setOnClickListener(this);
        musicBtn = (Button)findViewById(R.id.musicRingtoneButton);
        musicBtn.setOnClickListener(this);
        ncBtn = (Button) findViewById(R.id.napchatRingtoneButton);
        ncBtn.setOnClickListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ringtone_menu);
        initialize();

            }
    //TODO: implement the methods or events that happen when they choose music or napchat.
    @Override
    public void onClick(View v){
        switch(v.getId()){

            case R.id.defaultRingtoneButton:
                c.setRingtone(String.valueOf(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)),"Default");
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
                deviceToneIntent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALARM);
                c.startActivityForResult(deviceToneIntent,1);
                break;
            case R.id.musicRingtoneButton:
//                Intent musicIntent = new Intent(Intent.CATEGORY_APP_MUSIC);
//                c.startActivityForResult(musicIntent,2);
                break;
            case R.id.napchatRingtoneButton:
                break;
            default:
                break;
            }
                dismiss();
    }



}
