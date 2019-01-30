package weeb.data;

import java.util.HashMap;

public class Movie {

	private int movieId;
	private String title;
	private String description;
	private int runtime;
	private String rating;
	private String officialLink;
	
	private HashMap<Integer, Showtime> showtimes;
	
	public Movie(int movieId, String title, String description, int runtime, String rating,
			String officialLink) {
		super();
		this.movieId = movieId;
		this.title = title;
		this.description = description;
		this.runtime = runtime;
		this.rating = rating;
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

	public String getRatingID() {
		return rating;
	}

	public void setRatingID(String rating) {
		this.rating = rating;
	}

	public String getOfficialLink() {
		return officialLink;
	}

	public void setOfficialLink(String officialLink) {
		this.officialLink = officialLink;
	}
	
}
