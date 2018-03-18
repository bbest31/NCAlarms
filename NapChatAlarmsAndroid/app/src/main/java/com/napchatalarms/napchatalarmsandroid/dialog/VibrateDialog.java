package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.napchatalarms.napchatalarmsandroid.R;

/**
 * Created by bbest on 10/03/18.
 */

public class VibrateDialog extends Dialog implements View.OnClickListener{

    public Activity c;
    Button offBtn;
    Button locomotiveBtn;
    Button buzzsawBtn;
    Button heartbeatBtn;
    Button tiptoeBtn;

    public VibrateDialog(Activity a) {
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.vibrate_menu);
        initialize();
    }

    public void initialize(){
        offBtn = (Button) findViewById(R.id.no_vibrate_btn);
        offBtn.setOnClickListener(this);
        locomotiveBtn = (Button) findViewById(R.id.locomotive_btn);
        locomotiveBtn.setOnClickListener(this);
        heartbeatBtn = (Button) findViewById(R.id.heartbeat_btn);
        heartbeatBtn.setOnClickListener(this);
        buzzsawBtn = (Button) findViewById(R.id.buzzsaw_btn);
        buzzsawBtn.setOnClickListener(this);
        tiptoeBtn = (Button) findViewById(R.id.tiptoe_btn);
        tiptoeBtn.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.locomotive_btn:
                Intent locomotive_data = new Intent();
                locomotive_data.putExtra("PATTERN",0);
                c.onActivityReenter(Activity.RESULT_OK,locomotive_data);
                break;
            case R.id.buzzsaw_btn:
                Intent buzzsaw_data = new Intent();
                buzzsaw_data.putExtra("PATTERN",2);
                c.onActivityReenter(Activity.RESULT_OK,buzzsaw_data);
                break;
            case R.id.heartbeat_btn:
                Intent hearbeat_data = new Intent();
                hearbeat_data.putExtra("PATTERN",1);
                c.onActivityReenter(Activity.RESULT_OK,hearbeat_data);
                break;
            case R.id.tiptoe_btn:
                Intent tiptoe_data = new Intent();
                tiptoe_data.putExtra("PATTERN",3);
                c.onActivityReenter(Activity.RESULT_OK,tiptoe_data);
                break;
            case R.id.no_vibrate_btn:
                Intent no_vibrate_data = new Intent();
                no_vibrate_data.putExtra("PATTERN",-1);
                c.onActivityReenter(Activity.RESULT_OK,no_vibrate_data);
                break;
        }
        dismiss();
    }
}
