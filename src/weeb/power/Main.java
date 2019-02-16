package weeb.power;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import weeb.DBQuery.TheaterQuery;
import weeb.JSONQuery.JSONToSQL;
import weeb.JSONQuery.TheaterJSONQuery;
import weeb.data.Movie;
import weeb.data.Theater;

public class Main {
	public static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		// Unlike my previous version I'm breaking the menus into functions
		System.out.println("Welcome to the class based prototype for WeebWatch (Formally known as Anilert)");
		
		boolean quit = false;
		System.out.println();
		while (!quit) {
			JSONObject locationData = userLocationMenu();
			if(locationData != null) {
				double radius = getSearchRadiusMenu();
				if(radius != -1) {
					JSONToSQL jsonToSQL = new JSONToSQL();
					
					try {
						jsonToSQL.updateMovieTableByUserInput(locationData.getDouble("lat"), locationData.getDouble("lng"), radius);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						quit = true;
					}
					
					while(true) {
						Theater theater = theaterSelectMenu(jsonToSQL.getQueryedTheater());
						if(theater == null) {
							System.out.println("Back to location search");
							break;
						} else {
							
						}
					}
					
				} else {
					System.out.println("Back to location search");
				}
			}
			
		}
		
	}
	
	// Menu for selected the users local theaters
	public static Theater theaterSelectMenu(Map<Integer, Theater> theaters) {
		
		Map<Integer, Theater> selectableTheaters = new HashMap<>();
		
		int count = 1;
		for (Map.Entry<Integer, Theater> entry : theaters.entrySet()) {
			selectableTheaters.put(count, entry.getValue());
			count++;
		}
		
		while (true) {
			System.out.println("Your local theaters:");
			selectableTheaters.forEach((pos, theater) -> {
				System.out.println(pos + ": " + theater.getName());
			});
			System.out.println("Enter the number of the Theater to open it's menu or -1 to back out:");
			int choice = scanner.nextInt();
			scanner.nextLine();
			if(selectableTheaters.containsKey(choice)) {
				return selectableTheaters.get(choice);
			} else if (choice == -1) {
				return null;
			} else {
				System.out.println("Not an option. Please try again.");
			}
		}
	}
	
	public static void theaterMenu(Theater theater) {
		
		System.out.println(theater.getName());
		System.out.println(theater.getAddress());
		
		while(true) {
			
			HashMap<String, Set<Integer>> movieAndShowtimeIds = new HashMap<>();
			for (Map.Entry<String, Set<Integer>> entry : movieAndShowtimeIds.entrySet()) {
				
			}
			
		}
		
	}
	
	// User types in an address and sets the center for the movie search
	public static JSONObject userLocationMenu() {
		while (true) {
			System.out.println("Enter your place (or 'quit' to leave):");
			String myPlace = scanner.nextLine();
			if(!myPlace.equals("quit")) {
				Map<Integer, JSONObject> placeCandidates;
				try {
					placeCandidates = new TheaterJSONQuery().userLocationSearchResults(myPlace);
					if(!placeCandidates.isEmpty()) {
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
					} else {
						System.out.println("Sorry no results. Try again.");
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
	
	// User enters their search radius note: this will be replaced with a selection menu with fixed numbers
	public static double getSearchRadiusMenu() {
		while (true) {
			System.out.println("Enter your search radius (km) or -1 to go back to location selection:");
			double radius = scanner.nextDouble();
			scanner.nextLine();
			if(radius == -1) {
				return -1;
			} else if (radius > 0) {
				return radius;
			} else {
				System.out.println("You cannot enter negitive numbers. Try Again");
			}
		}
	}
}
