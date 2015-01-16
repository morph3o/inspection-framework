package com.insframe.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Repository;

import com.insframe.server.data.repository.UserRepository;
import com.insframe.server.model.User;

@Repository
public class UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private MongoOperations mongoOpts;
	
	public User findById(String id){
		return userRepository.findById(id);
	}
	public User findByFirstName(String firstName){
		return userRepository.findByFirstName(firstName);
	}
    public List<User> findByLastName(String lastName){
    	return userRepository.findByLastName(lastName);
    }
 
    public User findByUserName(String username){
    	return userRepository.findByUserName(username);
    }
    
    public Long deleteUserByUserName(String lastname){
    	return userRepository.deleteUserByUserName(lastname);
    }
    
    public void save(User user){
    	userRepository.save(user);
    }
    
    public void deleteAll(){
    	userRepository.deleteAll();
    }
    
    public List<User> findAll(){
    	return userRepository.findAll();
    }
    
    public User updateUser(User updatedUser){
    	User oldUser = userRepository.findByUserName(updatedUser.getUserName());
    	
    	if(!updatedUser.getFirstName().equalsIgnoreCase(oldUser.getFirstName())){
    		oldUser.setFirstName(updatedUser.getFirstName());
		} 
		if(!updatedUser.getLastName().equalsIgnoreCase(oldUser.getLastName())){
			oldUser.setLastName(updatedUser.getLastName());
		}
		if(!updatedUser.getEmailAddress().equalsIgnoreCase(oldUser.getEmailAddress())){
			oldUser.setEmailAddress(updatedUser.getEmailAddress());
		}
		if(!updatedUser.getPhoneNumber().equalsIgnoreCase(oldUser.getPhoneNumber())){
			oldUser.setPhoneNumber(updatedUser.getPhoneNumber());
		}
		if(!updatedUser.getRole().equalsIgnoreCase(oldUser.getRole())){
			oldUser.setRole(updatedUser.getRole());
		}
		if(!updatedUser.getPassword().equalsIgnoreCase(oldUser.getPassword())){
			oldUser.setPassword(updatedUser.getPassword());
		}
		
		userRepository.save(oldUser);
    	return updatedUser;
    }
	
}
