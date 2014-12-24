package com.insframe.server.error;

public class JSONErrorMessage {
	
	private String errorMessage;

	public JSONErrorMessage(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}

	public String getErrorMassage() {
		return errorMessage;
	}

	public void setErrorMassage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
