package converting;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public class GeoEvent {
	private String eventName;
	private String eventID;
	private String headliner;
	private String date;
	private Venue venue;
	private Artist artist;
	private String bandwebsite;
	private String eventWebsite;
	
	public GeoEvent(String eventName, String eventID, String headliner,
			String date, String venueName,String venueId, double lat,double longitude,
			String city,String country, String street, String postalCode, String venueURL, 
			String website, String lastFMEventUrl, String phonenumber) {
		this.eventName = eventName;
		this.eventID = eventID;
		this.headliner = headliner;
		this.date = date;
		this.venue = new Venue(venueName,venueId, lat, longitude, city, country, street, postalCode, venueURL,
				website, phonenumber);
		this.bandwebsite = lastFMEventUrl;
		this.eventWebsite = lastFMEventUrl;	
	
		
		
		
		
		lastfmapi.lastfm.Artist lastFmArtist = new lastfmapi.lastfm.Artist();
		JsonObject jsonArtist = lastFmArtist.getInfo(headliner, "64ecb66631fd1570172e9c44108b96d4").getJsonObject();


		
		ArtistConverter artistConverter = new ArtistConverter();

		this.artist = artistConverter.convertArtist(jsonArtist);
		


	}
	public GeoEvent(String eventName, String eventID, String headliner,
			String date, String venueName,String venueId, double lat,double longitude,
			String city,String country, String street, String postalCode, String venueURL, 
			String website, String lastFMEventUrl, String phonenumber, ArrayList<String> tags, String bio, ArrayList<String> similar_to, String artistName, String artistURL) {
		this.eventName = eventName;
		this.eventID = eventID;
		this.headliner = headliner;
		this.date = date;
		this.venue = new Venue(venueName,venueId, lat, longitude, city, country, street, postalCode, venueURL,
				website, phonenumber);
		this.bandwebsite = lastFMEventUrl;
		this.eventWebsite = lastFMEventUrl;	
		this.artist = new Artist(artistName, similar_to, tags, artistURL, bio);
	}
	
	
	public Artist getArtist() {
		return artist;
	}


	public void setArtist(Artist artist) {
		this.artist = artist;
	}


	public String getEventWebsite() {
		return eventWebsite;
	}

	public void setEventWebsite(String bandWebsite) {
		this.eventWebsite = bandWebsite;
	}

	public String getEventUrl() {
		return bandwebsite;
	}

	public void setEventUrl(String eventUrl) {
		this.bandwebsite = eventUrl;
	}

	public String getEventName() {
		return eventName;
	}
	
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	
	public String getEventID() {
		return eventID;
	}
	
	public void setEventID(String eventID) {
		this.eventID = eventID;
	}
	
	public String getHeadliner() {
		return headliner;
	}
	
	public void setHeadliner(String headliner) {
		this.headliner = headliner;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public Venue getVenue() {
		return venue;
	}
	
	public void setVenue(Venue venue) {
		this.venue = venue;
	}


	public String toString(){
		return eventName + " with " + headliner + "@" + getVenue();
	}
}