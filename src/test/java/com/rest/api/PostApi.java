/**
 * @author sandeepgarg
 *
 */
package com.rest.api;

import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jxl.JXLException;
import common.utils.*;


public class PostApi extends BaseClass{
	
	public PostApi() throws JXLException, IOException {
		super();
	}


	static Logger log = Logger.getLogger(PostApi.class);
	private ArrayList<Double> lattitude = new ArrayList<Double>();
	private ArrayList<Double> longitude = new ArrayList<Double>();	
	private RequestSpecification httpRequest = null;

	
	@BeforeTest
	public void initialize() throws NumberFormatException, Exception {
		log.setLevel(Level.TRACE);	
		for (int row=1;row<=3; row++)
		{
			lattitude.add(Double.valueOf(getValue(1, row)));
			longitude.add(Double.valueOf(getValue(2, row)));
		}
		
		RestAssured.baseURI = baseUri+":"+port;		
		httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
	
	}	
	
	@Test
	public void test_01_verify_placing_an_order() throws Exception {	
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);        
      
        //When
		httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
               
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));
            
        JSONObject jsonObj = new JSONObject(response.asString());
        int id = (Integer) jsonObj.get("id");
        log.info(id);
    }
	
	@Test
	public void test_02_verify_placing_an_order_at_specific_time() throws Exception {	
		String timeStamp = Instant.now().plus(25, ChronoUnit.MINUTES).toString();		
		JSONObject input_json = JsonUtil.placeOrderJsonAtSpecificTime(lattitude, longitude, timeStamp);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		//When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));
            
        JSONObject jsonObj = new JSONObject(response.asString());
        int id = (Integer) jsonObj.get("id");
        log.info(id);
    }
	
	
	@Test(dataProvider = "invalidTimeFormats", dataProviderClass = InputDataProvider.class)
	public void test_03_verify_order_not_placed_with_invalid_time_format(String invalidTimeFormat) throws Exception {	
		JSONObject input_json = JsonUtil.placeOrderJsonAtSpecificTime(lattitude, longitude, invalidTimeFormat);
		log.info("******************Input JSON*****************\n"+input_json);		
		
	    //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
 
	}
				
	@Test(dataProvider = "invalidLattitude", dataProviderClass = InputDataProvider.class)
	public void test_04_verify_order_not_placed_with_incorrect_location_of_one_stop(String invalidValue) throws Exception {	
		lattitude.add(Double.valueOf(invalidValue));
		longitude.add(Double.valueOf(invalidValue));
		
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));            
     
    }
	
	
	@Test(dataProvider = "invalidLattitude", dataProviderClass = InputDataProvider.class)
	public void test_05_verify_order_not_placed_with_incorrect_locations_of_stops(String invalidValue) throws Exception {
		for (int row=1;row<=2; row++)
		{	
			lattitude.add(Double.valueOf(invalidValue));
			longitude.add(Double.valueOf(invalidValue));
		}
		
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));            
     
    }
	
	@Test(dataProvider = "invalidEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_06_verify_order_not_placed_with_incorrect_end_point(String invalidEndPoint) throws Exception {	
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(invalidEndPoint);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));            
     
    }
	
	@Test
	public void test_07_verify_order_not_placed_in_past_time() throws Exception {	
		String timeStamp = Instant.now().minus(25, ChronoUnit.MINUTES).toString();
		JSONObject input_json = JsonUtil.placeOrderJsonAtSpecificTime(lattitude, longitude, timeStamp);
		log.info("******************Input JSON*****************\n"+input_json);		
		
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
   
    }
	
	
	@Test
	public void test_08_verify_order_not_placed_with_identical_two_stops() throws Exception {	
		lattitude.add(Double.valueOf(getValue(1, 1)));
		longitude.add(Double.valueOf(getValue(2, 1)));
		
		lattitude.add(Double.valueOf(getValue(1, 1)));
		longitude.add(Double.valueOf(getValue(2, 1)));
		
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
     
    }
	
}
		

