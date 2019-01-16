package weeb.data;

import java.util.HashMap;

public class Movie {

	private int movieId;
	private String title;
	private String released;
	private String description;
	private int runtime;
	private int ratingID;
	private String officialLink;
	
	private HashMap<Integer, Showtime> showtimes;
	
	public Movie(int movieId, String title, String released, String description, int runtime, int ratingID,
			String officialLink) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.released = released;
		this.description = description;
		this.runtime = runtime;
		this.ratingID = ratingID;
		this.officialLink = officialLink;
		showtimes = new HashMap<>();
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleased() {
		return released;
	}

	public void setReleased(String released) {
		this.released = released;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public int getRatingID() {
		return ratingID;
	}

	public void setRatingID(int ratingID) {
		this.ratingID = ratingID;
	}

	public String getOfficialLink() {
		return officialLink;
	}

	public void setOfficialLink(String officialLink) {
		this.officialLink = officialLink;
	}
	
}
