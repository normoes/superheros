package com.payworks.superhero.errors;

import org.springframework.http.HttpStatus;

/**
 * 
 * A superhero is created but the given name is taken.
 * 
 * Return {@code CONFLICT}.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-13
 *
 */
public class NameAlreadyTakenException extends SuperheroRestException {
	private static final long serialVersionUID = 1L;
	
	private HttpStatus status = HttpStatus.CONFLICT;

	public NameAlreadyTakenException(String message) {
		super(message);
    }

    public NameAlreadyTakenException(String message, HttpStatus status) {
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
