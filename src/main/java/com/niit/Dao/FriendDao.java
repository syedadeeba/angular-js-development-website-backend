package com.niit.Dao;

import java.util.List;

import com.niit.Model.Friend;
import com.niit.Model.Users;

public interface FriendDao {
	List<Users> listOfSuggestedUsers(String username);

	void friendRequest(String fromUsername, String toUsername);

	List<Friend> listOfPendingRequests(String loggedInUsername);

	void updatePendingRequest(String fromId, String toId, char Status);

	List<Friend> listOfFriends(String username);
}

