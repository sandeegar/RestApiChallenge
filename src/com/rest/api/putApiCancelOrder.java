package com.rest.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Random;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.Test;

import common.utils.inputDataProvider;
import common.utils.placeOrder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class putApiCancelOrder extends baseClass{
	
	static Logger log = Logger.getLogger(getApi.class);
	
	@Test
	public void test_01_verify_cancel_an_assigned_order_with_specific_order_id() throws Exception {	
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
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (int) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.get(path+"/"+id);
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        
        jsonObj = new JSONObject(response.asString());       
        String actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ASSIGNING"));
        
        //When
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/cancel");    
        
        // Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));
        
        jsonObj = new JSONObject(response.asString());
        int completeOrderId = (int) jsonObj.get("id");
        log.info("**************CancelledOrderId************\n"+id);
        assertThat(completeOrderId,equalTo(id));
        
        actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("CANCELLED"));
        
    }
	
	@Test
	public void test_02_verify_cancel_an_ongoing_order_with_specific_order_id() throws Exception {	
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
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (int) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.get(path+"/"+id);
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        jsonObj = new JSONObject(response.asString());       
        String actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ASSIGNING"));
        
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/take"); 
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));     
        jsonObj = new JSONObject(response.asString());       
        actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ONGOING"));
        
        // When
        putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/cancel"); 
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));
        
        // Then
        jsonObj = new JSONObject(response.asString());
        int completeOrderId = (int) jsonObj.get("id");
        log.info("**************CancelledOrderId************\n"+id);
        assertThat(completeOrderId,equalTo(id));
        
        actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("CANCELLED"));
        
    }
	
	@Test
	public void test_03_verify_order_not_cancelled_for_an_already_completed_order_with_specific_order_id() throws Exception {	
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
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (int) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.get(path+"/"+id);
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        jsonObj = new JSONObject(response.asString());       
        String actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ASSIGNING"));
        
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/take"); 
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));     
        jsonObj = new JSONObject(response.asString());       
        actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ONGOING"));
        
        putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/complete"); 
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));
        jsonObj = new JSONObject(response.asString());       
        actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("COMPLETED"));
        
        // When
        putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/cancel"); 
        
        //Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_UNPROCESSABLE_ENTITY));
                  
    }
	
	@Test
	public void test_04_verify_order_not_cancelled_an_already_cancelled_order_with_specific_order_id() throws Exception {	
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
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (int) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.get(path+"/"+id);
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        
        jsonObj = new JSONObject(response.asString());       
        String actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ASSIGNING"));
        
        //When
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/cancel"); 
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));        
        jsonObj = new JSONObject(response.asString());
        int completeOrderId = (int) jsonObj.get("id");
        log.info("**************CancelledOrderId************\n"+id);
        assertThat(completeOrderId,equalTo(id));        
        actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("CANCELLED"));
        
        putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/cancel"); 
                
        //Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_BAD_REQUEST));      
          
    }
	
	@Test(dataProvider = "invalidIds", dataProviderClass = inputDataProvider.class)
	public void test_05_verify_order_not_cancelled_with_invalid_order_id(String invalidId) throws Exception {	
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
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));              
        
        //When
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+invalidId+"/cancel");    
        
        // Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));      
        
    }
	
	
	@Test(dataProvider = "invalidCancelEndPoint", dataProviderClass = inputDataProvider.class)
	public void test_06_verify_order_not_cancelled_with_invalid_end_point(String invalidEndPoint) throws Exception {	
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
        httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));              
        
        //When
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(invalidEndPoint);    
        
        // Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));           
        
     }
	
	@Test
	public void test_07_verify_order_not_cancelled_without_placing_it() throws Exception {	
		log.setLevel(Level.TRACE);
		
		//Given
	    int id = (int) Math.random();
	    		
        // When
        RequestSpecification putRequest = RestAssured.given();
        Response response = putRequest.put(path+"/"+id+"/cancel");
        
        // Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));            
                
    }
	

}
