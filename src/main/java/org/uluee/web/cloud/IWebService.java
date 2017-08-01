package org.uluee.web.cloud;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.FlightSchedule;
import org.uluee.web.cloud.model.RSAddName;
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
	public List<FlightSchedule> getSchedules(LinkedHashMap<String, Object> param);
}
