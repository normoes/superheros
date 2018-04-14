package com.payworks.superhero.errors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.payworks.superhero.helpers.JsonFunctions;

/**
 * Handles REST exceptions.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-13
 *
 */
@ControllerAdvice
public class RestControllerExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestControllerExceptionHandler.class);

	@ExceptionHandler(value = {NoHandlerFoundException.class})
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public JsonError handleNoHandlerFoundException(ServletException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, HttpStatus.INTERNAL_SERVER_ERROR, request);
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
	
	@ExceptionHandler(value = {NameAlreadyTakenException.class})
	@ResponseBody	
	public JsonError handleNameAlreadyTakenException(SuperheroRestException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, ex.getStatus(), request);
		response.setStatus(ex.getStatus().value());
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
	
	@ExceptionHandler(value = {BadRequestException.class})
	@ResponseBody
	public JsonError handleBadRequestException(SuperheroRestException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, ex.getStatus(), request);
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
	
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)	
	@ResponseBody
	public JsonError handleResourceNotFoundException(RuntimeException ex, HttpServletResponse response, HttpServletRequest request) {
		JsonError jsonError = new JsonError(ex, HttpStatus.NOT_FOUND, request);
		logger.error(JsonFunctions.getJsonFromObject(jsonError));
		return jsonError;
	}
}


