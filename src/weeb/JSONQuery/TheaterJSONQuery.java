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
import weeb.data.Movie;
import weeb.data.Theater;
import weeb.power.JSONObjectReader;
import weeb.power.JSONReader;

public class TheaterJSONQuery {

	private StringBuilder urlPath;
	private final String googlePlacesSearch = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input=";
	private final String googlePlaceID = "https://maps.googleapis.com/maps/api/place/details/json?placeid=";

	public JSONObject queryTheaterJSON(String name, Double lat, Double lng, double radius) {

		String theaterPlaceId = null;
		String nameConverted = name.replaceAll(" ", "%20");

		urlPath = new StringBuilder(googlePlacesSearch);
		urlPath.append(nameConverted);
		urlPath.append("&inputtype=textquery");
		urlPath.append("&fields=place_id,name,types");
		urlPath.append("&locationbias=circle:");
		urlPath.append(radius * 1000);
		urlPath.append("@");
		urlPath.append(lat + ",");
		urlPath.append(lng);
		urlPath.append("&key=");
		urlPath.append(APIKeys.getGooglePlacesAPIKey());

		try {
			JSONArray candidates = new JSONObjectReader().readJsonObjectFromUrl(urlPath.toString())
					.getJSONArray("candidates");
			for (int i = 0; i < candidates.length(); i++) {
				JSONObject candidate = candidates.getJSONObject(i);
				if (candidate.getString("name").equals(name) 
						|| candidate.get("types").toString().contains("movie_theater")
						|| candidate.get("types").toString().contains("museum")
						|| candidates.length() == 1) {
					
					theaterPlaceId = candidate.getString("place_id");
					break;
				}
			}
			
			if(theaterPlaceId == null) {
				theaterPlaceId = candidates.getJSONObject(0).getString("place_id");
			}
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}
		
		return getTheaterJSON(theaterPlaceId);
	}
	
	private JSONObject getTheaterJSON(String placeId) {

		JSONObject theaterJSON = null;

		urlPath = new StringBuilder(googlePlaceID);
		urlPath.append(placeId);
		urlPath.append("&fields=address_component,adr_address,alt_id,formatted_address,geometry,"
				+ "icon,id,name,permanently_closed,photo,place_id,plus_code,scope,type,url,utc_offset,vicinity");
		urlPath.append("&key=");
		urlPath.append(APIKeys.getGooglePlacesAPIKey());

		try {
			theaterJSON = new JSONObjectReader().readJsonObjectFromUrl(urlPath.toString()).getJSONObject("result");
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}

		return theaterJSON;
	}

	// This function allows users to enter their location and center their search radius, it doesn't really
	// have anything to do with theaters other than it calls from the same api that gets their locations
	public Map<Integer, JSONObject> userLocationSearchResults(String address) {

		Map<Integer, JSONObject> results = new HashMap<>();
		String addressConverted = address.replaceAll(" ", "%20");

		urlPath = new StringBuilder(googlePlacesSearch);
		urlPath.append(addressConverted);
		urlPath.append("&inputtype=textquery");
		urlPath.append("&fields=place_id,photos,formatted_address,name,rating,opening_hours,geometry");
		urlPath.append("&key=");
		urlPath.append(APIKeys.getGooglePlacesAPIKey());

		try {
			JSONArray candidates = new JSONObjectReader().readJsonObjectFromUrl(urlPath.toString())
					.getJSONArray("candidates");

			for (int i = 0; i < candidates.length(); i++) {
				JSONObject candidate = candidates.getJSONObject(i);
				results.put(i + 1, candidate);
			}

		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}

}
