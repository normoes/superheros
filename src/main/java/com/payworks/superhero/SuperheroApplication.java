package com.payworks.superhero;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h1> Superheros </h1>
 * 
 * This is the backend for all the superheros out there.
 * Register now...
 * 
 * <p>
 * Since version 0.0.2:
 * added Basic Authentication
 * allies of a {@code Superhero} can be empty/null
 * 
 * <p>
 * Since version 0.0.3:
 * replaced {@code Date} by {@code LocalDate} in order to only allow valid months (1..12) and days
 * for {@code firstAppearance} in {@link Superhero}
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.3
 * @since 2018-04-14
 *
 */
@SpringBootApplication
public class SuperheroApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperheroApplication.class, args);
	}
}
