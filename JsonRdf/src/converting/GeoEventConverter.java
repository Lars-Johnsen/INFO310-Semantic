package converting;


import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;


public class GeoEventConverter {
	private static GeoEventConverter geoEventConverter;


	private GeoEventConverter(){
		
		
		
	}
	
	public static GeoEventConverter getInstance(){
		if(geoEventConverter == null) {
			geoEventConverter = new GeoEventConverter();
			return geoEventConverter;
		}
		else{
			return geoEventConverter;
		}
	}
	

	/**
	 * Converting GeoEvents from Json to Java objects, and
	 * returns an arraylist of GeoEvents
	 */
	public ArrayList<GeoEvent> eventDataGenerator(JsonObject jsonObject){
		ArrayList<GeoEvent> events = new ArrayList<GeoEvent>();
		try {
			JsonObject jobject = (JsonObject) jsonObject.getAsJsonObject("events");
			JsonArray eventOb = jobject.getAsJsonArray("event");

			for(JsonElement s : eventOb){
				JsonObject event = s.getAsJsonObject();
				String artist = event.get("title").getAsString();
				String eventID = event.get("id").getAsString();

				/**
				 * Getting headliner, and support bands
				 */
				JsonObject artists = event.getAsJsonObject("artists");
				JsonPrimitive headliner = (JsonPrimitive)artists.getAsJsonPrimitive("headliner");
				
				String Headliner = headliner.getAsString();
				ArrayList<String> support = new ArrayList<String>();
				String eventUrl = event.get("url").getAsString();

				if(artists.get("artist").isJsonArray()){

					JsonElement supList = artists.get("artist");
					JsonArray list = supList.getAsJsonArray();
					for(JsonElement c : list){
						support.add(c.getAsString());
					}
				}

				/**
				 * getting venue data.
				 */
				JsonObject venue = event.getAsJsonObject("venue");
				JsonElement venuID = venue.get("id");
				String StringID = venuID.getAsString();
				String VenueName = venue.get("name").getAsString();
				JsonObject location = venue.getAsJsonObject("location");

				/**
				 * Address and location
				 */
				JsonObject getPoint = location.getAsJsonObject("geo:point");


				double geoLat = getPoint.get("geo:lat").getAsDouble();
				double geoLong = getPoint.get("geo:long").getAsDouble();

				String city = location.get("city").getAsString();
				String country = location.get("country").getAsString();
				String street = location.get("street").getAsString();
				String postalCode = "0000";

				try{
					postalCode = location.get("postalcode").getAsString();
				} catch (Exception e) {
					e.printStackTrace(); 
					postalCode = "0000";
				}

				String url = venue.get("url").getAsString();
				String website = venue.get("website").getAsString();
				String phone = venue.get("phonenumber").getAsString();
				String date;

				try{
					date = event.get("startDate").getAsString();
				} catch (Exception e) {
					e.printStackTrace();
					date = "010101";
				}


				GeoEvent geoEvent = new GeoEvent(artist, eventID, Headliner,date, VenueName, StringID,
						geoLat, geoLong, city, country, street, postalCode, url, website, eventUrl, phone);
				events.add(geoEvent);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return events;

	}
}
