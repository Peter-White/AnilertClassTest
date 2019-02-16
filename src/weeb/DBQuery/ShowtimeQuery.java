package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import weeb.data.Movie;
import weeb.data.Showtime;
import weeb.data.Theater;

public class ShowtimeQuery {

	private static final String DB_NAME = "Anilert.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:/home/leafcoder/SQL/" + DB_NAME;
	
	// Because showtime acts as a many-to-many table for movies and 
	// theaters all SQL columns and tables will be needed
	private static final String TABLE_SHOWTIMES = "Showtimes";
	private static final String COLLUMN_SHOWTIMEID = "showtimeId";
	private static final String COLLUMN_THEATER_ID = "theaterID";
	private static final String COLLUMN_MOVIE_ID = "movieID";
	private static final String COLLUMN_DATETIME = "dateTime";
	private static final String TABLE_MOVIES = "Movies";
	private static final String COLLUMN_MOVIEID = "movieId";
	private static final String COLLUMN_TITLE = "title";
	private static final String COLLUMN_DESCRIPTION = "description";
	private static final String COLLUMN_RUNTIME = "runtime";
	private static final String COLLUMN_RATING = "rating";
	private static final String COLLUMN_OFFICIALSITE = "officialSite";
	private static final String TABLE_THEATERS = "Theaters";
	private static final String COLLUMN_THEATERID = "theaterId";
	private static final String COLLUMN_NAME = "name";
	private static final String COLLUMN_ADDRESS = "address";
	private static final String COLLUMN_LATITUDE = "latitude";
	private static final String COLLUMN_LONGITUDE = "longitude";
	private static final String COLLUMN_PLACE_ID = "place_id";
	
	
	public static Connection conn;
	public static Statement statement;

	public static Set<Showtime> queryAllShowtimes() {
		Set<Showtime> showtimes = new HashSet<>();
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_SHOWTIMES);
			while (results.next()) {
				Showtime showtime = new Showtime(results.getInt(COLLUMN_SHOWTIMEID), 
										results.getInt(COLLUMN_THEATER_ID), 
										results.getString(COLLUMN_MOVIE_ID), 
										results.getString(COLLUMN_DATETIME));
				
				showtimes.add(showtime);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return showtimes;
	}
	
	public static Showtime queryShowtime(int showtimeId) {
		Showtime showtime = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_SHOWTIMES);
			query.append(" WHERE " + COLLUMN_SHOWTIMEID + " IS " + showtimeId);
			
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {

				showtime = new Showtime(result.getInt(COLLUMN_SHOWTIMEID), 
						result.getInt(COLLUMN_THEATER_ID), 
						result.getString(COLLUMN_MOVIE_ID), 
						result.getString(COLLUMN_DATETIME));
			}

		} catch (SQLException e) {
			System.out.println("queryMovie triggered");
			e.printStackTrace();
			return null;
		}
		
		return showtime;
	}
	
	public static Showtime queryShowtime(int theaterID, String movieID, String dateTime) {
		Showtime showtime = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_SHOWTIMES);
			query.append(" WHERE " + COLLUMN_THEATER_ID + " IS " + theaterID + " AND ");
			query.append(COLLUMN_MOVIE_ID + " IS " + "\"" + movieID + "\"" + " AND ");
			query.append(COLLUMN_DATETIME + " IS " + "\"" + dateTime  + "\"");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {

				showtime = new Showtime(result.getInt(COLLUMN_SHOWTIMEID), 
						result.getInt(COLLUMN_THEATER_ID), 
						result.getString(COLLUMN_MOVIE_ID), 
						result.getString(COLLUMN_DATETIME));
			}

		} catch (SQLException e) {
			System.out.println("queryMovie triggered");
			e.printStackTrace();
			return null;
		}
		
		return showtime;
	}
	
	public static Set<Showtime> queryByMovieIDAndTheaterID(String movieID, int theaterID) {
		Set<Showtime> showtimes = new HashSet<>();
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_SHOWTIMES);
			query.append(" WHERE " + COLLUMN_MOVIE_ID + " IS " + "'" + movieID + "'" + " AND ");
			query.append(COLLUMN_THEATER_ID + " IS " + theaterID);
			
			ResultSet results = statement.executeQuery(query.toString());
			while (results.next()) {
				Showtime showtime = new Showtime(results.getInt(COLLUMN_SHOWTIMEID), 
						results.getInt(COLLUMN_THEATER_ID), 
						results.getString(COLLUMN_MOVIE_ID), 
						results.getString(COLLUMN_DATETIME));

				showtimes.add(showtime);
			}
			
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			System.out.println("queryMovie triggered");
			e.printStackTrace();
			return null;
		}
		
		return showtimes;
	}
	
	public static Showtime addShowtimeToDb(Showtime showtime) {
		
		try {
			if(queryShowtime(showtime.getTheaterID(), showtime.getMovieID(), showtime.getDateTime()) == null) {
				conn = DriverManager.getConnection(CONNECTION_STRING);
				statement = conn.createStatement();
				
				StringBuilder insertCommand = new StringBuilder("INSERT INTO " + TABLE_SHOWTIMES);
				insertCommand.append("(" + COLLUMN_THEATER_ID + "," + COLLUMN_MOVIE_ID + "," + COLLUMN_DATETIME + ")");
				insertCommand.append(" VALUES (");
				insertCommand.append(showtime.getTheaterID());
				insertCommand.append("," + "\"" + showtime.getMovieID() + "\"");
				insertCommand.append("," + "\"" + showtime.getDateTime() + "\"");
				insertCommand.append(")");
				
				statement.execute(insertCommand.toString());
				showtime = queryShowtime(showtime.getTheaterID(), showtime.getMovieID(), showtime.getDateTime());
				
				statement.close();
				conn.close();
			}
			
			showtime = queryShowtime(showtime.getTheaterID(), showtime.getMovieID(), showtime.getDateTime());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			showtime = null;
		}
			
		return showtime;
	}
	
	public static Map<String, Set<Integer>> getMoviesAndShowtimesForTheater(Theater theater) {
		
		Map<String, Set<Integer>> movieShowtimes = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_SHOWTIMES);
			query.append(" JOIN " + TABLE_MOVIES + " ON ");
			query.append(TABLE_SHOWTIMES + "." + COLLUMN_MOVIE_ID + " = " + TABLE_MOVIES + "." + COLLUMN_MOVIEID);
			query.append(" WHERE " + TABLE_SHOWTIMES + "." + COLLUMN_THEATER_ID + " = ");
			query.append(theater.getTheaterId());
			
			ResultSet results = statement.executeQuery(query.toString());
			movieShowtimes = new HashMap<String, Set<Integer>>();
			
			while (results.next()) {
				String movieID = results.getString(COLLUMN_MOVIE_ID);
				if(!movieShowtimes.containsKey(movieID)) {
					Set<Integer> showtimeIds = new HashSet<>();
					StringBuilder queryMovie = query;
					queryMovie.append(" AND " + TABLE_SHOWTIMES + "." + COLLUMN_MOVIE_ID + " = " + "\"" + movieID + "\"");
					
					ResultSet moreResults = statement.executeQuery(queryMovie.toString());
					while(moreResults.next()) {
						showtimeIds.add(moreResults.getInt("showtimeId"));
					}
					movieShowtimes.put(movieID, showtimeIds);
				}
			}
			
			statement.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return movieShowtimes;
	}
}
