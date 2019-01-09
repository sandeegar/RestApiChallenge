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

public class TakeOrderPutApi extends BaseClass{
	
	public TakeOrderPutApi() throws Exception {
		super();
	}
	
	@Test
	public void test_01_verify_take_order_with_specific_order_id() throws Exception {
		//Given
		JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);
		int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
        
        // When
        RequestSpecification putRequest = RestAssured.given();
        Response response = putRequest.put(path+"/"+id+"/take");
        
        // Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_OK));            
        JSONObject jsonObj = new JSONObject(response.asString());
        
        int fetchedId = (Integer) jsonObj.get("id");
        log.info("**************fetchedOrderId************\n"+id);
        assertThat(fetchedId,equalTo(id));
        
        String actualStatus = (String) jsonObj.get("status");
        log.info("**************StatusOfOrder************\n"+actualStatus);
        assertThat(actualStatus,equalTo("ONGOING"));
        
    }
	
	
	@Test(dataProvider = "invalidIds", dataProviderClass = InputDataProvider.class)
	public void test_02_verify_order_not_taken_with_invalid_order_id(String invalidId) throws Exception {	
		// When
        RequestSpecification putRequest = RestAssured.given();
        Response response = putRequest.put(path+"/"+invalidId+"/take");
        
        // Then
        int statusCode = response.getStatusCode();
        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));         
        
    }
	
	
	@Test(dataProvider = "invalidTakeEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_03_verify_order_not_taken_with_invalid_end_point(String invalidEndPoint) throws Exception {		     
        // When
        RequestSpecification putRequest = RestAssured.given();
        Response response = putRequest.put(invalidEndPoint);
        
        // Then
        int statusCode = response.getStatusCode();
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
