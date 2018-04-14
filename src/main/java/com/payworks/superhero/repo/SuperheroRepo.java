package com.payworks.superhero.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.payworks.superhero.model.Superhero;

public interface SuperheroRepo extends MongoRepository<Superhero, String> {
	/**
	 * There must be no duplicate {@code Superhero}, so
	 * getting superheros in the database are checked even for
	 * case-insensitivity.
	 *  
	 * @param name Name of the superhero to search for.
	 * @return {@code Superhero} instance. 
	 */
	@Query(value="{ 'name': {$regex : ?0, $options: 'i'} }")
    Superhero findByName(String name);
}
