package com.pls.organization.ws.soap;

import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import com.pls.organization.model.User;
import com.pls.organization.service.UserService;
import com.pls.organization.ws.UsersWS;

@WebService (name="usersWS", portName="Users")
public class UsersWSSoapImpl implements UsersWS {

	@EJB
	private UserService usersBean;

	@WebMethod
	public @WebResult(name="user") List<User> searchUsers(@WebParam(name="userId") String userId) {
		return usersBean.searchUsersByUserId(userId);
	}

	@WebMethod
	public @WebResult(name="user") List<User> getAllUsers() {
		 return usersBean.getAllUsers();
	}
	
	@WebMethod
	public @WebResult(name="result") String createUser(@WebParam(name="user") User newUser) {
		return usersBean.addUser(newUser);
	}

}

