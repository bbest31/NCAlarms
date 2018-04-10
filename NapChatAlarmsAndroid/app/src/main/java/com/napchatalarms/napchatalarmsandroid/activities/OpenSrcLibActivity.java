package com.napchatalarms.napchatalarmsandroid.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.napchatalarms.napchatalarmsandroid.R;


/**
 * The type Open src lib activity.
 */
public class OpenSrcLibActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_src_lib);

        //Check the height and width in pixels for device

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.v("OPEN SRC LIB","Height: "+height+" Width: "+width);

    }
}
