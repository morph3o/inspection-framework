package com.insframe.server.error;

public class JSONErrorMessage {
	
	private String errorMassage;

	public JSONErrorMessage(String errorMassage) {
		super();
		this.errorMassage = errorMassage;
	}

	public String getErrorMassage() {
		return errorMassage;
	}

	public void setErrorMassage(String errorMassage) {
		this.errorMassage = errorMassage;
	}
	
}
