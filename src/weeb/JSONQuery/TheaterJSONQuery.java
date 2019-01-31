package weeb.JSONQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weeb.data.APIKeys;
import weeb.data.Theater;
import weeb.power.JSONObjectReader;
import weeb.power.JSONReader;

public class TheaterJSONQuery {

	private StringBuilder urlPath;
	private final String googlePlacesSearch = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=";
	private final String googlePlaceID = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";
	private Set<String> theaterNames;
	
	public TheaterJSONQuery() {
		theaterNames = new HashSet<>();
	}
	
	public Set<String> addTheaterName(JSONObject movie) {
		try {
			JSONArray showtimes = movie.getJSONArray("showtimes");
			for (int i = 0; i < showtimes.length(); i++) {
				JSONObject theater = showtimes.getJSONObject(i).getJSONObject("theatre");
				theaterNames.add(theater.getString("name"));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return theaterNames;
	}
	
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
	
	public List<String> queryTheaterIds(String name, Double lat, Double lng, int radius) {
		
		String nameConverted = name.replaceAll(" ", "%20");
		List<String> theaterIds = new ArrayList<>();
		
		StringBuilder url = new StringBuilder(googlePlacesSearch);
		url.append(nameConverted);
		url.append("&inputtype=textquery");
		url.append("&fields=place_id,photos,formatted_address,name,rating,opening_hours,geometry");
		url.append("&locationbias=circle:");
		url.append(radius * 1000);
		url.append("@");
		url.append(lat + ",");
		url.append(lng);
		url.append("&key=");
		url.append(APIKeys.getGooglePlacesAPIKey());
		
		try {
			JSONArray candidates = new JSONObjectReader().readJsonObjectFromUrl(url.toString()).getJSONArray("candidates");
			for (int i = 0; i < candidates.length(); i++) {
				JSONObject candidate = candidates.getJSONObject(i);
				if(candidate.getString("name").equals(name)) {
					theaterIds.add(candidate.getString("place_id"));
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return theaterIds;
	}
	
	public Map<String, JSONObject> userLocationSearchResults(String address) {
		
		Map<String, JSONObject> results = new HashMap<String, JSONObject>();
		String addressConverted = address.replaceAll(" ", "%20");
		
		StringBuilder url = new StringBuilder(googlePlacesSearch);
		url.append(addressConverted);
		url.append("&inputtype=textquery");
		url.append("&fields=place_id,photos,formatted_address,name,rating,opening_hours,geometry");
		url.append("&key=");
		url.append(APIKeys.getGooglePlacesAPIKey());
		
		try {
			JSONArray candidates = new JSONObjectReader().readJsonObjectFromUrl(url.toString()).getJSONArray("candidates");
			
			for (int i = 0; i < candidates.length(); i++) {
				JSONObject candidate = candidates.getJSONObject(i);
				results.put(candidate.getString("formatted_address"), candidate);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}

	public Set<String> getTheaterNames() {
		return theaterNames;
	}

}
