package converting;

import java.util.ArrayList;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

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
		
		
		Model model = ModelFactory.createDefaultModel();
		
		for(GeoEvent geoEvent : geoArray){
			String venueURL = geoEvent.getVenue().getUrl();
			
			//TODO
			/**
			 * Start rendring
			 * Her må vi ha kall til databasen med
			 * @param String artistnavn.
			 */
			
			ArtistConverter artistConverter = new ArtistConverter();
			Artist artist = artistConverter.convertArtist();
			
			
			/**
			 * Slutt av endring
			 */
			
			//Resource event and adding it's properties
			
			Resource event = model.createResource(geoEvent.getEventUrl());
			event.addProperty(RDFS.label, model.createLiteral(geoEvent.getEventName()));
			
			event.addProperty(RDF.type, model.createProperty("http://musicontology.com/#term_Performance"))
			.addProperty(DCTerms.identifier, geoEvent.getEventID())
			.addProperty(DCTerms.date, geoEvent.getDate())
			.addProperty(model.createProperty("http://musicontology.com/#term_MusicArtist"), geoEvent.getHeadliner())
			.addProperty(model.createProperty("http://purl.org/NET/c4dm/event.owl#place"), geoEvent.getVenue().getUrl());
			
			/**
			 * Adding av artist
			 */
			//TODO
			
			Resource artistResource = model.createResource(artist.getArtistURL());
			artistResource.addProperty(RDFS.label, artist.getName())
			.addProperty(DCTerms.identifier, artist.getMbid());
			
			for(String s : artist.getSimilarArtists()){
				artistResource.addProperty(model.createProperty("http://purl.org/ontology/mo/similar_to"),s);
			}
			for (String t : artist.getTags()){
				artistResource.addProperty(model.createProperty("http://purl.org/ontology/mo/Genre"), t);
			}
			
			if(!geoEvent.getEventWebsite().isEmpty()){
				event.addProperty(FOAF.homepage, geoEvent.getEventWebsite());
			}
			System.out.println(event.getProperty(model.getProperty("http://musicontology.com/#term_MusicArtist")).getObject().toString());
			
			artistResource.addProperty(OWL.sameAs, event.getProperty(model.getProperty("http://musicontology.com/#term_MusicArtist")).getObject().toString());
			
			
			/**
			 * Slutt på adding av artist
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
		model.write(System.out);
		return model;
	}	

	
		
		
		
	
}