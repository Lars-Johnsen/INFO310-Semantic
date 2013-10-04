package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.LayoutManager;
import java.awt.Toolkit;
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

public class View extends JFrame {
	
	private JPanel			northPanel = new JPanel();
	private JPanel			westPanel = new JPanel();
	private JPanel			eastPanel = new JPanel();
	private JTextArea		inputText = new JTextArea();
	private DefaultListModel results      = new DefaultListModel();
	private JList            resultList   = new JList(results);
	
	
	
	/**
	 * Creating fields for GUI elements
	 */
	private JLabel eventNameLabel = new JLabel("Eventname");
	private JLabel eventNameArea = new JLabel();
	private JPanel eventNamePanel = new JPanel();

	private JLabel eventIdLabel = new JLabel("Event ID");
	private JLabel eventIdArea = new JLabel();
	private JPanel eventIdpanel = new JPanel();
	
	private JLabel headLinerLabel = new JLabel("Headliner");
	private JLabel headlinerArea = new JLabel();
	private JPanel headlinerpanel = new JPanel();
	
	private JLabel dateLabel = new JLabel("Date");
	private JLabel DateArea = new JLabel();
	private JPanel datePanel = new JPanel();
	
	private JLabel venueLabel = new JLabel("Venue");
	private JLabel venueArea = new JLabel();
	private JPanel venuePanel = new JPanel();
	
	private JLabel BandWebsiteLabel = new JLabel("Bandwebsite");
	private JLabel BandWebsiteArea = new JLabel();
	private JPanel BandWebsitePanel = new JPanel();
	
	private JLabel eventWebsiteLabel = new JLabel("Bandwebsite");
	private JLabel eventWebsiteArea = new JLabel();
	private JPanel eventWebsitePanel = new JPanel();
	
	public View(){
		setupInterFace();
	}
	
	private void setupInterFace() {	
		this.setPreferredSize(new Dimension(1024, 768));

		inputText.setPreferredSize(new Dimension(100, 20));
		resultList.setPreferredSize(new Dimension(100, 100));
		
		northPanel.removeAll();
		northPanel.setPreferredSize(new Dimension(1024, 120));
		northPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		
		eastPanel.removeAll();
		eastPanel.setPreferredSize(new Dimension(512,384));
		eastPanel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
		eastPanel.setLayout(new BoxLayout(eastPanel,BoxLayout.Y_AXIS));
		
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

		eventNamePanel.add(eventNameLabel);
		eventNamePanel.add(eventNameArea);
		
		//Event ID field and presentation

		eventIdpanel.add(eventIdLabel);
		eventIdpanel.add(eventIdArea);
		
		//headliner field and presentation
		
		headlinerpanel.add(headLinerLabel);
		headlinerpanel.add(headlinerArea);
		
		eastPanel.add(eventNamePanel);
		eastPanel.add(eventIdpanel);
		
		//date field and presentation
		
		datePanel.add(dateLabel);
		datePanel.add(DateArea);
		
		//venue field and presentation
		
		venuePanel.add(venueLabel);
		venuePanel.add(venueArea);
		
		//Bandwebsite field and presentation

		BandWebsitePanel.add(BandWebsiteLabel);
		BandWebsitePanel.add(BandWebsiteArea);
		
		
		//Bandwebsite field and presentation
		
		eventWebsitePanel.add(eventWebsiteLabel);
		eventWebsitePanel.add(eventWebsiteArea);
		
		inputArea.add(inputTextLabel);
		inputArea.add(inputText);
		inputArea.setPreferredSize(new Dimension(500, 200));
		
		northPanel.add(imageLabel);
		
		westPanel.add(inputArea);
		westPanel.add(resultList);

		getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(northPanel, BorderLayout.NORTH);
		this.getContentPane().add(westPanel, BorderLayout.WEST);
		this.getContentPane().add(eastPanel, BorderLayout.EAST);

		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setBounds(0, 0, 1024, 768);
	}

	public JPanel getCenterPanel() {
		return westPanel;
	}

	public void setCenterPanel(JPanel centerPanel) {
		this.westPanel = centerPanel;
	}

	public JTextArea getInputText() {
		return inputText;
	}

	public void setInputText(JTextArea inputText) {
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