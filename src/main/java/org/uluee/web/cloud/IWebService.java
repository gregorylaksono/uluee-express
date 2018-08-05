package org.uluee.web.cloud;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.uluee.web.cloud.model.BookingConfirmation;
import org.uluee.web.cloud.model.Commodity;
import org.uluee.web.cloud.model.DataPaymentTempDTD;
import org.uluee.web.cloud.model.FlightSchedule;
import org.uluee.web.cloud.model.MrnWrapper;
import org.uluee.web.cloud.model.PaypalData;
import org.uluee.web.cloud.model.Preferences;
import org.uluee.web.cloud.model.RSAddName;
import org.uluee.web.cloud.model.ScheduleDoorToDoor;
import org.uluee.web.cloud.model.Status;
import org.uluee.web.cloud.model.User;
import org.uluee.web.cloud.model.UserWrapper;



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
	public List<RSAddName> getFfwAddressByMatch(String match, String sessionId);
	public PaypalData generateRedirectUrlPaypal(double dTotdalRates, String currency);

	public String saveDataPaymentTemp(String tokenId, String paymentId, String sessionKey, String rateId);
	public DataPaymentTempDTD getTempData(String tokenId, String paymentId);

	public BookingConfirmation createBookingDoorToDoorNew(String sessionKey, String rateId);
	public BookingConfirmation getTracingShipmentInfo(String ca3dg, String awbStock, String awbNo) ;

	public String printBarCode(String ca3dg, String awbStock, String awbNo );
	public String printInvoice(String ca3dg, String awbStock, String awbNo );
	public String printAwb(String ca3dg, String awbStock, String awbNo );
	public Map<String, List<String>> getAirportList(String caId, String sessionId, String match);
	public List<FlightSchedule> getSchedules(String sessionId, String caId, String c3dg, String deprature, String destination, String deptDate, String destDate, String[] commodities);
	public BookingConfirmation book(String sessionId, String caidtemp, String ca3dgtemp, String[] flights, String[] commodities,
			String shipperId, String consignee, String agentId, String depDate);
	public String getFFWB(String sessionId);
	public List<String> getCurrencies(String sessionId);
	public boolean register(String registerContent);
	public Preferences getPreferences(String sessionId);
	public boolean savePreferences(Preferences pref,String sessionId);
	public List<UserWrapper> getUserWrapper(String sessionId);
	public boolean saveUser(UserWrapper u, String sessionId);
	public boolean updateUser(UserWrapper u, String sessionId);
	public boolean deleteUser(UserWrapper u, String sessionId);
	public boolean sendFwb(String sessionId,String ca3dg,String awbStock, String awbNo);
	public String print(String sessionId,String ca3dg,String awbStock, String awbNo,int type);
	public List<String> getCucCode(String sessionId);
	public List<String> getMrnCodeByAwb(String sessionId,String ca3dg,String awbStock, String awbNo);
	public String getCucNumberByAwb(String sessionId, String ca3dg, String awbStock, String awbNo);
	public boolean saveCucMuc(String sessionId, String ca3dg, String awbStock, String awbNo, List<MrnWrapper> codes, String selected);

}
