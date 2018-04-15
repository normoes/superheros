package com.payworks.superhero.errors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.mongodb.MongoException;
import com.payworks.superhero.helpers.JsonFunctions;

/**
 * <h1> RestControllerExceptionHandler </h1>
 * Handles REST exceptions.
 * 
 * Exceptions are converted into a proper JSON string representation.
 * Also, the exception is logged to {@code stdout}.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-13
 *
 */
@ControllerAdvice
public class RestControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

	/**
	 * Should no exception handler be found, use this one.
	 * 
	 * @param ex exception that occurred.
	 * @param response HTTP response
	 * @param request HTTP request
	 * @return JSON error string.
	 */
	@ExceptionHandler(value = {NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public JsonError handleNoHandlerFoundException(ServletException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
	
	/**
	 * This handles the case of creating a new {@code Superhero}, whose name
	 * already is taken by another {@code Superhero}.
	 * 
	 * @param ex exception that occurred.
	 * @param response HTTP response
	 * @param request HTTP request
	 * @return JSON error string.
	 */
	@ExceptionHandler(value = {NameAlreadyTakenException.class})
	@ResponseBody	
	public JsonError handleNameAlreadyTakenException(SuperheroRestException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, ex.getStatus(), request);
		response.setStatus(ex.getStatus().value());
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
	
	/**
	 * This handles the case of a bad request to any HTTP REST endpoint.
	 * 
	 * <ul>
	 *   <li> missing parameter
	 *   <li> ...
	 * </ul>
	 * 
	 * @param ex exception that occurred.
	 * @param response HTTP response
	 * @param request HTTP request
	 * @return JSON error string.
	 */
	@ExceptionHandler(value = {BadRequestException.class})
	@ResponseBody
	public JsonError handleBadRequestException(SuperheroRestException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, ex.getStatus(), request);
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
	
	/**
	 * This handles the case of a non-existent database resource.
	 * 
	 * @param ex exception that occurred.
	 * @param response HTTP response
	 * @param request HTTP request
	 * @return JSON error string.
	 */
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)	
	@ResponseBody
	public JsonError handleResourceNotFoundException(RuntimeException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, HttpStatus.NOT_FOUND, request);
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
	
	/**
	 * Here any mongodb error is handled.
	 * 
	 * <ul>
	 *   <li> timeout
	 *   <li> data access
	 *   <li> ...
	 * </ul>
	 * 
	 * @param ex exception that occurred.
	 * @param response HTTP response
	 * @param request HTTP request
	 * @return JSON error string.
	 */
	@ExceptionHandler(value = {DataAccessResourceFailureException.class, MongoException.class}) //MongoTimeoutException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)	
	@ResponseBody	
	public JsonError handleMongoTimeoutException(RuntimeException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
}


