package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;

import javax.swing.JList;

import lastfmapi.lastfm.Geo;
import lastfmapi.lastfm.Result;
import lastfmapi.util.StringUtilities;

import com.google.gson.JsonObject;

import converting.Database;
import converting.GeoEvent;
import converting.GeoEventConverter;
import converting.RdfCreator;

public class ViewController implements ActionListener, MouseListener{
	private View view;
	private ArrayList<String>searchterms = new ArrayList<String>();
	public ViewController(View view){
		this.view = view;

		view.getResultList().addMouseListener(this);

		//		updateResultList();
	}

	public void updateResultList(ArrayList<GeoEvent> result){
		view.getResults().clear();
		//IMAGE
		//		MapDisplayer mapDisplayer= new MapDisplayer(); 
		//		RenderedImage img = mapDisplayer.generateMap(result);


		//IMAGE
		for(GeoEvent geo : result){

			//			System.out.println(geo.getEventName());
			view.getResults().addElement(geo);
		}
		//		BufferedImage myPicture = null;
		//		try {
		//			myPicture = ImageIO.read(new File("img/map.png"));
		//		} catch (IOException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		JLabel imageLabel = new JLabel(new ImageIcon(myPicture));

		//		view.setMapShower(imageLabel);

		//		view.getImagePanel().add(imageLabel);
		view.repaint();	
		view.validate();
	}

	public void search(String term){
		Database db = Database.getInstance();

		if(term.equals("")){
			System.out.println("TOM-STRENG");
		}
		else{
			term = StringUtilities.searchCheck(term);
			if(searchterms.contains(term.toString())){
				System.out.println("SEARCHTERMS CONTAINS" + " " + term);
			}
			else{

				view.getInputText().setText(term);
				Result res = Geo.getAllEvents(term, "0", "64ecb66631fd1570172e9c44108b96d4");

				GeoEventConverter geoEventConverter = GeoEventConverter.getInstance();
				JsonObject jsonObject = res.getJsonObject();
				
				ArrayList<GeoEvent> geoArray = geoEventConverter.eventDataGenerator(jsonObject);
				ArrayList<GeoEvent> geoDuplicates = new ArrayList<GeoEvent>();
				
				for(GeoEvent geoEvent : geoArray){
					if(db.checkEvent(geoEvent.getEventUrl())){
						geoDuplicates.add(geoEvent);
						System.out.println("fjerner " + geoEvent.getEventUrl() + " fra lista");
					}
				}
				geoArray.removeAll(geoDuplicates);
				RdfCreator rdfCreator = new RdfCreator();
				rdfCreator.createRDF(geoArray);
				searchterms.add(term);
			}

			updateResultList(db.getModelInfoFromLocation(term));

			view.repaint();
			view.validate();
			
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
		System.out.println(geo.getArtist().getName());
		System.out.println(geo.getArtist().getBio());
		view.getArtistBioArea().setText(geo.getArtist().getBio());
		view.getImagePanel().removeAll();
		view.getDetailsPanel().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(view.getInputText())){
			view.getResults().clear();
			search(view.getInputText().getText());
		}
		else if(e.getActionCommand().equals("attend")){
			GeoEvent event = (GeoEvent)view.getResultList().getSelectedValue();
			attend(event.getEventUrl());

		}
		else if(e.getActionCommand().equals("recommend")){
		
			Database db = Database.getInstance();
			LinkedHashSet<String>eventListNames = new LinkedHashSet<String>();
			ArrayList<GeoEvent>eventList = new ArrayList<GeoEvent>();


			eventListNames.addAll(db.sameArtistyouAttendedPlaysOnADifferentEvent());
			eventListNames.addAll(db.ArtistyouAttendedHaveSimilar_TOWhoPlaysOnADifferentEvent());
			eventListNames.addAll(db.getEventsBasedOnAttendedArtistsGenre());

			//			eventListNames.addAll(db.getEventsAttended());
			System.out.println("HIT: Samme artist har en konsert et annet sted");
			System.out.println(db.sameArtistyouAttendedPlaysOnADifferentEvent());
			System.out.println("HIT: En similar artist av en artist du attender har konsert");
			System.out.println(db.ArtistyouAttendedHaveSimilar_TOWhoPlaysOnADifferentEvent());
			System.out.println("HIT: En annen artist med lik genre har konsert");
			System.out.println(db.getEventsBasedOnAttendedArtistsGenre());

			for(String string : eventListNames){
				eventList.add(db.getModelInfoFromEvent(string));
			}
			updateResultList(eventList);


		}
	}

	public void attend (String Eventuri){
		Database db = Database.getInstance();
		db.attend(Eventuri);

	}
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		@SuppressWarnings("unchecked")
		JList list = view.getResultList();
		if (e.getClickCount() == 2) {
			int index = list.locationToIndex(e.getPoint());
			System.out.println(index);
			GeoEvent clickedEvent = (GeoEvent) view.getResults().get(index);
			view.getUserResponseButtonPanel().setVisible(true);
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

