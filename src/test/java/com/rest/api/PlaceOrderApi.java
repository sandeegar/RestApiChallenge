/**
 * @author sandeepgarg
 *
 */
package com.rest.api;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.response.Response;
import common.utils.*;


public class PlaceOrderApi extends BaseClass{
	
	public PlaceOrderApi() throws Exception {
		super();
	}

	@Test
	public void test_01_verify_placing_an_order() throws Exception {
		try {
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			log.info(id);
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
		
    }
	
	@Test
	public void test_02_verify_placing_an_order_at_specific_time() throws Exception {
		try {
			String timeStamp = Instant.now().plus(RandomUtils.nextInt(1, 60), ChronoUnit.MINUTES).toString();		
			JSONObject inputJson = JsonUtil.placeOrderJsonAtSpecificTime(lattitude, longitude, timeStamp);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			log.info(id);
		}
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
    }
	
	
	@Test(dataProvider = "invalidTimeFormats", dataProviderClass = InputDataProvider.class)
	public void test_03_verify_order_not_placed_with_invalid_time_format(String invalidTimeFormat) throws Exception {
		try {
			//Given
			JSONObject input_json = JsonUtil.placeOrderJsonAtSpecificTime(lattitude, longitude, invalidTimeFormat);
			log.info("******************Input JSON*****************\n"+input_json);		
			
		    //When
	        httpRequest.body(input_json.toString());
	        Response response = httpRequest.post(path);
	        
	        //Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
 
	}
				
	@Test(dataProvider = "invalidLattitude", dataProviderClass = InputDataProvider.class)
	public void test_04_verify_order_not_placed_with_incorrect_location_of_one_stop(String invalidValue) throws Exception {	
		try {
			//Given
			lattitude.add(invalidValue);
			longitude.add(invalidValue);
			
			JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
			log.info("******************Input JSON*****************\n"+input_json);		
			
	        //When
	        httpRequest.body(input_json.toString());
	        Response response = httpRequest.post(path);
	        
	        //Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));     
		}
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
     
    }
	
	
	@Test(dataProvider = "invalidLattitude", dataProviderClass = InputDataProvider.class)
	public void test_05_verify_order_not_placed_with_incorrect_locations_of_stops(String invalidValue) throws Exception {
		try {
			//Given
			for (int row=1;row<=2; row++)
			{	
				lattitude.add(invalidValue);
				longitude.add(invalidValue);
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
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
     
    }
	
	@Test(dataProvider = "invalidEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_06_verify_order_not_placed_with_incorrect_end_point(String invalidEndPoint) throws Exception {
		try {
			//Given
			JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
			log.info("******************Input JSON*****************\n"+input_json);		
			
	        //When
	        httpRequest.body(input_json.toString());
	        Response response = httpRequest.post(invalidEndPoint);
	        
	        //Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));   
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
     
    }
	
	@Test
	public void test_07_verify_order_not_placed_in_past_time() throws Exception {
		try {
			//Given
			String timeStamp = Instant.now().minus(RandomUtils.nextInt(1, 60), ChronoUnit.MINUTES).toString();
			JSONObject input_json = JsonUtil.placeOrderJsonAtSpecificTime(lattitude, longitude, timeStamp);
			log.info("******************Input JSON*****************\n"+input_json);		
			
	        //When
	        httpRequest.body(input_json.toString());
	        Response response = httpRequest.post(path);
	        
	        //Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
   
    }
	
	
	@Test
	public void test_08_verify_order_not_placed_with_identical_two_stops() throws Exception {
		try {
			//Given
			lattitude.add(getValue(1, 1));
			longitude.add(getValue(2, 1));
			
			lattitude.add(getValue(1, 1));
			longitude.add(getValue(2, 1));
			
			JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
			log.info("******************Input JSON*****************\n"+input_json);		
			
	        //When
	        httpRequest.body(input_json.toString());
	        Response response = httpRequest.post(path);
	        
	        //Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
     
    }
	
}
		

