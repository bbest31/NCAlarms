package com.napchatalarms.napchatalarmsandroid.customui;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/**
 * Created by bbest on 02/02/18.
 */
//TODO: must be able to select multiple days and return and int[] to the activity by calling setRepeatDays and setRepeatText
public class RepeatDaysDialog extends Dialog {

    public Activity c;

    public RepeatDaysDialog(Activity a){
        super(a);
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

}
