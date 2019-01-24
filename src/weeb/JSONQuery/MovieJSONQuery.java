package weeb.JSONQuery;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weeb.data.APIKeys;

public class MovieJSONQuery extends weeb.JSONQuery.JSONReader {

	private final String graceNoteURLStart = "http://data.tmsapi.com/v1.1/movies/showings?startDate=" + currentDate();
	public final String numDays = "&numDays=60";
	private StringBuilder urlPath;
	private JSONArray jsonArray;
	
	private Map<String, JSONObject> movieQuery;
	
	public MovieJSONQuery() {
		movieQuery = new HashMap<String, JSONObject>();
	}
	
	public JSONArray queryMovieTheaterJSON(Double lat, Double lng, int radius) throws IOException, JSONException {
		urlPath = new StringBuilder();
		urlPath.append(graceNoteURLStart);
		urlPath.append(numDays);
		urlPath.append("&lat=" + lat);
		urlPath.append("&lng=" + lng);
		urlPath.append("&radius=" + radius);
		urlPath.append("&units=km");
		urlPath.append("&api_key=");
		urlPath.append(APIKeys.getGracenoteAPIKey());
		
		return readJsonArrayFromUrl(urlPath.toString());
	}
	
	public String currentDate() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return df.format(date).toString();
	}
}
