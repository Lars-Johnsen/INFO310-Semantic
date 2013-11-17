package converting;

import gui.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import lastfmapi.lastfm.Geo;
import lastfmapi.lastfm.Result;
import lastfmapi.util.StringUtilities;

import com.google.gson.JsonObject;
import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;
import com.hp.hpl.jena.rdf.model.Model;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Database db = Database.getInstance();
		db.deleteEventsOutofDate();
		db.sysoDB();

		View view = new View();
		
	}
}
