package weeb.power;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;

public class JSONArrayReader implements JSONReader {

	public JSONArray readJsonArrayFromUrl(String urlPath) throws IOException, JSONException {
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
	
	@Override
	public String readJSONData(Reader rd) {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    try {
			while ((cp = rd.read()) != -1) {
			  sb.append((char) cp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return sb.toString();
	}

}
