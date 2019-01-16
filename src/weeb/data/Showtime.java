package weeb.data;

import java.util.HashMap;

public class Showtime {

	private int showtimeId;
	private String theaterID;
	private int movieID;
	private String dateTime;
	private String purchaseLink;
	
	public Showtime(int showtimeId, String theaterID, int movieID, String dateTime, String purchaseLink) {
		super();
		this.showtimeId = showtimeId;
		this.theaterID = theaterID;
		this.movieID = movieID;
		this.dateTime = dateTime;
		this.purchaseLink = purchaseLink;
	}

	public int getShowtimeId() {
		return showtimeId;
	}

	public void setShowtimeId(int showtimeId) {
		this.showtimeId = showtimeId;
	}

	public String getTheaterID() {
		return theaterID;
	}

	public void setTheaterID(String theaterID) {
		this.theaterID = theaterID;
	}

	public int getMovieID() {
		return movieID;
	}

	public void setMovieID(int movieID) {
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
