package weeb.power;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import weeb.JSONQuery.JSONToSQL;
import weeb.JSONQuery.TheaterJSONQuery;

public class Main {
	
	
	public static void main(String[] args) {
		
		Map<Integer, JSONObject> placeCandidates = null;
		
		Map<String, JSONObject> userResults = new TheaterJSONQuery().userLocationSearchResults("Boston");
		
		JSONObject userLocation = userResults.get("Boston, MA, USA");

		try {
			double userLatitude = userLocation.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			double userLongitude = userLocation.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			
			int userChosenRadius = 10;
			
			JSONToSQL jsonToSQL = new JSONToSQL();
			jsonToSQL.updateMovieTableByUserInput(userLatitude, userLongitude, userChosenRadius);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
