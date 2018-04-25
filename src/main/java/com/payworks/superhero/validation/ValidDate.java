package com.payworks.superhero.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * <h1> ValidDate </h1>
 * 
 * Annotation to validate Java.Date
 * <ul>
 * 		<li>{@code null} is not valid</li>
 * 		<li>empty string value is not valid</li>
 * </ul>
 * 
 * @author Norman Moeschter-SChenck
 * @version 0.0.1
 * @since 2018-04-24
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MyDateValidator.class)
@Documented
public @interface ValidDate {

    String message() default "Date must not be null or empty";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
