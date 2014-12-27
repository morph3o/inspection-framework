package com.insframe.server.error;

@SuppressWarnings("serial")
public class InspectionObjectAccessException extends
		InspectionFrameworkBaseException {
	
	public static final String OBJECT_ID_NOT_FOUND_TEXT_ID = "error.no.inspectionobject.with.id";
	public static final String NO_OBJECTS_FOUND_TEXT_ID = "error.no.inspectionobjects";
	public static final String NO_OBJECTS_FOUND_BY_CUSTOMERNAME_TEXT_ID = "error.no.inspectionobject.with.customername";

	public InspectionObjectAccessException(String textId, String[] args) {
		super(textId, args);
	}
}
