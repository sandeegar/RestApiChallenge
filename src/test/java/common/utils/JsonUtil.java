package common.utils;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {
	
	
	private static JSONObject stopsParams = new JSONObject();
	private static JSONArray stopArr=new JSONArray();
		
		
	public static JSONObject placeOrderJson(ArrayList<Double> lattitude, ArrayList<Double> longitude) throws Exception {		
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
	
	public static JSONObject placeOrderJsonAtSpecificTime(ArrayList<Double> lattitude, ArrayList<Double> longitude, String timeStamp) throws Exception {			
		for (int stop_index=0; stop_index < lattitude.size(); stop_index++)		
		{
			stopsParams = new JSONObject();
			stopsParams.put("lat", lattitude.get(stop_index));
			stopsParams.put("lng", longitude.get(stop_index));
			stopArr.put(stopsParams);
		}		
    		  
		JSONObject requestParams = new JSONObject();
		requestParams.put("stops", stopArr);
		requestParams.put("orderAt", timeStamp);
					
	    return requestParams;
		
		
		
       }

}
