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

public class MovieJSONQuery extends weeb.JSONQuery.JSONReader {

	private final String graceNoteURLStart = "http://data.tmsapi.com/v1.1/movies/showings?startDate=" + currentDate();
	private final String movieDBStart = "https://api.themoviedb.org/3/search/multi?api_key=";
	public final String numDays = "&numDays=60";
	private StringBuilder urlPath;

	private Map<String, Movie> animeQuery;
	
	public MovieJSONQuery() {
		animeQuery = new HashMap<String, Movie>();
	}
	
	public Map<String, Movie> queryAnimeJSONByInput(Double lat, Double lng, int radius) throws IOException, JSONException {
		
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
		TheaterJSONQuery theaterJSONQuery = new TheaterJSONQuery();
		
		while (count < movies.length()) {
			JSONObject currentMovie = (JSONObject) movies.get(count);
			theaterJSONQuery.addTheatersJSON(currentMovie, lat, lng, radius);
			
			if(isAnime(currentMovie) && !animeQuery.containsKey(currentMovie.getString("title"))) {
				animeQuery.put(currentMovie.getString("title"), JSONObjectToMovie(currentMovie));
			}
			count++;
		}
		
		return animeQuery;
	}
	
	public Map<String, Movie> querySingleTheaterAnime(Double lat, Double lng) throws IOException, JSONException {
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
				animeQuery.put(currentMovie.getString("title"), JSONObjectToMovie(currentMovie));
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
	
	public Movie JSONObjectToMovie(JSONObject movieJSON) {
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
	
//	public Map<Integer, Theater> getTheatersFromMovieJSON(JSONObject movie) {
//		TheaterJSONQuery theaterJSONQuery = new TheaterJSONQuery();
//	}

	public Map<String, Movie> getAnimeQuery() {
		return animeQuery;
	}
	
	public static int runtimeConvert(String runTime) {
		Pattern regExPattern = Pattern.compile("PT(\\d*)H(\\d*)M");
		Matcher matcher = regExPattern.matcher("PT01H40M");
		
		int minutes = 0;
		while (matcher.find()) {
			minutes += Integer.parseInt(matcher.group(2));
			minutes += Integer.parseInt(matcher.group(1)) * 60;
		}
		
		return minutes;
	}
}
