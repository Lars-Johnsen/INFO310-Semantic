package converting;

import java.util.ArrayList;

public class Artist {
	private String name;
	private String mbid;
	private ArrayList<String> similarArtists;
	private ArrayList<String> tags;

	public Artist(String name, String mbid, ArrayList<String> similar, ArrayList<String> tags){
		this.name = name;
		this.mbid = mbid;
		this.similarArtists = similar;
		this.tags = tags;
	}
}
