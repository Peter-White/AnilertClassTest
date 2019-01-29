package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

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
	private static final String COLLUMN_RATINGID = "ratingId";
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
										results.getInt(COLLUMN_RATINGID), 
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
						result.getInt(COLLUMN_RATINGID), 
						result.getString(COLLUMN_OFFICIALSITE));
			}
			
			return movie;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
}
