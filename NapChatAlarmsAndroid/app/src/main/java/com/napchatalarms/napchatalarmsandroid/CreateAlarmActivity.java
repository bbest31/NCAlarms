package com.napchatalarms.napchatalarmsandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CreateAlarmActivity extends AppCompatActivity {

    //TODO:call UtilityFunction.UTCMilliseconds() to convert the time from TimePicker to what Alarm constructor should get.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);
    }
}
