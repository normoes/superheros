package com.payworks.superhero.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * <h1> JsonFunctions </h1>
 * This class contains static helper methods when working with JSON.
 *
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-13
 */
public class JsonFunctions {
	private static final Logger logger = LoggerFactory.getLogger(JsonFunctions.class);

	  /**
	  * This is the method that converts an object to a JSON string.
	  * @param obj Object to be represented as JSON string.
	  * @return JSON representation of {@code obj}.
	  */
		public static String getJsonFromObject(Object obj){
			ObjectWriter ow = new ObjectMapper().writer();
			String jsonString = "";
			try {
				jsonString = ow.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				logger.error("error converting object into JSON");
				logger.error(e.getMessage());
			}
			return jsonString;
		}

		

}
