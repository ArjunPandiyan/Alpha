package com.intuit.Beans;

public class URLResponse {
	String type;
	String message;
	
	public URLResponse(String status, String response) {
		this.type=status;
		this.message=response;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
