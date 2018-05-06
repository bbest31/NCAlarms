package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.CreateAlarmActivity;

/**
 * Created by bbest on 05/05/18.
 */

public class SnoozeDialog extends Dialog {

    private final Activity parentActivity;

    private RadioGroup snoozeRadioGroup;

    private int snoozeLength;

    private Button okayButton;

    public SnoozeDialog(Activity a, int initValue) {
        super(a);
        this.parentActivity = a;
        this.snoozeLength = initValue;
    }

    private void initialize(){
        snoozeRadioGroup = findViewById(R.id.snooze_radio_group);
        okayButton = findViewById(R.id.snooze_okay_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_snooze_menu);
        initialize();

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateAlarmActivity) parentActivity).setSnoozeLength(snoozeLength);
                dismiss();
            }
        });

        snoozeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.one_min_radio_button:
                        snoozeLength = 1;
                        break;
                    case R.id.five_min_radio_button:
                        snoozeLength = 5;
                        break;
                    case R.id.ten_min_radio_button:
                        snoozeLength = 10;
                        break;
                    case R.id.fifteen_min_radio_button:
                        snoozeLength = 15;
                        break;
                    case R.id.twenty_min_radio_button:
                        snoozeLength = 20;
                        break;
                    case R.id.twenty_five_min_radio_button:
                        snoozeLength = 25;
                        break;
                    case R.id.thirty_min_radio_button:
                        snoozeLength = 30;
                        break;
                }
            }
        });

       setChecked(snoozeLength);


    }

    /**
     * Sets the checked item on the radio group based on the snooze length value.
     * @param value
     */
    private void setChecked(int value){
        switch(value){
            case 1:
                snoozeRadioGroup.check(R.id.one_min_radio_button);
                break;
            case 5:
                snoozeRadioGroup.check(R.id.five_min_radio_button);
                break;
            case 10:
                snoozeRadioGroup.check(R.id.ten_min_radio_button);
                break;
            case 15:
                snoozeRadioGroup.check(R.id.fifteen_min_radio_button);
                break;
            case 20:
                snoozeRadioGroup.check(R.id.twenty_min_radio_button);
                break;
            case 25:
                snoozeRadioGroup.check(R.id.twenty_five_min_radio_button);
                break;
            case 30:
                snoozeRadioGroup.check(R.id.thirty_min_radio_button);
                break;
        }
    }

}
