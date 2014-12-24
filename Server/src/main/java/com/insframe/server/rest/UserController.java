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
	
	private static final String GET_USER_BY_LASTNAME = "/user/bylastname/{lastName}";
	private static final String GET_USER_BY_USERNAME = "/user/byusername/{userName}";
	private static final String GET_ALL_USERS = "/user/users";
	private static final String CREATE_USER = "/user/create";
	private static final String MODIFY_USER = "/user/update";
	
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
	
	@RequestMapping( value=MODIFY_USER, method=RequestMethod.POST )
	public User modifyUser(@RequestBody User user){
		if(user != null){
			return userService.updateUser(user);
		} else {
			return null;
		}
	}

}