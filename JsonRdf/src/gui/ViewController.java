package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

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
	private int indexForList = 0;
	private ArrayList<String> recomendationType = new ArrayList<String>();
	private ArrayList<GeoEvent>eventList = new ArrayList<GeoEvent>();
	public ViewController(View view){
		this.view = view;

		view.getResultList().addMouseListener(this);

	}
	/**
	 * Method for updating the resultlist with the relevant concerts.
	 * @param result
	 */
	@SuppressWarnings("unchecked")
	public void updateResultList(ArrayList<GeoEvent> result){
		view.getResults().clear();
	
		for(GeoEvent geo : result){
			view.getResults().addElement(geo);
		}
		
		view.repaint();	
		view.validate();
	}
	/**
	 * Method for searching after concert.
	 * WIll first search through the local database before making a call to the last.fm API.
	 * @param term
	 */
	public void search(String term){
		Database db = Database.getInstance();

		if(term.equals("")){
			JOptionPane.showMessageDialog(view, "Please Specify a search term");
		}
		else{
			term = StringUtilities.searchCheck(term);
			if(searchterms.contains(term.toString())){
				
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
	
		
		view.getArtistBioArea().setText(geo.getArtist().getBio());
		view.getImagePanel().removeAll();
		view.getDetailsPanel().setVisible(true);
	}
	/**
	 * Action Performed Method.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource().equals(view.getInputText())){
			view.getResults().clear();
			try{
			search(view.getInputText().getText());
			}
			catch(NullPointerException n){
				JOptionPane.showMessageDialog(view, "No Concerts found");
			}
			}
		else if(e.getActionCommand().equals("attend")){
			GeoEvent event = (GeoEvent)view.getResultList().getSelectedValue();
			attend(event.getEventUrl());

		}
		else if(e.getActionCommand().equals("recommend")){
			
			
			updateRecomendations();

		}
		else if(e.getActionCommand().equals("getRecArtist")){
			placeGeoElementOnScreen(eventList.get(indexForList));
	}
	
	}
	/**
	 * Method for updating the recommendationlist.
	 */
	public void updateRecomendations() {
		eventList.clear();
		recomendationType.clear();
		
		Database db = Database.getInstance();
		LinkedHashSet<String>eventListNamesForSameArtist = new LinkedHashSet<String>();
		LinkedHashSet<String>eventListNamesForSimilarArtist = new LinkedHashSet<String>();
		LinkedHashSet<String>eventListNamesForSameGenre = new LinkedHashSet<String>();
		
		
		
		
		eventListNamesForSameArtist.addAll(db.sameArtistyouAttendedPlaysOnADifferentEvent());
		eventListNamesForSimilarArtist.addAll(db.ArtistyouAttendedHaveSimilar_TOWhoPlaysOnADifferentEvent());
		eventListNamesForSameGenre.addAll(db.getEventsBasedOnAttendedArtistsGenre());
	
		for(String string : eventListNamesForSameArtist){
			eventList.add(db.getModelInfoFromEvent(string));
			recomendationType.add("We have recommended this because you have attended a concert from the same artist artist plays another concert");
		}
		for(String string : eventListNamesForSimilarArtist){
			eventList.add(db.getModelInfoFromEvent(string));
			recomendationType.add("We have recommended this because a similar artist plays nearby");
		}
		for(String string : eventListNamesForSameGenre){
			eventList.add(db.getModelInfoFromEvent(string));
			recomendationType.add("We have Recommended this because another artist of the same genre plays");
		}
		placeRecommendedEvent(eventList, recomendationType);
		
		
		
	}
	/**
	 * Places the recommende concsert on the screen. 
	 * @param eventList
	 * @param recomendationType
	 */
	public void placeRecommendedEvent(ArrayList<GeoEvent> eventList, ArrayList<String> recomendationType){
//		ArrayList<GeoEvent> recommendedList = new ArrayList<GeoEvent>();
		
		String reason = "";	
		
		indexForList += 1;
		try{
		view.getRecomendArtist().setText(eventList.get(indexForList).getEventName());
		reason = recomendationType.get(indexForList);
		}
		catch(IndexOutOfBoundsException e){
		
		}
			view.getReasonLabel().setText(reason);
			view.getReasonLabel().repaint();
			view.getRecomendArtist().setVisible(true);
			view.getRecomendArtist().addActionListener(this);
			view.getRecomendedLabel().setVisible(true);
			view.repaint();
			view.validate();
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

	@SuppressWarnings("rawtypes")
	@Override
	public void mouseReleased(MouseEvent e) {

		JList list = view.getResultList();
		if (e.getClickCount() == 2) {
			int index = list.locationToIndex(e.getPoint());
			GeoEvent clickedEvent = (GeoEvent) view.getResults().get(index);
			view.getUserResponseButtonPanel().setVisible(true);
			placeGeoElementOnScreen(clickedEvent);
		} else if (e.getClickCount() == 3) {   // Triple-click
			

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

