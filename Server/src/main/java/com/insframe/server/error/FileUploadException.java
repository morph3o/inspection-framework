package com.insframe.server.error;

public class FileUploadException extends InspectionFrameworkBaseException {

	public static final String EMPTY_FILE_UPLOAD_TEXT_ID = "error.empty.file.uploaded";
	
	public FileUploadException(String messageId, String[] args) {
		super(messageId, args);
	}

}
