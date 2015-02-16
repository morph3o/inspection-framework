package com.insframe.server.error;

@SuppressWarnings("serial")
public class UserAccessException extends InspectionFrameworkBaseException{
	
	public static final String USERNAME_NOT_FOUND = "error.no.user.with.username";
	public static final String LASTNAME_NOT_FOUND = "error.no.user.with.lastname";
	public static final String USERID_NOT_FOUND = "error.no.user.with.id";

	public UserAccessException(String messageId, String[] args) {
		super(messageId, args);
	}

}
