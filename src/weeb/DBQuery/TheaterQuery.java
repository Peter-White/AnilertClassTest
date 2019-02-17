package weeb.DBQuery;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import weeb.data.Theater;

public class TheaterQuery {
	
	private static final String DB_NAME = "WeebWatch.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:/home/leafcoder/SQL/" + DB_NAME;
	
	private static final String VIEW_ANIME_THEATERS = "anime_theaters";
	private static final String COLLUMN_ANICOUNT = "COUNT(Showtimes.showtimeId)";
	private static final String TABLE_THEATERS = "Theaters";
	private static final String COLLUMN_THEATERID = "theaterId";
	private static final String COLLUMN_NAME = "name";
	private static final String COLLUMN_ADDRESS = "address";
	private static final String COLLUMN_LATITUDE = "latitude";
	private static final String COLLUMN_LONGITUDE = "longitude";
	private static final String COLLUMN_PLACE_ID = "place_id";
	private static final String COLLUMN_TIMEZONE = "timezone";
	
	public static Connection conn;
	public static Statement statement;

	public static Theater queryTheater(String name, String address) {
		
		Theater theater = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_THEATERS);
			query.append(" WHERE " + COLLUMN_NAME + " LIKE " + "\"%" + name + "%\" AND ");
			query.append(COLLUMN_ADDRESS + " LIKE " + "\"%" + address + "%\"");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				theater = new Theater(result.getInt("theaterId"), 
						result.getString("name"),
						result.getString("address"), 
						result.getDouble("latitude"), 
						result.getDouble("longitude"),
						result.getString("place_id"),
						result.getString("timezone"));
			}
			
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return theater;
	}
	
	public static Theater queryTheater(int id) {
		
		Theater theater = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_THEATERS);
			query.append(" WHERE " + COLLUMN_THEATERID + " IS " + "'" + id + "'");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				theater = new Theater(result.getInt("theaterId"), 
						result.getString("name"),
						result.getString("address"), 
						result.getDouble("latitude"), 
						result.getDouble("longitude"),
						result.getString("place_id"),
						result.getString("timezone"));
			}
			
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return theater;
	}
	
	public static Theater queryTheater(String place_id) {
		Theater theater = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_THEATERS);
			query.append(" WHERE " + COLLUMN_THEATERID + " IS " + "'" + place_id + "'");
			
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				theater = new Theater(result.getInt("theaterId"), 
						result.getString("name"),
						result.getString("address"), 
						result.getDouble("latitude"), 
						result.getDouble("longitude"),
						result.getString("place_id"),
						result.getString("timezone"));
			}
			
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return theater;
	}
	
	public static Theater queryTheater(String name, double lat, double lng) {
		
		Theater theater = null;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_THEATERS);
			query.append(" WHERE " + COLLUMN_LATITUDE + " IS " + lat);
			query.append(" AND " + COLLUMN_LONGITUDE + " IS " + lng);
			
			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				theater = new Theater(result.getInt("theaterId"), 
						result.getString("name"),
						result.getString("address"), 
						result.getDouble("latitude"), 
						result.getDouble("longitude"),
						result.getString("place_id"),
						result.getString("timezone"));
			}
			
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return theater;
	}

	public static Map<String, Theater> queryAllTheaters() {
		Map<String, Theater> theaterQuery = new HashMap<String, Theater>();
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_THEATERS);
			while (results.next()) {
				Theater theater = new Theater(results.getInt("theaterId"), 
						results.getString("name"),
						results.getString("address"), 
						results.getDouble("latitude"), 
						results.getDouble("longitude"),
						results.getString("place_id"),
						results.getString("timezone"));
				theaterQuery.put(results.getString("address"), theater);
			}
			
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return theaterQuery;
	}
	
	public static Theater addTheaterToDB(Theater theater) {
		
		try {
			if(queryTheater(theater.getName(), theater.getAddress()) == null) {
				conn = DriverManager.getConnection(CONNECTION_STRING);
				statement = conn.createStatement();
				
				StringBuilder addStatement = new StringBuilder("INSERT INTO " + TABLE_THEATERS + "(");
				addStatement.append(COLLUMN_THEATERID + ",");
				addStatement.append(COLLUMN_NAME + ",");
				addStatement.append(COLLUMN_ADDRESS + ",");
				addStatement.append(COLLUMN_LATITUDE + ",");
				addStatement.append(COLLUMN_LONGITUDE + ",");
				addStatement.append(COLLUMN_PLACE_ID+ ",");
				addStatement.append(COLLUMN_TIMEZONE + ") ");
				addStatement.append("VALUES (");
				addStatement.append(theater.getTheaterId() + ",");
				addStatement.append("\"" + theater.getName() + "\"" + ",");
				addStatement.append("'" + theater.getAddress() + "'" + ",");
				addStatement.append(theater.getLatitude() + ",");
				addStatement.append(theater.getLongitude() + ",");
				addStatement.append("\"" + theater.getPlace_id()  + "\"" + ",");
				addStatement.append("\"" + theater.getTimezone()  + "\"" + ")");
				
				statement.execute(addStatement.toString());
				
				statement.close();
				conn.close();
			}
			
			theater = queryTheater(theater.getName(), theater.getAddress());
					
		} catch (SQLException e) {
			e.printStackTrace();
			theater = null;
		}

		return theater;
	}
	
	// Returns the number of anime playing in a theater
	public static int numberOfAnimeInTheater(Theater theater) {
		int count = 0;
		
		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();
			
			StringBuilder query = new StringBuilder("SELECT * FROM " + VIEW_ANIME_THEATERS);
			query.append(" WHERE " + COLLUMN_ADDRESS + " = " + "\"" + theater.getAddress() + "\"");

			ResultSet result = statement.executeQuery(query.toString());
			
			while (result.next()) {
				count = result.getInt(COLLUMN_ANICOUNT);
			}
			
			statement.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
}
