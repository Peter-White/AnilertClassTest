package weeb.power;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import weeb.DBQuery.ShowtimeQuery;
import weeb.DBQuery.TheaterQuery;
import weeb.JSONQuery.JSONToSQL;
import weeb.JSONQuery.TheaterJSONQuery;
import weeb.data.APIKeys;
import weeb.data.Showtime;
import weeb.data.Theater;

public class SeverUpdateApp {

	public static void main(String[] args) {

//		while (true) {
			
			new Thread() {

				@Override
				public void run() {
					
					Set<Showtime> showtimes = ShowtimeQuery.queryAllShowtimes();
					
					for (Showtime showtime : showtimes) {
						obsoleteShowtimeChecker(showtime);
					}
					
				}
				
			}.start();
			
			new Thread() {

				@Override
				public void run() {
					
					Map<String, Theater> theaters = TheaterQuery.queryAllTheaters();
					
					for (Map.Entry<String, Theater> entry: theaters.entrySet()) {
						JSONToSQL jsonToSQL = new JSONToSQL();
						jsonToSQL.updateMovieTableBySingleTheater(entry.getValue());
					}
					
				}
				
			}.start();
			
//		}
		
	}
	
	public static void obsoleteShowtimeChecker(Showtime showtime) {
		SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date today = new Date();
		
		// Get current datetime for selected theater
		Theater curentTheater = TheaterQuery.queryTheater(showtime.getTheaterID());
		TimeZone timeZone = TimeZone.getTimeZone(curentTheater.getTimezone());
		dtFormat.setTimeZone(timeZone);
		String dateFormat = dtFormat.format(today);
		today = ShowtimeQuery.datetimeConvert(dateFormat);
		
		Date showDate = ShowtimeQuery.datetimeConvert(showtime.getDateTime());
		
		if(today.after(showDate)) {
			ShowtimeQuery.deleteShowtime(showtime);
		}
	}

}
