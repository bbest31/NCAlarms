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

    private RadioButton oneMinRadioButton;

    private RadioButton fiveMinRadioButton;

    private RadioButton tenMinRadioButton;

    private RadioButton fifteenMinRadioButton;

    private RadioButton twentyMinRadioButton;

    private RadioButton twentyFiveMinRadioButton;

    private RadioButton thirtyMinRadioButton;

    private RadioGroup snoozeRadioGroup;

    private int snoozeLength;

    private Button okayButton;

    public SnoozeDialog(Activity a) {
        super(a);
        this.parentActivity = a;
    }

    private void initialize(){
        snoozeRadioGroup = findViewById(R.id.snooze_radio_group);
        oneMinRadioButton = findViewById(R.id.one_min_radio_button);
        fiveMinRadioButton = findViewById(R.id.five_min_radio_button);
        tenMinRadioButton = findViewById(R.id.ten_min_radio_button);
        fifteenMinRadioButton = findViewById(R.id.fifteen_min_radio_button);
        twentyMinRadioButton = findViewById(R.id.twenty_min_radio_button);
        twentyFiveMinRadioButton = findViewById(R.id.twenty_five_min_radio_button);
        thirtyMinRadioButton = findViewById(R.id.thirty_min_radio_button);
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



    }

}
