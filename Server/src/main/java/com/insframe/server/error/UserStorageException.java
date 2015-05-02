package com.insframe.server.error;

@SuppressWarnings("serial")
public class UserStorageException extends InspectionFrameworkBaseException{

	public static final String MISSING_MANDATORY_PARAMETER_USERNAME = "error.user.missing.mandatory.parameter.username";
	public static final int MISSING_MANDATORY_PARAMETER_USERNAME_ERRORCODE = 8101;
	public static final String MISSING_MANDATORY_PARAMETER_EMAIL = "error.user.missing.mandatory.parameter.password";
	public static final int MISSING_MANDATORY_PARAMETER_EMAIL_ERRORCODE = 8102;
	public static final String MISSING_MANDATORY_PARAMETER_PASSWORD = "error.user.missing.mandatory.parameter.email";
	public static final int MISSING_MANDATORY_PARAMETER_PASSWORD_ERRORCODE = 8103; 
	public static final String MISSING_MANDATORY_PARAMETER_ROLE = "error.user.missing.mandatory.parameter.role";
	public static final int MISSING_MANDATORY_PARAMETER_ROLE_ERRORCODE = 8104;
	public static final String INVALID_PARAMETER_EMAIL = "error.user.invalid.parameter.email";
	public static final int INVALID_PARAMETER_EMAIL_ERRORCODE = 8105;
	public static final String DUPLICATE_USERNAME = "error.user.duplicate.username";
	public static final int DUPLICATE_USERNAME_ERRORCODE = 8106;

	public UserStorageException(String messageId, int errorCode, String[] args) {
		super(messageId, errorCode, args);
	}

}
