package com.insframe.server.error;

@SuppressWarnings("serial")
public class AssignmentStorageException extends
		InspectionFrameworkBaseException {
	
	public static final String MISSING_MANDATORY_PARAMETER_TEXT_ID = "error.assignment.missing.mandatory.parameter";
	public static final int MISSING_MANDATORY_PARAMETER_ERRORCODE = 7101;
	public static final String INVALID_USER_REF_TEXT_ID = "error.assignment.invalid.user.ref";
	public static final int INVALID_USER_REF_ERROCODE = 7102;
	public static final String INVALID_INSP_OBJECT_REF_TEXT_ID = "error.assignment.invalid.inspection.object.ref";
	public static final int INVALID_INSP_OBJECT_REF_ERRORCODE = 7103;
	public static final String INVALID_TASK_STATE_TEXT_ID = "error.assignment.invalid.task.state";
	public static final int INVALID_TASK_STATE_ERRORCODE = 7104;
	public static final String INVALID_STATE_TEXT_ID = "error.assignment.invalid.state";
	public static final int INVALID_STATE_ERRORCODE = 7105;
	public static final String INVALID_ATTACHMENT_REF_TEXT_ID = "error.assignment.invalid.attachment.ref";
	public static final int INVALID_ATTACHMENT_REF_ERRORCODE = 7106;
	public static final String INVALID_TEMPLATE_ATTR_TEXT_ID = "error.assignment.invalid.template.attr";
	public static final int INVALID_TEMPLATE_ATTR_ERRORCODE = 7107;
	public static final String DUPLICATE_KEY_NAME_ID_TEXT_ID = "error.assignment.duplicate.name_id";
	public static final int DUPLICATE_KEY_NAME_ID_ERRORCODE = 7108;
	public static final String DUPLICATE_KEY_NAME_TEXT_ID = "error.assignment.duplicate.name";
	public static final int DUPLICATE_KEY_NAME_ERRORCODE = 7109; 
	public static final String UPDATED_VERSION_AVAILABLE_TEXT_ID = "error.assignment.updated.version.available";
	public static final int UPDATED_VERSION_AVAILABLE_ERRORCODE = 7110;
	public static final String INVALID_USER_TO_MODIFY_ASSIGNMENT_TEXT_ID = "error.assignment.invalid.user.to.modify";
	public static final int INVALID_USER_TO_MODIFY_ASSIGNMENT_ERRORCODE = 7111;
	public static final String ASSIGNMENT_FINISHED_TEXT_ID = "error.assignment.finished";
	public static final int ASSIGNMENT_FINISHED_ERRORCODE = 7112;
	public static final String ASSIGNMENT_STARTDATE_ENDDATE_NOT_VALID_TEXT_ID = "error.assignment.startdate.enddate.invalid";
	public static final int ASSIGNMENT_STARTDATE_ENDDATE_NOT_VALID_ERRORCODE = 7113;
	public static final String UNEXPECTED_ERROR_HAPPENED_TEXT_ID = "error.assignment.unexpected";
	public static final int UNEXPECTED_ERROR_HAPPENED_ERRORCODE = 7114;

	public AssignmentStorageException(String textId, int errorCode, String[] args) {
		super(textId, errorCode, args);
	}
}
