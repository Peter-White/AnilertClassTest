package weeb.power;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import org.json.JSONException;
import org.json.JSONObject;

import weeb.DBQuery.MovieQuery;
import weeb.DBQuery.TheaterQuery;
import weeb.JSONQuery.MovieJSONQuery;
import weeb.JSONQuery.TheaterJSONQuery;
import weeb.data.Movie;
import weeb.data.Showtime;
import weeb.data.Theater;

public class Main {
	
	public static Map<String, Theater> theatersINDB = TheaterQuery.queryAllTheaters();
	public static Map<String, Movie> moviesINDB = MovieQuery.queryAllMovies();
	
	public static void main(String[] args) {
		
		Map<String, JSONObject> userResults = new TheaterJSONQuery().userLocationSearchResults("Boston");
		
		userResults.forEach((key, value) -> System.out.println(key + ": " + value));
		
		JSONObject userLocation = userResults.get("Boston, MA, USA");
		
		try {
			
			System.out.println(userLocation.getJSONObject("geometry").getJSONObject("location"));
//			double userLatitude = userLocation.getJSONObject("geometry").getJSONObject("viewport").getJSONObject("location").getDouble("lat");
//			double userLongitude = userLocation.getJSONObject("geometry").getJSONObject("viewport").getJSONObject("location").getDouble("lng");
//			
//			System.out.println(userLatitude + "," + userLongitude);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int userChosenRadius = 20;
		
		
//		try {
//			Map<String, JSONObject> animes = new MovieJSONQuery().queryAnimeJSON(42.3381437, -71.0475773, 20);
//		} catch (IOException | JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		List<String> ids = new TheaterJSONQuery().queryTheaterIds("ShowPlace ICON Theatres", 42.3381437, -71.0475773, 20);
		
	}

}
