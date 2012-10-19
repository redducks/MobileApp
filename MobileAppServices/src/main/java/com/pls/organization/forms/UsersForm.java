package com.pls.organization.forms;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.bean.ManagedBean;

import com.pls.organization.model.User;

@SessionScoped
@ManagedBean(name="usersForm")
public class UsersForm implements Serializable {

	private static final long serialVersionUID = -7966719977934304037L;
	private User newUser = new User();
	private String userName;
	
	/**
	 * @return the newUser
	 */
	public User getNewUser() {
		return newUser;
	}
	/**
	 * @param newUser the newUser to set
	 */
	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}
	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
}
