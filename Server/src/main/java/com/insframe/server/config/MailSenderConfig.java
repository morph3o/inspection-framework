package com.insframe.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailSenderConfig {

	@Value("${mail.config.host}")
	private String MAIL_HOST;
	@Value("${mail.config.port}")
	private String MAIL_PORT;
	@Value("${mail.config.username}")
	private String MAIL_USERNAME;
	@Value("${mail.config.password}")
	private String MAIL_PASSWORD;
	
	@Bean
    public JavaMailSender mailSender(){
    	JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    	mailSender.setHost(MAIL_HOST);
    	mailSender.setPort(Integer.parseInt(MAIL_PORT));
    	mailSender.setUsername(MAIL_USERNAME);
    	mailSender.setPassword(MAIL_PASSWORD);
    	
    	mailSender.getJavaMailProperties().put("mail.smtp.auth", true);
    	mailSender.getJavaMailProperties().put("mail.smtp.starttls.enable", true);
    	
    	return mailSender;
    }
	
}
