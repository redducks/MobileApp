package com.pls.organization.ws.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.pls.organization.model.User;
import com.pls.organization.service.UserService;
import com.pls.organization.ws.UsersWS;

/**
 * JAX-RS Example
 * 
 * This class produces a RESTful service to read the contents of the members table.
 */
@Path("/users")
@RequestScoped
public class UsersWSRestImpl implements UsersWS {
	@EJB
	private UserService usersBean;

   @GET
   @Produces("application/xml")
   @Path("/{uid}")
   public List<User> searchUsers(@PathParam("uid") String userId) {
	   return usersBean.searchUsersByUserId(userId);
   }
   
   @GET
   @Produces("application/xml")
   @Path("/all") 
   public List<User> getAllUsers() {
	   return usersBean.getAllUsers();
   }
   
   @PUT
   @Produces("application/xml")
   public String createUser(User newUser) {
		return usersBean.addUser(newUser);
	}
}
