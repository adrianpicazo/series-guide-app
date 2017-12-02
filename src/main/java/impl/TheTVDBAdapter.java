package impl;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import abs.IRemoteManager;

public class TheTVDBAdapter implements IRemoteManager {

	private static final String URI = "https://api.thetvdb.com";

	private static URI getBaseURI() {
		return UriBuilder.fromUri(URI).build();
	}
	
	// ------------------------------------------------------------------------

	private Client client;
	private JsonFactory jsonFactory;
	private Token token;

	public TheTVDBAdapter() {
		client = ClientBuilder.newClient();
		jsonFactory = new JsonFactory();
		this.setToken();
	}
	
	// ------------------------------------------------------------------------

	@Override
	public Map<Long, String> searchSeries(String pattern) {
		Map<Long, String> theTVDBSeries = new TreeMap<>();

		Response response = client
				.target(getBaseURI())
				.path("search")
				.path("series")
				.queryParam("name", pattern)
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token.getToken())
				.header("Accept-Language", "en")
				.get();

		try {
			JsonParser jsonParser = jsonFactory.createParser(response.readEntity(String.class));
			
			String idSerie = null;
			String serieName = null;
			
			while (!jsonParser.isClosed()) {
				JsonToken jsonToken = jsonParser.nextToken();
				
				if (JsonToken.FIELD_NAME.equals(jsonToken)) {
					
					if (jsonParser.getCurrentName().equals("id")) {
						jsonParser.nextToken();
						idSerie = jsonParser.getValueAsString();
					}
					
					if (jsonParser.getCurrentName().equals("seriesName")) {
						jsonParser.nextToken();
						serieName = jsonParser.getValueAsString();
					}
					
					if (idSerie != null && serieName != null) {
						theTVDBSeries.put(Long.parseLong(idSerie), serieName);
						idSerie = null;
						serieName = null;
					}

				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		response.close();

		return theTVDBSeries;
	}
	
	public Serie getSerie(long id) {
		Serie serie = new Serie();
		Map<String, String> theTVDBSeriefields = new HashMap<>();
		
		Response response = client
				.target(getBaseURI())
				.path("series")
				.path(Long.toString(id))
				.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + token.getToken())
				.header("Accept-Language", "en")
				.get();
		
		try {
			JsonParser jsonParser = jsonFactory.createParser(response.readEntity(String.class));

			while (!jsonParser.isClosed()) {
				JsonToken jsonToken = jsonParser.nextToken();

				if (JsonToken.FIELD_NAME.equals(jsonToken)) {
					String fieldName = jsonParser.getCurrentName();
					jsonParser.nextToken();					
					theTVDBSeriefields.put(fieldName, jsonParser.getValueAsString());
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		response.close();
		
		serie.setId(Long.parseLong(theTVDBSeriefields.get("id")));
		serie.setTitulo(theTVDBSeriefields.get("seriesName"));
		serie.setEstado(theTVDBSeriefields.get("status"));
		serie.setInicioEmision(theTVDBSeriefields.get("firstAired"));
		serie.setDiaEmision(theTVDBSeriefields.get("airsDayOfWeek"));
		serie.setHorarioEmision(theTVDBSeriefields.get("airsTime"));
		serie.setCadena(theTVDBSeriefields.get("network"));
		serie.setGeneros(null);
		serie.setPuntuacion(Double.parseDouble(theTVDBSeriefields.get("siteRating")));
		serie.setTotalPuntuaciones(Integer.parseInt(theTVDBSeriefields.get("siteRatingCount")));
		serie.setSinopsis(theTVDBSeriefields.get("overview"));

		return serie;
	}

	private void setToken() {
		Response response = client
				.target(getBaseURI())
				.path("login")
				.request(MediaType.APPLICATION_JSON)
				.post(Entity.json(new Login()));
		
		token = response.readEntity(Token.class);
		response.close();
	}
	
	// ------------------------------------------------------------------------

	public static void main(String[] args) {
		TheTVDBAdapter adapter = new TheTVDBAdapter();
		System.out.println(adapter.searchSeries("The OA").values());
		//System.out.println(adapter.getSerie(321060));
	}

}