package com.payworks.superhero.helpers;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.type.TypeFactory;

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
	* This is the method that converts an object to a Json string.
	* 
 	* @param obj Object to be represented as Json string.
 	* @return JSON representation of {@code obj}.
 	*/
	public static String getJsonFromObject(Object obj) {
		ObjectWriter ow = new ObjectMapper().writer();
		String jsonString = "";
		try {
			jsonString = ow.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("error converting object into JSON");
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return jsonString;
	}

	/**
	 * This is the method that converts a Json string into a {@code List<T>} object.
	 * 
	 * Pass a class like {@code Superhero.class} as {@code classType} argument, 
	 * in order to cast to the correct object instances. 
	 * 
	 * @param json Contains the Json string to be converted into a {@code List<T>} object.
	 * @param <T> Represents a generic class type the Json string is going to be converted to.
  	 * @param classType is the class of a specific object.
	 * @return a list containing the object instances given within the Json string, can also be {@code null}.
	 */
	public static <T> List<T> getListFromJsonString(String json, Class<T> classType) {
		ObjectMapper mapper = new ObjectMapper();
		List<T> list = null;
		try {
			list = mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, classType));
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return list;	
	}
	
	/**
	  * This is the method that converts a Json string to a given object.
	  * 
	  * @param json Contains the Json string to be converted into an object.
	  * @param <T> Represents a generic class type the Json string is going to be converted to.
	  * @param classType Is the class of a specific object, the Json string should be deserialized to.
	  * @return an object (deserialized from the Json string), can also be {@code null}..
	  */
		public static <T> T getItemFromJson(String json, Class<T> classType){
			ObjectMapper mapper = new ObjectMapper();
			try {
				return mapper.readValue(json, classType);
			}catch(JsonParseException e){
				logger.error("could not parse json: " + json + " to " + classType);
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error("error getting object from json: " + json);
				logger.error(e.getMessage());
			} catch (Exception e) {
				logger.error("error getting object from json: " + json);
				logger.error(e.getMessage());
			}
			return null;
		}
}
