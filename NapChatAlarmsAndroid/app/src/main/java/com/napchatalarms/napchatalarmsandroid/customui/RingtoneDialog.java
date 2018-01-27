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
 * @author bbest
 */

public class RingtoneDialog extends Dialog implements android.view.View.OnClickListener{

    public CreateAlarmActivity c;
    public Dialog d;
    public Button cancel;
    public Button defaultBtn, deviceBtn, musicBtn, ncBtn;

    /**
     * Public constructor taking in the <code>Activity</code> to appear over.
     *
     * @param a - Activity to appear over.
     */
    public RingtoneDialog(CreateAlarmActivity a){
            super(a);
            // TODO Auto-generated constructor stub
            this.c=a;
            }

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
    //TODO: implement the methods or events that happen when they choose default and device.
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
                Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone");
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, false);
                intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALARM);
                c.startActivityForResult(intent,1);
                break;
            case R.id.musicRingtoneButton:
                break;
            case R.id.napchatRingtoneButton:
                break;
            default:
                break;
            }
                dismiss();
    }



}
