package converting;

import gui.View;

import java.util.ArrayList;

import lastfmapi.lastfm.Geo;
import lastfmapi.lastfm.Result;

import com.google.gson.JsonObject;
import com.hp.hpl.jena.rdf.model.Model;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				View view = new View();
		Result res = Geo.getAllEvents("London", "1", "64ecb66631fd1570172e9c44108b96d4");
		Database db = new Database();
		if(db.checkDBLocation("London")){
			System.out.println("Finnes lokalt");
			
		} else {
			GeoEventConverter geoEventConverter = new GeoEventConverter();
			JsonObject jsonObject = res.getJsonObject();
			ArrayList<GeoEvent> geoArray = geoEventConverter.eventDataGenerator(jsonObject);
			RdfCreator rdfCreator = new RdfCreator();
			Model model = rdfCreator.createRDF(geoArray);
			db.SaveDB(model);
		}
		for(GeoEvent geo : db.getModelInfo("London")){
			System.out.println(geo.getEventName());
		}
	}

}
