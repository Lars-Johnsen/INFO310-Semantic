package converting;

import java.io.FileReader;
import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ArtistConverter {
	
	/**
	 * Class for converting artistInfo from JSON format to, java objects.
	 * @author Lars Johnsen
	 * @return 
	 */

	
	public Artist convertArtist(){
		JsonParser parser = new JsonParser();
		Object obj = null;
		try {
			obj = parser.parse(new FileReader("artistJson.txt"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonObject artistObj = (JsonObject) obj;
		JsonObject artist = artistObj.get("artist").getAsJsonObject();




		ArrayList<String> similarArtistsURL = new ArrayList<String>();
		ArrayList<String> tagsArray = new ArrayList<String>();




		String name = artist.get("name").getAsString();
		String mbID = artist.get("mbid").getAsString();
		String artistURL = artist.get("url").getAsString();
		//Similar comes in an array of JsonObjects as URLS to lastfm.
		
		JsonObject similarObject = artist.get("similar").getAsJsonObject();
		
			JsonArray similarArtistArray = (JsonArray) similarObject.get("artist");
			
			for (JsonElement artistInSimilarArray : similarArtistArray){
				JsonObject similarArtistObject = (JsonObject) artistInSimilarArray;
				
				similarArtistsURL.add(similarArtistObject.get("url").getAsString());
			}	
			
			JsonObject tagsObject = artist.get("tags").getAsJsonObject();
			JsonArray tagsJsonArray = (JsonArray) tagsObject.get("tag");
			for(JsonElement tagsInArtist : tagsJsonArray){
				JsonObject tag = (JsonObject) tagsInArtist;
				
			
				tagsArray.add(tag.get("url").getAsString());
				
			}
			
			Artist artistInstance = new Artist(name, mbID, similarArtistsURL, tagsArray, artistURL);
			return artistInstance;
		
	}

	}


