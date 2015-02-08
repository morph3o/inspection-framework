package com.insframe.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendEmail(String from, String to, String subject, String message){
		SimpleMailMessage customMailMessage = new SimpleMailMessage();
		
		customMailMessage.setFrom(from);
		customMailMessage.setTo(to);
		customMailMessage.setSubject(subject);
		customMailMessage.setText(message);
		
		mailSender.send(customMailMessage);
	}
	
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
}
