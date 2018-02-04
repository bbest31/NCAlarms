package com.napchatalarms.napchatalarmsandroid.customui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.activities.CreateAlarmActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by bbest on 02/02/18.
 */
public class RepeatDaysDialog extends Dialog {

    public Activity c;
    CheckBox sunChckBx;
    CheckBox monChckBox;
    CheckBox tuesChckBx;
    CheckBox wedChckBx;
    CheckBox thursChckBx;
    CheckBox friChckBx;
    CheckBox satChckBx;
    Button okayBtn;
    List<Integer> repeatDays;

    public RepeatDaysDialog(Activity a, List<Integer> days){
        super(a);
        this.repeatDays = days;
        this.c = a;
    }

    public void initialize(){
        sunChckBx = (CheckBox)findViewById(R.id.sunday_checkBox);
        monChckBox = (CheckBox)findViewById(R.id.monday_checkBox);
        tuesChckBx = (CheckBox)findViewById(R.id.tuesday_checkBox);
        wedChckBx = (CheckBox)findViewById(R.id.wednesday_checkBox);
        thursChckBx = (CheckBox)findViewById(R.id.thursday_checkBox);
        friChckBx = (CheckBox)findViewById(R.id.friday_checkBox);
        satChckBx = (CheckBox)findViewById(R.id.saturday_checkBox);
        okayBtn = (Button)findViewById(R.id.repeat_okay_btn);

        for(Iterator<Integer> iterator = repeatDays.listIterator();iterator.hasNext();){
            switch (iterator.next()){
                case 1:
                    sunChckBx.setChecked(true);
                    break;
                case 2:
                    monChckBox.setChecked(true);
                    break;
                case 3:
                    tuesChckBx.setChecked(true);
                    break;
                case 4:
                    wedChckBx.setChecked(true);
                    break;
                case 5:
                    thursChckBx.setChecked(true);
                    break;
                case 6:
                    friChckBx.setChecked(true);
                    break;
                case 7:
                    satChckBx.setChecked(true);
                    break;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.repeat_selection_layout);
        initialize();

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateAlarmActivity)c).setRepeatDays(repeatDays);
                ((CreateAlarmActivity) c).setRepeatText(repeatDays);
                dismiss();
            }
        });

        sunChckBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatDays.add(1);
                } else {
                    for(Iterator<Integer> iter = repeatDays.iterator();iter.hasNext();){
                        if(iter.next() == 1){
                            iter.remove();
                        }
                    }

                }
            }
        });

        monChckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatDays.add(2);
                } else{
                    for(Iterator<Integer> iter = repeatDays.iterator();iter.hasNext();){
                        if(iter.next() == 2){
                            iter.remove();
                        }
                    }
                }
            }
        });

        tuesChckBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatDays.add(3);

                } else {
                    for(Iterator<Integer> iter = repeatDays.iterator();iter.hasNext();){
                        if(iter.next() == 3){
                            iter.remove();
                        }
                    }
                }
            }
        });

        wedChckBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatDays.add(4);
                } else{
                    for(Iterator<Integer> iter = repeatDays.iterator();iter.hasNext();){
                        if(iter.next() == 4){
                            iter.remove();
                        }
                    }
                }
            }
        });

        thursChckBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatDays.add(5);
                } else {
                    for(Iterator<Integer> iter = repeatDays.iterator();iter.hasNext();){
                        if(iter.next() == 5){
                            iter.remove();
                        }
                    }
                }
            }
        });

        friChckBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatDays.add(6);
                } else {
                    for(Iterator<Integer> iter = repeatDays.iterator();iter.hasNext();){
                        if(iter.next() == 6){
                            iter.remove();
                        }
                    }
                }
            }
        });

        satChckBx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    repeatDays.add(7);
                } else {
                    for(Iterator<Integer> iter = repeatDays.iterator();iter.hasNext();){
                        if(iter.next() == 7){
                            iter.remove();
                        }
                    }
                }
            }
        });

    }

}
