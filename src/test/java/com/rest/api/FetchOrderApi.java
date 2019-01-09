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

public class FetchOrderApi extends BaseClass{
	
	public FetchOrderApi() throws Exception {
		super();				
	}	

	@Test
	public void test_01_verify_retrieve_valid_order_with_id() throws Exception {
		try {
			//Given
			JSONObject inputJson = JsonUtil.placeOrderJson(lattitude, longitude);	
			int id = Utility.placeOrderAndGetId(inputJson, httpRequest, path);
	        
	        // When
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.get(path+"/"+id);
	        
	        // Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_OK));            
	        JSONObject jsonObj = new JSONObject(response.asString());
	        int fetchedId = (Integer) jsonObj.get("id");
	        log.info("**************fetchedOrderId************\n"+id);
	        assertThat(fetchedId,equalTo(id));
	        
	        String actualStatus = (String) jsonObj.get("status");
	        log.info("**************StatusOfOrder************\n"+id);
	        assertThat(actualStatus,equalTo("ASSIGNING"));
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
    }
	
	@Test(dataProvider = "invalidIds", dataProviderClass = InputDataProvider.class)
	public void test_02_verify_order_not_retrieved_with_invalid_order_id(String invalidId) throws Exception {	
		try {
	        // When
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.get(path+"/"+invalidId);
	        
	        // Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));
		}
		catch (Exception e) {
			log.error("There is an Execption", e);
		}        
        
    }
	
	@Test(dataProvider = "invalidFetchEndPoint", dataProviderClass = InputDataProvider.class)
	public void test_03_verify_order_not_retrieved_with_invalid_end_point(String invalidEndPoint) throws Exception {
		try {
			// When
	        RequestSpecification getRequest = RestAssured.given();
	        Response response = getRequest.get(invalidEndPoint);
	        
	        // Then
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_NOT_FOUND));   
		}
		
		catch (Exception e) {
			log.error("There is an Execption", e);
		}
        
        
    }
}
	