/**
 * @author sandeepgarg
 *
 */
package com.rest.api;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.Test;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import common.utils.*;


public class postApi extends baseClass{
	
	static Logger log = Logger.getLogger(postApi.class);
	
	
		
	@Test
	public void test_01_verify_placing_an_order() throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
		
		
		for (int row=1;row<=3; row++)
		{
			lattitude.add(Double.valueOf(baseClass.getValue(1, row)));
			longitude.add(Double.valueOf(baseClass.getValue(2, row)));
		}
		JSONObject input_json = placeOrder.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));
            
        JSONObject jsonObj = new JSONObject(response.asString());
        int id = (int) jsonObj.get("id");
        log.info(id);
    }
	
	@Test
	public void test_02_verify_placing_an_order_at_specific_time() throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
		
		String timeStamp = Instant.now().plus(25, ChronoUnit.MINUTES).toString();
		for (int row=1;row<=3; row++)
		{
			lattitude.add(Double.valueOf(baseClass.getValue(1, row)));
			longitude.add(Double.valueOf(baseClass.getValue(2, row)));
		}
		JSONObject input_json = placeOrderAtSpecificTime.placeOrderJsonAtSpecificTime(lattitude, longitude, timeStamp);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));
            
        JSONObject jsonObj = new JSONObject(response.asString());
        int id = (int) jsonObj.get("id");
        log.info(id);
    }
	
	
	@Test(dataProvider = "invalidTimeFormats", dataProviderClass = inputDataProvider.class)
	public void test_03_verify_order_not_placed_with_invalid_time_format(String invalidTimeFormat) throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
				
			
		for (int row=1;row<=3; row++)
		{
			lattitude.add(Double.valueOf(baseClass.getValue(1, row)));
			longitude.add(Double.valueOf(baseClass.getValue(2, row)));
		}
		
		
		JSONObject input_json = placeOrderAtSpecificTime.placeOrderJsonAtSpecificTime(lattitude, longitude, invalidTimeFormat);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
 
	}
				
	@Test(dataProvider = "invalidLattitude", dataProviderClass = inputDataProvider.class)
	public void test_04_verify_order_not_placed_with_incorrect_location_of_one_stop(String invalidValue) throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
				
		
		for (int row=1;row<=3; row++)
		{	
			if (row == 1)
			{	
				lattitude.add(Double.valueOf(invalidValue));
				longitude.add(Double.valueOf(invalidValue));
			}
			else {
			lattitude.add(Double.valueOf(baseClass.getValue(1, row)));
			longitude.add(Double.valueOf(baseClass.getValue(2, row)));
			}
		}
		JSONObject input_json = placeOrder.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));            
     
    }
	
	
	@Test(dataProvider = "invalidLattitude", dataProviderClass = inputDataProvider.class)
	public void test_05_verify_order_not_placed_with_incorrect_locations_of_all_stops(String invalidValue) throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
				
		
		for (int row=1;row<=2; row++)
		{	
			lattitude.add(Double.valueOf(invalidValue));
			longitude.add(Double.valueOf(invalidValue));
		}
		
		JSONObject input_json = placeOrder.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));            
     
    }
	
	@Test(dataProvider = "invalidEndPoint", dataProviderClass = inputDataProvider.class)
	public void test_06_verify_order_not_placed_with_incorrect_end_point(String invalidEndPoint) throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
				
		
		for (int row=1;row<=3; row++)
		{	
			lattitude.add(Double.valueOf(baseClass.getValue(1, row)));
			longitude.add(Double.valueOf(baseClass.getValue(2, row)));
		}
		
		JSONObject input_json = placeOrder.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(invalidEndPoint);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));            
     
    }
	
	@Test
	public void test_07_verify_order_not_placed_in_past_time() throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
		
		String timeStamp = Instant.now().minus(25, ChronoUnit.MINUTES).toString();
		for (int row=1;row<=3; row++)
		{
			lattitude.add(Double.valueOf(baseClass.getValue(1, row)));
			longitude.add(Double.valueOf(baseClass.getValue(2, row)));
		}
		JSONObject input_json = placeOrderAtSpecificTime.placeOrderJsonAtSpecificTime(lattitude, longitude, timeStamp);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
   
    }
	
	
	@Test
	public void test_08_verify_order_not_placed_with_identical_two_stops() throws Exception {	
		log.setLevel(Level.TRACE);
			
		ArrayList<Double> lattitude = new ArrayList<Double>();
		ArrayList<Double> longitude = new ArrayList<Double>();
		
		
		lattitude.add(Double.valueOf(baseClass.getValue(1, 1)));
		longitude.add(Double.valueOf(baseClass.getValue(2, 1)));
		
		lattitude.add(Double.valueOf(baseClass.getValue(1, 1)));
		longitude.add(Double.valueOf(baseClass.getValue(2, 1)));
		
		JSONObject input_json = placeOrder.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    RestAssured.baseURI = baseUri+":"+port;
        RequestSpecification httpRequest = RestAssured.given();
        httpRequest.header("Content-Type", "application/json");
      
        //When
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        
        //Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));
     
    }
	
}
		

