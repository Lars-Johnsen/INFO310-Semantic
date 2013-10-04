package converting;

public class Venue {
	private String name;
	private String venueID;
	public String getVenueID() {
		return venueID;
	}
	public void setVenueID(String venueID) {
		this.venueID = venueID;
	}
	private double latitude;
	private double longitude;
	private String city;
	private String country;
	private String street;
	private String postalCode;
	private String url;
	private String venueWebsiteUrl;
	private String phoneNumber;
	
	
	public Venue(String name, String venuID, double latitude, double longitude, String city,
			String country, String street, String postalCode, String url,
			String venueWebsiteUrl, String phoneNumber) {
		super();
		this.name = name;
		this.venueID = venuID;
		this.latitude = latitude;
		this.longitude = longitude;
		this.city = city;
		this.country = country;
		this.street = street;
		this.postalCode = postalCode;
		this.url = url;
		this.venueWebsiteUrl = venueWebsiteUrl;
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getVenueWebsiteUrl() {
		return venueWebsiteUrl;
	}
	public void setVenueWebsiteUrl(String venueWebsiteUrl) {
		this.venueWebsiteUrl = venueWebsiteUrl;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
