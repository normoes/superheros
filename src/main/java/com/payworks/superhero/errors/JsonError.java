package com.payworks.superhero.errors;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

/**
 * With every error create an error object, which, later on, can be
 * easily serialized to a JSON string representation.
 * 
 * This provides unified JSON error messages from the backend.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-13
 *
 */
public class JsonError {
	
	private String timestamp;  // time the exception occurred in seconds (unix timestamp)
	private String exception;
	private String status;
	private String error;
	private String path;
	private String message;
	
	public JsonError(Throwable ex, HttpStatus status, HttpServletRequest request) {
		this.timestamp = Long.toString(Instant.now().getEpochSecond());
		this.exception = ex.getClass().getCanonicalName();
		this.status = String.valueOf(status.value());
		this.error = status.getReasonPhrase();
		this.path = request.getServletPath();
		this.message = ex.getMessage();
	}

	/**
	 * Get the current time in seconds.
	 * @return current time in seconds (unix timestamp)
	 */
	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
