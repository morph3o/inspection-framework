package com.insframe.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.insframe.server.error.JSONErrorMessage;
import com.insframe.server.error.UserAccessException;
import com.insframe.server.error.UserStorageException;
import com.insframe.server.model.User;
import com.insframe.server.service.UserService;

@RestController
public class UserController {
	
	private static final String GET_ALL_USERS = "/users";
	private static final String GET_USER_BY_ID = "/users/{userID}";
	private static final String GET_USER_BY_LASTNAME = "/users/bylastname/{lastName}";
	private static final String GET_USER_BY_USERNAME = "/users/byusername/{userName}";
	private static final String CREATE_USER = "/users";
	private static final String MODIFY_USER = "/users/{userID}";
	private static final String DELETE_USER_BY_USERID = "/users/{userID}";
	private static final String DELETE_USER_BY_USERNAME = "/users/byusername/{username}";
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( value=GET_USER_BY_ID, method=RequestMethod.GET )
    public Object getUserByID(@PathVariable("userID") String userID) throws UserAccessException {
		return (User) userService.findById(userID);
    }
	
	@RequestMapping( value=GET_USER_BY_LASTNAME, method=RequestMethod.GET )
    public Object getFirstUserFoundByLastName(@PathVariable("lastName") String lastName) throws UserAccessException {
		return (User) userService.findByLastName(lastName).get(0);
    }
	
	@RequestMapping( value=GET_USER_BY_USERNAME, method=RequestMethod.GET )
    public User getUserByUserName(@PathVariable("userName") String userName) throws UserAccessException {
		User user = userService.findByUserName(userName);
		return user;
    }
	
	@RequestMapping( value=GET_ALL_USERS, method=RequestMethod.GET )
	public List<User> getAllUsers(){
		List<User> userList = userService.findAll();
		return userList;
	}
	
	@RequestMapping( value=CREATE_USER, method=RequestMethod.POST )
	public User insertUser(@RequestBody User user) throws UserStorageException{
		userService.save(user);
		return user;
	}
	
	@RequestMapping( value=MODIFY_USER, method=RequestMethod.PUT )
	public User modifyUser(@RequestBody User user, @PathVariable("userID") String userID) throws UserAccessException{
		return userService.updateUser(user, userID);
	}
	
	@RequestMapping( value=DELETE_USER_BY_USERNAME, method=RequestMethod.DELETE )
	public void deleteUserByUsername(@PathVariable("username") String username) throws UserAccessException{
		userService.deleteUserByUserName(username);
	}
	
	@RequestMapping( value=DELETE_USER_BY_USERID, method=RequestMethod.DELETE )
	public void deleteUserByUserId(@PathVariable("userID") String userID) throws UserAccessException{
		userService.deleteUserById(userID);
	}

}