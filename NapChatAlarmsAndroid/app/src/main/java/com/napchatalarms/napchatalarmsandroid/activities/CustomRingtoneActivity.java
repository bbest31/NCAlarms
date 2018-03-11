package com.napchatalarms.napchatalarmsandroid.activities;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.napchatalarms.napchatalarmsandroid.R;

public class CustomRingtoneActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ringtone);
        initialize();
    }

    private void initialize(){
        button = (Button) findViewById(R.id.bubble_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MediaPlayer mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bamboo_forest);
                mPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mPlayer.setLooping(true);
                mPlayer.start();
            }
        });
    }
}
