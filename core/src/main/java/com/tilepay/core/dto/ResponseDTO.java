package com.tilepay.core.dto;

public class ResponseDTO {
	
	private String message;
	
	private boolean isSuccessful = false;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}
}
