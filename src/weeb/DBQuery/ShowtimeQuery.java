package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
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
						result.getInt(COLLUMN_THEATERID), 
						result.getString(COLLUMN_MOVIEID), 
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
			query.append(" WHERE " + COLLUMN_THEATERID + " IS " + theaterID + " AND ");
			query.append(COLLUMN_MOVIEID + " IS " + "\"" + movieID + "\"" + " AND ");
			query.append(COLLUMN_DATETIME + " IS " + "\"" + dateTime  + "\"");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {

				showtime = new Showtime(result.getInt(COLLUMN_SHOWTIMEID), 
						result.getInt(COLLUMN_THEATERID), 
						result.getString(COLLUMN_MOVIEID), 
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
			query.append(" WHERE " + COLLUMN_MOVIEID + " IS " + "'" + movieID + "'" + " AND ");
			query.append(COLLUMN_THEATERID + " IS " + theaterID);
			
			ResultSet results = statement.executeQuery(query.toString());
			while (results.next()) {
				Showtime showtime = new Showtime(results.getInt(COLLUMN_SHOWTIMEID), 
						results.getInt(COLLUMN_THEATERID), 
						results.getString(COLLUMN_MOVIEID), 
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
				insertCommand.append("(" + COLLUMN_THEATERID + "," + COLLUMN_MOVIEID + "," + COLLUMN_DATETIME + ")");
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
}
