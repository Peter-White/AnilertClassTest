package weeb.JSONQuery;

import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weeb.data.Showtime;

public class ShowtimeJSONQuery {

	public Set<JSONObject> queryShowtimesByMovieShowtimeJSONArray(String movieID, JSONArray showtimesJSONArray) {
		Set<JSONObject> showtimes = new HashSet<>();
		
		try {
			for (int i = 0; i < showtimesJSONArray.length(); i++) {
			
				JSONObject showtimeObject = (JSONObject) showtimesJSONArray.get(i);
				showtimes.add(showtimeObject);
				
			}	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return showtimes;
	}
	
}
