package weeb.JSONQuery;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weeb.DBQuery.MovieQuery;
import weeb.DBQuery.ShowtimeQuery;
import weeb.DBQuery.TheaterQuery;
import weeb.data.Movie;
import weeb.data.Showtime;
import weeb.data.Theater;

public class JSONToSQL {
	
	// This function triggers the update of the movie, theater, and showtime tables due to the design of the movie API
	public void updateMovieTableByUserInput(double lat, double lng, double rad) {
		
		Coordinates coordinates = new Coordinates(lat, lng, rad);
		
		try {
			Map<String, JSONObject> movies = new MovieJSONQuery().queryAnimeJSONByInput(
					coordinates.getLatitude(), 
					coordinates.getLongitude(), 
					coordinates.getRadius());
			movies.forEach((key, value) -> {
				
				
				
			});
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	// This function triggers the update of the movie, and showtime tables due to the design of the movie API
	public void updateMovieTableBySingleTheater(Double lat, Double lng) {
		Coordinates coordinates = new Coordinates(lat, lng, 0.05);
		
		try {
			Map<String, JSONObject> movies = new MovieJSONQuery().queryAnimeJSONByInput(
					coordinates.getLatitude(), 
					coordinates.getLongitude(), 
					coordinates.getRadius());
			movies.forEach((key, value) -> {
				
			});
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void updateShowtimeTable() {
		
	}
	
	public void updateTheaterTable() {
		
	}
	
	private Movie JSONObjectToMovie(JSONObject movieJSON) {
		Movie movie = null;
		
		try {
			movie = new Movie();
			movie.setMovieId(movieJSON.getString("tmsId"));
			movie.setTitle(movieJSON.getString("title"));
			
			String description;
			if(movieJSON.has("shortDescription")) {
				description = movieJSON.getString("shortDescription");
			} else if (movieJSON.has("longDescription")) {
				description = movieJSON.getString("longDescription");
			} else {
				description = null;
			}
			movie.setDescription(description);
			
			movie.setRuntime(runtimeConvert(movieJSON.getString("runTime")));
			if(movieJSON.has("ratings")) {
				JSONArray ratings = movieJSON.getJSONArray("ratings");
				JSONObject rating = (JSONObject) ratings.get(0);
				movie.setRating(rating.getString("code"));
			} else {
				movie.setRating("NR");
			}
			
			if(movieJSON.has("officialUrl")) {
				movie.setOfficialLink(movieJSON.getString("officialUrl"));
			} else {
				movie.setOfficialLink(null);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movie;
	}
	
	public int runtimeConvert(String runTime) {
		Pattern regExPattern = Pattern.compile("PT(\\d*)H(\\d*)M");
		Matcher matcher = regExPattern.matcher("PT01H40M");
		
		int minutes = 0;
		while (matcher.find()) {
			minutes += Integer.parseInt(matcher.group(2));
			minutes += Integer.parseInt(matcher.group(1)) * 60;
		}
		
		return minutes;
	}
	
	class Coordinates {
		private double latitude;
		private double longitude;
		private double radius;
		
		public Coordinates(double lat, double lng, double rad) {
			this.latitude = lat;
			this.longitude = lng;
			this.radius = rad;
		}

		public double getLatitude() {
			return latitude;
		}

		public double getLongitude() {
			return longitude;
		}

		public double getRadius() {
			return radius;
		}
	}
}
