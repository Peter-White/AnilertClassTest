package weeb.JSONQuery;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
	
	public void updateMovieTableByUserInput(double lat, double lng, double rad) {
		
		Coordinates coordinates = new Coordinates(lat, lng, rad);
		
		try {
			Map<String, JSONObject> animes = new MovieJSONQuery().queryAnimeJSONByInput(lat, lng, radius);
			animes.forEach((key, value) -> {
				
			});
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updateMovieTableBySingleTheater(Double lat, Double lng) {
		Coordinates coordinates = new Coordinates(lat, lng, 0.05);
	}
	
	public void updateShowTable() {
		
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
				movie.setRatingID(rating.getString("code"));
			} else {
				movie.setRatingID("NR");
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
		double latitude;
		double longitude;
		double radius;
		
		public Coordinates(double lat, double lng, double rad) {
			this.latitude = lat;
			this.longitude = lng;
			this.radius = rad;
		}
	}
}
