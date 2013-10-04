package converting;

public class GeoEvent {
	private String eventName;
	private String eventID;
	private String headliner;
	private String date;
	private Venue venue;
	private String bandwebsite;
	private String eventWebsite;
	
	private String anders = "ANDERS";
	private String lars = "LARS";
	private String eskil = "ESKIL";
	private String chris = "CHRIS";
	
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

	@Override
	public String toString() {
		return "GeoEvent [eventName=" + eventName + ", eventID=" + eventID
				+ ", headliner=" + headliner + ", date=" + date + ", venue="
				+ venue + ", getEventName()=" + getEventName()
				+ ", getEventID()=" + getEventID() + ", getHeadliner()="
				+ getHeadliner() + ", getDate()=" + getDate() + ", getVenue()="
				+ getVenue() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
}