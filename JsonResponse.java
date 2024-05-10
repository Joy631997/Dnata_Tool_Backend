package com.teleapps.DnataReportingTool.Dto;

public class JsonResponse {
	

	private int statusCode;
	private String statusDescription;
	private String message;
	private Object responseBody;
	
	public JsonResponse(int statusCode, String statusDescription, String message, Object responseBody) {
		
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.message = message;
		this.responseBody = responseBody;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(Object responseBody) {
		this.responseBody = responseBody;
	}

}
