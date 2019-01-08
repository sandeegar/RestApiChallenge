package common.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class placeOrder {
	
	public static JSONObject placeOrderJson(ArrayList<Double> lattitude, ArrayList<Double> longitude) throws Exception {
		
						
			JSONObject stopsParams = new JSONObject();
     		JSONArray stopArr=new JSONArray();
     		
			for (int stop_index=0; stop_index < lattitude.size(); stop_index++)		
			{
				stopsParams = new JSONObject();
				stopsParams.put("lat", lattitude.get(stop_index));
				stopsParams.put("lng", longitude.get(stop_index));
				stopArr.put(stopsParams);
			}		
	    		  
			JSONObject requestParams = new JSONObject();
			requestParams.put("stops", stopArr);
						
		    return requestParams;
			
			
			
	}		
	
}	
