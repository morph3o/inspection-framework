package com.insframe.server.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.insframe.server.config.WebAppConfigurationAware;

public class MailServiceTest extends WebAppConfigurationAware{

	@Autowired
	private MailService mailService;
	
	@Test
	public void shouldSendEmail(){
		System.out.println("****** Should send an email to someEmail@someDomain.com ******");
		String from = "test@testing.com";
		String to = "morph3o@icloud.com";
		String subject = "Testing email feature of inspection framework";
		String message = "This email is for testing the sending email feature of inspection framework.";
		mailService.sendEmail(from, to, subject, message);
		System.out.println("****** End of Should send an email to someEmail@someDomain.com ******");
	}
	
}
