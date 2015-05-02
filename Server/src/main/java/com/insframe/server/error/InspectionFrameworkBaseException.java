package com.insframe.server.error;


@SuppressWarnings("serial")
public class InspectionFrameworkBaseException extends Exception {
	
	private String messageId;
	private String[] args;
	private int errorCode;
	
	
	public InspectionFrameworkBaseException(String messageId, int errorCode, String... args) {
		super(messageId);
		this.messageId = messageId;
		this.args = args;
		this.errorCode = errorCode;
	}

	public String getMessageId() {
		return messageId;
	}

	public String[] getArgs() {
		return args;
	}

	public int getErrorCode() {
		return errorCode;
	}
	
}
