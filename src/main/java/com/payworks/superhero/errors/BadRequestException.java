package com.payworks.superhero.errors;

import org.springframework.http.HttpStatus;

/**
 * 
 * Something went wrong while HTTP request was processed.
 * 
 * Return {@code BAD REQUEST}.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-14
 *
 */
public class BadRequestException extends SuperheroRestException {
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status = HttpStatus.BAD_REQUEST;
	
	public BadRequestException(String message) {
		super(message);
    }

    public BadRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
