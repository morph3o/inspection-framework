package com.insframe.server.error;

@SuppressWarnings("serial")
public class InspectionObjectAccessException extends
		InspectionFrameworkBaseException {
	
	public static final String OBJECT_ID_NOT_FOUND_TEXT_ID = "error.no.inspectionobject.with.id";
	public static final int OBJECT_ID_NOT_FOUND_ERRORCODE = 6001;
	public static final String NO_OBJECTS_FOUND_TEXT_ID = "error.no.inspectionobjects";
	public static final int NO_OBJECTS_FOUND_ERRORCODE = 6002;
	public static final String NO_OBJECTS_FOUND_BY_CUSTOMERNAME_TEXT_ID = "error.no.inspectionobject.with.customername";
	public static final int NO_OBJECTS_FOUND_BY_CUSTOMERNAME_ERRORCODE = 6003;
	public static final String NO_OBJECTS_FOUND_BY_OBJECTNAME_TEXT_ID = "error.no.inspectionobject.with.objectname";
	public static final int NO_OBJECTS_FOUND_BY_OBJECTNAME_ERRORCODE = 6004;
	public static final String INSPOBJ_ASSIGNED_IN_ASSIGNMENTS_TEXT_ID = "error.inspobject.assigned.in.assignments";
	public static final int INSPOBJ_ASSIGNED_IN_ASSIGNMENTS_ERRORCODE = 6005;
	

	public InspectionObjectAccessException(String textId, int errorCode, String[] args) {
		super(textId, errorCode, args);
	}
}
