package com.niit.Controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.Dao.FriendDao;
import com.niit.Model.Error;
import com.niit.Model.Friend;
import com.niit.Model.Users;

@Controller
public class FriendController {
	@Autowired
	private FriendDao friendDao;

	@RequestMapping(value ="/suggesteduserslist", method=RequestMethod.GET)
	public ResponseEntity<?> getSuggestedUsersList(HttpSession session) {
		Users users = (Users)session.getAttribute("user");
		if (users == null) {
			Error error = new Error(3, "UnAuthorized user");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		List<Users> suggestedUsers = friendDao.listOfSuggestedUsers(users.getUsername());
		return new ResponseEntity<List<Users>>(suggestedUsers, HttpStatus.OK);
	}

	@RequestMapping(value ="/friendrequest/{toUsername}")
	public ResponseEntity<?> friendRequest(@PathVariable String toUsername, HttpSession session) {
		Users users = (Users) session.getAttribute("user");
		if (users == null) {
			Error error = new Error(3, "UnAuthorized user");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		String fromUsername=users.getUsername();
		friendDao.friendRequest(fromUsername, toUsername);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/pendingrequests", method = RequestMethod.GET)
	public ResponseEntity<?> pendingRequests(HttpSession session) {
		Users users = (Users)session.getAttribute("user");
		if (users==null) {
			Error error = new Error(3, "UnAuthorized user");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		List<Friend> pendingRequests = friendDao.listOfPendingRequests(users.getUsername());
		return new ResponseEntity<List<Friend>>(pendingRequests, HttpStatus.OK);
	}

	@RequestMapping(value = "/updatependingrequest/{fromId}/{status}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePendingRequests(@PathVariable String fromId,@PathVariable char Status, HttpSession session) {
		Users users = (Users) session.getAttribute("user");
		if (users == null) {
			Error error = new Error(3, "UnAuthorized user");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		friendDao.updatePendingRequest(fromId, users.getUsername(), Status);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listoffriends", method = RequestMethod.GET)
	public ResponseEntity<?> listOfFriends(HttpSession session) {
		Users users = (Users)session.getAttribute("user");
		if (users == null) {
			Error error = new Error(3, "UnAuthorized user");
			return new ResponseEntity<Error>(error, HttpStatus.UNAUTHORIZED);
		}
		List<Friend> friends=friendDao.listOfFriends(users.getUsername());
		return new ResponseEntity<List<Friend>>(friends, HttpStatus.OK);
		
	}
}
