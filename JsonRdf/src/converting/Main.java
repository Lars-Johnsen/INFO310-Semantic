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
		Database db = Database.getInstance();
		db.deleteEventsNotAttended();
		db.sysoDB();
		View view = new View();
		
	}
}
