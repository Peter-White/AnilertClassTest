package weeb.power;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONObjectReader implements JSONReader {

	public JSONObject readJsonObjectFromUrl(String url) throws IOException, JSONException {
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
