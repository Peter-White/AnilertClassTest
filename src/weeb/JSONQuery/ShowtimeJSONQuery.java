package weeb.JSONQuery;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import weeb.data.Showtime;

public class ShowtimeJSONQuery {

	public List<Showtime> queryShowtimesByMovieShowtimeJSONArray(String movieID, JSONArray showtimesJSONArray) {
		List<Showtime> showtimes = new ArrayList<>();
		
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
