package com.payworks.superhero.rest;


import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.payworks.superhero.errors.BadRequestException;
import com.payworks.superhero.errors.NameAlreadyTakenException;
import com.payworks.superhero.helpers.JsonFunctions;
import com.payworks.superhero.model.Superhero;
import com.payworks.superhero.repo.SuperheroRepo;

/**
 * <h1> SuperheroController </h1>
 * This class handles HTTP REST entry points regarding the {@link Superhero} management.
 *
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-13
 */

@RestController
public class SuperheroController {
	private static final Logger logger = LoggerFactory.getLogger(SuperheroController.class);
	
	private final SuperheroRepo superheroRepo;
	
	/**
	 * Superhero constructor
	 * 
	 * @param superheroRepo is injected on creation
	 */
	@Autowired
	public SuperheroController(SuperheroRepo superheroRepo) {
		this.superheroRepo = superheroRepo;
    }
	
    /**
     * Creates a new {@code Superhero}.
     * The new {@link Superhero} is sent as {@code request body (data)} via HTTP REST POST
     * to the {@code /superheros/}> HTTP REST endpoint.
     * 
     * Superhero names are checked for uniqueness.
     * 
     * The endpoint expects {@code context-type:application/json}.
     * The endpoint produces {@code context-type:application/json}.
     *
     * @param newSuperhero JSON string representation of User converted to {@code Superhero} class.
     * @param response Is used to set HTTP status codes on response.
     * @return the created {@code Superhero} as JSON string.
     * @throws NameAlreadyTakenException when POSTing a {@code Superhero} whose name is taken already. 
     */
    @RequestMapping(value = {"/superheros"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces =
    		MediaType.APPLICATION_JSON_VALUE)
    public Superhero createNewSuperhero(@Valid @RequestBody Superhero newSuperhero, HttpServletResponse response) throws NameAlreadyTakenException{
    	logger.info("POST superhero");    	    	
        logger.info(JsonFunctions.getJsonFromObject(newSuperhero));
        
        // check the given superhero instance for its existence
        Optional<Superhero> hero = Optional.of(newSuperhero);
        if ( !hero.isPresent() ) {
        	throw new BadRequestException("No superhero provided.");
        }
        
        // check for the existence of the given superhero's name
    	if ( superheroRepo.findByName(hero.get().getName()) != null ) {
    		throw new NameAlreadyTakenException("Given superhero name taken already: " + hero.get().getName());
    	}
    	
    	// store the new superhero into the database
        newSuperhero = superheroRepo.save(hero.get());
        response.setStatus(HttpServletResponse.SC_CREATED);
        return newSuperhero;
    }
    
    /**
     * Get all superheros.
     * 
     * The endpoint produces {@code context-type:application/json}.
     *
     * @param response Is used to set HTTP status codes on response.
     * @return a list of all superheros, can be empty.
     */
    @RequestMapping(value = {"/superheros"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<Superhero> getAllSuperheros(HttpServletResponse response) {
        logger.info("GET superheros");
        List<Superhero> superheros = superheroRepo.findAll();

        response.setStatus(HttpServletResponse.SC_OK);
        return superheros;
    } 
    
    /**
     * Get a specific {@link Superhero} by his/her name.
     * 
     * The endpoint produces {@code context-type:application/json}.
     *
     * @param name Defines the superhero to be returned.
     * @param response Is used to set HTTP status codes on response.
     * @return the specific {@code Superhero} corresponding to the given name.
     * @throws ResourceNotFoundException when nothing was found. 
     */
    @RequestMapping(value = {"/superheros/{name}"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Superhero getUserById(@PathVariable("name") String name, HttpServletResponse response) throws ResourceNotFoundException {
        logger.info("GET specific superhero");
        Optional<Superhero> superhero = Optional.ofNullable(superheroRepo.findByName(name));
    	if ( superhero.isPresent() ) {
    		response.setStatus(HttpServletResponse.SC_OK);
    		return superhero.get();
    	}
       	throw new ResourceNotFoundException("No superhero found with name: " + name);
    }
}
