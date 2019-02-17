package weeb.power;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import weeb.DBQuery.ShowtimeQuery;
import weeb.JSONQuery.TheaterJSONQuery;
import weeb.data.APIKeys;
import weeb.data.Showtime;

public class SeverUpdateApp {

	public static void main(String[] args) {

//		while (true) {
//			
//			new Thread() {
//
//				@Override
//				public void run() {
//					Set<Showtime> showtimes = ShowtimeQuery.queryAllShowtimes();
//					
//				}
//				
//			}.start();
//			
//		}
		
//		SimpleDateFormat timeToDie = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//		TimeZone timeZone = TimeZone.getTimeZone(new TheaterJSONQuery().getTimeZone(42.3912716, -71.1384544));
//		timeToDie.setTimeZone(timeZone);
//		Date date = new Date();
//		System.out.println(timeToDie.format(date));
		
		Showtime showtime = ShowtimeQuery.queryShowtime(124);
		ShowtimeQuery.deleteShowtime(showtime);
	}

}
