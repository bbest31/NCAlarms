package com.napchatalarms.napchatalarmsandroid.fragments;

import android.content.Intent;
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
import com.napchatalarms.napchatalarmsandroid.activities.RequestActivity;
import com.napchatalarms.napchatalarmsandroid.adapters.FriendAdapter;
import com.napchatalarms.napchatalarmsandroid.dialog.AddFriendDialog;
import com.napchatalarms.napchatalarmsandroid.dialog.DeleteFriendDialog;
import com.napchatalarms.napchatalarmsandroid.model.User;

import static android.app.Activity.RESULT_OK;

public class FriendsFragment extends android.support.v4.app.Fragment {

    private final int REQUEST_REQCODE = 1;
    private SwipeMenuListView friendsListView;
    private Button addFriendButton;
    private Button requestButton;
    private FriendAdapter friendAdapter;

    /**
     * Empty constructor
     */
    public FriendsFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        initialize(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateFriendList();
    }

    private void initialize(View view) {
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
                deleteItem.setTitle(R.string.Remove);
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
                if (index == 0) {
                    DeleteFriendDialog dialog = new DeleteFriendDialog(getActivity(), friendAdapter.getItem(position).getUID());
                    dialog.show();
                    updateFriendList();
                }
                return false;
            }
        });

        friendsListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        friendsListView.setOpenInterpolator(new BounceInterpolator());

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent requestIntent = new Intent(getActivity(), RequestActivity.class);
                startActivityForResult(requestIntent, REQUEST_REQCODE);
            }
        });

        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendDialog addFriendDialog = new AddFriendDialog(getActivity());
                addFriendDialog.show();
                updateFriendList();
            }
        });

        updateFriendList();

    }

    private void updateFriendList() {
        friendAdapter = new FriendAdapter(getContext(), User.getInstance().getFriendList());
        friendsListView.setAdapter(friendAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    //Back from RequestActivity
                    updateFriendList();
                    break;
                default:
                    break;
            }
        }
    }

}