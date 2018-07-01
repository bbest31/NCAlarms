package com.napchatalarms.napchatalarmsandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.adapters.RequestAdapter;
import com.napchatalarms.napchatalarmsandroid.model.User;

public class RequestActivity extends AppCompatActivity {

    private ListView requestListView;
    private RequestAdapter requestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        requestListView = findViewById(R.id.requests_list_view);
        requestAdapter = new RequestAdapter(this, User.getInstance().getFriendRequests());
        requestListView.setAdapter(requestAdapter);

        requestListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: handle confirm or deny actions and update requests list.
            }
        });
    }


}
