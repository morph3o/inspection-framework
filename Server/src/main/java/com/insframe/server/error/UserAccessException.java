package com.insframe.server.error;

@SuppressWarnings("serial")
public class UserAccessException extends InspectionFrameworkBaseException{
	
	public static final String USERNAME_NOT_FOUND = "error.no.user.with.username";
	public static final int USERNAME_NOT_FOUND_ERRORCODE = 8001;
	public static final String LASTNAME_NOT_FOUND = "error.no.user.with.lastname";
	public static final int LASTNAME_NOT_FOUND_ERRORCODE = 8002;
	public static final String USERID_NOT_FOUND = "error.no.user.with.id";
	public static final int USERID_NOT_FOUND_ERRORCODE = 8003;

	public UserAccessException(String messageId, int errorCode, String[] args) {
		super(messageId, errorCode, args);
	}

}
