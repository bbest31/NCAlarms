package com.napchatalarms.napchatalarmsandroid.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.CreateAlarmActivity;

/**
 * Created by bbest on 05/05/18.
 */

public class SnoozeDialog extends Dialog {

    private final Activity parentActivity;

    private CheckBox oneMinCheckBox;

    private CheckBox fiveMinCheckBox;

    private CheckBox tenMinCheckBox;

    private CheckBox fifteenMinCheckBox;

    private CheckBox twentyMinCheckBox;

    private CheckBox twentyFiveMinCheckBox;

    private CheckBox thirtyMinCheckBox;

    private int snoozeLength;

    private Button okayButton;

    public SnoozeDialog(Activity a) {
        super(a);
        this.parentActivity = a;
    }

    private void initialize(){
        oneMinCheckBox = findViewById(R.id.one_min_checkbox);
        fiveMinCheckBox = findViewById(R.id.five_min_checkbox);
        tenMinCheckBox = findViewById(R.id.ten_min_checkbox);
        fifteenMinCheckBox = findViewById(R.id.fifteen_min_checkbox);
        twentyMinCheckBox = findViewById(R.id.twenty_min_checkbox);
        twentyFiveMinCheckBox = findViewById(R.id.twenty_five_min_checkbox);
        thirtyMinCheckBox = findViewById(R.id.thirty_min_checkbox);
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

        oneMinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearPreviousChoice();
                snoozeLength = 1;
            }
        });

        fiveMinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearPreviousChoice();
                snoozeLength = 5;
            }
        });

        tenMinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearPreviousChoice();
                snoozeLength=10;
            }
        });

        fifteenMinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearPreviousChoice();
                snoozeLength=15;
            }
        });

        twentyMinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearPreviousChoice();
                snoozeLength = 20;
            }
        });

        twentyFiveMinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearPreviousChoice();
                snoozeLength = 25;

            }
        });

        thirtyMinCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                clearPreviousChoice();
                snoozeLength = 30;
            }
        });



    }

    private void clearPreviousChoice(){
    }

}
