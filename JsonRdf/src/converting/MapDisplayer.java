package converting;

import java.awt.image.RenderedImage;
import java.io.File;
import java.net.URL;
import java.util.*;
import javax.imageio.ImageIO;

public class MapDisplayer {
	private String inputCity = "tokyo";


	
	public RenderedImage generateMap(ArrayList<GeoEvent> s){
		RenderedImage img = null;
		String city = s.get(0).getVenue().getCity();
	
		System.out.println("Du lager bidlet");

		String url= "http://maps.googleapis.com/maps/api/staticmap";
		url+= "?center="+city;
		url+=inputCity+"&zoom=7&size=400x300&maptype=roadmap";
		url +="&sensor=false";
		for(GeoEvent geo : s){

			double lat = geo.getVenue().getLatitude();
			double longitude = geo.getVenue().getLongitude();
			url += "&markers=color:red|label:A|" + lat + "," + longitude;
		}
		try {
			URL imageURL = new URL(url);
			img = ImageIO.read(imageURL);
			File outputfile = new File("img/map.png");
			ImageIO.write(img, "png",outputfile);







			System.out.println("Saved!");
		} catch (Exception ex) {
			System.out.println("Error!" + ex);
		}
		return img;
	}
}


