package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import weeb.data.Theater;

public class TheaterQuery {
	
	public static Map<String, Theater> theaterQuery = new HashMap<String, Theater>();
	private static final String DB_NAME = "Anilert.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:/home/leafcoder/SQL/" + DB_NAME;
	
	private static final String TABLE_THEATERS = "Theaters";
	private static final String COLLUMN_THEATERID = "theaterId";
	private static final String COLLUMN_NAME = "name";
	private static final String COLLUMN_ADDRESS = "address";
	private static final String COLLUMN_LATITUDE = "latitude";
	private static final String COLLUMN_LONGITUDE = "longitude";
	
	public static Connection conn;
	public static Statement statement;
	
	public static Theater queryTheater(String name, String address) {
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_THEATERS);
			query.append(" WHERE " + COLLUMN_NAME + " LIKE " + "'%" + name + "%' AND ");
			query.append(COLLUMN_ADDRESS + " LIKE " + "'%" + address + "%'");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			Theater theater = null;
			
			while (result.next()) {
				theater = new Theater(result.getString("theaterId"), 
						result.getString("name"),
						result.getString("address"), 
						result.getDouble("latitude"), 
						result.getDouble("longitude"));
			}
			
			return theater;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static Map<String, Theater> queryAllTheaters(String url) {
		theaterQuery = new HashMap<String, Theater>();
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_THEATERS);
			while (results.next()) {
				Theater theater = new Theater(results.getString("theaterId"), 
												results.getString("name"),
												results.getString("address"), 
												results.getDouble("latitude"), 
												results.getDouble("longitude"));
				theaterQuery.put(results.getString("name"), theater);
			}
			return theaterQuery;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static boolean isTheaterPresent(String url, String name) {
		return false;
	}
}
