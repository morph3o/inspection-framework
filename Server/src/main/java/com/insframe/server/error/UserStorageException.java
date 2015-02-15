package com.insframe.server.error;

@SuppressWarnings("serial")
public class UserStorageException extends InspectionFrameworkBaseException{

	public static final String MISSING_MANDATORY_PARAMETER_USERNAME = "error.user.missing.mandatory.parameter.username";
	public static final String MISSING_MANDATORY_PARAMETER_EMAIL = "error.user.missing.mandatory.parameter.password";
	public static final String MISSING_MANDATORY_PARAMETER_PASSWORD = "error.user.missing.mandatory.parameter.email";
	public static final String MISSING_MANDATORY_PARAMETER_ROLE = "error.user.missing.mandatory.parameter.role";
	public static final String INVALID_PARAMETER_EMAIL = "error.user.invalid.parameter.email";
	public static final String DUPLICATE_USERNAME = "error.user.duplicate.username";
//	public static final String DUPLICATE_EMAIL = "error.user.duplicate.email";

	public UserStorageException(String messageId, String[] args) {
		super(messageId, args);
	}

}
