package converting;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;

public class MapDisplayer {
private String inputCity = "tokyo";
	
	
	public MapDisplayer(ArrayList<GeoEvent> s){
	
		
		generateMap();	
	}
	private void generateMap(){
	try {
		URL url= new URL("http://maps.googleapis.com/maps/api/staticmap?center="
				+inputCity+"&zoom=14&size=600x300&maptype=roadmap"
				+"&sensor=false");
		
		RenderedImage img = ImageIO.read(url);
		File outputfile = new File("map.png");
		ImageIO.write(img, "png",outputfile);
		
		
		
		
		
		
		
		System.out.println("Saved!");
		} catch (Exception ex) {
		System.out.println("Error!" + ex);
		}
		
}
}


