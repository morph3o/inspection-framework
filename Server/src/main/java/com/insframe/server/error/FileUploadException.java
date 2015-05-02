package com.insframe.server.error;

@SuppressWarnings("serial")
public class FileUploadException extends InspectionFrameworkBaseException {

	public static final String EMPTY_FILE_UPLOAD_TEXT_ID = "error.empty.file.uploaded";
	public static final int EMPTY_FILE_UPLOAD_ERRORCODE = 5001;
	
	public FileUploadException(String messageId, int errorCode, String[] args) {
		super(messageId, errorCode, args);
	}

}
