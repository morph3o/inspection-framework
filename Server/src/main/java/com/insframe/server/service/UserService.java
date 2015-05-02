package com.insframe.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.UserRepository;
import com.insframe.server.error.AssignmentAccessException;
import com.insframe.server.error.UserAccessException;
import com.insframe.server.error.UserStorageException;
import com.insframe.server.model.User;
import com.insframe.server.security.CustomUserDetails;
import com.insframe.server.tools.INFRATools;

@Repository
public class UserService implements UserDetailsService{
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MongoOperations mongoOpts;
	@Autowired
	private MailService mailService;
	@Autowired
	private AssignmentService assignmentService;
	
	public User findById(String id) throws UserAccessException{
		if(!userRepository.exists(id)) throw new UserAccessException(UserAccessException.USERID_NOT_FOUND,UserAccessException.USERID_NOT_FOUND_ERRORCODE, new String[]{id});
		return userRepository.findById(id);
	}
	public User findByFirstName(String firstName){
		return userRepository.findByFirstName(firstName);
	}
    public List<User> findByLastName(String lastName) throws UserAccessException{
    	if(userRepository.findByUserName(lastName) == null) throw new UserAccessException(UserAccessException.LASTNAME_NOT_FOUND, UserAccessException.LASTNAME_NOT_FOUND_ERRORCODE, new String[]{lastName});
    	return userRepository.findByLastName(lastName);
    }
 
    public User findByUserName(String username) throws UserAccessException{
    	if(userRepository.findByUserName(username) == null) throw new UserAccessException(UserAccessException.USERNAME_NOT_FOUND,UserAccessException.USERNAME_NOT_FOUND_ERRORCODE, new String[]{username});
    	return userRepository.findByUserName(username);
    }
    
    public void deleteUserByUserName(String username) throws UserAccessException{
    	User user = userRepository.findByUserName(username);
    	if(user == null) throw new UserAccessException(UserAccessException.USERNAME_NOT_FOUND, UserAccessException.USERNAME_NOT_FOUND_ERRORCODE, new String[]{username});
    	deleteUserById(user.getId());
    }
    
    public void deleteUserById(String id) throws UserAccessException {
    	@SuppressWarnings("unused")
		boolean noReferenceFound;
    	if(userRepository.findById(id) == null) throw new UserAccessException(UserAccessException.USERID_NOT_FOUND, UserAccessException.USERID_NOT_FOUND_ERRORCODE, new String[]{id});
    	try {
			assignmentService.findByUserId(id);
			noReferenceFound = false;
		} catch (AssignmentAccessException e) {
			noReferenceFound = true;
		}
    	if(noReferenceFound == false) throw new UserAccessException(UserAccessException.USER_ASSIGNED_IN_ASSIGNMENTS, UserAccessException.USER_ASSIGNED_IN_ASSIGNMENTS_ERRORCODE, new String[]{id});
    	userRepository.deleteUserById(id);
    }
    
    public void save(User user) throws UserStorageException{
    	// User validation
    	if(user.getUserName() == null || user.getUserName().isEmpty()){
    		throw new UserStorageException(UserStorageException.MISSING_MANDATORY_PARAMETER_USERNAME, UserStorageException.MISSING_MANDATORY_PARAMETER_USERNAME_ERRORCODE, new String[]{});
    	} else {
    		if(userRepository.findByUserName(user.getUserName()) != null){
    			throw new UserStorageException(UserStorageException.DUPLICATE_USERNAME,UserStorageException.DUPLICATE_USERNAME_ERRORCODE, new String[]{user.getUserName()});
    		}
    	}
    	if(user.getPassword() == null || user.getPassword().isEmpty()){
    		throw new UserStorageException(UserStorageException.MISSING_MANDATORY_PARAMETER_PASSWORD, UserStorageException.MISSING_MANDATORY_PARAMETER_PASSWORD_ERRORCODE, new String[]{});
    	}
    	if(user.getEmailAddress() == null || user.getEmailAddress().isEmpty()){
    		throw new UserStorageException(UserStorageException.MISSING_MANDATORY_PARAMETER_EMAIL, UserStorageException.MISSING_MANDATORY_PARAMETER_EMAIL_ERRORCODE, new String[]{});
    	} else {
    		if(!INFRATools.isValidEmail(user.getEmailAddress())) throw new UserStorageException(UserStorageException.INVALID_PARAMETER_EMAIL, UserStorageException.INVALID_PARAMETER_EMAIL_ERRORCODE, new String[]{user.getEmailAddress()});
    	}
    	if(user.getRole() == null || user.getRole().isEmpty()){
    		throw new UserStorageException(UserStorageException.MISSING_MANDATORY_PARAMETER_ROLE, UserStorageException.MISSING_MANDATORY_PARAMETER_ROLE_ERRORCODE, new String[]{});
    	}else{
    		if(user.getRole().equals("ADMIN")){
    			user.setRole("ROLE_ADMIN");
    		} else if(user.getRole().equals("INSPECTOR")){
    			user.setRole("ROLE_INSPECTOR");
    		}
    	}
    	userRepository.save(user);
    }
    
