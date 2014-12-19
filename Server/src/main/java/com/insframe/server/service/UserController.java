package com.insframe.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.insframe.server.data.repository.UserRepository;
import com.insframe.server.error.JSONErrorMessage;
import com.insframe.server.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/user")
    public Object getFirstUserFoundByLastName(@RequestParam(value="lastName") String lastName) {
		if(userRepository.findByLastName(lastName).size() > 0){
			return (User) userRepository.findByLastName(lastName).get(0);
		} else {
			return new JSONErrorMessage("There is no user with last name: "+lastName);
		}
    }

}
