package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weeb.data.Movie;
import weeb.data.Theater;

public class MovieQuery {
	
	private static final String DB_NAME = "Anilert.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:/home/leafcoder/SQL/" + DB_NAME;
	
	private static final String TABLE_MOVIES = "Movies";
	private static final String COLLUMN_MOVIEID = "movieId";
	private static final String COLLUMN_TITLE = "title";
	private static final String COLLUMN_DESCRIPTION = "description";
	private static final String COLLUMN_RUNTIME = "runtime";
	private static final String COLLUMN_RATING = "rating";
	private static final String COLLUMN_OFFICIALSITE = "officialSite";
	
	public static Connection conn;
	public static Statement statement;
	
	public static Map<String, Movie> queryAllMovies() {
		Map<String, Movie> movieQuery = new HashMap<String, Movie>();
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_MOVIES);
			while (results.next()) {
				Movie movie = new Movie(results.getInt(COLLUMN_MOVIEID), 
										results.getString(COLLUMN_TITLE), 
										results.getString(COLLUMN_DESCRIPTION), 
										results.getInt(COLLUMN_RUNTIME), 
										results.getString(COLLUMN_RATING), 
										results.getString(COLLUMN_OFFICIALSITE));
				
				movieQuery.put(results.getString(COLLUMN_TITLE), movie);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return movieQuery;
	}
	
	public static Movie queryMovie(int movieId) {
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_MOVIES);
			query.append(" WHERE " + COLLUMN_MOVIEID + " IS " + "'" + movieId + "'");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			Movie movie = null;
			
			while (result.next()) {

				movie = new Movie(result.getInt(COLLUMN_MOVIEID), 
						result.getString(COLLUMN_TITLE), 
						result.getString(COLLUMN_DESCRIPTION), 
						result.getInt(COLLUMN_RUNTIME), 
						result.getString(COLLUMN_RATING), 
						result.getString(COLLUMN_OFFICIALSITE));
			}
			
			return movie;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static Movie queryMovie(String title) {
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_MOVIES);
			query.append(" WHERE " + COLLUMN_TITLE + " IS " + "'" + title + "'");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			Movie movie = null;
			
			while (result.next()) {

				movie = new Movie(result.getInt(COLLUMN_MOVIEID), 
						result.getString(COLLUMN_TITLE), 
						result.getString(COLLUMN_DESCRIPTION), 
						result.getInt(COLLUMN_RUNTIME), 
						result.getString(COLLUMN_RATING), 
						result.getString(COLLUMN_OFFICIALSITE));
			}
			
			return movie;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
//	public static Map<String, Movie> mergeMovieJSONToDb(Map<String, Movie> moviesInDB, Map<String, JSONObject> JSONMovies) {
//		
//		try {
//			conn = DriverManager.getConnection(CONNECTION_STRING);
//			statement = conn.createStatement();
//			
//			JSONMovies.forEach((key, value) -> {
//				if(!moviesInDB.containsKey(key)) {
//					
//				}
//			});
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//	}
	
	public static Movie addMovieToDb(JSONObject movieJSON) {
		StringBuilder insertCommand = new StringBuilder("INSERT INTO " + TABLE_MOVIES);
		insertCommand.append("(" + COLLUMN_TITLE + "," + COLLUMN_DESCRIPTION + "," + COLLUMN_RUNTIME + "," + COLLUMN_RATING + "," + COLLUMN_OFFICIALSITE + ")");
		insertCommand.append(" VALUES (");
		
		try {
			insertCommand.append("'" + movieJSON.getString("title") + "'");
			
			if(movieJSON.has("shortDescription")) {
				insertCommand.append("," + "'" + movieJSON.getString("shortDescription") + "'");
			} else if (movieJSON.has("longDescription")) {
				insertCommand.append("," + "'" + movieJSON.getString("longDescription") + "'");
			} else {
				insertCommand.append("," + null);
			}
			
			insertCommand.append("," + runtimeConvert(movieJSON.getString("runTime")));
			
			if(movieJSON.has("ratings")) {
				JSONArray ratings = movieJSON.getJSONArray("ratings");
				JSONObject rating = (JSONObject) ratings.get(0);
				
				insertCommand.append("," + "'" + rating.getString("code") + "'");
			} else {
				insertCommand.append("," + "'" + "NR" + "'");
			}
			
			if(movieJSON.has("officialUrl")) {
				insertCommand.append("," + "'" + movieJSON.getString("officialUrl") + "'");
			} else {
				insertCommand.append("," + null);
			}
			
			insertCommand.append(")");
			
			System.out.println(insertCommand.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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
	
	public static String runtimeConvert(int runtime) {
		int hours = 0;
		while(runtime >= 60) {
			runtime %= 60;
			hours++;
		}
		
		String runTimeConverted = (hours == 1) ? hours + " hour" : hours + " hours";
		if (runtime != 0) {
			runTimeConverted += (runtime == 1) ? runtime + " minute" : runtime + " minutes";
		}
		
		return runTimeConverted;
	}
}
