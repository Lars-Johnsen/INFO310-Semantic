/*
 * Copyright (c) 2012, the Last.fm Java Project and Committers
 * All rights reserved.
 *
 * Redistribution and use of this software in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above
 *   copyright notice, this list of conditions and the
 *   following disclaimer in the documentation and/or other
 *   materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
 * TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package lastfmapi.lastfm;

import java.util.*;

import com.google.gson.JsonObject;

import lastfmapi.util.MapUtilities;


/**
 * Provides nothing more than a namespace for the API methods starting with geo.
 *
 * @author Janni Kovacs
 */
public class Geo {

	/**
	 * This inner class represents a Metro, which is composed of its name and the name of its country.
	 *
	 * @see Geo#getMetros(String, String)
	 */

	private Geo() {
	}

	/**
	 * Get all events in a specific location by country or city name.<br/> This method returns <em>all</em> events by subsequently calling
	 * {@link #getEvents(String, String, int, String)} and concatenating the single results into one list.<br/> Pay attention if you use this
	 * method as it may produce a lot of network traffic and therefore may consume a long time.
	 *
	 * @param location Specifies a location to retrieve events for
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param apiKey A Last.fm API key.
	 * @return a list containing all events
	 */
	public static Result getAllEvents(String location, String distance, String apiKey) {
		int page = 1;
		Result res = getEvents(location, distance, page, apiKey);
		return res;
	}
	/**
	 * Get all events in a specific location by country or city name.<br/> This method only returns the first page of a possibly paginated
	 * result. To retrieve all pages get the total number of pages via {@link de.umass.lastfm.PaginatedResult#getTotalPages()} and subsequently
	 * call {@link #getEvents(String, String, int, String)} with the successive page numbers.
	 *
	 * @param location Specifies a location to retrieve events for
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static Result getEvents(String location, String distance, String apiKey) {
		return getEvents(location, distance, 1, apiKey);
	}

	/**
	 * Get all events in a specific location by country or city name.<br/> This method only returns the specified page of a paginated result.
	 *
	 * @param location Specifies a location to retrieve events for
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param page A page number for pagination
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static Result getEvents(String location, String distance, int page, String apiKey) {
		return getEvents(location, distance, page, 300, apiKey);
	}

	public static Result getEvents(String location, String distance, int page, int limit, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", String.valueOf(page));
		MapUtilities.nullSafePut(params, "location", location);
		MapUtilities.nullSafePut(params, "distance", distance);
		MapUtilities.nullSafePut(params, "limit", limit);
		MapUtilities.nullSafePut(params, "format", "json");
		Result result = Caller.getInstance().call("geo.getEvents", apiKey, params);
		return result;
	}

	/**
	 * Get all events in a specific location by latitude/longitude.<br/> This method only returns the specified page of a paginated result.
	 *
	 * @param latitude Latitude
	 * @param longitude Longitude
	 * @param page A page number for pagination
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static JsonObject getEvents(double latitude, double longitude, int page, String apiKey) {
		return getEvents(latitude, longitude, page, -1, apiKey);
	}

	public static JsonObject getEvents(double latitude, double longitude, int page, int limit, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("page", String.valueOf(page));
		params.put("lat", String.valueOf(latitude));
		params.put("long", String.valueOf(longitude));
		MapUtilities.nullSafePut(params, "limit", limit);
		Result result = Caller.getInstance().call("geo.getEvents", apiKey, params);
		return result.getJsonObject();
	}

	public static JsonObject getEvents(double latitude, double longitude, String distance, String apiKey) {
		return getEvents(latitude, longitude, distance, -1, -1, apiKey);
	}

	/**
	 * Get all events within the specified distance of the location specified by latitude/longitude.<br/>
	 * This method only returns the specified page of a paginated result.
	 *
	 * @param latitude Latitude
	 * @param longitude Longitude
	 * @param distance Find events within a specified radius (in kilometres)
	 * @param page A page number for pagination
	 * @param limit The maximum number of items returned per page
	 * @param apiKey A Last.fm API key.
	 * @return a {@link PaginatedResult} containing a list of events
	 */
	public static JsonObject getEvents(double latitude, double longitude, String distance, int page, int limit, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("lat", String.valueOf(latitude));
		params.put("long", String.valueOf(longitude));
		params.put("distance", distance);
		MapUtilities.nullSafePut(params, "page", page);
		MapUtilities.nullSafePut(params, "limit", limit);
		Result result = Caller.getInstance().call("geo.getEvents", apiKey, params);
		return result.getJsonObject();
	}
	
}
