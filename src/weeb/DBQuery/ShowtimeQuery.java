package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Showtime> queryAllShowtimes() {
		List<Showtime> showtimes = new ArrayList<>();
		
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
}
