package converting;

import java.util.ArrayList;

/**
 * Class For representing the artist in Java objevt form
 * @author lars
 *
 */



public class Artist {
	private String name;

	private ArrayList<String> similarArtists;
	private ArrayList<String> tags;
	private String artistURL;
	private String bio;

	public Artist(String name, ArrayList<String> similar, ArrayList<String> tags,
			String artistURL, String bioBeforetransform){
		this.name = name;

		this.similarArtists = similar;
		this.tags = tags;
		this.artistURL = artistURL;
		
		this.bio = bioBeforetransform.trim().replaceAll(" +", " ").replaceAll("[\\t\\n\\r]"," ").replaceAll("\\<.*?>", "").replaceAll("&quot", "");
		
		
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public ArrayList<String> getSimilarArtists() {
		return similarArtists;
	}

	public void setSimilarArtists(ArrayList<String> similarArtists) {
		this.similarArtists = similarArtists;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public String getArtistURL() {
		return artistURL;
	}

	public void setArtistURL(String artistURL) {
		this.artistURL = artistURL;
	}
}
