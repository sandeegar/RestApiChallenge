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

public class PutApiTakeOrder extends BaseClass{
	
	public PutApiTakeOrder() throws JXLException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	static Logger log = Logger.getLogger(PutApiTakeOrder.class);
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
	public void test_01_verify_take_order_with_specific_order_id() throws Exception {	
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
        
        // When
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+id+"/take");
        
        // Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        jsonObj = new JSONObject(response.asString());
        
        int fetchedId = (Integer) jsonObj.get("id");
        log.info("**************fetchedOrderId************\n"+id);
        assertThat(fetchedId,equalTo(id));
        
        String actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ONGOING"));
        
    }
	
	
	@Test(dataProvider = "invalidIds", dataProviderClass = InputDataProvider.class)
	public void test_02_verify_order_not_taken_with_invalid_order_id(String invalidId) throws Exception {	
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
        
        // When
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(path+"/"+invalidId+"/take");
        
        // Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));         
        
    }
	
	
	@Test(dataProvider = "invalidTakeEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_03_verify_order_not_taken_with_invalid_end_point(String invalidEndPoint) throws Exception {	
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
        
     // When
        RequestSpecification putRequest = RestAssured.given();
        response = putRequest.put(invalidEndPoint);
        
        // Then
        statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));          
        
     }
	
	@Test
	public void test_04_verify_order_not_taken_without_placing_it() throws Exception {	
		//Given
	    int id = (int) Math.random();
	    		
        // When
        RequestSpecification putRequest = RestAssured.given();
        Response response = putRequest.put(path+"/"+id+"/take");
        
        // Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));            
                
    }

}