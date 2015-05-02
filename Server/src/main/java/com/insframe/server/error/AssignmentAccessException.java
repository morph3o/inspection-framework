package com.insframe.server.error;

@SuppressWarnings("serial")
public class AssignmentAccessException extends
		InspectionFrameworkBaseException {
	
	public static final String OBJECT_ID_NOT_FOUND_TEXT_ID = "error.no.assignment.with.id";
	public static final int OBJECT_ID_NOT_FOUND_ERRORCODE = 7001;
	public static final String NO_OBJECTS_FOUND_TEXT_ID = "error.no.assignments";
	public static final int NO_OBJECTS_FOUND_ERRORCODE = 7002;
	public static final String NO_OBJECTS_FOUND_BY_NAME_TEXT_ID = "error.no.assignment.with.name";
	public static final int NO_OBJECTS_FOUND_BY_NAME_ERRORCODE = 7003;
	public static final String TASK_ID_NOT_FOUND_TEXT_ID = "error.assignment.no.task.with.id";
	public static final int TASK_ID_NOT_FOUND_ERRORCODE = 7004;
	public static final String NO_OBJECTS_BY_USER_ID_FOUND_TEXT_ID = "error.no.assignment.with.user_id";
	public static final int NO_OBJECTS_BY_USER_ID_FOUND_ERRORCODE = 7005;
	public static final String NO_OBJECTS_BY_INSPOBJ_ID_FOUND_TEXT_ID = "error.no.assignment.with.inspobj_id";
	public static final int NO_OBJECTS_BY_INSPOBJ_ID_FOUND_ERRORCODE = 7006;
	
	public AssignmentAccessException(String textId, int errorCode, String[] args) {
		super(textId, errorCode, args);
	}
}
