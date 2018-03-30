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

import java.util.Iterator;
import java.util.List;

/** Dialog for User to select which days of the week the alarm will repeat on.
 * Created by bbest on 02/02/18.
 */
public class RepeatDaysDialog extends Dialog {

    /**
     * The C.
     */
    private final Activity c;
    /**
     * The Sun check bx.
     */
    private CheckBox sunCheckBox;
    /**
     * The Mon check box.
     */
    private CheckBox monCheckBox;
    /**
     * The Tues check bx.
     */
    private CheckBox tuesCheckBox;
    /**
     * The Wed check bx.
     */
    private CheckBox wedCheckBox;
    /**
     * The Thurs check bx.
     */
    private CheckBox thursCheckBox;
    /**
     * The Fri check bx.
     */
    private CheckBox friCheckBox;
    /**
     * The Sat check bx.
     */
    private CheckBox satCheckBox;
    /**
     * The Okay btn.
     */
    private Button okayBtn;
    /**
     * The Repeat days.
     */
    private final List<Integer> repeatDays;

    /**
     * Instantiates a new Repeat days dialog.
     *
     * @param a    the a
     * @param days the days
     */
    public RepeatDaysDialog(Activity a, List<Integer> days) {
        super(a);
        this.repeatDays = days;
        this.c = a;
    }

    /**
     * Initialize.
     */
    private void initialize() {
        sunCheckBox = findViewById(R.id.sunday_checkBox);
        monCheckBox = findViewById(R.id.monday_checkBox);
        tuesCheckBox = findViewById(R.id.tuesday_checkBox);
        wedCheckBox = findViewById(R.id.wednesday_checkBox);
        thursCheckBox = findViewById(R.id.thursday_checkBox);
        friCheckBox = findViewById(R.id.friday_checkBox);
        satCheckBox = findViewById(R.id.saturday_checkBox);
        okayBtn = findViewById(R.id.repeat_okay_btn);


        for (Integer repeatDay : repeatDays) {
            switch (repeatDay) {
                case 1:
                    sunCheckBox.setChecked(true);
                    break;
                case 2:
                    monCheckBox.setChecked(true);
                    break;
                case 3:
                    tuesCheckBox.setChecked(true);
                    break;
                case 4:
                    wedCheckBox.setChecked(true);
                    break;
                case 5:
                    thursCheckBox.setChecked(true);
                    break;
                case 6:
                    friCheckBox.setChecked(true);
                    break;
                case 7:
                    satCheckBox.setChecked(true);
                    break;
            }
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_repeat_menu);
        initialize();

        okayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateAlarmActivity) c).setRepeatDays(repeatDays);
                ((CreateAlarmActivity) c).setRepeatText(repeatDays);
                dismiss();
            }
        });

        sunCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatDays.add(1);
                } else {
                    for (Iterator<Integer> integerIterator = repeatDays.iterator(); integerIterator.hasNext(); ) {
                        if (integerIterator.next() == 1) {
                            integerIterator.remove();
                        }
                    }

                }
            }
        });

        monCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatDays.add(2);
                } else {
                    for (Iterator<Integer> iterator = repeatDays.iterator(); iterator.hasNext(); ) {
                        if (iterator.next() == 2) {
                            iterator.remove();
                        }
                    }
                }
            }
        });

        tuesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatDays.add(3);

                } else {
                    for (Iterator<Integer> iterator = repeatDays.iterator(); iterator.hasNext(); ) {
                        if (iterator.next() == 3) {
                            iterator.remove();
                        }
                    }
                }
            }
        });

        wedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatDays.add(4);
                } else {
                    for (Iterator<Integer> iterator = repeatDays.iterator(); iterator.hasNext(); ) {
                        if (iterator.next() == 4) {
                            iterator.remove();
                        }
                    }
                }
            }
        });

        thursCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatDays.add(5);
                } else {
                    for (Iterator<Integer> iterator = repeatDays.iterator(); iterator.hasNext(); ) {
                        if (iterator.next() == 5) {
                            iterator.remove();
                        }
                    }
                }
            }
        });

        friCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatDays.add(6);
                } else {
                    for (Iterator<Integer> iterator = repeatDays.iterator(); iterator.hasNext(); ) {
                        if (iterator.next() == 6) {
                            iterator.remove();
                        }
                    }
                }
            }
        });

        satCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    repeatDays.add(7);
                } else {
                    for (Iterator<Integer> iterator = repeatDays.iterator(); iterator.hasNext(); ) {
                        if (iterator.next() == 7) {
                            iterator.remove();
                        }
                    }
                }
            }
        });

    }

}
