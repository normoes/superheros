package com.payworks.superhero.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * <h1> WebSecurityConfig </h1>
 * This class sets up spring security and configures it.
 * 
 * A user can login using Basic Authentication.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-14
 */

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) // enable method-level authorization (@PreAuthorize("expression"))
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
	
	private static final String ENTRY_POINT = "/**";
	private static final String USER = "user";
    private static final String PWD = "superSecretPasswd1";
    private static final String ROLE = "USER";  
    
    /**
     * Debugging/Testing PasswordEncoder.
     * 
     * Passwords are still kept in memory.
     * 
     * @return an insecure  PasswordEncoder.
     */
    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
    	return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
//    
//    /**
//     * real life (production) passwordEncoder
//     *
//     * @return secure PasswordEncoder
//     */
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
    
    /**
     * Configures the inMemoryAuthentication and adds a single user,
     * who then has the possibility to log in.
     * 
     * username: user
     * password: superSecretPasswd1
     * role: USER
     * 
     * @param auth injected AuthenticationManagerBuilder instance
     * @throws Exception if anything goes wrong
     * 
     * @author Norman Moeschter-Schenck
     * @version 0.0.1
     * @since 2018-04-14
     */
	 @Autowired
	 public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	 	 auth
	 		.inMemoryAuthentication()
	 			.withUser(USER).password(PWD).roles(ROLE);
	 	 logger.info("InMemory user added: " + USER);
	 }	 	
	 
	 /**
	  * This configuration describes the secured access to the superheros service. 
	  * 
	  * The access is restricted via Basic Authentication.
	  * 
	  * This should not be used in a browser context (no CSRF, no HTTP sessions), 
	  * only from cli (curl) or from within applications.
	  * 
	  * @author Norman Moeschter-Schenck
	  * @version 0.0.1
	  * @since 2018-04-14
	  *
	  */
	 @Configuration
	 @Order(1) // higher order = lower priority
	 public static class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter{
		 
	     @Override
	     protected void configure(HttpSecurity http) throws Exception {	     	
	         http
                 .antMatcher(ENTRY_POINT) 
	                 .authorizeRequests()
	                     .anyRequest().hasAnyRole(ROLE)
	                     .and()
                 .httpBasic() // enable Basic Authentication
                 .and()
                 .csrf().disable(); // no csrf when communicating directly with the REST API
	         // do not even obtain security context to check for logged in users
	         http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);	
	     }
	 }
}
