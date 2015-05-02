package com.insframe.server.error;

@SuppressWarnings("serial")
public class InspectionObjectStorageException extends
		InspectionFrameworkBaseException {
	
	public static final String MISSING_MANDATORY_PARAMETER_TEXT_ID = "error.inspobject.missing.mandatory.parameter";
	public static final int MISSING_MANDATORY_PARAMETER_ERRORCODE = 6101;
	public static final String INVALID_ATTACHMENT_REF_TEXT_ID = "error.inspobject.invalid.attachment.ref";
	public static final int INVALID_ATTACHMENT_REF = 6102;
	public static final String DUPLICATE_KEY_NAME_ID_TEXT_ID = "error.inspobject.duplicate.name_id";
	public static final int DUPLICATE_KEY_NAME_ID_ERRORCODE = 6103;
	public static final String DUPLICATE_KEY_NAME_TEXT_ID = "error.inspobject.duplicate.name";
	public static final int DUPLICATE_KEY_NAME_ERRORCODE = 6104;

	public InspectionObjectStorageException(String textId, int errorCode, String[] args) {
		super(textId, errorCode, args);
	}
}
