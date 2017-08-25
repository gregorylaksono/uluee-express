package org.uluee.web.cloud;

import java.util.LinkedHashMap;
import java.util.List;

import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.DataPaymentTempDTD;
import org.uluee.web.cloud.model.PaypalData;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.ScheduleDoorToDoor;
import org.uluee.web.cloud.model.User;



public interface IWebService {
	public static final String ADDRESS = "address";
	public static final String LATITUDE = "lat";
	public static final String LONGTITUDE = "long";
	public static final String COMPANY = "company";
	
	public User login(String username, String password);
	public List<String> getGoogleAutocomplete(String match);
	public List<RSAddName> getShipperFfwAlsoNotifyDeliveredToAddressByMatchService(String match, String sessionId);
	public List<RSAddName> getConsigneeAddressByMatch(String match, String sessionId);
	public List<Commodity> getCommodity(String commodity,String sessionId);
	public RSAddName getLatitudeLongitude(String select);
	public Long saveAddUser(RSAddName result, String sessionId, String email);
	public List<ScheduleDoorToDoor> getSchedules(LinkedHashMap<String, Object> param);
	public List<RSAddName> getFfwAddressByMatch(String match, String sessionId);
	public PaypalData generateRedirectUrlPaypal(double dTotdalRates, String currency);
	
	public String saveDataPaymentTemp(String tokenId, String paymentId, String sessionKey, String rateId);
	public DataPaymentTempDTD getTempData(String tokenId, String paymentId);
	
	public BookingConfirmation createBookingDoorToDoorNew(String sessionKey, String rateId);
}
