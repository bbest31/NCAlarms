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
import com.napchatalarms.napchatalarmsandroid.model.Friend;

import java.util.ArrayList;

/**
 * Created by bbest on 06/05/18.
 */

public class FriendAdapter extends ArrayAdapter<Friend> {

    private final Context context;

    public FriendAdapter(Context context, ArrayList<Friend> friends){
        super(context, R.layout.friend_layout, friends);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
         Friend friend = getItem(position);

        //Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_layout, parent, false);
        }
        TextView displayName = convertView.findViewById(R.id.friend_username);
        displayName.setText(friend.getName());
        TextView hiddenUID = convertView.findViewById(R.id.friend_hidden_uid);
        hiddenUID.setText(friend.getUID());

        return  convertView;
    }
}
