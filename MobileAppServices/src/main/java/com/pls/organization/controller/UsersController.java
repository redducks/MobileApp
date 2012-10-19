package com.pls.organization.controller;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

import com.pls.core.controller.BaseController;
import com.pls.organization.forms.UsersForm;
import com.pls.organization.model.User;
import com.pls.organization.ws.rest.UsersWSRestImpl;

@ManagedBean(name = "userManager")
@RequestScoped
public class UsersController extends BaseController {

	private List<User> users;

	@Inject
	private UsersWSRestImpl restService;


	public List<User> getUsers() {
		return users;
	}

	@ManagedProperty(value = "#{usersForm}")
	private UsersForm usersForm;

	/**
	 * @return the usersForm
	 */
	public UsersForm getUsersForm() {
		return usersForm;
	}

	/**
	 * @param usersForm
	 *            the usersForm to set
	 */
	public void setUsersForm(UsersForm usersForm) {
		this.usersForm = usersForm;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void search() {
		users = restService.searchUsers(usersForm.getUserName());
	}
	
	public void register() {
		String result = restService.createUser(usersForm.getNewUser());
		if (result.equalsIgnoreCase("success")) {
			usersForm.setNewUser(new User());
		}
	}

}
