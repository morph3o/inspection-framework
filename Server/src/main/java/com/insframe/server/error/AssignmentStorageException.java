package com.insframe.server.error;

@SuppressWarnings("serial")
public class AssignmentStorageException extends
		InspectionFrameworkBaseException {
	
	public static final String MISSING_MANDATORY_PARAMETER_TEXT_ID = "error.assignment.missing.mandatory.parameter";
	public static final String INVALID_USER_REF_TEXT_ID = "error.assignment.invalid.user.ref";
	public static final String INVALID_INSP_OBJECT_REF_TEXT_ID = "error.assignment.invalid.inspection.object.ref";
	public static final String INVALID_STATE_TEXT_ID = "error.assignment.invalid.state";
	public static final String INVALID_ATTACHMENT_REF_TEXT_ID = "error.assignment.invalid.attachment.ref";
	public static final String INVALID_TEMPLATE_ATTR_TEXT_ID = "error.assignment.invalid.template.attr";
	public static final String DUPLICATE_KEY_NAME_ID = "error.assignment.duplicate.name_id";
	public static final String DUPLICATE_KEY_NAME = "error.assignment.duplicate.name";

	public AssignmentStorageException(String textId, String[] args) {
		super(textId, args);
	}
}
