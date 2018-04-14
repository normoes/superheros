package com.payworks.superhero.errors;

import org.springframework.http.HttpStatus;

/**
 * This class works as base class for all HTTP REST exceptions.
 * 
 * It provides an HTTP status code.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-14
 *
 */
public abstract class SuperheroRestException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SuperheroRestException(String message) {
		super(message);
	}

	/**
	 * 
	 * @return the status of the HTTP response for the specific exception type.
	 */
	public abstract HttpStatus getStatus();
}
