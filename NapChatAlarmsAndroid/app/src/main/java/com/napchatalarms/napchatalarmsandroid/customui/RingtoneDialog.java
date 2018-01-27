package com.napchatalarms.napchatalarmsandroid.customui;

import android.app.Activity;
import android.app.Dialog;
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

@Override
protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ringtone_menu);
        cancel=(Button)findViewById(R.id.cancel_ringtone_btn);
        cancel.setOnClickListener(this);

        }
//TODO: implement the methods or events that happen when they choose default and device.
@Override
public void onClick(View v){
        switch(v.getId()){
        case R.id.defaultRingtoneButton:
            break;
        case R.id.cancel_ringtone_btn:
            dismiss();
            break;
        default:
            break;
        }
            dismiss();
        }



}
