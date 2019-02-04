package weeb.DBQuery;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

import weeb.data.Showtime;

public class ShowtimeQuery {

	private static final String DB_NAME = "Anilert.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:/home/leafcoder/SQL/" + DB_NAME;
	
	private static final String TABLE_SHOWTIMES = "Showtimes";
	private static final String COLLUMN_SHOWTIMEID = "theaterId";
	private static final String COLLUMN_NAME = "name";
	private static final String COLLUMN_ADDRESS = "address";
	private static final String COLLUMN_LATITUDE = "latitude";
	private static final String COLLUMN_LONGITUDE = "longitude";
	private static final String COLLUMN_PLACE_ID = "place_id";
	
	public static Connection conn;
	public static Statement statement;
	
//	public static ArrayList<Showtime> queryAllShowtimes() {
//		
//	}
}
