package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class View extends JFrame {

	private JPanel			northPanel = new JPanel();
	private JPanel			westPanel = new JPanel();
	private JPanel			eastPanel = new JPanel();
	private JPanel			detailsPanel = new JPanel();
	private JPanel 			imagePanel = new JPanel();
	private JPanel			southPanel = new JPanel();
	private JTextField		inputText = new JTextField();
	private JButton			goButton = new JButton("Go!");
	private DefaultListModel results      = new DefaultListModel();
	private JList            resultList   = new JList(results);
//	private JLabel 				mapShower = new JLabel();
	private ViewController 			viewController = new ViewController(this);
	private JButton					attend = new JButton("attend");
	private JButton					recommend = new JButton("recommend");

	private String stringForTest = "DETTE ER EN TEST STRENG OG SKAL";
	/**
	 * Creating fields for GUI elements
	 */
	private JLabel eventNameLabel = new JLabel("Eventname: ");
	private JLabel eventNameArea = new JLabel(stringForTest);
	private JPanel eventNamePanel = new JPanel();

	private JLabel eventIdLabel = new JLabel("Event ID: ");
	private JLabel eventIdArea = new JLabel(stringForTest);
	private JPanel eventIdpanel = new JPanel();

	private JLabel headLinerLabel = new JLabel("Headliner: ");
	private JLabel headlinerArea = new JLabel(stringForTest);
	private JPanel headlinerpanel = new JPanel();

	private JLabel dateLabel = new JLabel("Date: ");
	private JLabel DateArea = new JLabel(stringForTest);
	private JPanel datePanel = new JPanel();

	private JLabel venueLabel = new JLabel("Venue: ");
	private JLabel venueArea = new JLabel(stringForTest);
	private JPanel venuePanel = new JPanel();

	private JLabel BandWebsiteLabel = new JLabel("Bandwebsite: ");
	private JLabel BandWebsiteArea = new JLabel(stringForTest);
	private JPanel BandWebsitePanel = new JPanel();

	private JLabel eventWebsiteLabel = new JLabel("Bandwebsite: ");
	private JLabel eventWebsiteArea = new JLabel(stringForTest);
	private JPanel eventWebsitePanel = new JPanel();
	
	

	public View(){
		setupInterFace();
	}

	private void setupInterFace() {	
		this.setPreferredSize(new Dimension(1024, 1224));

		inputText.setPreferredSize(new Dimension(100, 20));
		resultList.setPreferredSize(new Dimension(100, 200));
//		mapShower.setPreferredSize(new Dimension(400, 300));
//		mapShower.setBorder(BorderFactory.createBevelBorder(DEFAULT_CURSOR));
		
//		System.out.println(mapShower.getWidth());
		
//		mapShower.setBackground(Color.BLACK);
		northPanel.removeAll();
		northPanel.setPreferredSize(new Dimension(1024, 120));
		northPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		southPanel.removeAll();
		southPanel.setPreferredSize(new Dimension(1024, 120));
		southPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));

		eastPanel.removeAll();
		eastPanel.setPreferredSize(new Dimension(512,384));
		eastPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
		eastPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

		westPanel.removeAll();
		westPanel.setPreferredSize(new Dimension(512, 384));
		westPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));

		BufferedImage myPicture = null;
		try {
			myPicture = ImageIO.read(new File("img/lastfmlogo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JLabel imageLabel = new JLabel(new ImageIcon(ImageUtilities.resize(myPicture, 222, 120)));



		JLabel inputTextLabel = new JLabel("Type in your area:");
		JPanel inputArea = new JPanel();



		//Code for creating the Eventname area

		Dimension detailPanelDimension = new Dimension(400, 50);
		Dimension resultListDimension = new Dimension(400, 200);

		inputText.setPreferredSize(detailPanelDimension);


		eventNamePanel.add(eventNameLabel);
		eventNamePanel.add(eventNameArea);
		eventNamePanel.setPreferredSize(detailPanelDimension);

		//Event ID field and presentation

		eventIdpanel.add(eventIdLabel);
		eventIdpanel.add(eventIdArea);
		eventIdpanel.setPreferredSize(detailPanelDimension);

		//headliner field and presentation

		headlinerpanel.add(headLinerLabel);
		headlinerpanel.add(headlinerArea);
		headlinerpanel.setPreferredSize(detailPanelDimension);

		//date field and presentation

		datePanel.add(dateLabel);
		datePanel.add(DateArea);
		datePanel.setPreferredSize(detailPanelDimension);

		//venue field and presentation

		venuePanel.add(venueLabel);
		venuePanel.add(venueArea);
		venuePanel.setPreferredSize(detailPanelDimension);

		//Bandwebsite field and presentation

		BandWebsitePanel.add(BandWebsiteLabel);
		BandWebsitePanel.add(BandWebsiteArea);
		BandWebsitePanel.setPreferredSize(detailPanelDimension);

		//Bandwebsite field and presentation

		eventWebsitePanel.add(eventWebsiteLabel);
		eventWebsitePanel.add(eventWebsiteArea);
		eventWebsitePanel.setPreferredSize(detailPanelDimension);


		

		inputArea.add(inputTextLabel);
		inputArea.add(inputText);
		inputArea.add(goButton);
		inputArea.add(attend);
		inputArea.add(recommend);
		inputArea.setPreferredSize(new Dimension(500, 200));

		northPanel.add(imageLabel);
		resultList.setPreferredSize(resultListDimension);
		
		westPanel.add(inputArea);
		westPanel.add(resultList);

		detailsPanel.add(eventNamePanel);
		detailsPanel.add(eventIdpanel);
		detailsPanel.add(headlinerpanel);
		detailsPanel.add(datePanel);
		detailsPanel.add(venuePanel);
		detailsPanel.add(BandWebsitePanel);
		detailsPanel.add(eventWebsitePanel);

		
		detailsPanel.setVisible(false);
		eastPanel.add(detailsPanel);
		eastPanel.add(imagePanel);
		
		goButton.setBorderPainted(false);
		goButton.setContentAreaFilled(false);
		goButton.setOpaque(false);
		
		//TODO
		// IFølge MVC skal denne ligge i controller?
		goButton.addActionListener(viewController);
		goButton.setActionCommand("Go!");
		attend.addActionListener(viewController);
		attend.setActionCommand("attend");
		recommend.addActionListener(viewController);
		recommend.setActionCommand("recommend");
		

		getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		this.getContentPane().add(westPanel, BorderLayout.WEST);
		this.getContentPane().add(eastPanel, BorderLayout.EAST);
		this.getContentPane().add(southPanel, BorderLayout.SOUTH);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setBounds(0, 0, 1024, 768);
	}

	public JPanel getDetailsPanel() {
		return detailsPanel;
	}

	public void setDetailsPanel(JPanel detailsPanel) {
		this.detailsPanel = detailsPanel;
	}

	public JPanel getImagePanel() {
		return imagePanel;
	}

	public void setImagePanel(JPanel imagePanel) {
		this.imagePanel = imagePanel;
	}

	public JPanel getSouthPanel() {
		return southPanel;
	}

	public void setSouthPanel(JPanel southPanel) {
		this.southPanel = southPanel;
	}


	public JPanel getNorthPanel() {
		return northPanel;
	}

	public void setNorthPanel(JPanel northPanel) {
		this.northPanel = northPanel;
	}

	public JPanel getWestPanel() {
		return westPanel;
	}

	public void setWestPanel(JPanel westPanel) {
		this.westPanel = westPanel;
	}

	public JPanel getEastPanel() {
		return eastPanel;
	}

	public void setEastPanel(JPanel eastPanel) {
		this.eastPanel = eastPanel;
	}

	public JButton getGoButton() {
		return goButton;
	}

	public void setGoButton(JButton goButton) {
		this.goButton = goButton;
	}

	public ViewController getViewController() {
		return viewController;
	}

	public void setViewController(ViewController viewController) {
		this.viewController = viewController;
	}

	public String getStringForTest() {
		return stringForTest;
	}

	public void setStringForTest(String stringForTest) {
		this.stringForTest = stringForTest;
	}

	public JLabel getEventNameLabel() {
		return eventNameLabel;
	}

	public void setEventNameLabel(JLabel eventNameLabel) {
		this.eventNameLabel = eventNameLabel;
	}

	public JLabel getEventNameArea() {
		return eventNameArea;
	}

	public void setEventNameArea(JLabel eventNameArea) {
		this.eventNameArea = eventNameArea;
	}

	public JPanel getEventNamePanel() {
		return eventNamePanel;
	}

	public void setEventNamePanel(JPanel eventNamePanel) {
		this.eventNamePanel = eventNamePanel;
	}

	public JLabel getEventIdLabel() {
		return eventIdLabel;
	}

	public void setEventIdLabel(JLabel eventIdLabel) {
		this.eventIdLabel = eventIdLabel;
	}

	public JLabel getEventIdArea() {
		return eventIdArea;
	}

	public void setEventIdArea(JLabel eventIdArea) {
		this.eventIdArea = eventIdArea;
	}

	public JPanel getEventIdpanel() {
		return eventIdpanel;
	}

	public void setEventIdpanel(JPanel eventIdpanel) {
		this.eventIdpanel = eventIdpanel;
	}

	public JLabel getHeadLinerLabel() {
		return headLinerLabel;
	}

	public void setHeadLinerLabel(JLabel headLinerLabel) {
		this.headLinerLabel = headLinerLabel;
	}

	public JLabel getHeadlinerArea() {
		return headlinerArea;
	}

	public void setHeadlinerArea(JLabel headlinerArea) {
		this.headlinerArea = headlinerArea;
	}

	public JPanel getHeadlinerpanel() {
		return headlinerpanel;
	}

	public void setHeadlinerpanel(JPanel headlinerpanel) {
		this.headlinerpanel = headlinerpanel;
	}

	public JLabel getDateLabel() {
		return dateLabel;
	}

	public void setDateLabel(JLabel dateLabel) {
		this.dateLabel = dateLabel;
	}

	public JLabel getDateArea() {
		return DateArea;
	}

	public void setDateArea(JLabel dateArea) {
		DateArea = dateArea;
	}

	public JPanel getDatePanel() {
		return datePanel;
	}

	public void setDatePanel(JPanel datePanel) {
		this.datePanel = datePanel;
	}

	public JLabel getVenueLabel() {
		return venueLabel;
	}

	public void setVenueLabel(JLabel venueLabel) {
		this.venueLabel = venueLabel;
	}

	public JLabel getVenueArea() {
		return venueArea;
	}

	public void setVenueArea(JLabel venueArea) {
		this.venueArea = venueArea;
	}

	public JPanel getVenuePanel() {
		return venuePanel;
	}

	public void setVenuePanel(JPanel venuePanel) {
		this.venuePanel = venuePanel;
	}

	public JLabel getBandWebsiteLabel() {
		return BandWebsiteLabel;
	}

	public void setBandWebsiteLabel(JLabel bandWebsiteLabel) {
		BandWebsiteLabel = bandWebsiteLabel;
	}

	public JLabel getBandWebsiteArea() {
		return BandWebsiteArea;
	}

	public void setBandWebsiteArea(JLabel bandWebsiteArea) {
		BandWebsiteArea = bandWebsiteArea;
	}

	public JPanel getBandWebsitePanel() {
		return BandWebsitePanel;
	}

	public void setBandWebsitePanel(JPanel bandWebsitePanel) {
		BandWebsitePanel = bandWebsitePanel;
	}

	public JLabel getEventWebsiteLabel() {
		return eventWebsiteLabel;
	}

	public void setEventWebsiteLabel(JLabel eventWebsiteLabel) {
		this.eventWebsiteLabel = eventWebsiteLabel;
	}

	public JLabel getEventWebsiteArea() {
		return eventWebsiteArea;
	}

	public void setEventWebsiteArea(JLabel eventWebsiteArea) {
		this.eventWebsiteArea = eventWebsiteArea;
	}

	public JPanel getEventWebsitePanel() {
		return eventWebsitePanel;
	}

	public void setEventWebsitePanel(JPanel eventWebsitePanel) {
		this.eventWebsitePanel = eventWebsitePanel;
	}

	public JPanel getCenterPanel() {
		return westPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.westPanel = centerPanel;
	}

	public JTextField getInputText() {
		return inputText;
	}

	public void setInputText(JTextField inputText) {
		this.inputText = inputText;
	}

	public DefaultListModel getResults() {
		return results;
	}

	public void setResults(DefaultListModel results) {
		this.results = results;
	}

	public JList getResultList() {
		return resultList;
	}

	public void setResultList(JList resultList) {
		this.resultList = resultList;
	}
}