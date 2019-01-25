package weeb.power;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import org.json.JSONObject;

import weeb.DBQuery.TheaterQuery;
import weeb.JSONQuery.MovieJSONQuery;
import weeb.JSONQuery.TheaterJSONQuery;
import weeb.data.Movie;
import weeb.data.Showtime;
import weeb.data.Theater;

public class Main {
	
	public static Map<String, Theater> theatersINDB = TheaterQuery.queryAllTheaters();
	
	public static void main(String[] args) {
		
		List<String> ids = new TheaterJSONQuery().queryTheaterIds("ShowPlace ICON Theatres", 42.3381437, -71.0475773, 20);
		
		if(!ids.isEmpty()) {
			for (String id : ids) {
				System.out.println(id);
			}
		}
		
//		Map<String, Movie> moviesInDB = new HashMap<String, Movie>();
////		Map<Integer, Character> moviesFromJSON = new HashMap<Integer, Character>();
//		
//		List<JSONObject> JSONMovies = new ArrayList<>();
		
//		Map<Movie, Showtime> 
		
//		int count = 0;
//		while(count < 10) {
//			moviesInDB.put(count, (char) (count+97));
//			moviesFromJSON.put(count, (char) (count+97));
//			count++;
//		}
//		
//		moviesFromJSON.remove(5);
//		moviesFromJSON.remove(7);
//		moviesFromJSON.put(12, (char) 115);
//		moviesFromJSON.put(15, (char) 120);
//		moviesFromJSON.put(20, (char) 122);
//		
//		if(moviesInDB.size() > 0) {
//			Iterator<Entry<Integer, Character>> iterator = moviesInDB.entrySet().iterator();
//			while (iterator.hasNext()) {
//				Entry<Integer, Character> entry = iterator.next();
//				if(moviesFromJSON.containsKey(entry.getKey())) {
//					moviesFromJSON.remove(entry.getKey());
//				} else {
//					iterator.remove();
//				}
//			}
//		}		
//		
//		moviesInDB.putAll(moviesFromJSON);
//		
//		System.out.println("In The Database:");
//		moviesInDB.forEach((key, value) -> System.out.println(key + ": " + value));

	}

}
