package converting;


import java.util.ArrayList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ArtistConverter {
	
	/**
	 * Class for converting artistInfo from JSON format to, java objects.
	 * @author Lars Johnsen
	 * @return Artist return a java object of the artist.
	 */
	public Artist convertArtist(JsonObject artistObj){

		JsonObject artist = artistObj.get("artist").getAsJsonObject();
		ArrayList<String> similarArtistsURL = new ArrayList<String>();
		ArrayList<String> tagsArray = new ArrayList<String>();

		String name = artist.get("name").getAsString();
		String mbID = artist.get("mbid").getAsString();
		String artistURL = artist.get("url").getAsString();

		//Similar comes in an array of JsonObjects as URLS to lastfm.
		if(artist.get("similar").isJsonObject()){
			JsonObject similarObject = artist.get("similar").getAsJsonObject();


			//If similarobject is an Array loop trough and collect all instances.
			if(similarObject.get("artist").isJsonArray()){
				JsonArray similarArtistArray = (JsonArray) similarObject.get("artist");

				for (JsonElement artistInSimilarArray : similarArtistArray){
					JsonObject similarArtistObject = (JsonObject) artistInSimilarArray;

					similarArtistsURL.add(similarArtistObject.get("url").getAsString());
				}	
			}
		}
		if(artist.get("tags").isJsonObject()){
			JsonObject tagsObject = artist.get("tags").getAsJsonObject();
			if(tagsObject.isJsonArray()){
				JsonArray tagsJsonArray = (JsonArray) tagsObject.get("tag");
				for(JsonElement tagsInArtist : tagsJsonArray){
					JsonObject tag = (JsonObject) tagsInArtist;


					tagsArray.add(tag.get("url").getAsString());
				}
			}
		}

		Artist artistInstance = new Artist(name, mbID, similarArtistsURL, tagsArray, artistURL);
		return artistInstance;

	}

}


