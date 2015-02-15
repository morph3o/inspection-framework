package com.insframe.server.data.repository;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.config.WebAppConfigurationAware;
import com.insframe.server.error.UserAccessException;
import com.insframe.server.error.UserStorageException;
import com.insframe.server.model.User;
import com.insframe.server.service.UserService;

public class UserServiceTest extends WebAppConfigurationAware{
	
	@Autowired
	private UserService userService;
	
	@Before
	public void init() throws UserStorageException{
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
	
	@Test(expected=UserAccessException.class)
	public void shouldDeleteUserByUsername() throws UserAccessException{
		System.out.println("****** Should Delete User by Username ******");
		System.out.println("Now the user with username : \'hcarns\' will be deleted. ");
		userService.deleteUserByUserName("hcarns");
		Assert.assertNull(userService.findByUserName("hcarns"));
		System.out.println("User \'hcarns\' was deleted.");
		System.out.println("****** End of Should Delete User by Username ******");
	}
	
	@Test(expected=UserAccessException.class)
	public void shouldDeleteUserByUserID() throws UserAccessException{
		System.out.println("****** Should Delete User by Username ******");
		String userId = userService.findByUserName("hcarns").getId();
		System.out.println("Now the user with username : \'hcarns\' and userID: \'"+ userId +"\' will be deleted. ");
		userService.deleteUserById(userId);
		Assert.assertNull(userService.findById(userId));
		System.out.println("User \'hcarns\' was deleted.");
		System.out.println("****** End of Should Delete User by Username ******");
	}
	
	@Test
	public void shouldShowUserFindByUsername() throws UserAccessException{
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
	public void shouldCreateUser() throws UserStorageException, UserAccessException{
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
	public void shouldModifyUser() throws UserAccessException, UserStorageException{
		System.out.println("****** Should Modify User ******");
		User user = userService.findByUserName("ppilgrim");
		System.out.println(user);
		System.out.println("The email address will be modified from ppilgrim@gmail.com to newEmailAddress@gmail.com");
		user.setEmailAddress("newEmailAddress@gmail.com");
		userService.updateUser(user, user.getId());
		User newUser = userService.findByUserName("ppilgrim");
		System.out.println(newUser);
		Assert.assertEquals("newEmailAddress@gmail.com", newUser.getEmailAddress());
		System.out.println("****** End of Should Modify User ******");
	}
	
	@Test(expected=UserAccessException.class)
	public void shouldThrowExceptionByUsernameNotFound() throws UserAccessException{
		System.out.println("****** Should throw an exception by Username not found ******");
		userService.findByUserName("asdasdasdasd");
		System.out.println("****** End Should throw an exception by Username not found ******");
	}
	
	@Test(expected=UserAccessException.class)
	public void shouldThrowExceptionByIDNotFound() throws UserAccessException{
		System.out.println("****** Should throw an exception by ID not found ******");
		userService.findById("asdasdasd");
		System.out.println("****** End Should throw an exception by ID not found ******");
	}
	
	@Test(expected=UserStorageException.class)
	public void shouldThrowExceptionByInvalidEmail() throws UserStorageException{
		System.out.println("****** Should throw an exception by ID not found ******");
		User user = new User("userTest", "emailInvalid", "pass", "role", "lastname", "firstname", "phone", "mobile");
		userService.save(user);
		System.out.println("****** End Should throw an exception by ID not found ******");
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
