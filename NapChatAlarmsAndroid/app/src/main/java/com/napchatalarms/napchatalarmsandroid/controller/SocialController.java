package com.napchatalarms.napchatalarmsandroid.controller;

import com.napchatalarms.napchatalarmsandroid.model.Friend;
import com.napchatalarms.napchatalarmsandroid.model.Group;
import com.napchatalarms.napchatalarmsandroid.model.User;

/**
 * Controller responsible for the interaction with the social components of the application.
 * <>
 * This includes the User's Friend and Groups.
 * </>
 * Created by bbest on 10/02/18.
 */

public class SocialController {

    /**
     * @param friend
     */
    public static void addFriend(Friend friend) {
        User.getInstance().getFriendList().getFriends().add(friend);
    }


    /**
     * @param friend
     */
    public static void removeFriend(Friend friend) {
        for (Group g : User.getInstance().getGroupMap().values()) {
            g.removeFriend(friend.getUid());
        }
        User.getInstance().getFriendList().getFriends().remove(friend);
    }

    /**
     * @param group
     */
    public static void addGroup(Group group) {
        User.getInstance().getGroupMap().put(group.getGroupName(), group);

    }

    /**
     * @param name
     */
    public static void removeGroup(String name) {
        User.getInstance().getGroupMap().remove(name);
    }

    /**
     * @param groupName
     * @param uid
     */
    public void removeFriendFromGroup(String groupName, String uid) {
        User.getInstance().getGroupMap().get(groupName).removeFriend(uid);
    }


}
