package weeb.JSONQuery;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weeb.data.APIKeys;
import weeb.data.Theater;
import weeb.power.JSONObjectReader;
import weeb.power.JSONReader;

public class TheaterJSONQuery {

	private StringBuilder urlPath;
	private final String googlePlacesStart = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?";
	private final String googlePlaceID = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
	
	public Theater createTheaterFromJSON(String id) {
		
		Theater theater = null;
		
		StringBuilder url = new StringBuilder(googlePlaceID);
		url.append(id);
		url.append("&fields=address_component,adr_address,alt_id,formatted_address,geometry,"
				+ "icon,id,name,permanently_closed,photo,place_id,plus_code,scope,type,url,utc_offset,vicinity");
		url.append("&key=");
		url.append(APIKeys.getGooglePlacesAPIKey());
		
		try {
			JSONObject result = new JSONObjectReader().readJsonObjectFromUrl(url.toString()).getJSONObject("result");
			
			theater = new Theater();
			theater.setTheaterId(result.getString("place_id"));
			theater.setName(result.getString("name"));
			theater.setAddress(result.getString("formatted_address"));
			theater.setLatitude(result.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
			theater.setLongitude(result.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return theater;
	}
	
}
