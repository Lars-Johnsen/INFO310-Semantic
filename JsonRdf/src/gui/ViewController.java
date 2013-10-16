package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import lastfmapi.lastfm.Geo;
import lastfmapi.lastfm.Result;
import lastfmapi.util.StringUtilities;

import com.google.gson.JsonObject;
import com.hp.hpl.jena.rdf.model.Model;

import converting.Database;
import converting.GeoEvent;
import converting.GeoEventConverter;
import converting.MapDisplayer;
import converting.RdfCreator;

public class ViewController implements ActionListener, MouseListener{
	private View view;
	public ViewController(View view){
		this.view = view;

		view.getResultList().addMouseListener(this);

		//		updateResultList();
	}

	public void updateResultList(ArrayList<GeoEvent> result){

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

		if(term.equals("")){
			System.out.println("TOM-STRENG");
		}
		else{
			term = StringUtilities.searchCheck(term);
			view.getInputText().setText(term);
			Result res = Geo.getAllEvents(term, "1", "64ecb66631fd1570172e9c44108b96d4");
			Database db = Database.getInstance();
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
		view.getImagePanel().removeAll();
		view.getDetailsPanel().setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("Go!")){
			view.getResults().clear();
			search(view.getInputText().getText());
		}
		else if(e.getActionCommand().equals("attend")){
			GeoEvent event = (GeoEvent)view.getResultList().getSelectedValue();
			attend(event.getEventUrl());

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

