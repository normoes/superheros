package com.payworks.superhero.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.payworks.superhero.model.Superhero;

/**
 * <h1> JsonFunctionsTest </h1>
 * Test Json functionalities.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.1
 * @since 2018-04-16
 *
 */
@ContextConfiguration(classes = JsonFunctions.class)
public class JsonFunctionsTest {
	public static final Logger logger = LoggerFactory.getLogger(JsonFunctionsTest.class);
	
	// superhero instances
	private Superhero batman;
	
	@Before
	public void setUp() {
		// inject mocks
	    MockitoAnnotations.initMocks(this);
	    
	    // initialise superhero instances
	    this.batman=new Superhero();
	    this.batman.setName("batman");
	    this.batman.setPublisher("DC");
	    this.batman.setPseudonym("Bruce Wayne");
	    this.batman.setFirstAppearance(new Date());
	    this.batman.setPowers(Arrays.asList("some great thing","another great thing, nobody ever thought of"));
	    this.batman.setAllies(Arrays.asList("robin"));
	}
	
	@Test
	public void GetJsonFromObject() throws Exception {
		
		String jsonResult = JsonFunctions.getJsonFromObject(this.batman);		
		JSONObject obj = new JSONObject(jsonResult);
		
		// check simple string attributes
		assertTrue(obj.get("name").toString().equals(this.batman.getName()));
		assertTrue(obj.get("pseudonym").toString().equals(this.batman.getPseudonym()));
		assertTrue(obj.get("publisher").toString().equals(this.batman.getPublisher()));
		
		// check list containing powers
		JSONArray powersJsonArray = obj.getJSONArray("powers");		
		int len = powersJsonArray.length();
		// check length
		assertEquals(this.batman.getPowers().size(), len);
		// check exact appearance of every single powers
		for (int i=0;i<len;i++){ 
			assertTrue(this.batman.getPowers().contains(powersJsonArray.get(i).toString()));
		} 
	}

}
