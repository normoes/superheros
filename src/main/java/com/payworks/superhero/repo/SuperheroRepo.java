package com.payworks.superhero.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.payworks.superhero.model.Superhero;

/**
 * <h1> SuperheroRepo </h1>
 * Wraps access to mongo db. 
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-15
 *
 */
public interface SuperheroRepo extends MongoRepository<Superhero, String> {
	/**
	 * There must be no duplicate {@link Superhero}, so
	 * getting superheros in the database are checked even for
	 * case-insensitivity.
	 *  
	 * @param name Name of the superhero to search for.
	 * @return {@link Superhero} instance. 
	 */
	@Query(value="{ 'name': {$regex : ?0, $options: 'i'} }")
    Superhero findByName(String name);
}
