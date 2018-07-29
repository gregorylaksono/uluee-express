package org.uluee.web.cloud;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;
import org.uluee.web.Uluee_expressUI;
import org.uluee.web.cloud.model.Address;
import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.CommodityItem;
import org.uluee.web.cloud.model.DataPaymentTempDTD;
import org.uluee.web.cloud.model.Flight;
import org.uluee.web.cloud.model.FlightSchedule;
import org.uluee.web.cloud.model.PaypalData;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.ScheduleDoorToDoor;
import org.uluee.web.cloud.model.Status;
import org.uluee.web.cloud.model.StatusInfoWrapper;
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
		if(container.size()>0) {
			user = container.get(0);
		}
		return user;
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
	public PaypalData generateRedirectUrlPaypal(double dTotdalRates, String currency) {
		String returnURL = Constant.returnPaypalUrl;		
		String cancelURL = Constant.cancelPaypalUrl;
		String redirectUrl = null;
		String accessToken = null;;
		PaypalData data = new PaypalData();
		try {
			System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
			InputStream is = WebServiceCaller.class.getResourceAsStream("/sdk_config.properties");
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
	public String saveDataPaymentTemp(String tokenId, String paymentId, String sessionKey, 
			String rateId) {

		LinkedHashMap<String, Object> createBookingRequest = new LinkedHashMap<>();
		createBookingRequest.put("sessionId",((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		createBookingRequest.put("tokenId", tokenId);
		createBookingRequest.put("paymentId", paymentId);
		createBookingRequest.put("sessionKey",sessionKey);
		createBookingRequest.put("rateId", rateId);

		StringBuffer result = new StringBuffer();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				//						result.append(data.getProperty("code").toString()); 
			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(createBookingRequest, "saveBookingTempDoorToDoor", callBack);
		return result.toString();
	}

	public DataPaymentTempDTD getTempData(String tokenId, String paymentId) {
		LinkedHashMap<String, Object> createBookingRequest = new LinkedHashMap<>();
		createBookingRequest.put("tokenId", tokenId);
		createBookingRequest.put("paymentId", paymentId);
		final DataPaymentTempDTD temp = new DataPaymentTempDTD();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				temp.setAmount_from(data.getProperty("amount_from").toString());
				temp.setAmount_to(data.getProperty("amount_to").toString());
				temp.setCurrency_from(data.getProperty("currency_from").toString());
				temp.setCurrency_to(data.getProperty("currency_to").toString());
				temp.setRateId(data.getProperty("rateId").toString());

			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(createBookingRequest, "getBookingTempDoorToDoor", callBack);

		return temp;
	}

	@Override
	public BookingConfirmation createBookingDoorToDoorNew( String sessionKey, String rateId) {
		LinkedHashMap<String, Object> createBookingRequest = new LinkedHashMap<>();

		createBookingRequest.put("sessionId", ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		createBookingRequest.put("sessionKey", sessionKey);
		createBookingRequest.put("rateId", rateId);
		createBookingRequest.put("force", true);
		final BookingConfirmation temp = new BookingConfirmation();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				SoapObject master = (SoapObject) data.getProperty("address");
				SoapObject consigneeSoap = (SoapObject) master.getProperty("consignee");
				SoapObject shipperSoap = (SoapObject) master.getProperty("shipper");
				SoapObject bookingStatusInfo = (SoapObject) data.getProperty("statusInformation");
				SoapObject commidityStatusInfo = (SoapObject) data.getProperty("shipmentInformation");
				String awb = data.getProperty("awb").toString();
				String[] awbDataPart = awb.split("-");
				List<CommodityItem> items = new ArrayList();
				for(int i=0; i<commidityStatusInfo.getPropertyCount(); i++) {
					CommodityItem item = new CommodityItem();
					SoapObject commodityStats = (SoapObject) commidityStatusInfo.getProperty(i);
					String height = commodityStats.getProperty("height").toString();
					String length = commodityStats.getProperty("length").toString();
					String name = commodityStats.getProperty("name").toString();
					String pcs = commodityStats.getProperty("pcs").toString();
					String total = commodityStats.getProperty("total").toString();
					String wgt = commodityStats.getProperty("wgt").toString();
					String vol = commodityStats.getProperty("vol").toString();
					String width = commodityStats.getProperty("width").toString();

					item.setHeight(height).setLength(length).setName(name).setPieces(pcs).setWeight(wgt).setWidth(width);
					items.add(item);
				}

				temp.setItemDetails(items);
				Address consAddress = new Address();
				consAddress.setCity(consigneeSoap.getProperty("city").toString()).setCountry(consigneeSoap.getProperty("country").toString()).
				setEmail(consigneeSoap.getProperty("email").toString()).setName(consigneeSoap.getProperty("name").toString());

				Address shipperAddress = new Address();
				shipperAddress.setCity(shipperSoap.getProperty("city").toString()).setCountry(shipperSoap.getProperty("country").toString()).
				setEmail(shipperSoap.getProperty("email").toString()).setName(shipperSoap.getProperty("name").toString());
				temp.setShipper(shipperAddress);
				temp.setConsignee(consAddress);
				temp.setCa3dg(awbDataPart[0]);
				temp.setAwbStock(awbDataPart[1]);
				temp.setAwbNo(awbDataPart[2]);
				LinkedList<Status> statusList = new LinkedList<Status>();
				for(int i=0; i<bookingStatusInfo.getPropertyCount(); i++) {
					Status status = new Status();
					SoapObject flight = (SoapObject) bookingStatusInfo.getProperty(i);
					String date =flight.getProperty("date").toString();
					String remark = flight.getProperty("remark").toString();
					String stats = flight.getProperty("status").toString();

					status.setDate(date);
					status.setRemark(remark);
					status.setStatus(stats);
					statusList.add(status);
				}

				temp.setStatusInformation(statusList);

			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(createBookingRequest, "createBookingDoorToDoor", callBack);

		return temp;
	}


	private LinkedList<Status> getStatusTracing(String ca3dg, String awbStock, String awbNo) {
		LinkedHashMap<String, Object> statusRequest = new LinkedHashMap<>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		SimpleDateFormat sdf2 = new SimpleDateFormat("M/d/yyyy HH:mm");
		statusRequest.put("sessionId", ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		statusRequest.put("ca3dg",ca3dg);
		statusRequest.put("awbStock", awbStock);
		statusRequest.put("awbNo", awbNo);

		final LinkedList<Status> temp = new LinkedList();

		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				for (int i = 0; i < data.getPropertyCount(); i++) {
					SoapObject dataTrackingObj = (SoapObject) data.getProperty(i);													
					try {
						Status status = new Status();
						Date date = sdf1.parse(dataTrackingObj.getProperty("date").toString());
						status.setDate(sdf2.format(date));
						status.setRemark(dataTrackingObj.getProperty("remark").toString());
						status.setStatus(dataTrackingObj.getProperty("status").toString());
						temp.add(status);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(statusRequest, "getAWBStatusTrack", callBack );
		return temp;
	}

	public BookingConfirmation getTracingShipmentInfo(String ca3dg, String awbStock, String awbNo) {
		LinkedList<Status> statusData = getStatusTracing(ca3dg, awbStock, awbNo);

		LinkedHashMap<String, Object> tracingInfoRequest = new LinkedHashMap<>();
		tracingInfoRequest.put("sessionId", ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		tracingInfoRequest.put("ca3dg",ca3dg);
		tracingInfoRequest.put("awbStock", awbStock);
		tracingInfoRequest.put("awbNo", awbNo);
		final BookingConfirmation temp = new BookingConfirmation();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				SoapObject address = (SoapObject) data.getProperty("address");
				SoapObject shipper = (SoapObject) address.getProperty("shipper");
				SoapObject consignee = (SoapObject) address.getProperty("consignee");
				List<CommodityItem> comList = new ArrayList<>();
				Address address1 = new Address();
				address1.setName(shipper.getProperty("name").toString());
				address1.setCountry(shipper.getProperty("country").toString());
				address1.setEmail(shipper.getProperty("email").toString());
				address1.setCity(shipper.getProperty("city").toString());

				Address address2 = new Address();
				address2.setName(consignee.getProperty("name").toString());
				address2.setCountry(consignee.getProperty("country").toString());
				address2.setEmail(consignee.getProperty("email").toString());
				address2.setCity(consignee.getProperty("city").toString());

				SoapObject shipmentInfo = (SoapObject) data.getProperty("shipmentInformation");

				for(int i=0; i<shipmentInfo.getPropertyCount(); i++){
					SoapObject shipmentIndex = (SoapObject) shipmentInfo.getProperty(i);
					String height = shipmentIndex.getProperty("height").toString();
					String name = shipmentIndex.getProperty("name").toString();
					String weight = shipmentIndex.getProperty("wgt").toString();
					String width = shipmentIndex.getProperty("width").toString();
					String pieces = shipmentIndex.getProperty("pcs").toString();

					CommodityItem item = new CommodityItem();
					item.setHeight(height).setName(name).setWeight(weight).setWidth(width).setPieces(pieces);
					comList.add(item);

				}

				temp.setShipper(address1);
				temp.setConsignee(address2);
				temp.setStatusInformation(statusData);
				temp.setItemDetails(comList);
				temp.setCa3dg(ca3dg);
				temp.setAwbStock(awbStock);
				temp.setAwbNo(awbNo);
			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(tracingInfoRequest, "getShipmentSummary", callBack  );
		return temp;
	}

	@Override
	public boolean sendFWB(String ca3dg, String awbStock, String awbNo) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("sessionId", ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		params.put("ca3dg",ca3dg);
		params.put("awbStock", awbStock);
		params.put("awbNo", awbNo);
		final StringBuffer r = new StringBuffer();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				SoapObject ActernityResponse = (SoapObject) data.getProperty(0);

				String code = ActernityResponse.getProperty("code").toString();
				if(code.equals("00:success")){
					r.append(true);
				}
			}

			@Override
			public void handleError(String statusCode) {

			}

		};
		new CallSOAPAction(params, "sendFWB", callBack  );
		return Boolean.parseBoolean(r.toString());
	}

	@Override
	public String printBarCode(String ca3dg, String awbStock, String awbNo) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("sessionId", ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		params.put("ca3dg", ca3dg);
		params.put("awbStock", awbStock);
		params.put("awbNo", awbNo);
		final StringBuffer r = new StringBuffer();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				// Response Successful
				if (statusCode.equalsIgnoreCase("00:success")) {
					for (int i = 0; i < data.getPropertyCount(); i++) {
						r.append(data.getProperty(i).toString());						
					}
				}			
			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(params, "printBarcode", callBack  );
		return r.toString();
	}

	@Override
	public String printInvoice(String ca3dg, String awbStock, String awbNo) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("sessionId", ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		params.put("ca3dg", ca3dg);
		params.put("awbStock", awbStock);
		params.put("awbNo", awbNo);
		final StringBuffer r = new StringBuffer();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				// Response Successful
				if (statusCode.equalsIgnoreCase("00:success")) {
					for (int i = 0; i < data.getPropertyCount(); i++) {
						r.append(data.getProperty(i).toString());						
					}
				}			
			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(params, "printInvoicePdf", callBack  );
		return r.toString();
	}

	@Override
	public String printAwb(String ca3dg, String awbStock, String awbNo) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("sessionId", ((Uluee_expressUI)UI.getCurrent()).getUser().getSessionId());
		params.put("ca3dg", ca3dg);
		params.put("awbStock", awbStock);
		params.put("awbNo", awbNo);
		final StringBuffer r = new StringBuffer();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				r.append(data.toString());						
			}

			@Override
			public void handleError(String statusCode) {

			}
		};
		new CallSOAPAction(params, "printAWB", callBack  );
		return r.toString();


	}

	@Override
	public Map<String, List<String>> getAirportList(String caId, String sessionId, String match) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("sessionId", sessionId);
		params.put("request", "departure");
		params.put("airport", match);
		params.put("caId", caId);
		List<String> airlineList = new ArrayList<>();
		List<String> airlineListTo = new ArrayList<>();
		Map<String, List<String>> result = new HashMap<>();
		result.put("from", airlineList);
		result.put("to", airlineListTo);

		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				SoapObject airportFromObj = (SoapObject) data.getProperty(1);
				SoapObject airportToObj = (SoapObject) data.getProperty(2);
				if (airportFromObj != null) {
					airlineList.clear();
					for (int i = 0; i < airportFromObj.getPropertyCount(); i++) {
						String FormatAirline = airportFromObj.getProperty(i).toString();
						String airlineArrayOfString[]	= FormatAirline.split(",");
						String airlineCode = airlineArrayOfString[0];
						String airlineCity = airlineArrayOfString[1];
						String airlineCountry = airlineArrayOfString[2];
						airlineCountry = airlineCountry.substring(0, airlineCountry.length() - 1);
						String ailrlineFull = airlineCountry+","+airlineCity+" "+"("+airlineCode +")"+";";
						airlineList.add(ailrlineFull);
					}
				}
				for (int i = 0; i < airportToObj.getPropertyCount(); i++) {
					String FormatAirline = airportToObj.getProperty(i).toString();	
					String airlineArrayOfString[]	= FormatAirline.split(",");
					String airlineCode = airlineArrayOfString[0];
					String airlineCity = airlineArrayOfString[1];
					String airlineCountry = airlineArrayOfString[2];
					airlineCountry = airlineCountry.substring(0, airlineCountry.length() - 1);
					String ailrlineFull = airlineCountry+","+airlineCity+" "+"("+airlineCode +")"+";";
					airlineListTo.add(ailrlineFull);
				}
				Collections.sort(airlineList);
				Collections.sort(airlineListTo);

			}

			@Override
			public void handleError(String statusCode) {

			}

		};
		new CallSOAPAction(params, "getAirportListBySchedule", callBack);
		return result;


	}

	@Override
	public List<FlightSchedule> getSchedules(String sessionId, String caId, String c3dg, String deprature,
			String destination, String deptDate, String destDate, String[] commodities) {
		List<FlightSchedule> result =  new ArrayList<FlightSchedule>();
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("sessionId",sessionId);

		params.put("airline_ca_id", caId);
		params.put("airline_ca_3dg", c3dg);

		params.put("departure",deprature);
		params.put("destination",destination);
		params.put("minDeparture",deptDate);
		params.put("maxArrival",destDate);
		
		params.put("commodities",commodities);
		
		ISOAPResultCallBack callBack = new ISOAPResultCallBack() {

			@Override
			public void handleResult(SoapObject data, String statusCode) {
				for (int i = 0; i < data.getPropertyCount(); i++) {
					SoapObject flightScheduleObj = (SoapObject) data.getProperty(i);						

					List<Flight> flightList = new ArrayList<Flight>();
					SoapObject flightListObj = (SoapObject) flightScheduleObj.getProperty("flight");

					for (int j = 0; j < flightListObj.getPropertyCount(); j++) {
						SoapObject flightObj = (SoapObject) flightListObj.getProperty(j);

						Flight flight = new Flight();
						Date departureString = null;
						Date arrivalString = null;
						try {
							departureString = sdf.parse(flightObj.getProperty("departure").toString());
							arrivalString = sdf.parse(flightObj.getProperty("arrival").toString());
						} catch (ParseException e) {
							e.printStackTrace();
						}

						flight.setDepartureTime(departureString);
						flight.setArrivalTime(arrivalString);
						flight.setCaId(flightObj.getProperty("caId").toString());
						flight.setFltId(Long.parseLong(flightObj.getProperty("fltId").toString()));
						flight.setMode(Integer.parseInt(flightObj.getProperty("mode").toString()));
						flight.setRate(flightScheduleObj.getProperty("rateFrom").toString());
						flight.setCurrency(flightScheduleObj.getProperty("rateFromCurr").toString());
						flight.setDepart(flightObj.getProperty("dept").toString());
						flight.setDestin(flightObj.getProperty("dest").toString());
						flightList.add(flight);
					}

					FlightSchedule flightSchedule = new FlightSchedule();
					flightSchedule.setFlightList(flightList);
					result.add(flightSchedule);						
				}				
			}

			@Override
			public void handleError(String statusCode) {
				System.out.println(statusCode);
			}
			
		};
		new CallSOAPAction(params, "getSchedules", callBack);
		return result;
	}

	@Override
	public BookingConfirmation book(String sessionId, String caidtemp, String ca3dgtemp, String[] flights,
			String[] commodities, String shipperId, String consignee, String agentId, String depDate) {
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<>();
		params.put("sessionId",sessionId);
		params.put("airline_ca_id",caidtemp );
		params.put("airline_ca_3dg", ca3dgtemp);
		params.put("flights", flights);
		params.put("commodities", commodities);
		params.put("shipperId", shipperId);
		params.put("consignee", consignee);
		params.put("agentId", agentId);
		params.put("depDate", depDate);
		
		
		BookingConfirmation result = new BookingConfirmation();
		ISOAPResultCallBack callBack = new ISOAPResultCallBack(){

			@Override
			public void handleResult(SoapObject soap, String statusCode) {
				SoapObject ActernityResponse = (SoapObject) soap.getProperty(0);
				String createBookingStatus = ActernityResponse.getProperty("code").toString(); 
				String message = ActernityResponse.getProperty("message").toString();
				if (createBookingStatus.equalsIgnoreCase(CallSOAPAction.SUCCESS_CODE)) {

					SoapObject data = (SoapObject) ActernityResponse.getProperty("data");
					String awb = data.getProperty("awb").toString();
					if (awb != null){
						String ca3dg = awb.substring(0, 3);
						String awbStock = awb.substring(4, 8);
						String awbNo = awb.substring(9, 13);
						SoapObject bookingInfo = (SoapObject) data.getProperty("bookingInformation");
						SoapObject dataList = (SoapObject) bookingInfo.getProperty("list");
						String customCode = dataList.getProperty("customCode").toString();
						result.setCa3dg(ca3dg);
						result.setAwbNo(awbNo);
						result.setAwbStock(awbStock);
						
					}			
			}
			}

			@Override
			public void handleError(String statusCode) {
				
			}
		};	
		new CallSOAPAction(params, "createBooking", callBack);
		return result;
	}



	@Override
	public String getFFWB(String sessionId) {

		return null;
	}


}
