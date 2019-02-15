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

	public static Map<String, Movie> queryAllAnime() {
		Map<String, Movie> movieQuery = new HashMap<String, Movie>();

		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();

			ResultSet results = statement.executeQuery("SELECT * FROM " + TABLE_MOVIES);
			while (results.next()) {
				Movie movie = new Movie(results.getString(COLLUMN_MOVIEID), results.getString(COLLUMN_TITLE),
						results.getString(COLLUMN_DESCRIPTION), results.getInt(COLLUMN_RUNTIME),
						results.getString(COLLUMN_RATING), results.getString(COLLUMN_OFFICIALSITE));

				movieQuery.put(results.getString(COLLUMN_TITLE), movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return movieQuery;
	}

	public static Movie queryAnime(int movieId) {
		Movie movie = null;

		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();

			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_MOVIES);
			query.append(" WHERE " + COLLUMN_MOVIEID + " IS " + "\"" + movieId + "\"");

			ResultSet result = statement.executeQuery(query.toString());

			while (result.next()) {

				movie = new Movie(result.getString(COLLUMN_MOVIEID), result.getString(COLLUMN_TITLE),
						result.getString(COLLUMN_DESCRIPTION), result.getInt(COLLUMN_RUNTIME),
						result.getString(COLLUMN_RATING), result.getString(COLLUMN_OFFICIALSITE));
			}

		} catch (SQLException e) {
			System.out.println("queryMovie triggered");
			e.printStackTrace();
			return null;
		}

		return movie;
	}

	public static Movie queryAnime(String title) {
		Movie movie = null;

		try {
			conn = DriverManager.getConnection(CONNECTION_STRING);
			statement = conn.createStatement();

			StringBuilder query = new StringBuilder("SELECT * FROM " + TABLE_MOVIES);
			query.append(" WHERE " + COLLUMN_TITLE + " IS " + "\"" + title + "\"");

			ResultSet result = statement.executeQuery(query.toString());

			while (result.next()) {

				movie = new Movie(result.getString(COLLUMN_MOVIEID), result.getString(COLLUMN_TITLE),
						result.getString(COLLUMN_DESCRIPTION), result.getInt(COLLUMN_RUNTIME),
						result.getString(COLLUMN_RATING), result.getString(COLLUMN_OFFICIALSITE));
			}

		} catch (SQLException e) {
			System.out.println("queryMovie triggered");
			e.printStackTrace();
			return null;
		}

		return movie;
	}

	public static Movie addAnimeToDb(Movie movie) {
		
		try {
			if (queryAnime(movie.getTitle()) == null) {
				conn = DriverManager.getConnection(CONNECTION_STRING);
				statement = conn.createStatement();

				StringBuilder insertCommand = new StringBuilder("INSERT INTO " + TABLE_MOVIES);
				insertCommand.append("(" + COLLUMN_MOVIEID + "," + COLLUMN_TITLE + "," + COLLUMN_DESCRIPTION + "," + COLLUMN_RUNTIME + ","
						+ COLLUMN_RATING + "," + COLLUMN_OFFICIALSITE + ")");
				insertCommand.append(" VALUES (");
				insertCommand.append("\"" + movie.getMovieId() + "\"");
				insertCommand.append("," + "\"" + movie.getTitle().replaceAll("\"", "'") + "\"");
				
				if(movie.getDescription() != null) {
					insertCommand.append("," + "\"" + movie.getDescription().replaceAll("\"", "'") + "\"");
				} else {
					insertCommand.append("," + null);
				}
				
				insertCommand.append("," + movie.getRuntime());
				insertCommand.append("," + "\"" + movie.getRating() + "\"");
				
				if(movie.getOfficialLink() != null) {
					insertCommand.append("," + "\"" + movie.getOfficialLink() + "\"");
				} else {
					insertCommand.append("," + null);
				}
				
				insertCommand.append(")");
				
				System.out.println(insertCommand.toString());
				
				statement.execute(insertCommand.toString());
			}
			
			movie = queryAnime(movie.getTitle());

		} catch (SQLException e) {
			e.printStackTrace();
			movie = null;
		}

		return movie;
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
		while (runtime >= 60) {
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
