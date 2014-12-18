package com.insframe.server.data.repository;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.config.WebAppConfigurationAware;
import com.insframe.server.model.User;

public class UserRepositoryTest extends WebAppConfigurationAware{
	
	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void removeAll(){
		userRepository.deleteAll();
	}
	
	@Test
	public void shouldSaveUser(){
		userRepository.save(new User("morph3o", "asda@gmail.com", "123", "ROLE_ADMIN", "Divasto", "Piero", "2222", "33333"));
	}

}
