package com.insframe.server.error;

@SuppressWarnings("serial")
public class AssignmentAccessException extends
		InspectionFrameworkBaseException {
	
	public static final String OBJECT_ID_NOT_FOUND_TEXT_ID = "error.no.assignment.with.id";
	public static final String NO_OBJECTS_FOUND_TEXT_ID = "error.no.assignments";
	public static final String NO_OBJECTS_FOUND_BY_NAME_TEXT_ID = "error.no.assignment.with.name";

	public AssignmentAccessException(String textId, String[] args) {
		super(textId, args);
	}
}
