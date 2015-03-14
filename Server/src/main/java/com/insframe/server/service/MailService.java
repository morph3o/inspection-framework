package com.insframe.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.insframe.server.model.Assignment;
import com.insframe.server.model.User;

@Service
public class MailService {
	
	final static private String SENDER_EMAIL = "info@inspection-framework.com";

	@Autowired
	private UserService userService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MessageSource messageSource;
	
	public void sendEmail(String from, String to, String subject, String message){
		SimpleMailMessage customMailMessage = new SimpleMailMessage();
		
		customMailMessage.setFrom(from);
		customMailMessage.setTo(to);
		customMailMessage.setSubject(subject);
		customMailMessage.setText(message);
		
		mailSender.send(customMailMessage);
	}
	
	public void sendEmail(String to, String subject, String message){
		SimpleMailMessage customMailMessage = new SimpleMailMessage();
		
		customMailMessage.setFrom(SENDER_EMAIL);
		customMailMessage.setTo(to);
		customMailMessage.setSubject(subject);
		customMailMessage.setText(message);
		
		mailSender.send(customMailMessage);
	}
	
	public void sendEmailToAdmins(String subject, String message){
		List<User> adminList = userService.findByRole("ROLE_ADMIN");
		StringBuffer strBuffer = new StringBuffer();
		SimpleMailMessage[] mailList = new SimpleMailMessage[adminList.size()];
		
		for(int i=0; i<adminList.size(); i++){
			SimpleMailMessage auxMailMessage = new SimpleMailMessage();
			User auxUser = adminList.get(i);
			strBuffer.append(messageSource.getMessage("email.message.dear", new String[]{auxUser.getFirstName()+" "+auxUser.getLastName()}, LocaleContextHolder.getLocale()));
			strBuffer.append(messageSource.getMessage("email.message.message", new String[]{message}, LocaleContextHolder.getLocale()));
			
			auxMailMessage.setFrom(SENDER_EMAIL);
			auxMailMessage.setTo(auxUser.getEmailAddress());
			auxMailMessage.setSubject(subject);
			auxMailMessage.setText(strBuffer.toString());
			
			mailList[i] = auxMailMessage;
			strBuffer = new StringBuffer();
		}
		
		mailSender.send(mailList);
	}
	
	public void sendEmailToAdminsWithAssignmentDetails(String subject, String message, Assignment assignment){
		List<User> adminList = userService.findByRole("ROLE_ADMIN");
		StringBuffer strBuffer = new StringBuffer();
		SimpleMailMessage[] mailList = new SimpleMailMessage[adminList.size()];
		
		for(int i=0; i<adminList.size(); i++){
			SimpleMailMessage auxMailMessage = new SimpleMailMessage();
			User auxUser = adminList.get(i);
			strBuffer.append(messageSource.getMessage("email.message.dear", new String[]{auxUser.getFirstName()+" "+auxUser.getLastName()}, LocaleContextHolder.getLocale()));
			strBuffer.append(messageSource.getMessage("email.message.message", new String[]{message}, LocaleContextHolder.getLocale())+"\n\n");
			strBuffer.append(messageSource.getMessage("email.message.assignment.details.header", new String[]{message}, LocaleContextHolder.getLocale()));
			strBuffer.append(assignment.toDescription());
			
			auxMailMessage.setFrom(SENDER_EMAIL);
			auxMailMessage.setTo(auxUser.getEmailAddress());
			auxMailMessage.setSubject(subject);
			auxMailMessage.setText(strBuffer.toString());
			
			mailList[i] = auxMailMessage;
			strBuffer = new StringBuffer();
		}
		
		mailSender.send(mailList);
	}
	
	public void sendEmailWithPassword(User user){
		SimpleMailMessage auxMailMessage = new SimpleMailMessage();
		StringBuffer strBuffer = new StringBuffer();
		
		strBuffer.append(messageSource.getMessage("email.message.dear", new String[]{user.getFirstName()+" "+user.getLastName()}, LocaleContextHolder.getLocale()));
		strBuffer.append(messageSource.getMessage("email.message.password", null, LocaleContextHolder.getLocale()));
		strBuffer.append(user.getPassword());
		
		auxMailMessage.setFrom(SENDER_EMAIL);
		auxMailMessage.setTo(user.getEmailAddress());
		auxMailMessage.setSubject(messageSource.getMessage("email.subject.password", null, LocaleContextHolder.getLocale()));
		auxMailMessage.setText(strBuffer.toString());
		
		mailSender.send(auxMailMessage);
	}
}
