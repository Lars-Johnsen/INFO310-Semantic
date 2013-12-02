package converting;



import java.util.ArrayList;
import java.util.Date;

import lastfmapi.util.StringUtilities;

import com.hp.hpl.jena.query.Dataset;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.update.Update;
import com.hp.hpl.jena.update.UpdateAction;
import com.hp.hpl.jena.update.UpdateExecutionFactory;
import com.hp.hpl.jena.update.UpdateFactory;
import com.hp.hpl.jena.update.UpdateRequest;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;

public class Database {

	Dataset dataset ;
	public static Database database;
	/**
	 * Constructor of class Database
	 * @param model
	 */
	private Database() {
		Resource bruker = getModel().createResource("http://www.user.no/anderslangseth");
		bruker.addProperty(VCARD.NAME, "Anders Langseth");
	}
	/**
	 * getInstance method to make sure we only work with one instance of the class
	 * @return Database the database, with the model.
	 */
	public static Database getInstance(){
		if (database == null){
			database = new Database();
			return database;
		}
		else return database;
	}

	public void SaveDB(Model model){
		Model dbModel = getModel();
		dbModel.add(model);

		dataset.close();
	}


	/**
	 * Checking if it exists an event in the databse that has the location "place"
	 * @param place, represents a place.
	 * @return 
	 */
	public boolean checkDBLocation(String place){
		Model model = getModel();
		Boolean result;
		String queryString = 
				//BRUKE ASK I STEDET FOR SELECT!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!!!!!!!!!!!!!!!!!!!!!!
				"ASK { "
				+ "FILTER (?place =\"" + place + "\" || regex(?place,\"" + place + "\"))."
				+ "?venueAddress <http://www.w3.org/2001/vcard-rdf/3.0#Locality> ?place ; " 
				+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			result = queryexec.execAsk() ;
		} finally { queryexec.close() ; }
		dataset.close();
		return result;
	}
	/**
	 * Checks if the artist is registered in the database.
	 * If it is, it returns the Resource URI of the artist.
	 * @param artistnavn
	 * @return
	 */
	public String checkDBArtist(String artistnavn){

		Model model = getModel();
		String result ="";
		String queryString = 
				"SELECT ?ArtistURI" 
						+ "	WHERE { "
						+ "?ArtistURI <http://www.w3.org/2000/01/rdf-schema#label> \"" + artistnavn + "\" ; "
						+ "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> 'http://purl.org/ontology/mo/MusicArtist' ."
						+ "}" ;

		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){

				QuerySolution solution = results.nextSolution() ;
				result = solution.getResource("ArtistURI").getURI();
			}
		} finally { 

			queryexec.close() ; 
		}
		return result;
	}

	/**
	 * Get information from model, from a given "place"
	 * @param place
	 * @return
	 */
	public ArrayList<GeoEvent> getModelInfoFromLocation(String place){
		Model model = getModel();

		String queryString = 	
				//BRUKE * I STEDET FOR � SKRIVE ALLE VARIABLENE VI VIL HA UT!!!!!!!
				"SELECT  ?eventName ?eventID ?artist ?date ?venueName ?venueID ?lat ?long ?city ?country ?street" 
				+ " ?postalcode ?venueURL ?eventwebsite ?event ?artist ?phonenumber ?genre ?bio ?similar_to ?artistName" 
				+ "	WHERE { "
				+ "FILTER (?place =\"" + place + "\" || regex(?place,\"" + place + "\"))."
				+ "?venueAddress <http://www.w3.org/2001/vcard-rdf/3.0#Locality> ?place ; " 
				+ "<http://www.w3.org/2003/01/geo/wgs84_pos/#lat> ?lat ;"
				+ "<http://www.w3.org/2003/01/geo/wgs84_pos/#long> ?long ;"
				+ "<http://www.w3.org/2001/vcard-rdf/3.0#Locality> ?city ;"
				+ "<http://www.w3.org/2001/vcard-rdf/3.0#Country> ?country ;"
				+ "<http://www.w3.org/2001/vcard-rdf/3.0#Street> ?street ;"
				+ "<http://www.w3.org/2001/vcard-rdf/3.0#Pcode> ?postalcode ;"
				+ "<http://www.w3.org/2001/vcard-rdf/3.0#TEL> ?phonenumber ."

						+ "?venue <http://www.w3.org/2002/07/owl#sameAs> ?venueAddress ."

						+ "?event <http://purl.org/dc/elements/1.1/coverage> ?venue ;"
						+ "<http://www.w3.org/2000/01/rdf-schema#label> ?eventName ;"
						+ "<http://purl.org/dc/terms/identifier> ?eventID ;"
						+ "<http://purl.org/dc/terms/date> ?date ;" 
						+ "<http://purl.org/NET/c4dm/event.owl#place> ?venueURL ;"
						+ "<http://xmlns.com/foaf/0.1/homepage> ?eventwebsite ;"
						+ "<http://purl.org/ontology/mo/performer> ?artist ."

						+ "?venue <http://www.w3.org/2000/01/rdf-schema#label> ?venueName ;"
						+ "<http://purl.org/dc/terms/identifier> ?venueID ."


						+ "?artist <http://www.w3.org/2000/01/rdf-schema#label> ?artistName ."	
						+ "?artist <http://purl.org/ontology/mo/biography> ?bio ."

						+ "} "
						+ "ORDER BY ASC(?date)" ;



		ArrayList<GeoEvent> liste = new ArrayList<GeoEvent>();
		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;


			while ( results.hasNext() ){

				QuerySolution solution = results.nextSolution() ;


				double lat = Double.parseDouble(solution.getLiteral("lat").getString());
				double longitude = Double.parseDouble(solution.getLiteral("long").getString());

				//PUTT INN I GEOEVENT!
				//Bruker konstrukt�r2



				GeoEvent geoEvent = new GeoEvent(solution.get("eventName").toString(), 
						solution.get("eventID").toString(),
						solution.get("artistName").toString(),
						solution.get("date").toString(),
						solution.get("venueName").toString(),
						solution.get("venueID").toString(),
						lat,
						longitude,
						solution.get("city").toString(),
						solution.get("country").toString(),
						solution.get("street").toString(),
						solution.get("postalcode").toString(),
						solution.get("venueURL").toString(),
						solution.get("event").asResource().getURI(),
						solution.get("eventwebsite").toString(),
						solution.get("phonenumber").toString(), 
						getGenreArtist(solution.get("artistName").toString()), 
						solution.get("bio").toString(), 
						getSimilar_toArtist(solution.get("artistName").toString()), 
						solution.get("artistName").toString(), 
						solution.getResource("artist").getURI()
						);
				liste.add(geoEvent);




			}
		} finally { 
			queryexec.close() ; 
		}
		dataset.close();
		return liste;
	}
	public ArrayList<String> getSimilar_toArtist(String artistnavn){

		Model model = getModel();
		ArrayList<String> similar_to = new ArrayList<String>();
		String queryString = 
				"SELECT ?similar_to" 
						+ "	WHERE { "
						+ "?ArtistURI <http://www.w3.org/2000/01/rdf-schema#label> \"" + artistnavn + "\" ; "
						+ "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> 'http://purl.org/ontology/mo/MusicArtist' ;"
						+ "<http://purl.org/ontology/mo/similar_to> ?similar_to ."
						+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){

				QuerySolution solution = results.nextSolution() ;
				similar_to.add(solution.get("?similar_to").toString());

			}
		} finally { 
			queryexec.close() ; 
		}
		return similar_to;
	}

	public ArrayList<String> getGenreArtist(String artistnavn){

		Model model = getModel();
		ArrayList<String> genre = new ArrayList<String>();
		String queryString = 
				"SELECT ?genre" 
						+ "	WHERE { "
						+ "?ArtistURI <http://www.w3.org/2000/01/rdf-schema#label> \"" + artistnavn + "\" ; "
						+ "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type> 'http://purl.org/ontology/mo/MusicArtist' ;"
						+ "<http://purl.org/ontology/mo/Genre> ?genre ."
						+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){

				QuerySolution solution = results.nextSolution() ;
				genre.add(solution.get("genre").toString());


			}
		} finally { 
			queryexec.close() ; 
		}
		return genre;
	}
	public void deleteEventsNotAttended(){
		Model model = getModel();
		String queryString = 
				"DELETE  { "
						+ "?event ?prop ?val }" 
						+ "WHERE { "
						+ "?event <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> 'http://purl.org/ontology/mo/Performance' ."
						+ " FILTER NOT EXISTS {<http://www.user.no/anderslangseth> <http://data.semanticweb.org/ns/swc/ontology#plansToAttend> ?event}  "
						+ "?event ?prop ?val"
						+ "}" ;


		UpdateRequest query = UpdateFactory.create(queryString) ;
		UpdateAction.execute(query, model);
		dataset.close();

	}
	public boolean checkEvent(String eventuri){
		Model model = getModel();
		Boolean result;
		String queryString = 
				//BRUKE ASK I STEDET FOR SELECT!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!!!!!!!!!!!!!!!!!!!!!!
				"ASK { "
				+ "?event <http://xmlns.com/foaf/0.1/homepage> \"" + eventuri + "\" ."
				+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			result = queryexec.execAsk() ;
		} finally { queryexec.close() ; }
		dataset.close();
		return result;
	}
	public GeoEvent getModelInfoFromEvent(String eventuri){
		//IKKE OK, skal v�re som getModelInfoFromLocation, men heller benytte eventets URI.
		Model model = getModel();
		GeoEvent geoEvent = null;
		String queryString = 	
				//BRUKE * I STEDET FOR � SKRIVE ALLE VARIABLENE VI VIL HA UT!!!!!!!
				"SELECT ?eventName ?eventID ?artist ?date ?venueName ?venueID ?lat ?long ?city ?country ?street" 
				+ " ?postalcode ?venueURL ?eventwebsite ?event ?artist ?phonenumber ?artistName ?bio" 
				+ "	WHERE { "
				+ "?event <http://xmlns.com/foaf/0.1/homepage> \"" + eventuri + "\" ."
				+" ?event <http://purl.org/dc/elements/1.1/coverage> ?venue ;"
				+ "<http://www.w3.org/2000/01/rdf-schema#label> ?eventName ;"
				+ "<http://purl.org/dc/terms/identifier> ?eventID ;"
				+ "<http://purl.org/dc/terms/date> ?date ;" 
				+ "<http://purl.org/NET/c4dm/event.owl#place> ?venueURL ;"
				+ "<http://xmlns.com/foaf/0.1/homepage> ?eventwebsite ;"
				+ "<http://purl.org/ontology/mo/performer> ?artist ."

						+ "?venue <http://www.w3.org/2000/01/rdf-schema#label> ?venueName ; "
						+ "<http://purl.org/dc/terms/identifier> ?venueID . "

						+ "?venue <http://www.w3.org/2002/07/owl#sameAs> ?VenueAddress . "
						+ "?VenueAddress <http://www.w3.org/2001/vcard-rdf/3.0#Locality> ?place ; " 
						+ "<http://www.w3.org/2003/01/geo/wgs84_pos/#lat> ?lat ;"
						+ "<http://www.w3.org/2003/01/geo/wgs84_pos/#long> ?long ;"
						+ "<http://www.w3.org/2001/vcard-rdf/3.0#Locality> ?city ;"
						+ "<http://www.w3.org/2001/vcard-rdf/3.0#Country> ?country ;"
						+ "<http://www.w3.org/2001/vcard-rdf/3.0#Street> ?street ;"
						+ "<http://www.w3.org/2001/vcard-rdf/3.0#Pcode> ?postalcode ;"
						+ "<http://www.w3.org/2001/vcard-rdf/3.0#TEL> ?phonenumber ."

						+ "?artist <http://www.w3.org/2000/01/rdf-schema#label> ?artistName ."	
						+ "?artist <http://purl.org/ontology/mo/biography> ?bio ."
						+ "}" ;


		ArrayList<GeoEvent> liste = new ArrayList<GeoEvent>();
		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;


			while ( results.hasNext() ){

				QuerySolution solution = results.nextSolution() ;


				double lat = Double.parseDouble(solution.getLiteral("lat").getString());
				double longitude = Double.parseDouble(solution.getLiteral("long").getString());
				//PUTT INN I GEOEVENT!
				geoEvent = new GeoEvent(solution.get("eventName").toString(), 
						solution.get("eventID").toString(),
						solution.get("eventName").toString(),
						solution.get("date").toString(),
						solution.get("venueName").toString(),
						solution.get("venueID").toString(),
						lat,
						longitude,
						solution.get("city").toString(),
						solution.get("country").toString(),
						solution.get("street").toString(),
						solution.get("postalcode").toString(),
						solution.get("venueURL").toString(),
						solution.get("event").asResource().getURI(),
						solution.get("eventwebsite").toString(),
						solution.get("phonenumber").toString(),						
						getGenreArtist(solution.get("artistName").toString()), 
						solution.get("bio").toString(), 
						getSimilar_toArtist(solution.get("artistName").toString()), 
						solution.get("artistName").toString(), 
						solution.getResource("artist").getURI()
						);

				liste.add(geoEvent);


			}
		} finally { 
			queryexec.close() ; 
		}
		dataset.close();
		return geoEvent;
	}
	/**
	 * Creating model to use in other methods
	 * @return model
	 */
	public Model getModel(){
		dataset = TDBFactory.createDataset("rdfbase");
		Model model = dataset.getDefaultModel();
		return model;
	}
	/**
	 * Inserts a predicate plansToAttend to the user with the actual event that the user plan to attend.
	 * @param eventURI
	 */
	public void attend(String eventURI){

		Model model = getModel();
		String eventResource = "<" + eventURI +">";
		String queryString = 
				"INSERT DATA { "
						+ "<http://www.user.no/anderslangseth> <http://data.semanticweb.org/ns/swc/ontology#plansToAttend>"
						+  eventResource +  ". " 
						+ "}" ;


		UpdateRequest query = UpdateFactory.create(queryString) ;
		UpdateAction.execute(query, model);
		dataset.close();
	}

	/**
	 * Prints out the database
	 * Used just for checking
	 */
	public void sysoDB(){
		getModel().write(System.out);
	}

	public ArrayList<String> sameArtistyouAttendedPlaysOnADifferentEvent(){
		Model model = getModel();
		ArrayList<String>eventList = new ArrayList<String>();
		String queryString = 
				"SELECT ?Event" 
						+ "	WHERE { "
						+ "FILTER (?Event != ?Attended) . "
						+ "<http://www.user.no/anderslangseth> <http://data.semanticweb.org/ns/swc/ontology#plansToAttend> ?Attended . "
						+ "?Attended <http://purl.org/ontology/mo/performer> ?AttendedArtist . "
						+ "?Event <http://purl.org/ontology/mo/performer> ?AttendedArtist . "
						+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){
				QuerySolution solution = results.nextSolution() ;
				eventList.add(solution.getResource("?Event").getURI());
			}
		} finally { 
			queryexec.close() ; 
		}
		return eventList;
	}
	public ArrayList<String> ArtistyouAttendedHaveSimilar_TOWhoPlaysOnADifferentEvent(){
		Model model = getModel();
		ArrayList<String>eventlist = new ArrayList<String>();
		String queryString = 
				"SELECT ?Event" 
						+ "	WHERE { "
						+ "FILTER (?Event != ?Attended) . "
						+ "<http://www.user.no/anderslangseth> <http://data.semanticweb.org/ns/swc/ontology#plansToAttend> ?Attended . "
						+ "?Attended <http://purl.org/ontology/mo/performer> ?AttendedArtist . "
						+ "?AttendedArtist <http://purl.org/ontology/mo/similar_to> ?SimilarArtist . "
						+ "?Event <http://purl.org/ontology/mo/performer> ?SimilarArtist . "
						+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){
				QuerySolution solution = results.nextSolution() ;
				eventlist.add(solution.getResource("?Event").getURI());
			}
		} finally { 
			queryexec.close() ; 
		}
		return eventlist;
	}
	public ArrayList<String> getEventsBasedOnAttendedArtistsGenre(){
		Model model = getModel();
		ArrayList<String> eventList = new ArrayList<String>();
		String queryString = 
				"SELECT DISTINCT ?Event" 
						+ "	WHERE { "
						+ "FILTER (?Event != ?Attended) . "
						+ "<http://www.user.no/anderslangseth> <http://data.semanticweb.org/ns/swc/ontology#plansToAttend> ?Attended . "
						+ "?Attended <http://purl.org/ontology/mo/performer> ?AttendedArtist . "
						+ "?AttendedArtist <http://purl.org/ontology/mo/Genre> ?AttendedArtistGenre . "
						+ "?Event <http://purl.org/ontology/mo/performer> ?Artist . "
						+ "?Artist <http://purl.org/ontology/mo/Genre> ?AttendedArtistGenre . "
						+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){
				QuerySolution solution = results.nextSolution() ;
				eventList.add(solution.getResource("?Event").getURI());
			}
		} finally { 
			queryexec.close() ; 
		}
		return eventList;
	}
	public ArrayList<String> getEventsAttended(){
		ArrayList <String> eventsAttended = new ArrayList<String>();
		Model model = getModel();
		String queryString = 
				"SELECT ?Attended" 
						+ "	WHERE { "
						+ "<http://www.user.no/anderslangseth> <http://data.semanticweb.org/ns/swc/ontology#plansToAttend> ?Attended . "
						+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){
				QuerySolution solution = results.nextSolution() ;
				eventsAttended.add(solution.getResource("?Attended").getURI());
			}
		} finally { 
			queryexec.close() ; 
		}
		return eventsAttended;
	}
	public void deleteEvent(String eventuri){
		Model model = getModel();
		String queryString = 
						"DELETE  { "
						+ "?event ?prop ?val }" 
						+ "WHERE { "
						+ "?event <http://xmlns.com/foaf/0.1/homepage> \"" + eventuri + "\" ."
						+ "?event ?prop ?val"
						+ "}" ;


		UpdateRequest query = UpdateFactory.create(queryString) ;
		UpdateAction.execute(query, model);
		dataset.close();

	}
	public void deleteEventsOutofDate(){
		ArrayList <String> eventsToDelete = new ArrayList<String>();
		Model model = getModel();
		String queryString = "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#> "
				+"SELECT ?event ?date" 
				+ "	WHERE { "
				+ "?event <http://purl.org/dc/terms/date> ?date ." 
				+ " FILTER NOT EXISTS {<http://www.user.no/anderslangseth> <http://data.semanticweb.org/ns/swc/ontology#plansToAttend> ?event}  "
				+ "}" ;


		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;
		
		Date atm = new Date();
		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){
				QuerySolution solution = results.nextSolution() ;
				if(StringUtilities.getDateFromString(solution.get("?date").toString()).before(atm)){
					eventsToDelete.add(solution.getResource("?event").getURI());
				}

			}
			for(String eventuri : eventsToDelete){
				deleteEvent(eventuri);
			}
		} finally { 
			queryexec.close() ; 
		}

	}
}
