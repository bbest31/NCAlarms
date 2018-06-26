package com.napchatalarms.napchatalarmsandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.adapters.FriendAdapter;
import com.napchatalarms.napchatalarmsandroid.model.Friend;

import java.util.ArrayList;

public class ContactsActivity extends AppCompatActivity {

    private ArrayList<Friend> friends;
    private ArrayList<Friend> selectedFriends;
    private Button okayButton;
    private Button cancelButton;
    private ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        initialize();

        final FriendAdapter friendAdapter = new FriendAdapter(this, friends, selectedFriends);
        contactListView.setAdapter(friendAdapter);


    }

    private void initialize() {
        okayButton = findViewById(R.id.contacts_okay_button);
        cancelButton = findViewById(R.id.contacts_cancel_button);
        contactListView = findViewById(R.id.contacts_listview);
        friends = new ArrayList<>();
        selectedFriends = new ArrayList<>();

        // Grab contact info
        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            processContact(cursor);
            while (cursor.moveToNext()) {
                processContact(cursor);
            }
        }

        okayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("contactsArray", selectedFriends);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    private void processContact(Cursor cursor) {
        if (cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER) > 0) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            friends.add(new Friend(name, number));
        }
    }


}
