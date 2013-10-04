package gui;

import converting.GeoEvent;
import java.util.*;

public class ViewController {
	private View view;
	public ViewController(View view){
		this.view = view;
//		updateResultList();
	}
	
	public void updateResultList(ArrayList<GeoEvent> result){
		for(GeoEvent geo : result){
			view.getResults().addElement(geo);
		}
		
	}
}
