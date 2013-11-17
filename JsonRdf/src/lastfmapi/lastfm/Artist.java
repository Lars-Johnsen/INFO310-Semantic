


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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import lastfmapi.util.MapUtilities;
import lastfmapi.util.StringUtilities;


/**
 * Bean that contains artist information.<br/> This class contains static methods that executes API methods relating to artists.<br/> Method
 * names are equivalent to the last.fm API method names.
 *
 * @author Janni Kovacs
 */
public class Artist {


	public static Result getInfo(String artistOrMbid, String apiKey) {
		return getInfo(artistOrMbid, null, null, apiKey);
	}

	/**
	 * Retrieves detailed artist info for the given artist or mbid entry.
	 *
	 * @param artistOrMbid Name of the artist or an mbid
	 * @param username The username for the context of the request, or <code>null</code>. If supplied, the user's playcount for this artist is
	 * included in the response
	 * @param apiKey The API key
	 * @return detailed artist info
	 */
	public static Result getInfo(String artistOrMbid, String username, String apiKey) {
		return getInfo(artistOrMbid, null, username, apiKey);
	}

	/**
	 * Retrieves detailed artist info for the given artist or mbid entry.
	 *
	 * @param artistOrMbid Name of the artist or an mbid
	 * @param locale The language to fetch info in, or <code>null</code>
	 * @param username The username for the context of the request, or <code>null</code>. If supplied, the user's playcount for this artist is
	 * included in the response
	 * @param apiKey The API key
	 * @return detailed artist info
	 */
	public static Result getInfo(String artistOrMbid, Locale locale, String username, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtilities.isMbid(artistOrMbid)) {
			params.put("mbid", artistOrMbid);
		} else {
			params.put("artist", artistOrMbid);
		}
		if (locale != null && locale.getLanguage().length() != 0) {
			params.put("lang", locale.getLanguage());
		}
		MapUtilities.nullSafePut(params, "username", username);
		MapUtilities.nullSafePut(params, "format", "json");
		Result result = Caller.getInstance().call("artist.getInfo", apiKey, params);
		return result;
	}
	public static Result getEvents(String artistOrMbid, String apiKey) {
		return getEvents(artistOrMbid, false, -1, 20, apiKey);
	}

	/**
	 * Returns a list of upcoming events for an artist.
	 *
	 * @param artistOrMbid The artist name in question
	 * @param festivalsOnly Whether only festivals should be returned, or all events
	 * @param page The page number to fetch
	 * @param limit The number of results to fetch per page
	 * @param apiKey A Last.fm API key
	 * @return a list of events
	 */
	public static Result getEvents(String artistOrMbid, boolean festivalsOnly, int page, int limit, String apiKey) {
		Map<String, String> params = new HashMap<String, String>();
		if (StringUtilities.isMbid(artistOrMbid)) {
			params.put("mbid", artistOrMbid);
		} else {
			params.put("artist", artistOrMbid);
		}
		MapUtilities.nullSafePut(params, "page", page);
		MapUtilities.nullSafePut(params, "limit", limit);
		if(festivalsOnly)
			params.put("festivalsonly", "1");
		Result result = Caller.getInstance().call("artist.getEvents", apiKey, params);
		return result;
	}




}