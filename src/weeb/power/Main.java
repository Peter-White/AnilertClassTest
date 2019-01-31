package weeb.power;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

			double userLatitude = userLocation.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
			double userLongitude = userLocation.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
			
			int userChosenRadius = 20;
			
			Map<String, JSONObject> animes = new MovieJSONQuery().queryAnimeJSON(userLatitude, userLongitude, userChosenRadius);
			
			TheaterJSONQuery theaterJSONQuery = new TheaterJSONQuery();
			
			animes.forEach((key, value) -> {
				Movie movie = MovieQuery.addMovieToDb(value);
				theaterJSONQuery.addTheaterName(value);
			});
			
			Set<String> theaters = theaterJSONQuery.getTheaterNames();
			
			theaters.forEach((name) -> System.out.println(name));
			
		} catch (JSONException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

}
