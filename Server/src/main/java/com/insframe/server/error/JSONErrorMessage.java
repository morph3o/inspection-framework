package com.insframe.server.error;

public class JSONErrorMessage {
	
	private int errorCode;
	private String errorMessage;
	private String errorURL;
	
	public JSONErrorMessage(String errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
	
	public JSONErrorMessage(String errorMessage, String errorURL, int errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorURL = errorURL;
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorURL() {
		return errorURL;
	}

	public void setErrorURL(String errorURL) {
		this.errorURL = errorURL;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
