package weeb.power;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import weeb.DBQuery.ShowtimeQuery;
import weeb.DBQuery.TheaterQuery;
import weeb.JSONQuery.TheaterJSONQuery;
import weeb.data.APIKeys;
import weeb.data.Showtime;
import weeb.data.Theater;

public class SeverUpdateApp {

	public static void main(String[] args) {

//		while (true) {
//			
//			new Thread() {
//
//				@Override
//				public void run() {
					SimpleDateFormat dtFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
					Date today = new Date();
					Set<Showtime> showtimes = ShowtimeQuery.queryAllShowtimes();
					
					for (Showtime showtime : showtimes) {
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
					
//				}
//				
//			}.start();
			
//		}
		
//		SimpleDateFormat timeToDie = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//		TimeZone timeZone = TimeZone.getTimeZone(new TheaterJSONQuery().getTimeZone(42.3912716, -71.1384544));
//		timeToDie.setTimeZone(timeZone);
//		Date date = new Date();
//		System.out.println(timeToDie.format(date));
//		
//		Showtime showtime = ShowtimeQuery.queryShowtime(124);
//		ShowtimeQuery.deleteShowtime(showtime);
		
//		Date oldDate = new Date(2018-1900, 3, 12, 13, 16);
//		Date today = new Date();
//		
//		System.out.println(today);
//		System.out.println(today.after(oldDate));
		
	}

}
