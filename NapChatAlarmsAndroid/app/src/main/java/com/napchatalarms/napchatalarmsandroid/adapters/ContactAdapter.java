package com.napchatalarms.napchatalarmsandroid.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import com.napchatalarms.napchatalarmsandroid.R;
import com.napchatalarms.napchatalarmsandroid.model.Contact;

import java.util.ArrayList;

/**
 * Created by bbest on 06/05/18.
 */

public class ContactAdapter extends ArrayAdapter<Contact> {

    private final Context context;

    public ContactAdapter(Context context, ArrayList<Contact> contacts){
        super(context, R.layout.contact_layout, contacts);
        this.context = context;
    }


}
