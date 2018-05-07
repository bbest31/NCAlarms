package com.napchatalarms.napchatalarmsandroid.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.Contact;

import java.util.ArrayList;

/**
 * Created by bbest on 06/05/18.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private final Context context;
    ArrayList<Contact> contactArrayList;

    public ContactAdapter(Context context, ArrayList<Contact> contacts, ArrayList<Contact> selectedContacts){
        super(context, R.layout.contact_layout, contacts);
        this.context = context;
        this.contactArrayList = selectedContacts;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
         Contact contact = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.contact_layout, parent, false);
        }

        CheckBox contactCheckBox = convertView.findViewById(R.id.contact_check_box);
        contactCheckBox.setText(contact.getName());


        TextView phoneNumber = convertView.findViewById(R.id.hidden_contact_number);
        phoneNumber.setText(contact.getNumber());

        contactCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    contactArrayList.add(new Contact(contactCheckBox.getText().toString(),phoneNumber.getText().toString()));
                } else {
                    contactArrayList.remove(new Contact(contactCheckBox.getText().toString(),phoneNumber.getText().toString()));
                }
            }
        });

        return  convertView;
    }
}
