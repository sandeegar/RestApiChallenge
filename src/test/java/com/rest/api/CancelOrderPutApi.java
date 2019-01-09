package com.rest.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.testng.annotations.Test;

import common.utils.InputDataProvider;
import common.utils.JsonUtil;
import common.utils.Utility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class CancelOrderPutApi extends BaseClass{
	
	public CancelOrderPutApi() throws Exception {
		super();
	}
	
	@Test
	public void test_01_verify_cancel_an_assigned_order_with_specific_order_id() throws Exception {
		try {
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.get(path+"/"+id);
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        
	        //Given
	        JSONObject jsonObj = new JSONObject(response.asString());       
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
	        int completeOrderId = (Integer) jsonObj.get("id");
	        log.info("**************CancelledOrderId************\n"+id);
	        assertThat(completeOrderId,equalTo(id));
	        
	        actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("CANCELLED"));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
    }
	
	@Test
	public void test_02_verify_cancel_an_ongoing_order_with_specific_order_id() throws Exception {
		try {			
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.get(path+"/"+id);
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        JSONObject jsonObj = new JSONObject(response.asString());       
	        String actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("ASSIGNING"));
	        
	        //Given
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
	        int completeOrderId = (Integer) jsonObj.get("id");
	        log.info("**************CancelledOrderId************\n"+id);
	        assertThat(completeOrderId,equalTo(id));
	        
	        actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("CANCELLED"));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
    }
	
	@Test
	public void test_03_verify_order_not_cancelled_for_an_already_completed_order_with_specific_order_id() throws Exception {	
		try {			
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.get(path+"/"+id);
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        JSONObject jsonObj = new JSONObject(response.asString());       
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
	        
	        //Given
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
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
                  
    }
	
	@Test
	public void test_04_verify_order_not_cancelled_an_already_cancelled_order_with_specific_order_id() throws Exception {	
		try {			
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
			
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.get(path+"/"+id);
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        
	        //Given
	        JSONObject jsonObj = new JSONObject(response.asString());       
	        String actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("ASSIGNING"));
	        
	        //When
	        RequestSpecification putRequest = RestAssured.given();
	        response = putRequest.put(path+"/"+id+"/cancel"); 
	        statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));        
	        jsonObj = new JSONObject(response.asString());
	        int completeOrderId = (Integer) jsonObj.get("id");
	        log.info("**************CancelledOrderId************\n"+id);
	        assertThat(completeOrderId,equalTo(id));        
	        actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+actualStatus);
	        assertThat(actualStatus,equalTo("CANCELLED"));
	        
	        putRequest = RestAssured.given();
	        response = putRequest.put(path+"/"+id+"/cancel"); 
	                
	        //Then
	        statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_UNPROCESSABLE_ENTITY));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
          
    }
	
	@Test(dataProvider = "invalidIds", dataProviderClass = InputDataProvider.class)
	public void test_05_verify_order_not_cancelled_with_invalid_order_id(String invalidId) throws Exception {
		try {
		    //When
	        RequestSpecification putRequest = RestAssured.given();
	        Response response = putRequest.put(path+"/"+invalidId+"/cancel");    
	        
	        // Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));     
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
    }
	
	
	@Test(dataProvider = "invalidCancelEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_06_verify_order_not_cancelled_with_invalid_end_point(String invalidEndPoint) throws Exception {
		try {
			//When
	        RequestSpecification putRequest = RestAssured.given();
	        Response response = putRequest.put(invalidEndPoint);    
	        
	        // Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));    
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
     }
	
	@Test
	public void test_07_verify_order_not_cancelled_without_placing_it() throws Exception {	
		try {
			//Given
		    int id = (int) Math.random();
		    		
	        // When
	        RequestSpecification putRequest = RestAssured.given();
	        Response response = putRequest.put(path+"/"+id+"/cancel");
	        
	        // Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));   
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
                
    }
	

}
