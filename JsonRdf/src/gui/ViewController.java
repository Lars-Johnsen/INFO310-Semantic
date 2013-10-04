package gui;

import converting.Database;
import converting.GeoEvent;
import converting.GeoEventConverter;
import converting.RdfCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import lastfmapi.lastfm.Geo;
import lastfmapi.lastfm.Result;

import com.google.gson.JsonObject;
import com.hp.hpl.jena.rdf.model.Model;

public class ViewController implements ActionListener{
	private View view;
	public ViewController(View view){
		this.view = view;
		//		updateResultList();
	}

	public void updateResultList(ArrayList<GeoEvent> result){
		for(GeoEvent geo : result){
			System.out.println(geo.getEventName());
			view.getResults().addElement(geo);
		}

	}

	public void search(String term){

		if(term.equals("")){
			System.out.println("TOM-STRENG");
		}
		else{
			Result res = Geo.getAllEvents(term, "1", "64ecb66631fd1570172e9c44108b96d4");
			Database db = new Database();
			if(db.checkDBLocation(term)){
				System.out.println("Finnes lokalt");

			} else {
				GeoEventConverter geoEventConverter = new GeoEventConverter();
				JsonObject jsonObject = res.getJsonObject();
				ArrayList<GeoEvent> geoArray = geoEventConverter.eventDataGenerator(jsonObject);
				RdfCreator rdfCreator = new RdfCreator();
				Model model = rdfCreator.createRDF(geoArray);
				db.SaveDB(model);
			}

			updateResultList(db.getModelInfo(term));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Go!")){
			search(view.getInputText().getText());
		}
	}
}

