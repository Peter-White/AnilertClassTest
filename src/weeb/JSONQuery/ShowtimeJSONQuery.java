package weeb.JSONQuery;

import java.util.HashSet;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weeb.data.Showtime;

public class ShowtimeJSONQuery {

	public Set<Showtime> queryShowtimesByMovieShowtimeJSONArray(String movieID, JSONArray showtimesJSONArray) {
		Set<Showtime> showtimes = new HashSet<>();
		
		try {
			for (int i = 0; i < showtimesJSONArray.length(); i++) {
			
				JSONObject showtimeObject = (JSONObject) showtimesJSONArray.get(i);
				Showtime showtime = JSONObjectToShowtime(movieID, showtimeObject);
				showtimes.add(showtime);
				
			}	
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		return showtimes;
	}
	
	private Showtime JSONObjectToShowtime(String movieID, JSONObject showtimeObject) {
		Showtime showtime = null;
		
		try {
			showtime = new Showtime();
			showtime.setMovieID(movieID);
			showtime.setDateTime(showtimeObject.getString("dateTime"));
			showtime.setTheaterID(showtimeObject.getJSONObject("theatre").getInt("id"));
			showtime.setPurchaseLink(showtimeObject.getString("ticketURI"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return showtime;
	}
	
}
