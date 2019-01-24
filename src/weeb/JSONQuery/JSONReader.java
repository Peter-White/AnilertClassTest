package weeb.JSONQuery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class JSONReader {

	
	protected JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
		URL urlConvert = new URL(url);
		InputStream is = urlConvert.openStream();
		try {
		  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		  String jsonText = readJSONData(rd);
		  JSONObject json = new JSONObject(jsonText);
		  return json;
		} finally {
		  is.close();
		}
	};
	  
	protected JSONArray readJsonArrayFromUrl(String urlPath) throws IOException, JSONException {
		URL url = new URL(urlPath.toString());
		InputStream is = url.openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readJSONData(rd);
			JSONArray jsonArray = new JSONArray(jsonText);
			return jsonArray;
		} finally {
			is.close();
		}
	}
	
	private String readJSONData(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	};
	
}
