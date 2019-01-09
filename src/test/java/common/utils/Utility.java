package common.utils;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.json.JSONObject;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utility {
	
	public static int placeOrderAndGetId(JSONObject inputJson, RequestSpecification httpRequest, String path) {
	        httpRequest.body(inputJson.toString()).log().all();
	        Response response = httpRequest.post(path);
	        int statusCode = response.getStatusCode();
	        assertThat(statusCode, is(HttpStatus.SC_CREATED));            
	        JSONObject jsonObj = new JSONObject(response.asString());
	        int id = (Integer) jsonObj.get("id");
	        return id;
		}

}
