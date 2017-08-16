package org.uluee.web.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;
import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.Flight;
import org.uluee.web.cloud.model.FlightSchedule;
import org.uluee.web.cloud.model.PaypalData;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.User;
import org.uluee.web.util.CallSOAPAction;
import org.uluee.web.util.CallSOAPAction.ISOAPResultCallBack;
import org.uluee.web.util.Constant;

import com.google.gwt.thirdparty.json.JSONArray;
import com.google.gwt.thirdparty.json.JSONException;
import com.google.gwt.thirdparty.json.JSONObject;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.OAuthTokenCredential;
import com.vaadin.ui.UI;

import elemental.html.IceCallback;




public class WebServiceCaller implements IWebService {

	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	private DecimalFormat df = new DecimalFormat("#.00"); 
	
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
//			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} catch (IOException e) {
//			System.out.println("Error processing Places API URL");
			e.printStackTrace();

		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return jsonResults.toString();
	}
	
	
	public static void main(String[] args) {
//		LinkedHashMap<String, Object> param = new LinkedHashMap<>();
//			param.put("sessionId","3F72F2D96D92B6D3EC58B6EEAEE636FE9");
//			param.put("shipperName", "McDonald’s, Bahnhofplatz, Zürich, Switzerland" );
//			param.put("consigneeName", "McDonald's, Am Borsigturm, Tegel, Berlin, Germany");	
//			param.put("minDep", "08/11/2017 16:22");
//			param.put("maxArr", "08/12/2017 16:22");
//			param.put("commodities","1354:0:books |null|1|Each|1.0|1|1|1|1.0|| | | | | | ");
//			param.put("shipperAddId", "123451397");	
//			param.put("latitudeShipper", "47.3766969");	
//			param.put("longitudeShipper", "8.540349299999999");	
//			param.put("consigneeAddId", "123451398");
//			param.put("latitudeConsignee", "50.1077812");	
//			param.put("longitudeConsignee", "8.663020099999999");
//		new WebServiceCaller().getSchedules(param);
		
		PaypalData a = new WebServiceCaller().generateRedirectUrlPaypal(100d, "USD");
		System.out.println(a.getRedirectUrl());
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
	public List<RSAddName> getFfwAddressByMatch(String match, String sessionId) {
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
					if (array.getProperty("type") != null) rsAddNameTemp.setType(array.getProperty("type").toString());
					if (array.getProperty("name") != null) rsAddNameTemp.setCompanyName(array.getProperty("name").toString());
					if (array.getProperty("city") != null) rsAddNameTemp.setCity(array.getProperty("city").toString());
					if (array.getProperty("country") != null) rsAddNameTemp.setCountry(array.getProperty("country").toString());
					if (array.getProperty("latitude") != null) rsAddNameTemp.setLatitude(array.getProperty("latitude").toString());
					if (array.getProperty("longitude") != null) rsAddNameTemp.setLongitude(array.getProperty("longitude").toString());
					if (array.getProperty("street") != null) rsAddNameTemp.setStreet(array.getProperty("street").toString());
					consigneeNames.add(rsAddNameTemp);
				}
			}

			@Override
			public void handleError(String statusCode) {


			}

		};
		new CallSOAPAction(map, "getFfwAddressByMatch", callBack);
		return consigneeNames;
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
					if(type.equals("administrative_area_level_1") ){
						city = addComObject.getString("long_name").toString();
					}
					if(type.equals("administrative_area_level_1") && city.equals("")) {
						city = addComObject.getString("long_name").toString();
					}
					if(type.equals("administrative_area_level_3") && city.equals("")){
						city = addComObject.getString("short_name").toString();
					}
					if(type.equals("country") ){
						countryName = addComObject.getString("long_name").toString();
						countryId = addComObject.getString("short_name").toString();
					}
					if(type.equals("route") ){
						street = addComObject.getString("long_name").toString();
					}
					if(type.equals("administrative_area_level_4") && street.equals("")){
						street = addComObject.getString("long_name").toString();
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
			}
			addResult.setCompanyName(select).setCity(city).setCountry(countryName).
			setLatitude(latitude).setLongitude(longitude).setStreet(street).setType("s");
	
		} catch (JSONException e) {
//			System.out.println("Error processing Placses API URL");
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

	@Override
	public List<FlightSchedule> getSchedules(LinkedHashMap<String, Object> param) {
		List<FlightSchedule> flightScheduleList = new ArrayList<FlightSchedule>();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack(){

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				try {
					
					for (int i = 0; i < data.getPropertyCount(); i++) {
						SoapObject flightScheduleObj = (SoapObject) data.getProperty(i);						
						
						List<Flight> flightList = new ArrayList<Flight>();
						SoapObject flightListObj = (SoapObject) flightScheduleObj.getProperty("flight");
						
						for (int j = 0; j < flightListObj.getPropertyCount(); j++) {
							SoapObject flightObj = (SoapObject) flightListObj.getProperty(j);
							
							Flight flight = new Flight();
							Date departureString = sdf.parse(flightObj.getProperty("departure").toString());
							Date arrivalString = sdf.parse(flightObj.getProperty("arrival").toString());
							
							flight.setDepartureTime(departureString);
							flight.setArrivalTime(arrivalString);							
							flight.setCaId(flightObj.getProperty("caId").toString());
							flight.setTempCa3dg(flightObj.getProperty("ca3dg").toString());
							flight.setFltId(Long.parseLong(flightObj.getProperty("fltId").toString()));
							flight.setMode(Integer.parseInt(flightObj.getProperty("mode").toString()));
							flight.setTotalRates(flightScheduleObj.getProperty("total_fee_to").toString().split(" ")[0]);
							flight.setCurrency(flightScheduleObj.getProperty("total_fee_to").toString().split(" ")[1]);
							flightList.add(flight);
						}
						
						FlightSchedule flightSchedule = new FlightSchedule();
						flightSchedule.setFlightList(flightList);
						flightSchedule.setDurationConsignee(Double.parseDouble(flightScheduleObj.getProperty("consignee_duration").toString()));
						flightSchedule.setDurationShipper(Double.parseDouble(flightScheduleObj.getProperty("shipper_duration").toString()));
						
						String[] totalFeeFrom = flightScheduleObj.getProperty("total_fee_from").toString().split(" ");
						String[] totalFeeTo = flightScheduleObj.getProperty("total_fee_to").toString().split(" ");
						
						Number tranposortRateFrom  = df.parse(totalFeeFrom[0]);
						String sTransportRateFrom = df.format(tranposortRateFrom);
						flightSchedule.setTransportRateFrom(sTransportRateFrom);
						flightSchedule.setCurrFrom(totalFeeFrom[1]);
						
						Number transportRateTo = df.parse(totalFeeTo[0]);
						String sTransportRateTo = df.format(transportRateTo);
						flightSchedule.setTransportRateTo(sTransportRateTo);
						flightSchedule.setCurrTo(totalFeeTo[1]);
						
						Double totalChargesTo = Double.parseDouble(flightSchedule.getTransportRateTo()) + Double.parseDouble(flightScheduleObj.getProperty("total_insurance_to").toString());
						Double totalChargesFrom = Double.parseDouble(flightSchedule.getTransportRateFrom()) + Double.parseDouble(flightScheduleObj.getProperty("total_insurance_from").toString());
						
						flightSchedule.setTotalChargesTo(df.format(totalChargesTo));
						flightSchedule.setTotalChargesFrom(df.format(totalChargesFrom));
						
						String commodities = flightScheduleObj.getProperty("commodities").toString();
						flightSchedule.setCommodities(commodities);
						
						String value = flightSchedule.getTransportRateFrom();
						
						flightScheduleList.add(flightSchedule);						
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void handleError(String statusCode) {
				System.out.println(statusCode);
				
			}
			
		};
		new CallSOAPAction(param, "getSchedulesDoorToDoor", callBack);
		return flightScheduleList;
	}

	@Override
	public PaypalData generateRedirectUrlPaypal(double dTotdalRates, String currency) {
		String returnURL = Constant.returnPaypalUrl;		
		String cancelURL = Constant.cancelPaypalUrl;
		String redirectUrl = null;
		String accessToken = null;;
		PaypalData data = new PaypalData();
		try {
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			InputStream is = WebServiceCaller.class.getResourceAsStream("sdk_config.properties");
			OAuthTokenCredential credential = Payment.initConfig(is);
			accessToken  = credential.getAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}

		String newTotalRates = String.format(Locale.US, "%,.2f", dTotdalRates);
		System.out.println("before parsing ==> " + newTotalRates);
		newTotalRates = newTotalRates.replace(",", "");
		System.out.println("old Total Rates ==> " + newTotalRates);
		try{
			Amount amount = new Amount();
			amount.setTotal(newTotalRates);
			amount.setCurrency(currency);
			
			Transaction transaction = new Transaction();
			transaction.setAmount(amount);
			
			List<Transaction> transactions = new ArrayList<Transaction>();
			transactions.add(transaction);			
			
			Payer payer = new Payer();
			payer.setPaymentMethod("paypal");
			
			PayerInfo info = new PayerInfo();
			payer.setPayerInfo(info);
				
			RedirectUrls redirectUrls = new RedirectUrls();
			
			redirectUrls.setCancelUrl(cancelURL);
			redirectUrls.setReturnUrl(returnURL);
			
			Payment payment = new Payment();
			payment.setIntent("sale");
			payment.setPayer(payer);
			payment.setTransactions(transactions);
			payment.setRedirectUrls(redirectUrls);			
			Payment createdPayment = payment.create(accessToken);	
			redirectUrl = createdPayment.getLinks().get(1).getHref();

			String paymentId = createdPayment.getLinks().get(0).getHref().split("payment/")[1];
			String token = createdPayment.getLinks().get(1).getHref().split("token=")[1];
			data.setPaymentId(paymentId).setToken(token).setRedirectUrl(redirectUrl);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return data;
	}

	@Override
	public String saveDataPaymentTemp(List<Flight> flightList, String paymentID, String tokenID, String totalAmount,
			String curr3lc, String[] commodities, String shipperId, String consigneeId) {
				
				LinkedHashMap<String, Object> createBookingRequest = new LinkedHashMap<>();
				createBookingRequest.put("sessionId",((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
				createBookingRequest.put("airline_ca_id", flightList.get(0).getCaId());
				createBookingRequest.put("airline_ca_3dg", flightList.get(0).getTempCa3dg());

				for (Flight flight : flightList) {
					//example parameter: flights=XOM|123450000110|11/12/2013%2008:00|11/12/2013%2012:00|0&
					String tempFlight = flight.getCaId()+"|"+flight.getFltId()+"|"+sdf.format(flight.getDepartureTime())+
							"|"+sdf.format(flight.getArrivalTime())+"|"+flight.getMode()+"|"+flight.getTempCa3dg();
					createBookingRequest.put("flights",tempFlight);
				}

				for (int i = 0; i < commodities.length; i++) {
					createBookingRequest.put("commodities",commodities[i]);
				}

				createBookingRequest.put("shipperId",shipperId);
//				createBookingRequest.addProperty("shipperId",shipperName);

//				String tempConsignee = consigneeId+"|"+consigneeParentId+"|"+consigneeType+"|"+"false";
//				String tempConsignee = consigneeId+"|"+consigneeName+"|"+""+"|"+"false";

				createBookingRequest.put("consignee",/*tempConsignee*/consigneeId);
				createBookingRequest.put("insurance", "");
				createBookingRequest.put("alsoNotifyId","");
				createBookingRequest.put("deliveredToId","");
				createBookingRequest.put("depDate",sdf.format(flightList.get(0).getDepartureTime()));
				createBookingRequest.put("accInfo", "");
				createBookingRequest.put("handlInfo", "");
				createBookingRequest.put("tokenId", tokenID);
				createBookingRequest.put("paymentId", paymentID);
				createBookingRequest.put("amount", totalAmount);
				createBookingRequest.put("cur3lc", curr3lc);
				
				ISOAPResultCallBack callBack = new ISOAPResultCallBack() {
					
					@Override
					public void handleResult(SoapObject data, String statusCode) {
						data.getProperty("code").toString(); 
					}
					
					@Override
					public void handleError(String statusCode) {
						
					}
				};
				new CallSOAPAction(createBookingRequest, "getSchedulesDoorToDoor", callBack);
				return null;
	}



}
