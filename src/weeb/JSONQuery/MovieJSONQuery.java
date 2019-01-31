package weeb.JSONQuery;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weeb.data.APIKeys;

public class MovieJSONQuery extends weeb.JSONQuery.JSONReader {

	private final String graceNoteURLStart = "http://data.tmsapi.com/v1.1/movies/showings?startDate=" + currentDate();
	private final String movieDBStart = "https://api.themoviedb.org/3/search/multi?api_key=";
	public final String numDays = "&numDays=60";
	private StringBuilder urlPath;

	private Map<String, JSONObject> animeQuery;
	
	public MovieJSONQuery() {
		animeQuery = new HashMap<String, JSONObject>();
	}
	
	public Map<String, JSONObject> queryAnimeJSON(Double lat, Double lng, int radius) throws IOException, JSONException {
		
		urlPath = new StringBuilder();
		urlPath.append(graceNoteURLStart);
		urlPath.append(numDays);
		urlPath.append("&lat=" + lat);
		urlPath.append("&lng=" + lng);
		urlPath.append("&radius=" + radius);
		urlPath.append("&units=km");
		urlPath.append("&api_key=");
		urlPath.append(APIKeys.getGracenoteAPIKey());
		
		JSONArray movies = readJsonArrayFromUrl(urlPath.toString());
		
		int count = 0;
		while (count < movies.length()) {
			JSONObject currentMovie = (JSONObject) movies.get(count);
			if(isAnime(currentMovie) && !animeQuery.containsKey(currentMovie.getString("title"))) {
				animeQuery.put(currentMovie.getString("title"), currentMovie);
			}
			count++;
		}
		
		return animeQuery;
	}
	
	public Map<String, JSONObject> querySingleTheaterAnime(Double lat, Double lng) throws IOException, JSONException {
		urlPath = new StringBuilder();
		urlPath.append(graceNoteURLStart);
		urlPath.append(numDays);
		urlPath.append("&lat=" + lat);
		urlPath.append("&lng=" + lng);
		urlPath.append("&radius=0.005");
		urlPath.append("&units=km");
		urlPath.append("&api_key=");
		urlPath.append(APIKeys.getGracenoteAPIKey());
		
		JSONArray movies = readJsonArrayFromUrl(urlPath.toString());
		
		int count = 0;
		while (count < movies.length()) {
			JSONObject currentMovie = (JSONObject) movies.get(count);
			if(isAnime(currentMovie) && !animeQuery.containsKey(currentMovie.getString("title"))) {
				animeQuery.put(currentMovie.getString("title"), currentMovie);
			}
			count++;
		}
		
		return animeQuery;
	}
	
	public String currentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return df.format(date).toString();
	}
	
	public boolean isAnime(JSONObject currentMovie) throws JSONException, IOException {
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
		return false;
	}
	
	public Boolean movieDBCheck(String title) throws JSONException, IOException {
		
		  title = title.replaceAll(" ", "%20");
		  urlPath = new StringBuilder(movieDBStart);
		  urlPath.append(APIKeys.getMovieDBAPIKey());
		  urlPath.append("&language=en&query=");
		  urlPath.append(title);
		  urlPath.append("&page=1&include_adult=true");
			
		  JSONArray results = readJsonObjectFromUrl(urlPath.toString()).getJSONArray("results");
			
		  for(int i = 0; i < results.length(); i++) {
			  JSONObject currentMovie = (JSONObject) results.get(i);
			  if(currentMovie.has("original_language")) {
				 String originalLanguage = currentMovie.getString("original_language"); 
				 String genreIds = currentMovie.get("genre_ids").toString();
			  
				  if(originalLanguage.equals("ja") && genreIds.contains("16")) {
					  return true;
				  }
			  }
		  }
		  
		  return false;
	}

	public Map<String, JSONObject> getAnimeQuery() {
		return animeQuery;
	}
	
}
