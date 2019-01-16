package weeb.data;

public class Rating {

	private int ratingId;
	private String name;
	
	public Rating(int ratingId, String name) {
		super();
		this.ratingId = ratingId;
		this.name = name;
	}

	public int getRatingId() {
		return ratingId;
	}

	public void setRatingId(int ratingId) {
		this.ratingId = ratingId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
