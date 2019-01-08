package common.utils;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class placeOrderAtSpecificTime {
	
	public static JSONObject placeOrderJsonAtSpecificTime(ArrayList<Double> lattitude, ArrayList<Double> longitude, String timeStamp) throws Exception {
		
						
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
			requestParams.put("orderAt", timeStamp);
						
		    return requestParams;
			
			
			
	}		
	
}	
