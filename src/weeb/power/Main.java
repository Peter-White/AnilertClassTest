package weeb.power;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weeb.DBQuery.TheaterQuery;
import weeb.JSONQuery.JSONToSQL;
import weeb.JSONQuery.TheaterJSONQuery;
import weeb.data.Theater;

public class Main {
	public static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		// Unlike my previous version I'm breaking the menus into functions
		System.out.println("Welcome to the class based prototype for WeebWatch (Formally known as Anilert)");
		
		boolean quit = false;
		System.out.println();
		while (!quit) {
			JSONObject locationData = userLocation();
			if(locationData != null) {
				System.out.println(locationData.toString());
			}
			
		}
//		
//		Map<String, JSONObject> userResults = new TheaterJSONQuery().userLocationSearchResults("Boston");
//		
//		JSONObject userLocation = userResults.get("Boston, MA, USA");
//
//		try {
//			double userLatitude = userLocation.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
//			double userLongitude = userLocation.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
//			
//			int userChosenRadius = 50;
//			
//			JSONToSQL jsonToSQL = new JSONToSQL();
//			jsonToSQL.updateMovieTableByUserInput(userLatitude, userLongitude, userChosenRadius);
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	// User types in an address and sets the center for the movie search
	public static JSONObject userLocation() {
		while (true) {
			System.out.println("Enter your place (or 'quit' to leave):");
			String myPlace = scanner.nextLine();
			if(!myPlace.equals("quit")) {
				Map<Integer, JSONObject> placeCandidates;
				try {
					placeCandidates = new TheaterJSONQuery().userLocationSearchResults(myPlace);
					System.out.println("Results:");
					placeCandidates.forEach((key, value) -> {
						try {
							System.out.println(key + ": " + value.getString("formatted_address"));
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
					boolean backToMain = false;
					while (!backToMain) {
						System.out.println("Enter your loaction number or 0 if it is not present");
						int choice = scanner.nextInt();
						scanner.nextLine();
						if(choice == 0) {
							System.out.println("Try to be more specific.");
							backToMain = true;
						} else if (placeCandidates.containsKey(choice)) {
							return placeCandidates.get(choice).getJSONObject("geometry").getJSONObject("location");
						} else {
							System.out.println("Selection not available. Please try again.");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		}
	}
	
	public static double getSearchRadius() {
		while (true) {
			
		}
	}
}
