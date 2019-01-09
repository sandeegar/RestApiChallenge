package com.rest.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpStatus;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import common.utils.InputDataProvider;
import common.utils.JsonUtil;
import common.utils.Utility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jxl.JXLException;

public class CompleteOrderPutApi extends BaseClass{
	
	public CompleteOrderPutApi() throws Exception {
		super();
	}
	
	@Test
	public void test_01_verify_complete_an_ongoing_order_with_specific_order_id() throws Exception {	
		try {
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.put(path+"/"+id+"/take");
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        
	        //Given
	        JSONObject jsonObj = new JSONObject(response.asString());       
	        String actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("ONGOING"));
	        
	        //When
	        RequestSpecification putRequest = RestAssured.given();
	        response = putRequest.put(path+"/"+id+"/complete");    
	        
	        // Then
	        statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));
	        
	        jsonObj = new JSONObject(response.asString());
	        int completeOrderId = (Integer) jsonObj.get("id");
	        log.info("**************CompletedOrderId************\n"+id);
	        assertThat(completeOrderId,equalTo(id));
	        
	        actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("COMPLETED"));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
    }
	
	
	@Test(dataProvider = "invalidIds", dataProviderClass = InputDataProvider.class)
	public void test_02_verify_order_not_completed_with_invalid_order_id(String invalidId) throws Exception {	
		try {
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.put(path+"/"+id+"/take");
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        
	        //Given
	        JSONObject jsonObj = new JSONObject(response.asString());       
	        String actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("ONGOING"));
	        
	        //When
	        RequestSpecification putRequest = RestAssured.given();
	        response = putRequest.put(path+"/"+invalidId+"/complete");    
	        
	        // Then
	        statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));   
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
    }
	
	
	@Test(dataProvider = "invalidCompleteEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_03_verify_order_not_completed_with_invalid_end_point(String invalidEndPoint) throws Exception {
		try {
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.put(path+"/"+id+"/take");
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        
	        //Given
	        JSONObject jsonObj = new JSONObject(response.asString());       
	        String actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("ONGOING"));
	        
	        //When
	        RequestSpecification putRequest = RestAssured.given();
	        response = putRequest.put(invalidEndPoint);    
	        
	        // Then
	        statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));           
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
     }
	
	@Test
	public void test_04_verify_order_not_completed_without_placing_it() throws Exception {
		try {
			//Given
		    int id = (int) Math.random();
		    		
	        // When
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.put(path+"/"+id+"/complete");
	        
	        // Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND)); 
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
                
    }
	
	@Test
	public void test_05_verify_order_not_completed_for_an_already_completed_order_with_specific_order_id() throws Exception {
		try {
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.put(path+"/"+id+"/take");
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        
	        //Given
	        JSONObject jsonObj = new JSONObject(response.asString());       
	        String actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("ONGOING"));
	        
	        //When
	        RequestSpecification putRequest = RestAssured.given();
	        response = putRequest.put(path+"/"+id+"/complete");         
	        statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));
	        
	        putRequest = RestAssured.given();
	        response = putRequest.put(path+"/"+id+"/complete"); 
	        
	        // Then
	        statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_UNPROCESSABLE_ENTITY));    
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
    }

}
