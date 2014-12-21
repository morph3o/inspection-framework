package com.insframe.server.error;

public class JSONError {
	
	private String errorMessage;

	public JSONError(String errorMessage) {
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
