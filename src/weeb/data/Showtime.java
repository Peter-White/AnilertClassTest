package weeb.data;

import java.util.HashMap;

public class Showtime {

	private int showtimeId;
	private int theaterID;
	private String movieID;
	private String dateTime;
	private String purchaseLink;
	
	public Showtime(int showtimeId, int theaterID, String movieID, String dateTime, String purchaseLink) {
		super();
		this.showtimeId = showtimeId;
		this.theaterID = theaterID;
		this.movieID = movieID;
		this.dateTime = dateTime;
		this.purchaseLink = purchaseLink;
	}
	
	public Showtime() {
		
	}

	public int getShowtimeId() {
		return showtimeId;
	}

	public void setShowtimeId(int showtimeId) {
		this.showtimeId = showtimeId;
	}

	public int getTheaterID() {
		return theaterID;
	}

	public void setTheaterID(int theaterID) {
		this.theaterID = theaterID;
	}

	public String getMovieID() {
		return movieID;
	}

	public void setMovieID(String movieID) {
		this.movieID = movieID;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	public String getPurchaseLink() {
		return purchaseLink;
	}

	public void setPurchaseLink(String purchaseLink) {
		this.purchaseLink = purchaseLink;
	}
	
}
