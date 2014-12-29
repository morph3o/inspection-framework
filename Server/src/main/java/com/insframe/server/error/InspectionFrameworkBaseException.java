package com.insframe.server.error;


@SuppressWarnings("serial")
public class InspectionFrameworkBaseException extends Exception {
	
	private String messageId;
	private String[] args;
	
	public InspectionFrameworkBaseException(String messageId, String... args) {
		super();
		this.messageId = messageId;
		this.args = args;
	}

	public String getMessageId() {
		return messageId;
	}

	public String[] getArgs() {
		return args;
	}
}
