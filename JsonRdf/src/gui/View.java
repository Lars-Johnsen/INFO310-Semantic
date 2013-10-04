package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
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
		
		northPanel.add(imageLabel);
		
		westPanel.add(inputText);
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
