package weeb.JSONQuery;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weeb.data.APIKeys;
import weeb.data.Movie;
import weeb.data.Theater;

public class MovieJSONQuery {

	private final String graceNoteURLStart = "http://data.tmsapi.com/v1.1/movies/showings?startDate=" + currentDate();
	private final String movieDBStart = "https://api.themoviedb.org/3/search/multi?api_key=";
	public final String numDays = "&numDays=60";
	private StringBuilder urlPath;

	private Map<String, JSONObject> movieJSONQuery;
	
	public MovieJSONQuery() {
		movieJSONQuery = new HashMap<String, JSONObject>();
	}
	
	public Map<String, JSONObject> queryMovieJSONByInput(double lat, double lng, double radius) {

		JSONArray movies = null;
		
		try {
			urlPath = new StringBuilder();
			urlPath.append(graceNoteURLStart);
			urlPath.append(numDays);
			urlPath.append("&lat=" + lat);
			urlPath.append("&lng=" + lng);
			urlPath.append("&radius=" + radius);
			urlPath.append("&units=km");
			urlPath.append("&api_key=");
			urlPath.append(APIKeys.getGracenoteAPIKey());
			
			movies = new JSONArrayReader().readJsonArrayFromUrl(urlPath.toString());
			
			int count = 0;
			
			while (count < movies.length()) {
				
				JSONObject currentMovie = (JSONObject) movies.get(count);
				movieJSONQuery.put(currentMovie.getString("title"), currentMovie);
				count++;
			}
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movieJSONQuery;
	}
	
	public String currentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return df.format(date).toString();
	}
	
	public boolean isAnime(JSONObject currentMovie) {
		try {
			if(currentMovie.has("animation") && currentMovie.get("animation").equals("anime")) {
				return true;
			} else if(currentMovie.has("genres")) {
				String genres = currentMovie.get("genres").toString();
				if(genres.indexOf("Anime") != -1) {
					return true;
				}
			} else {
				String description = null;
				if(currentMovie.has("shortDescription")) {
					description = (String) currentMovie.get("shortDescription");
					if(description.indexOf("Anime") != -1 || description.indexOf("anime") != -1) {
						return true;
					}
				} else if(currentMovie.has("longDescription")) {
					description = (String) currentMovie.get("longDescription");
					if(description.indexOf("Anime") != -1 || description.indexOf("anime") != -1) {
						return true;
					}
				} else {
					if(movieDBCheck(currentMovie.getString("title"))) {
						return true;
					}
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public Boolean movieDBCheck(String title) {
		
		  String titleConverted = title.replaceAll(" ", "%20");
		  urlPath = new StringBuilder(movieDBStart);
		  urlPath.append(APIKeys.getMovieDBAPIKey());
		  urlPath.append("&language=en&query=");
		  urlPath.append(titleConverted);
		  urlPath.append("&page=1&include_adult=true");
			
		  JSONArray results;
		  
		try {
			results = new JSONObjectReader().readJsonObjectFromUrl(urlPath.toString()).getJSONArray("results");
			
			int length = 0;
			
			if(title.split(" ").length > 1) {
				length = results.length();
			} else {
				if(results.length() > 60) {
					length = 60;
				}
			}
			
			for(int i = 0; i < length; i++) {
			  JSONObject currentMovie = (JSONObject) results.get(i);
			  if(currentMovie.has("original_language")) {
				 String originalLanguage = currentMovie.getString("original_language"); 
				 String genreIds = currentMovie.get("genre_ids").toString();
			  
				  if(originalLanguage.equals("ja") && genreIds.contains("16")) {
					  return true;
				  }
			  }
		    }
			
		} catch (JSONException | IOException e) {
			e.printStackTrace();
		}

		return false;
	}
}
