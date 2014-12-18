package com.insframe.server.error;

public class JSONError {
	
	private String errorMassage;

	public JSONError(String errorMassage) {
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
