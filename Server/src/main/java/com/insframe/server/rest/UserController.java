package com.insframe.server.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.insframe.server.error.JSONErrorMessage;
import com.insframe.server.model.User;
import com.insframe.server.service.UserService;

@RestController
public class UserController {
	
//	/users	GET	Retrieve a list of users
//	/users	POST	Create a new user
//	/users/{id}	GET	Get details for an existing user
//	/users/{id}	PUT	Modify details of an existing user
//	/users/{id}	DELETE	Delete details of an existing user
	
	private static final String GET_ALL_USERS = "/users";
	private static final String GET_USER_BY_LASTNAME = "/users/bylastname/{lastName}";
	private static final String GET_USER_BY_USERNAME = "/users/byusername/{userName}";
	private static final String CREATE_USER = "/users";
	private static final String MODIFY_USER = "/users/{userID}";
	private static final String DELETE_USER_BY_USERID = "/users/{userID}";
	private static final String DELETE_USER_BY_USERNAME = "/users/byusername/{username}";
	
	@Autowired
	private UserService userService;
	
	@RequestMapping( value=GET_USER_BY_LASTNAME, method=RequestMethod.GET )
    public Object getFirstUserFoundByLastName(@PathVariable("lastName") String lastName) {
		if(userService.findByLastName(lastName).size() > 0){
			return (User) userService.findByLastName(lastName).get(0);
		} else {
			return new JSONErrorMessage("There is no user with last name: "+lastName);
		}
    }
	
	@RequestMapping( value=GET_USER_BY_USERNAME, method=RequestMethod.GET )
    public User getUserByUserName(@PathVariable("userName") String userName) {
		User user = userService.findByUserName(userName);
		return user;
    }
	
	@RequestMapping( value=GET_ALL_USERS, method=RequestMethod.GET )
	public List<User> getAllUsers(){
		List<User> userList = userService.findAll();
		return userList;
	}
	
	@RequestMapping( value=CREATE_USER, method=RequestMethod.POST )
	public User insertUser(@RequestBody User user){
		userService.save(user);
		return user;
	}
	
	@RequestMapping( value=MODIFY_USER, method=RequestMethod.PUT )
	public User modifyUser(@RequestBody User user, @PathVariable("userID") String userID){
		System.out.println(user);
		if(user != null && userID != null && !userID.isEmpty()){
			return userService.updateUser(user);
		} else {
			return null;
		}
	}
	
	@RequestMapping( value=DELETE_USER_BY_USERNAME, method=RequestMethod.DELETE )
	public void deleteUserByUsername(@PathVariable("username") String username){
		userService.deleteUserByUserName(username);
	}
	
	@RequestMapping( value=DELETE_USER_BY_USERID, method=RequestMethod.DELETE )
	public void deleteUserByUserId(@PathVariable("userID") String userID){
		userService.deleteUserById(userID);
	}

}