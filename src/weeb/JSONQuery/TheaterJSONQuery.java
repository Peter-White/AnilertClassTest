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
	private Map<Integer, Theater> theaterAddresses;
	
	public TheaterJSONQuery() {
		theaterAddresses = new HashMap<>();
	}

	public void addTheatersJSON(JSONObject movie, double lat, double lng, int radius) {
		try {
			JSONArray showtimes = movie.getJSONArray("showtimes");
			for (int i = 0; i < showtimes.length(); i++) {
				JSONObject theater = showtimes.getJSONObject(i).getJSONObject("theatre");
				if (!theaterAddresses.containsKey(theater.getInt("id"))) {
					String placeId = queryTheaterPlaceIds(theater.getString("name"), lat, lng, radius);
					int id = theater.getInt("id");
					if(placeId != null) {
						theaterAddresses.put(theater.getInt("id"), JSONObjectToTheater(id, getTheaterJSON(placeId)));
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
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

	private String queryTheaterPlaceIds(String name, Double lat, Double lng, int radius) {

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
		
		return theaterPlaceId;
	}

	public Map<String, JSONObject> userLocationSearchResults(String address) {

		Map<String, JSONObject> results = new HashMap<String, JSONObject>();
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
				results.put(candidate.getString("formatted_address"), candidate);
			}

		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return results;
	}
	
	private Theater JSONObjectToTheater(int id, JSONObject placeByID) {
		Theater theater = null;
		
		try {
			theater = new Theater();
			theater.setTheaterId(id);
			theater.setName(placeByID.getString("name"));
			theater.setAddress("formatted_address");
			theater.setLatitude(placeByID.getJSONObject("geometry").getJSONObject("location").getDouble("lat"));
			theater.setLongitude(placeByID.getJSONObject("geometry").getJSONObject("location").getDouble("lng"));
			theater.setPlace_id("place_id");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return theater;
	}

	public Map<Integer, Theater> getTheaterMap() {
		return theaterAddresses;
	}

}
