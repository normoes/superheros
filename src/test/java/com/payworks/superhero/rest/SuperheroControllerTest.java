package com.payworks.superhero.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payworks.superhero.SuperheroApplication;
import com.payworks.superhero.helpers.JsonFunctions;
import com.payworks.superhero.model.Superhero;
import com.payworks.superhero.repo.SuperheroRepo;


/**
 * <h1> SuperheroControllerTest </h1>
 * Test the {@code Superhero} REST controller.
 * 
 * <p>
 * Since version 0.0.2:
 * Added test case for empty Date in JSON string.
 * <p>
 * Since version 0.0.3:
 * Added test cases to check {@code firstAppearance} ({@code Date}).
 * <p>
 * Since version 0.0.4:
 * Replaced {@code Date} by {@code LocalDate}.
 * 
 * @author Norman Moeschter-Schenck
 * @version 0.0.4
 * @since 2018-04-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SuperheroApplication.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude={MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, MongoRepositoriesAutoConfiguration.class})
public class SuperheroControllerTest {
	public static final Logger logger = LoggerFactory.getLogger(SuperheroControllerTest.class);

	private static final String url = "/superheros";
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	@Mock
	private SuperheroRepo superheroRepo;

	@Autowired
	@InjectMocks
	private SuperheroController superheroController;
	
	@Autowired
	private WebApplicationContext context;

	private MockMvc mockMvc;
	
	// superhero instances
	private Superhero batman;
	private Superhero robin;
	
	@Before
	public void setUp() {
		// inject mocks
	    MockitoAnnotations.initMocks(this);

	    // initialise mockMvc
	    mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.build();
	    
	    // initialise superhero instances
	    this.batman=new Superhero();
	    this.batman.setName("batman");
	    this.batman.setPublisher("DC");
	    this.batman.setPseudonym("Bruce Wayne");
	    this.batman.setFirstAppearance(LocalDate.now());
	    this.batman.setPowers(Arrays.asList("some great thing","another great thing, nobody ever thought of"));
	    this.batman.setAllies(Arrays.asList("robin1","robin2", "robin3"));
	    
	    this.robin=new Superhero();
	    this.robin.setName("robin1");
	    this.robin.setPublisher("DC");
	    this.robin.setPseudonym("dick grayson");
	    this.robin.setFirstAppearance(LocalDate.now());
	    this.robin.setPowers(Arrays.asList("great listener"));
	}
	
	/**
	 * Get all superheros.
	 * 
	 * Mocks a list containing 2 superheros.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void getAllSuperheros() throws Exception {
		Mockito.when(superheroRepo.findAll()).thenReturn(Arrays.asList(this.batman, this.robin));
		
		MvcResult result = mockMvc.perform(
		        get(SuperheroControllerTest.url)
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON_VALUE)
		        )
				.andExpect(status().isOk())
				.andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		List<Superhero> superheros = JsonFunctions.getListFromJsonString(jsonResult, Superhero.class);
		assertEquals(2, superheros.size());
		assertEquals(true, superheros.contains(this.batman));
		assertEquals(true, superheros.contains(this.robin));
	}
	
	/**
	 * Get all superheros.
	 * 
	 * Mocks an empty database reply - no superheros found at all.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void getAllSuperherosNoHero()  throws Exception {
		Mockito.when(superheroRepo.findAll()).thenReturn(Collections.emptyList());
		
		MvcResult result = mockMvc.perform(
		        get(SuperheroControllerTest.url)
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON_VALUE)
		        )
				.andExpect(status().isOk())
				.andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		assertEquals("[]", jsonResult);
	}
	
	/**
	 * Get a specific superhero.
	 * 
	 * Mocks robin and checks for equality.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void getSuperhero()  throws Exception {
		Mockito.when(superheroRepo.findByName(any(String.class))).thenReturn(this.robin);
		
		MvcResult result = mockMvc.perform(
		        get(SuperheroControllerTest.url + "/robin")
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON_VALUE)
		        )
				.andExpect(status().isOk())
				.andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		Superhero superhero = JsonFunctions.getItemFromJson(jsonResult, Superhero.class);
		assertEquals(this.robin, superhero);
	}
	
	/**
	 * Get a specific superhero.
	 * 
	 * Mocks an empty database reply - no superhero found.
	 * Expects {@code ResourceNotFoundException} in the response.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void getSuperheroNoHero()  throws Exception {
		Mockito.when(superheroRepo.findByName(any(String.class))).thenReturn(null);
		
		mockMvc.perform(
		        get(SuperheroControllerTest.url + "/robin")
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON_VALUE)
		        )
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("exception").value(ResourceNotFoundException.class.getCanonicalName()))
				.andReturn();		
	}
	
	/**
	 * Get a specific superhero who has got no allies.
	 * 
	 * Mocks robin to be returned from the database.
	 * 
	 * Access to {@code allies( = null)} throws a {@code NullPointerException}.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test(expected=NullPointerException.class)
	public void getSuperheroNoAllies()  throws Exception {
		Mockito.when(superheroRepo.findByName(any(String.class))).thenReturn(this.robin);
		
		MvcResult result = mockMvc.perform(
		        get(SuperheroControllerTest.url + "/robin")
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON_VALUE)
		        )
				.andExpect(status().isOk())
				.andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		Superhero superhero = JsonFunctions.getItemFromJson(jsonResult, Superhero.class);
		assertEquals(this.robin, superhero);
		assertEquals(null, this.robin.getAllies());
		// throws NullPointerException
		assertEquals(0, this.robin.getAllies().size());
	}
	
	/**
	 * Check the correct Date format of 
	 * a superhero's {@code firstAppearance} attribute.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void getSuperheroDateFormat()  throws Exception {
		Mockito.when(superheroRepo.findByName(any(String.class))).thenReturn(this.batman);
		
		MvcResult result = mockMvc.perform(
		        get(SuperheroControllerTest.url + "/robin")
		            .contentType(MediaType.APPLICATION_JSON)
		            .accept(MediaType.APPLICATION_JSON_VALUE)
		        )
				.andExpect(status().isOk())
				.andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		Superhero superhero = JsonFunctions.getItemFromJson(jsonResult, Superhero.class);
		assertEquals(this.batman, superhero);
		// Get firstAppearance from json result string.
		JSONObject obj = new JSONObject(jsonResult);
		String jsonDate = (String) obj.get("firstAppearance");
		// Get year, month and date from batman's firstAppearance
		Calendar calendar = new GregorianCalendar();
		Date date = Date.from(this.batman.getFirstAppearance().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		calendar.setTime(date);
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		// force 2-digit representation of month and day
		DecimalFormat dFormat = new DecimalFormat("00");
		String month = dFormat.format(Double.valueOf(calendar.get(Calendar.MONTH)+ 1));  // JANNUARY = 0
		String day = dFormat.format(Double.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		// Format should be: 'YYYY-MM-DD'.
		assertTrue(jsonDate.equals(year + "-" + month + "-" + day));
	}
	
	/**
	 * Date {@code firstAppearance} may not be {@code null}.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void getSuperheroDateNotNull()  throws Exception {
		Mockito.when(superheroRepo.findByName(any(String.class))).thenReturn(null);
		
		this.batman.setFirstAppearance(null);
		
		logger.error(objectMapper.writeValueAsString(this.batman));
		
		mockMvc.perform(
		        post(SuperheroControllerTest.url)
			        .contentType(MediaType.APPLICATION_JSON)
		            .content(objectMapper.writeValueAsString(this.batman))
		        )
				.andExpect(status().isBadRequest())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("exception").value(InvalidIdException.class.getCanonicalName()))
				;
	}	
	
	/**
	 * Date {@code firstAppearance} may not be empty.
	 * 
	 * @throws Exception if anything goes wrong
	 */
	@Test
	public void getSuperheroDateNotEmpty()  throws Exception {
		Mockito.when(superheroRepo.findByName(any(String.class))).thenReturn(null);
		
		String content = "{\"name\":\"batman\",\"pseudonym\":\"Bruce Wayne\",\"publisher\":\"DC\",\"powers\":[\"some great thing\",\"another great thing, nobody ever thought of\"],\"allies\":[\"robin1\",\"robin2\",\"robin3\"],\"firstAppearance\":\"\"}";
		
		logger.error(objectMapper.writeValueAsString(this.batman));
		
		MvcResult result = mockMvc.perform(
		        post(SuperheroControllerTest.url)
			        .contentType(MediaType.APPLICATION_JSON)
		            .content(content)
		        )
				.andExpect(status().isBadRequest())
				.andDo(print())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("exception").value(NameAlreadyTakenException.class.getCanonicalName()))				
				.andReturn();
	}
}
