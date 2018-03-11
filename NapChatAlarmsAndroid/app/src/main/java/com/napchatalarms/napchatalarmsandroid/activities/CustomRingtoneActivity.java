package com.napchatalarms.napchatalarmsandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.napchatalarms.napchatalarmsandroid.R;

public class CustomRingtoneActivity extends AppCompatActivity {

    private Button bambooBtn;
    private Button steampunkBtn;
    private Button alleyCatBtn;
    private Intent returnIntent;
    private String uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ringtone);
        initialize();
    }

    private void initialize(){
        bambooBtn = (Button) findViewById(R.id.bamboo_btn);
        bambooBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = "android.resource://"+getPackageName()+"/"+R.raw.bamboo_forest;
                returnIntent = new Intent();
                returnIntent.putExtra("URI",String.valueOf(uri));
                returnIntent.putExtra("NAME",getResources().getString(R.string.bamboo));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        steampunkBtn = (Button) findViewById(R.id.steampunk_btn);
        steampunkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = "android.resource://"+getPackageName()+"/"+R.raw.steampunk;
                returnIntent = new Intent();
                returnIntent.putExtra("URI",String.valueOf(uri));
                returnIntent.putExtra("NAME",getResources().getString(R.string.steampunk));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });

        alleyCatBtn = (Button) findViewById(R.id.alley_cat_btn);
        alleyCatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uri = "android.resource://"+getPackageName()+"/"+R.raw.alley_cat;
                returnIntent = new Intent();
                returnIntent.putExtra("URI",String.valueOf(uri));
                returnIntent.putExtra("NAME",getResources().getString(R.string.alleycat));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }
}
