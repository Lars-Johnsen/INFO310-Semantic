package gui;

import converting.Database;
import converting.GeoEvent;
import converting.GeoEventConverter;
import converting.RdfCreator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;

import javax.swing.JList;

import lastfmapi.lastfm.Geo;
import lastfmapi.lastfm.Result;

import com.google.gson.JsonObject;
import com.hp.hpl.jena.rdf.model.Model;

public class ViewController implements ActionListener, MouseListener{
	private View view;
	public ViewController(View view){
		this.view = view;

		view.getResultList().addMouseListener(this);

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
	/**
	 * Places info about an event on the east panel of the screen
	 * @param geo the clicked event from the resultList
	 */
	public void placeGeoElementOnScreen(GeoEvent geo){
		
		view.getEventNameArea().setText(geo.getEventName());
		view.getEventIdArea().setText(geo.getEventID());
		view.getHeadlinerArea().setText(geo.getHeadliner());
		view.getDateArea().setText(geo.getDate());
		view.getVenueArea().setText(geo.getVenue().getName());
		view.getBandWebsiteArea().setText(geo.getEventWebsite());
		
		view.getEastPanel().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Go!")){
			view.getResults().clear();
			search(view.getInputText().getText());
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JList list = (JList)e.getSource();
		if (e.getClickCount() == 2) {
			int index = list.locationToIndex(e.getPoint());
			System.out.println(index);
			GeoEvent clickedEvent = (GeoEvent) view.getResults().get(index);
			placeGeoElementOnScreen(clickedEvent);
		} else if (e.getClickCount() == 3) {   // Triple-click
			int index = list.locationToIndex(e.getPoint());

		}
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