    public void deleteAll(){
    	userRepository.deleteAll();
    }
    
    public List<User> findAll(){
    	List<User> userList = userRepository.findAll();
    	for(User user : userList){
    		user.setPassword(null);
    	}
    	return userList;
    }
    
    public User updateUser(User updatedUser, String userid) throws UserAccessException{
    	User oldUser = userRepository.findById(userid);
    	if(oldUser == null) throw new UserAccessException(UserAccessException.USERID_NOT_FOUND, UserAccessException.USERID_NOT_FOUND_ERRORCODE, new String[]{updatedUser.getId()});
    	
    	if(updatedUser.getFirstName() != null && !updatedUser.getFirstName().equalsIgnoreCase(oldUser.getFirstName())){
    		oldUser.setFirstName(updatedUser.getFirstName());
		} 
		if(updatedUser.getLastName() != null && !updatedUser.getLastName().equalsIgnoreCase(oldUser.getLastName())){
			oldUser.setLastName(updatedUser.getLastName());
		}
		if(updatedUser.getEmailAddress() != null && !updatedUser.getEmailAddress().equalsIgnoreCase(oldUser.getEmailAddress())){
			oldUser.setEmailAddress(updatedUser.getEmailAddress());
		}
		if(updatedUser.getPhoneNumber() != null && !updatedUser.getPhoneNumber().equalsIgnoreCase(oldUser.getPhoneNumber())){
			oldUser.setPhoneNumber(updatedUser.getPhoneNumber());
		}
		if(updatedUser.getRole() != null && !updatedUser.getRole().equalsIgnoreCase(oldUser.getRole())){
			oldUser.setRole(updatedUser.getRole());
		}
		if(updatedUser.getPassword() != null && !updatedUser.getPassword().equalsIgnoreCase(oldUser.getPassword())){
			oldUser.setPassword(updatedUser.getPassword());
		}
		userRepository.save(oldUser);
    	return updatedUser;
    }
    
    public List<User> findByRole(String role){
    	return userRepository.findByRole(role);
    }
    
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		
		if(user == null){
			throw new UsernameNotFoundException(UserAccessException.USERNAME_NOT_FOUND);
		}

		return new CustomUserDetails(user, passwordEncoder);
	}
	
	public User getCurrentUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal() instanceof CustomUserDetails) {
			return ((CustomUserDetails) authentication.getPrincipal()).getUser();
		}
		return null;
	}
	
	public void rememberPassword(String username, String email) throws UserAccessException{
		User u = userRepository.findByUserName(username);
		if(u == null) throw new UserAccessException(UserAccessException.USERNAME_NOT_FOUND, UserAccessException.USERNAME_NOT_FOUND_ERRORCODE, new String[]{username});
		if(!u.getEmailAddress().equals(email)) throw new UserAccessException(UserAccessException.MAIL_OR_USERNAME_NOT_VALID, UserAccessException.MAIL_OR_USERNAME_NOT_VALID_ERRORCODE, new String[]{username, email});
		mailService.sendEmailWithPassword(u);
		
	}
	
}
