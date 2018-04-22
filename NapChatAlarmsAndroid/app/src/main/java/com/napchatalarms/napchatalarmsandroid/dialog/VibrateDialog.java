package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.napchatalarms.napchatalarmsandroid.R;

/** Dialog for the User to choose a custom vibrate pattern.
 * Created by bbest on 10/03/18.
 */
public class VibrateDialog extends Dialog implements View.OnClickListener {

    /**
     * The parent Activity.
     */
    private final Activity c;
    private Button locomotiveBtn;
    private Button heartbeatBtn;
    private Button buzzsawBtn;
    private Button offBtn;
    private Button okBtn;
    private Intent data;
    private Vibrator vibrator;

    /**
     * Instantiates a new Vibrate dialog.
     *
     * @param a the a
     */
    public VibrateDialog(Activity a) {
        super(a);
        this.c = a;
        this.vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_vibrate_menu);
        initialize();
    }

    /**
     * Initialize.
     */
    private void initialize() {
        /*
      The Off btn.
     */
        offBtn = findViewById(R.id.no_vibrate_btn);
        offBtn.setOnClickListener(this);
        /*
      The Locomotive btn.
     */
        locomotiveBtn = findViewById(R.id.locomotive_btn);
        locomotiveBtn.setOnClickListener(this);
        /*
      The Heartbeat btn.
     */
        heartbeatBtn = findViewById(R.id.heartbeat_btn);
        heartbeatBtn.setOnClickListener(this);
        /*
      The Buzzsaw btn.
     */
        buzzsawBtn = findViewById(R.id.buzzsaw_btn);
        buzzsawBtn.setOnClickListener(this);

        okBtn = findViewById(R.id.ok_vibrate_btn);
        okBtn.setOnClickListener(this);

    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.locomotive_btn:
                data = new Intent();
                data.putExtra("PATTERN", 0);
                clearSelection();
                selectButton(locomotiveBtn);
                long[] locomotivePreview = {0, 500, 100, 500, 600, 500, 100, 500};
                vibrator.vibrate(locomotivePreview,-1);
                break;
            case R.id.buzzsaw_btn:
                data = new Intent();
                data.putExtra("PATTERN", 1);
                clearSelection();
                selectButton(buzzsawBtn);
                long[] buzzsawPreview = {0,900};
                vibrator.vibrate(buzzsawPreview,-1);
                break;
            case R.id.heartbeat_btn:
                data = new Intent();
                data.putExtra("PATTERN", 2);
                clearSelection();
                selectButton(heartbeatBtn);
                long[] heartbeatPreview = {0, 200, 100, 200, 500, 200, 100, 200, 500};
                vibrator.vibrate(heartbeatPreview,-1);
                break;
            case R.id.no_vibrate_btn:
                data = new Intent();
                data.putExtra("PATTERN", -1);
                clearSelection();
                selectButton(offBtn);
                break;
            case R.id.ok_vibrate_btn:
                if(data != null){
                    c.onActivityReenter(Activity.RESULT_OK, data);
                } else{
                    c.onActivityReenter(Activity.RESULT_CANCELED,data);
                }
                dismiss();
                break;
        }

    }

    private void selectButton(Button button){
        button.setTextColor(getContext().getColor(R.color.white));
        button.setBackgroundColor(getContext().getColor(R.color.light_purple));
    }

    private void clearSelection(){
        locomotiveBtn.setTextColor(getContext().getColor(R.color.black));
        locomotiveBtn.setBackgroundColor(getContext().getColor(R.color.off_white));
        buzzsawBtn.setTextColor(getContext().getColor(R.color.black));
        buzzsawBtn.setBackgroundColor(getContext().getColor(R.color.off_white));
        heartbeatBtn.setTextColor(getContext().getColor(R.color.black));
        heartbeatBtn.setBackgroundColor(getContext().getColor(R.color.off_white));
        offBtn.setTextColor(getContext().getColor(R.color.black));
        offBtn.setBackgroundColor(getContext().getColor(R.color.off_white));

    }
}
