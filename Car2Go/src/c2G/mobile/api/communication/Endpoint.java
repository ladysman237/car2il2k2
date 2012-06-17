package c2G.mobile.api.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import c2G.mobile.api.objekts.Account;
import c2G.mobile.api.objekts.Booking;
import c2G.mobile.api.objekts.CanceledBooking;
import c2G.mobile.api.objekts.Coordinate;
import c2G.mobile.api.objekts.GasStation;
import c2G.mobile.api.objekts.Location;
import c2G.mobile.api.objekts.ParkingSpot;
import c2G.mobile.api.objekts.Position;
import c2G.mobile.api.objekts.Vehicle;

public class Endpoint implements EndpointCommunication {
	
	public List<Vehicle> getAllFreeVehicles(String loc, String oauth_consumer_key) {
		List<Vehicle> result = new ArrayList<Vehicle>();
		String url = String.format(GETALLFREEVEHICLES, loc, oauth_consumer_key);
		String data = getDataByURL(url);
		try {
			JSONObject jsonObj = new JSONObject(data);
			JSONArray jsonArray = new JSONArray(jsonObj.getString("placemarks"));
			Log.i(Endpoint.class.getName(), "Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Position position = new Position(getCoordinatesFromJsonObj((JSONArray) jsonObject.get("coordinates")), jsonObject.get("address").toString());
				Vehicle vehicle = new Vehicle(
						jsonObject.get("vin").toString(),
						jsonObject.get("name").toString(),
						position,
						jsonObject.get("exterior").toString(),
						jsonObject.get("interior").toString(),
						jsonObject.get("fuel").toString(),
						jsonObject.get("engineType").toString());
				result.add(vehicle);
				
				Log.i(Endpoint.class.getName(), "starting shit");
				Log.i(Endpoint.class.getName(), vehicle.getEngineType());
				Log.i(Endpoint.class.getName(), vehicle.getExterior());
				Log.i(Endpoint.class.getName(), vehicle.getFuel());
				Log.i(Endpoint.class.getName(), vehicle.getInterior());
				Log.i(Endpoint.class.getName(), vehicle.getPlate());
				Log.i(Endpoint.class.getName(), vehicle.getVin());
				Log.i(Endpoint.class.getName(), vehicle.getPosition().getAdress());
				Log.i(Endpoint.class.getName(), String.valueOf(vehicle.getPosition().getLatitude()));
				Log.i(Endpoint.class.getName(), String.valueOf(vehicle.getPosition().getLongitude()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<ParkingSpot> getAllParkingSpots(String loc, String oauth_consumer_key) {
		List<ParkingSpot> result = new ArrayList<ParkingSpot>();
		String url = String.format(GETALLPARKINGSPOTS, loc, oauth_consumer_key);
		String data = getDataByURL(url);
		try {
			JSONObject jsonObj = new JSONObject(data);
			JSONArray jsonArray = new JSONArray(jsonObj.getString("placemarks"));
			Log.i(Endpoint.class.getName(), "Number of entries " + jsonArray.length());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Coordinate coordinate = new Coordinate(getCoordinatesFromJsonObj((JSONArray) jsonObject.get("coordinates")));
				ParkingSpot parkingSpot = new ParkingSpot(
						coordinate,
						jsonObject.get("name").toString(),
						jsonObject.get("totalCapacity").toString(),
						jsonObject.get("usedCapacity").toString(), 
						jsonObject.get("chargingPole").toString());
				result.add(parkingSpot);
				Log.i(Endpoint.class.getName(), "starting out parkinspots");
				Log.i(Endpoint.class.getName(), parkingSpot.getName());
				Log.i(Endpoint.class.getName(), String.valueOf(parkingSpot.getTotalCapacity()));
				Log.i(Endpoint.class.getName(), String.valueOf(parkingSpot.getUsedCapacity()));
				Log.i(Endpoint.class.getName(), String.valueOf(parkingSpot.isChargingPole()));
				Log.i(Endpoint.class.getName(), String.valueOf(parkingSpot.getCoordinate().getLatitude()));
				Log.i(Endpoint.class.getName(), String.valueOf(parkingSpot.getCoordinate().getLongitude()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String getDataByURL(String url) {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		Log.i(Endpoint.class.getName(), url);
		HttpGet httpGet = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(Endpoint.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}
	
	private Coordinate getCoordinatesFromJsonObj(JSONArray jsonArray) throws JSONException{
		Coordinate coordiante = new Coordinate(jsonArray.getDouble(0),jsonArray.getDouble((0)));
		return coordiante;
	}

	public List<Location> getAllLocations(String oauth_consumer_key) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<GasStation> getAllPGasStations(String loc,
			String oauth_consumer_key) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Account> getAllAccounts(String loc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Booking> getBookings(String loc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Booking> getBooking(String loc) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Booking> createBooking(String loc, String vin, String accountID) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CanceledBooking> cancelBooking(String bookingID) {
		// TODO Auto-generated method stub
		return null;
	}
}
