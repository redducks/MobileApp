package com.pls.organization.ws;

import java.util.List;

import com.pls.organization.model.User;

public interface UsersWS {
	public List<User> searchUsers(String userId);

	public List<User> getAllUsers();

	public String createUser(User newUser);
}
