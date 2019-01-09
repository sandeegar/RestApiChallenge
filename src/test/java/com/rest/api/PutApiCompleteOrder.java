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
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import jxl.JXLException;

public class PutApiCompleteOrder extends BaseClass{
	
	public PutApiCompleteOrder() throws JXLException, IOException {
		super();
	}

	static Logger log = Logger.getLogger(PutApiCompleteOrder.class);
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
	public void test_01_verify_complete_an_ongoing_order_with_specific_order_id() throws Exception {	
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (Integer) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.put(path+"/"+id+"/take");
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        
        jsonObj = new JSONObject(response.asString());       
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
	
	
	@Test(dataProvider = "invalidIds", dataProviderClass = InputDataProvider.class)
	public void test_02_verify_order_not_completed_with_invalid_order_id(String invalidId) throws Exception {	
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (Integer) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.put(path+"/"+id+"/take");
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        
        jsonObj = new JSONObject(response.asString());       
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
	
	
	@Test(dataProvider = "invalidCompleteEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_03_verify_order_not_completed_with_invalid_end_point(String invalidEndPoint) throws Exception {	
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (Integer) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.put(path+"/"+id+"/take");
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        
        jsonObj = new JSONObject(response.asString());       
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
	
	@Test
	public void test_04_verify_order_not_completed_without_placing_it() throws Exception {	
		//Given
	    int id = (int) Math.random();
	    		
        // When
        RequestSpecification getRequest = RestAssured.given();
        Response response = getRequest.put(path+"/"+id+"/complete");
        
        // Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));            
                
    }
	
	@Test
	public void test_05_verify_order_not_completed_for_an_already_completed_order_with_specific_order_id() throws Exception {	
		JSONObject input_json = JsonUtil.placeOrderJson(lattitude, longitude);
		log.info("******************Input JSON*****************\n"+input_json);		
		
		// Given 
	    httpRequest.body(input_json.toString());
        Response response = httpRequest.post(path);
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_CREATED));        
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int id = (Integer) jsonObj.get("id");
        log.info("**************createdOrderId************\n"+id);
        RequestSpecification getRequest = RestAssured.given();
        response = getRequest.put(path+"/"+id+"/take");
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        
        jsonObj = new JSONObject(response.asString());       
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

}