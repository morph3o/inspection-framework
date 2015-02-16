package com.insframe.server.error;

@SuppressWarnings("serial")
public class InspectionObjectStorageException extends
		InspectionFrameworkBaseException {
	
	public static final String MISSING_MANDATORY_PARAMETER_TEXT_ID = "error.inspobject.missing.mandatory.parameter";
	public static final String INVALID_ATTACHMENT_REF_TEXT_ID = "error.inspobject.invalid.attachment.ref";
	public static final String DUPLICATE_KEY_NAME_ID = "error.inspobject.duplicate.name_id";
	public static final String DUPLICATE_KEY_NAME = "error.inspobject.duplicate.name";

	public InspectionObjectStorageException(String textId, String[] args) {
		super(textId, args);
	}
}
