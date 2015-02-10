package com.insframe.server.data.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.config.WebAppConfigurationAware;
import com.insframe.server.model.User;
import com.insframe.server.service.UserService;

public class UserServiceTest extends WebAppConfigurationAware{
	
	@Autowired
	private UserService userService;
	
	@Before
	public void init(){
		userService.deleteAll();
		
		userService.save(new User("fpetru", "fpetru@gmail.com", "pass1", "ROLE_ADMIN", "Petrus", "Frank", "+49162123456", "+49162123456"));
		userService.save(new User("hcarns", "hcarns@gmail.com", "pass2", "ROLE_USER", "Carns", "Hertha", "+49162123456", "+49162123456"));
		userService.save(new User("ppilgrim", "ppilgrim@gmail.com", "pass3", "ROLE_ADMIN", "Pilgrim", "Parthenia", "+49162123456", "+49162123456"));
		userService.save(new User("rlongworth", "rlongworth@gmail.com", "pass4", "ROLE_USER", "Longworth", "Reginald", "+49162123456", "+49162123456"));
	}
	
	@Test
	public void shoudlPrintAllUsers(){
		System.out.println("****** Should Show all the users in the database ******");
		System.out.println("Show the 4 users and their data");
		List<User> userList = userService.findAll();
		for(User user : userList){
			System.out.println(user);
		}
		Assert.assertTrue(userList.size() == 4);
		System.out.println("****** End of Should Show All the Users in the database ******");
	}
	
	@Test
	public void shouldDeleteUserByUsername(){
		System.out.println("****** Should Delete User by Username ******");
		System.out.println("Now the user with username : \'hcarns\' will be deleted. ");
		userService.deleteUserByUserName("hcarns");
		Assert.assertNull(userService.findByUserName("hcarns"));
		System.out.println("User \'hcarns\' was deleted.");
		System.out.println("****** End of Should Delete User by Username ******");
	}
	
	@Test
	public void shouldDeleteUserByUserID(){
		System.out.println("****** Should Delete User by Username ******");
		String userId = userService.findByUserName("hcarns").getId();
		System.out.println("Now the user with username : \'hcarns\' and userID: \'"+ userId +"\' will be deleted. ");
		userService.deleteUserById(userId);
		Assert.assertNull(userService.findById(userId));
		System.out.println("User \'hcarns\' was deleted.");
		System.out.println("****** End of Should Delete User by Username ******");
	}
	
	@Test
	public void shouldShowUserFindByUsername(){
		System.out.println("****** Should Show User found by Username ******");
		User user = (User) userService.findByUserName("fpetru");
		
		Assert.assertNotNull(user);
		Assert.assertEquals("fpetru", user.getUserName());
		Assert.assertEquals("Petrus", user.getLastName());
		Assert.assertEquals("Frank", user.getFirstName());
		Assert.assertEquals("fpetru@gmail.com", user.getEmailAddress());
		Assert.assertEquals("pass1", user.getPassword());
		Assert.assertEquals("+49162123456", user.getPhoneNumber());
		Assert.assertEquals("+49162123456", user.getMobileNumber());
		Assert.assertEquals("ROLE_ADMIN", user.getRole());
		
		System.out.println(user);
		System.out.println("****** End of Should Show User found by Username ******");
	}
	
	@Test
	public void shouldCreateUser(){
		System.out.println("****** Should Create new User ******");
		
		User newUser = new User();
		newUser.setUserName("aschultze");
		newUser.setFirstName("Adella");
		newUser.setLastName("Schultze");
		newUser.setPassword("pass5");
		newUser.setEmailAddress("aschultze@gmail.com");
		newUser.setPhoneNumber("+491623531597");
		newUser.setMobileNumber("+491623531597");
		newUser.setRole("ROLE_ADMIN");
		System.out.println("Creating user: "+newUser);
		
		userService.save(newUser);
		
		System.out.println("Fetching user created from database.");
		System.out.println("Check parameters of user.");
		User newUserFromDB = userService.findByUserName("aschultze");
		Assert.assertNotNull(newUserFromDB);
		Assert.assertEquals("aschultze", newUserFromDB.getUserName());
		Assert.assertEquals("Schultze", newUserFromDB.getLastName());
		Assert.assertEquals("Adella", newUserFromDB.getFirstName());
		Assert.assertEquals("aschultze@gmail.com", newUserFromDB.getEmailAddress());
		Assert.assertEquals("pass5", newUserFromDB.getPassword());
		Assert.assertEquals("+491623531597", newUserFromDB.getPhoneNumber());
		Assert.assertEquals("+491623531597", newUserFromDB.getMobileNumber());
		Assert.assertEquals("ROLE_ADMIN", newUserFromDB.getRole());
		
		System.out.println("User created: "+newUserFromDB);
		System.out.println("****** End of Should Create new User ******");
	}
	
	@Test
	public void shouldModifyUser(){
		System.out.println("****** Should Modify User ******");
		User user = userService.findByUserName("ppilgrim");
		System.out.println(user);
		System.out.println("The email address will be modified from ppilgrim@gmail.com to newEmailAddress@gmail.com");
		user.setEmailAddress("newEmailAddress@gmail.com");
		userService.save(user);
		User newUser = userService.findByUserName("ppilgrim");
		System.out.println(newUser);
		Assert.assertEquals("newEmailAddress@gmail.com", newUser.getEmailAddress());
		System.out.println("****** End of Should Modify User ******");
	}
	
//	private boolean hasAuthority(User user, String role) {
//		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
//		for(GrantedAuthority authority : authorities) {
//			if(authority.getAuthority().equals(role)) {
//				return true;
//			}
//		}
//		return false;
//	}

}
