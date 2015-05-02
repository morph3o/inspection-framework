package com.insframe.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insframe.server.error.UserAccessException;
import com.insframe.server.error.UserStorageException;
import com.insframe.server.model.User;
import com.insframe.server.security.CustomUserDetails;
import com.insframe.server.service.UserService;

@RestController
public class UserController {
	
	private static final String GET_ALL_USERS = "/users";
	private static final String GET_CURRENT_USER = "/users/current";
	private static final String GET_USER_BY_ID = "/users/{userID}";
	private static final String GET_USER_BY_LASTNAME = "/users/bylastname/{lastName}";
	private static final String GET_USER_BY_USERNAME = "/users/byusername/{userName}";
	private static final String CREATE_USER = "/users";
	private static final String MODIFY_USER = "/users/{userID}";
	private static final String DELETE_USER_BY_USERID = "/users/{userID}";
	private static final String DELETE_USER_BY_USERNAME = "/users/byusername/{username}";
	private static final String REMEMBER_PASSWORD = "/users/rememberpass/{username}";
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( value=GET_USER_BY_ID, method=RequestMethod.GET )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
    public Object getUserByID(@PathVariable("userID") String userID) throws UserAccessException {
		return (User) userService.findById(userID);
    }
	
	@RequestMapping( value=GET_USER_BY_LASTNAME, method=RequestMethod.GET )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
    public Object getFirstUserFoundByLastName(@PathVariable("lastName") String lastName) throws UserAccessException {
		return (User) userService.findByLastName(lastName).get(0);
    }
	
	@RequestMapping( value=GET_USER_BY_USERNAME, method=RequestMethod.GET )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
    public User getUserByUserName(@PathVariable("userName") String userName) throws UserAccessException {
		User user = userService.findByUserName(userName);
		return user;
    }
	
	@RequestMapping( value=GET_ALL_USERS, method=RequestMethod.GET )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
	public List<User> getAllUsers(){
		List<User> userList = userService.findAll();
		return userList;
	}
	
	@RequestMapping( value=CREATE_USER, method=RequestMethod.POST )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
	public User insertUser(@RequestBody User user) throws UserStorageException{
		userService.save(user);
		user.setPassword(null);
		return user;
	}
	
	@RequestMapping( value=MODIFY_USER, method=RequestMethod.PUT )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
	public User modifyUser(@RequestBody User user, @PathVariable("userID") String userID) throws UserAccessException{
		return userService.updateUser(user, userID);
	}
	
	@RequestMapping( value=DELETE_USER_BY_USERNAME, method=RequestMethod.DELETE )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
	public void deleteUserByUsername(@PathVariable("username") String username) throws UserAccessException{
		userService.deleteUserByUserName(username);
	}
	
	@RequestMapping( value=DELETE_USER_BY_USERID, method=RequestMethod.DELETE )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
	public void deleteUserByUserId(@PathVariable("userID") String userID) throws UserAccessException{
		userService.deleteUserById(userID);
	}
	
	@RequestMapping( value=GET_CURRENT_USER, method = RequestMethod.GET )
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_INSPECTOR')")
	public User loginUser(){
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			((CustomUserDetails) authentication.getPrincipal()).setPassword(null);
			return ((CustomUserDetails) authentication.getPrincipal()).getUser();
		}
		return null;
	}
	
	@RequestMapping( value=REMEMBER_PASSWORD, method = RequestMethod.GET )
	public void rememberPassword(@RequestParam("username") String username, @RequestParam("email") String email) throws UserAccessException {
		userService.rememberPassword(username, email);
	}

}