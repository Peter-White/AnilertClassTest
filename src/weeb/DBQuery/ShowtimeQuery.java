package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weeb.data.Movie;
import weeb.data.Showtime;

public class ShowtimeQuery {

	private static final String DB_NAME = "Anilert.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:/home/leafcoder/SQL/" + DB_NAME;
	
	private static final String TABLE_SHOWTIMES = "Showtimes";
	private static final String COLLUMN_SHOWTIMEID = "showtimeId";
	private static final String COLLUMN_THEATERID = "theaterID";
	private static final String COLLUMN_MOVIEID = "movieID";
	private static final String COLLUMN_DATETIME = "dateTime";
	private static final String COLLUMN_PURCHASELINK = "purchaseLink";
	
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
										results.getInt(COLLUMN_THEATERID), 
										results.getString(COLLUMN_MOVIEID), 
										results.getString(COLLUMN_DATETIME), 
										results.getString(COLLUMN_PURCHASELINK));
				
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
						result.getInt(COLLUMN_THEATERID), 
						result.getString(COLLUMN_MOVIEID), 
						result.getString(COLLUMN_DATETIME), 
						result.getString(COLLUMN_PURCHASELINK));
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
			query.append(" WHERE " + COLLUMN_MOVIEID + " IS " + "'" + movieID + "'" + " AND ");
			query.append(COLLUMN_THEATERID + " IS " + theaterID);
			
			System.out.println(query.toString());
			
			ResultSet results = statement.executeQuery(query.toString());
			while (results.next()) {
				Showtime showtime = new Showtime(results.getInt(COLLUMN_SHOWTIMEID), 
						results.getInt(COLLUMN_THEATERID), 
						results.getString(COLLUMN_MOVIEID), 
						results.getString(COLLUMN_DATETIME), 
						results.getString(COLLUMN_PURCHASELINK));

				showtimes.add(showtime);
			}
			
		} catch (SQLException e) {
			System.out.println("queryMovie triggered");
			e.printStackTrace();
			return null;
		}
		
		return showtimes;
	}
}
