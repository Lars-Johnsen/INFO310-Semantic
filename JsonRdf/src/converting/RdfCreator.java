package converting;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.vocabulary.DC;
import com.hp.hpl.jena.vocabulary.DCTerms;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.hp.hpl.jena.vocabulary.VCARD;

public class RdfCreator {

	static String musicURI    = "http://purl.org/ontology/mo/";
	static String terms = "http://purl.org/dc/terms/";


	public RdfCreator() {

	}

	/**
	 * Generating model from ArrayList
	 * @param geoArray
	 * @return model
	 */
	public Model createRDF(ArrayList<GeoEvent> geoArray){

		Database db = Database.getInstance();
		Model model = db.getModel();

		for(GeoEvent geoEvent : geoArray){
			String venueURL = geoEvent.getVenue().getUrl();


			//Resource event and adding it's properties

			Resource event = model.createResource(geoEvent.getEventUrl());
			event.addProperty(RDFS.label, model.createLiteral(geoEvent.getEventName()));

			//			event.addProperty(RDF.type, model.createProperty("http://musicontology.com/#term_Performance"))
			event.addProperty(DCTerms.identifier, geoEvent.getEventID())
			.addProperty(DCTerms.date, geoEvent.getDate())

			.addProperty(model.createProperty("http://purl.org/NET/c4dm/event.owl#place"), geoEvent.getVenue().getUrl());

			/**
			 * Adding av artist
			 */
			

			String artistRessurs = db.checkDBArtist(geoEvent.getHeadliner());

			if(artistRessurs.equals("")){

				lastfmapi.lastfm.Artist lastFmArtist = new lastfmapi.lastfm.Artist();
				JsonObject jsonArtist = lastFmArtist.getInfo(geoEvent.getHeadliner(), "64ecb66631fd1570172e9c44108b96d4").getJsonObject();

				ArtistConverter artistConverter = new ArtistConverter();
				Artist artist = artistConverter.convertArtist(jsonArtist);

				Resource artistResource = model.createResource(artist.getArtistURL());
				artistResource.addProperty(RDFS.label, artist.getName())
				.addProperty(DCTerms.identifier, artist.getMbid())
				.addProperty(RDF.type, "http://purl.org/ontology/mo/MusicArtist");

				for(String s : artist.getSimilarArtists()){
					artistResource.addProperty(model.createProperty("http://purl.org/ontology/mo/similar_to"),s);
				}
				for (String t : artist.getTags()){
					artistResource.addProperty(model.createProperty("http://purl.org/ontology/mo/Genre"), t);
				}


				event.addProperty(model.createProperty("http://purl.org/ontology/mo/performer"), artistResource);
			}
			else{
				System.out.println("N電電電電電電電電電電電電電電");
				event.addProperty(model.createProperty("http://purl.org/ontology/mo/performer"), model.getResource(artistRessurs));	
			}
			if(!geoEvent.getEventWebsite().isEmpty()){
				event.addProperty(FOAF.homepage, geoEvent.getEventWebsite());
			}
			/**
			 * Slutt p奪 adding av artist
			 */

			//Resource venue and adding it's properties
			Resource venue = model.createResource(venueURL);
			venue.addProperty(RDFS.label, geoEvent.getVenue().getName())
			.addProperty(RDF.type, "music:Venue");
			venue.addProperty(DCTerms.identifier, geoEvent.getVenue().getVenueID());

			Resource venueAddress = model.createResource(venueURL+"#address");
			venueAddress.addProperty(VCARD.Country, geoEvent.getVenue().getCountry());
			venueAddress.addProperty(VCARD.Locality, geoEvent.getVenue().getCity());
			venueAddress.addProperty(VCARD.Street, geoEvent.getVenue().getStreet());
			venueAddress.addProperty(VCARD.Pcode, geoEvent.getVenue().getPostalCode());
			venueAddress.addProperty(VCARD.TEL, geoEvent.getVenue().getPhoneNumber());
			venueAddress.addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos/#lat"), String.valueOf(geoEvent.getVenue().getLatitude()));
			venueAddress.addProperty(model.createProperty("http://www.w3.org/2003/01/geo/wgs84_pos/#long"), String.valueOf(geoEvent.getVenue().getLongitude()));

			venue.addProperty(OWL.sameAs, venueAddress);		
			event.addProperty(DC.coverage, venue);

		}
		//		model.write(System.out);
		return model;
	}	






}