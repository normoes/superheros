package com.payworks.superhero.validation;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <h1> MyDateValidator </h1>
 * 
 * Actual Java.Date from JSON validator.
 * Expects {@code Date} and checks for {@code null}.
 * 
 * {@code @NotNull} only validates null Dates correctly.
 * Empty JSON string values are also handled here.
 * 
 * <p>
 * Since version 0.0.2:
 * Replaced {@code Date} by {@code LocalDate}.
 * 
 * @author Norman Moeschter-SChenck
 * @version 0.0.2
 * @since 2018-04-24
 *
 */
public class MyDateValidator implements ConstraintValidator<ValidDate, LocalDate> {
    @Override
    public void initialize(ValidDate constraint) {
   }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
    	// null and empty JSON string value results in value == null
    	if( Objects.isNull(value) ){
    		return false;
		}	
    	return true;
    }
}
