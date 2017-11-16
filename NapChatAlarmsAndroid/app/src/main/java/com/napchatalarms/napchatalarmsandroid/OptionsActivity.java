package com.napchatalarms.napchatalarmsandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class OptionsActivity extends AppCompatActivity {

    public Button logoutButton;

    public void initialize(){
    logoutButton = (Button)findViewById(R.id.logout_btn);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        initialize();

        logoutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
            logout();
            }
        });
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent loginIntent = new Intent(OptionsActivity.this,LoginActivity.class);
        startActivity(loginIntent);
        //finish();
    }
}
