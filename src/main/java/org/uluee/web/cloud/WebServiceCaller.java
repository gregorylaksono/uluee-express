package org.uluee.web.cloud;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.util.CallSOAPAction;
import org.uluee.web.util.CallSOAPAction.ISOAPResultCallBack;
import org.uluee.web.util.Constant;

import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONException;
import com.google.gwt.thirdparty.json.JSONObject;




public class WebServiceCaller implements IWebService {


	@Override
	public User login(String username, String password) {
		final List<User> container = new ArrayList();
		User user = null;
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
		map.put("username", username);
		map.put("password", password);
		ISOAPResultCallBack callBack = new ISOAPResultCallBack(){

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				String sessionId = data.getProperty("sessionId").toString();
				String addId = data.getProperty("addId").toString();
				
				User user = new User().setAddId(addId).setSessionId(sessionId).setPassword(password).setUsername(username);
				container.add(user);
			}

			@Override
			public void handleError(String statusCode) {


			}

		};
		new CallSOAPAction(map, "login", callBack);
		return container.get(0);
	}
	
	public List<String> getGoogleAutocomplete(String match) {
		String jsonResults = getGoogleAutoComplete(match);

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults);

			if (jsonObj.get("status").equals("ZERO_RESULTS") && match.contains(",")) {
				String[] arrayMatch = match.split(",");
				for (int i = 0; i<arrayMatch.length; i++) {
					jsonResults = getGoogleAutoComplete(arrayMatch[i]);
					jsonObj = new JSONObject(jsonResults);
					JSONArray jsonArray = jsonObj.getJSONArray("predictions");
					if (jsonObj.get("status").equals("OK") && jsonArray.length() > 0) {
						break;
					}
				}
				
			}
			
			JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

			List googleList = new ArrayList<String>(predsJsonArray.length());
			for (int i = 0; i < predsJsonArray.length(); i++) {
				googleList.add(predsJsonArray.getJSONObject(i).getString("description"));
			}
			return googleList;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}			
	}
	
	
	public String getGoogleAutoComplete(String match) {
		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		try {
			StringBuilder sb = new StringBuilder(Constant.PLACES_API_BASE + Constant.TYPE_AUTOCOMPLETE + Constant.OUT_JSON);
			sb.append("?sensor=false&key=" + Constant.API_KEY);
			sb.append("&input=" + URLEncoder.encode(match, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return jsonResults.toString();
	}
	
	
	public static void main(String[] args) {
		new WebServiceCaller().getGoogleAutocomplete("mc payment");
	}

	@Override
	public List<RSAddName> getShipperFfwAlsoNotifyDeliveredToAddressByMatchService(String match, String sessionId) {
		List<RSAddName> shipperNames = new ArrayList();
		LinkedHashMap map = new LinkedHashMap<>();
		map.put("sessionId", sessionId);
		map.put("match", match);
		ISOAPResultCallBack callBack = new ISOAPResultCallBack(){

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				for (int i = 0; i < data.getPropertyCount(); i++) {
					SoapObject array = (SoapObject) data.getProperty(i);
					RSAddName rsAddNameTemp = new RSAddName();
					rsAddNameTemp.setParentID(array.getProperty("parentId").toString());
					rsAddNameTemp.setCompanyID(array.getProperty("id").toString());
					rsAddNameTemp.setType(array.getProperty("type").toString());
					rsAddNameTemp.setCompanyName(array.getProperty("name").toString());
					rsAddNameTemp.setCity(array.getProperty("city").toString());
					rsAddNameTemp.setCountry(array.getProperty("country").toString());
					rsAddNameTemp.setLatitude(array.getProperty("latitude").toString());
					rsAddNameTemp.setLongitude(array.getProperty("longitude").toString());
					rsAddNameTemp.setStreet(array.getProperty("street").toString());
					rsAddNameTemp.setNotSaved(false);
					shipperNames.add(rsAddNameTemp);
				}
			}

			@Override
			public void handleError(String statusCode) {


			}

		};
		new CallSOAPAction(map, "getShipperAlsoNotifyDeliveredToAddressByMatch", callBack);
		return shipperNames;
	}

	@Override
	public List<RSAddName> getConsigneeAddressByMatch(String match, String sessionId) {
		List<RSAddName> consigneeNames = new ArrayList();
		LinkedHashMap map = new LinkedHashMap<>();
		map.put("sessionId", sessionId);
		map.put("match", match);
		ISOAPResultCallBack callBack = new ISOAPResultCallBack(){

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				for (int i = 0; i < data.getPropertyCount(); i++) {
					SoapObject array = (SoapObject) data.getProperty(i);
					RSAddName rsAddNameTemp = new RSAddName();
					rsAddNameTemp.setParentID(array.getProperty("parentId").toString());
					rsAddNameTemp.setCompanyID(array.getProperty("id").toString());
					rsAddNameTemp.setType(array.getProperty("type").toString());
					rsAddNameTemp.setCompanyName(array.getProperty("name").toString());
					rsAddNameTemp.setCity(array.getProperty("city").toString());
					rsAddNameTemp.setCountry(array.getProperty("country").toString());
					rsAddNameTemp.setLatitude(array.getProperty("latitude").toString());
					rsAddNameTemp.setLongitude(array.getProperty("longitude").toString());
					rsAddNameTemp.setStreet(array.getProperty("street").toString());
					consigneeNames.add(rsAddNameTemp);
				}
			}

			@Override
			public void handleError(String statusCode) {


			}

		};
		new CallSOAPAction(map, "getConsigneeAddressByMatch", callBack);
		return consigneeNames;
	}

	@Override
	public List<Commodity> getCommodity(String commodity, String sessionId) {
		List<Commodity> commodityList = new ArrayList();
		LinkedHashMap map = new LinkedHashMap<>();
		map.put("sessionId", sessionId);
		map.put("match", commodity);
		ISOAPResultCallBack callBack = new ISOAPResultCallBack(){

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				for (int i = 0; i < data.getPropertyCount(); i++) {
					String temp = data.getProperty(i).toString();
					String[] temps = temp.split("\\|");
					Commodity comm = new Commodity();
					comm.setAnnId(Long.parseLong(temps[0]));
					comm.setCommId(Long.parseLong(temps[1]));
					comm.setCommName(temps[2]);
					if (temps.length > 3) {
						comm.setSccCode(temps[3]);
						comm.setSccName(temps[4]);
					}
					commodityList.add(comm);
				}
			}
			@Override
			public void handleError(String statusCode) {


			}

		};
		new CallSOAPAction(map, "getCommodityByMatch", callBack);
		
		return commodityList;
	}

	public RSAddName getLatitudeLongitude(String select) {

		HttpURLConnection conn = null;
		StringBuilder jsonResults = new StringBuilder();
		RSAddName addResult = new RSAddName();
		try {
			StringBuilder sb = new StringBuilder(Constant.PLACES_API_GEOCODE + Constant.OUT_JSON);
			sb.append("?sensor=false&key=" + Constant.API_KEY);
			sb.append("&address=" + URLEncoder.encode(select, "utf8"));

			URL url = new URL(sb.toString());
			conn = (HttpURLConnection) url.openConnection();
			InputStreamReader in = new InputStreamReader(conn.getInputStream());

			// Load the results into a StringBuilder
			int read;
			char[] buff = new char[1024];
			while ((read = in.read(buff)) != -1) {
				jsonResults.append(buff, 0, read);
			}
		} catch (MalformedURLException e) {
			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} catch (IOException e) {
			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}

		try {
			// Create a JSON object hierarchy from the results
			JSONObject jsonObj = new JSONObject(jsonResults.toString());			
			JSONObject res = jsonObj.getJSONArray("results").getJSONObject(0);
			JSONObject locationObj = res.getJSONObject("geometry").getJSONObject("location");			
			String longitude = locationObj.get("lng").toString();
			String latitude = locationObj.get("lat").toString();
			String address = "";
			String street = "";
			String countryName = "";
			String countryId = "";
			String city = "";
			JSONArray addrresComArray = res.getJSONArray("address_components");
			
			for (int i = 0; i < addrresComArray.length(); i++) {

				JSONObject addComObject = (JSONObject) addrresComArray.get(i);

				JSONArray typeArray =  (JSONArray) addComObject.get("types");
				for (int k = 0; k < typeArray.length(); k++) {
					String type = typeArray.getString(k);
					if(type.equals("administrative_area_level_3") ){
						street = addComObject.getString("short_name").toString();
					}
					if(type.equals("country") ){
						countryName = addComObject.getString("long_name").toString();
						countryId = addComObject.getString("short_name").toString();
					}
					if(type.equals("administrative_area_level_1") ){
						city = addComObject.getString("long_name").toString();
						System.out.println(city);
					}
					if(address.equals("")){
						String[] formatAddress = res.getString("formatted_address").split(",");;
						int addressSize = formatAddress.length;
						for(int counter = 0; counter < addressSize; counter++){
							address = address.concat(formatAddress[counter]).concat(",");
							address = address.substring(0, address.length() - 1);
						}
					}
				}
			addResult.setCompanyName(select).setCity(city).setCountry(countryName).
			setLatitude(latitude).setLongitude(longitude).setStreet(street).setType("s");
			}
	
		} catch (JSONException e) {
			System.out.println("Error processing Placses API URL");
			e.printStackTrace();
		}
		
		return addResult;
	}

	@Override
	public Long saveAddUser(RSAddName result, String sessionId, String email) {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>();
	    final StringBuilder sBuilder = new StringBuilder();

		ISOAPResultCallBack callBack = new ISOAPResultCallBack(){

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				Long r = Long.parseLong(String.valueOf(data.getAttribute("value")));
				sBuilder.append(String.valueOf(r));
			}

			@Override
			public void handleError(String statusCode) {

				
			}
		};
		
		map.put("sessionId", sessionId);
		map.put("addType", result.getType());
		map.put("name", result.getCompanyName());
		map.put("contactPerson", "");
		map.put("street", result.getStreet());
		map.put("city", result.getCity());
		map.put("fax", "");
		map.put("telp", "");
		map.put("email", email);
		map.put("countryName",result.getCountry());
		map.put("longitude", result.getLongitude());
		map.put("latitude", result.getLatitude());
		
		new CallSOAPAction(map, "addUserDummy", callBack);
		return Long.parseLong(sBuilder.toString());
	}



}
