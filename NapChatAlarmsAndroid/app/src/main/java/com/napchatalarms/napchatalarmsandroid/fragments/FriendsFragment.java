package com.napchatalarms.napchatalarmsandroid.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.Button;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.napchatalarms.napchatalarmsandroid.R;

public class FriendsFragment extends android.support.v4.app.Fragment {

    private SwipeMenuListView friendsListView;
    private Button addFriendButton;
    private Button requestButton;

    /**
     * Empty constructor
     */
    public FriendsFragment(){

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        initialize(view);
        return view;
    }

    private void initialize(View view){
        friendsListView = view.findViewById(R.id.friend_list_view);
        addFriendButton = view.findViewById(R.id.add_friend_button);
        requestButton = view.findViewById(R.id.requests_button);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getContext());
                // set item background
                deleteItem.setBackground(getActivity().getDrawable(R.drawable.round_red_btn));
                // set item width
                deleteItem.setWidth(300);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete_trashcan);
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        friendsListView.setMenuCreator(creator);

        friendsListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                if(index == 0) {
                    //TODO:Delete friend button selected. Affirm action with dialog.Remove from user model and update DB reference. Also update the other users friends list.


                }
                return false;
            }
        });

        friendsListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        friendsListView.setOpenInterpolator(new BounceInterpolator());

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Go to Request Activity
            }
        });

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Open dialog to add friend
            }
        });

    }

}