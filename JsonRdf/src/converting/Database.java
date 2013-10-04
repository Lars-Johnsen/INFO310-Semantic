package converting;



import java.util.ArrayList;

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
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;

public class Database {

	Dataset dataset ;
	String hei ="hei";

	/**
	 * Constructor of class Database
	 * @param model
	 */
	public Database() {

	}

	public void SaveDB(Model model){
		Model dbModel = getModel();
		dbModel.add(model);
		dbModel.write(System.out);

		dataset.close();
	}

	/**
	 * Test-method for queries
	 */
	//	public void Sparql(){
	//		Model model = getModel();
	//
	//		String queryString = 
	//				"SELECT ?venue WHERE { "
	//						+ "?venue a \"music:Venue\" . " 
	//						+ "}" ;
	//
	//		System.out.println(queryString);
	//		Query query = QueryFactory.create(queryString) ;
	//		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;
	//
	//		try {
	//			ResultSet results = queryexec.execSelect() ;
	//
	//			while ( results.hasNext() ){
	//				QuerySolution solution = results.nextSolution() ;
	//				System.out.println(solution.get("venue").toString());
	//			}
	//		} finally { 
	//				queryexec.close() ; 
	//			}
	//
	//		dataset.close();
	//	}

	/**
	 * Checking if it exists a local file from param "place"
	 * @param place
	 * @return
	 */
	public boolean checkDBLocation(String place){
		boolean hit = false;
		Model model = getModel();

		String queryString = 
				//BRUKE ASK I STEDET FOR SELECT!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!?!!!!!!!!!!!!!!!!!!!!!!
				"SELECT ?info WHERE { "
						+ "?venue <http://www.w3.org/2001/vcard-rdf/3.0#Locality> \"" + place + "\" . " 
						+ "?event <http://www.w3.org/2002/07/owl#sameAs> ?venue ."
						+ "?noe <http://purl.org/dc/elements/1.1/coverage> ?event ."
						+ "?noe <http://www.w3.org/2000/01/rdf-schema#label> ?info ."
						+ "}" ;
		System.out.println(queryString);

		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;
			if(results.hasNext()){
				hit = true;
			}
		} finally { queryexec.close() ; }
		dataset.close();
		return hit;
	}

	/**
	 * Get information from model, from a given "place"
	 * @param place
	 * @return
	 */
	public void getModelInfo(String place){
		Model model = getModel();

		String queryString = 	
				//BRUKE * I STEDET FOR � SKRIVE ALLE VARIABLENE VI VIL HA UT!!!!!!!
				"SELECT ?eventName ?eventID ?artist ?date ?venueName ?venueID ?lat ?long ?city ?country ?street" 
				+ " ?postalcode ?venueURL ?eventwebsite ?event ?artist ?phonenumber" 
				+ "	WHERE { "
						+ "?venueAddress <http://www.w3.org/2001/vcard-rdf/3.0#Locality> \"" + place + "\" ; " 
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
						+ "<http://musicontology.com/#term_MusicArtist> ?artist ;"
						+ "<http://purl.org/dc/terms/date> ?date ;" 
						+ "<http://purl.org/NET/c4dm/event.owl#place> ?venueURL ;"
						+ "<http://xmlns.com/foaf/0.1/homepage> ?eventwebsite ;"
						+ "<http://musicontology.com/#term_MusicArtist> ?artist ."
						
						+ "?venue <http://www.w3.org/2000/01/rdf-schema#label> ?venueName ;"
						+ "<http://purl.org/dc/terms/identifier> ?venueID ."

						+ "}" ;
		
		System.out.println(queryString);

		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;

		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){
				QuerySolution solution = results.nextSolution() ;
				System.out.println(solution.get("eventName").toString());
				System.out.println(solution.get("eventID").toString());
				System.out.println(solution.get("artist").toString());
				System.out.println(solution.get("date").toString());
				System.out.println(solution.get("venueName").toString());
				System.out.println(solution.get("venueID").toString());
				System.out.println(solution.get("lat").toString());
				System.out.println(solution.get("long").toString());
				System.out.println(solution.get("city").toString());
				System.out.println(solution.get("country").toString());
				System.out.println(solution.get("street").toString());
				System.out.println(solution.get("postalcode").toString());
				System.out.println(solution.get("venueURL").toString());
				System.out.println(solution.get("eventwebsite").toString());
				System.out.println(solution.get("event").toString());
				System.out.println(solution.get("artist").toString());
				System.out.println(solution.get("phonenumber").toString());
//				System.out.println(solution.get("bandwebsite").toString());
				System.out.println("--------------------");
						
			}
		} finally { 
			queryexec.close() ; 
		}
		dataset.close();
	}
	
	public void getModelInfo2(String place){
		Model model = getModel();

		String queryString = 
//				"PREFIX dc: "
				
				//BRUKE * I STEDET FOR � SKRIVE ALLE VARIABLENE VI VIL HA UT!!!!!!!
				"SELECT *" 
				+ "	WHERE { "
						+ "?venueAddress <http://www.w3.org/2001/vcard-rdf/3.0#Locality> \"" + place + "\" ; " 
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
						+ "<http://musicontology.com/#term_MusicArtist> ?artist ;"
						+ "<http://purl.org/dc/terms/date> ?date ;" 
						+ "<http://purl.org/NET/c4dm/event.owl#place> ?venueURL ;"
						+ "<http://xmlns.com/foaf/0.1/homepage> ?eventwebsite ;"
						+ "<http://musicontology.com/#term_MusicArtist> ?artist ."
						
						+ "?venue <http://www.w3.org/2000/01/rdf-schema#label> ?venueName ;"
						+ "<http://purl.org/dc/terms/identifier> ?venueID ."

						+ "}" ;
		
		System.out.println(queryString);

		Query query = QueryFactory.create(queryString) ;
		QueryExecution queryexec = QueryExecutionFactory.create(query, model) ;
		double geoLat = 0.0;
		double geoLong = 0.0;
		try {
			ResultSet results = queryexec.execSelect() ;

			while ( results.hasNext() ){
				QuerySolution solution = results.nextSolution() ;
				
				double lat = Double.parseDouble(solution.getLiteral("lat").getString());
				double longitude = Double.parseDouble(solution.getLiteral("long").getString());
				
				//PUTT INN I GEOEVENT!
				GeoEvent geoEvent = new GeoEvent(solution.get("eventName").toString(), 
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
												 solution.get("event").toString(),
												 solution.get("eventwebsite").toString(),
												 solution.get("phonenumber").toString());												

				System.out.println(solution.get("date").toString());
				System.out.println(solution.get("venueName").toString());
				System.out.println(solution.get("venueID").toString());
				System.out.println(solution.get("lat").toString());
				System.out.println(solution.get("long").toString());
				System.out.println(solution.get("city").toString());
				System.out.println(solution.get("country").toString());
				System.out.println(solution.get("street").toString());
				System.out.println(solution.get("postalcode").toString());
				System.out.println(solution.get("venueURL").toString()); //Ser ut til Œ vera korrekt!
				System.out.println(solution.get("eventwebsite").toString()); //Ser ut til Œ vera bandwebsite
				System.out.println(solution.get("event").toString()); //Ser ut til Œ vera LAST FM - Event website
				System.out.println(solution.get("phonenumber").toString());
				System.out.println("--------------------");
						
			}
		} finally { 
			queryexec.close() ; 
		}
		dataset.close();
	}

	/**
	 * Creating model to use in other methods
	 * @return model
	 */
	public Model getModel(){
		dataset = TDBFactory.createDataset("data/rdfbase");
		Model model = dataset.getDefaultModel();
		return model;
	}
}
