package org.uluee.web.cloud.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class ScheduleDoorToDoor implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7484983826764009577L;
	private String shipper_add_id;
	private String shipper_distance;
	private String shipper_duration;
	private String shipper_rate_from;
	private String shipper_rate_to;
	
	private String consignee_add_id;	            		
	private String consignee_distance;
	private String consignee_duration;
	private String consignee_rate_from;
	private String consignee_rate_to;

	private String rate_airlane_per_kg_from;
	private String rate_airlane_per_kg_to;
	private String total_airlane_from;
	private String total_airlane_to;
	private String fuel_charges_airlane_from;
	private String fuel_charges_airlane_to;
	private String security_charges_airlane_from;
	private String security_charges_airlane_to;			            		
	private String total_fee_from;
	private String total_fee_to;	
	
	private String total_insurance_from;
	private String total_insurance_to;
    private String rateId;
    private Collection flight;
    private boolean isStandalone;
    private String commodities;
    private String sessionKey;
    private String currFrom;
    private String currTo;
    private Object courierImpl;

	public ScheduleDoorToDoor() {
	}
	
	
	public ScheduleDoorToDoor(Collection flight) {
		super();
		this.flight = flight;
	}

	public String getShipper_add_id() {
		return shipper_add_id;
	}

	public ScheduleDoorToDoor setShipper_add_id(String shipper_add_id) {
		this.shipper_add_id = shipper_add_id;
		return this;
	}

	public String getShipper_distance() {
		return shipper_distance;
	}

	public ScheduleDoorToDoor setShipper_distance(String shipper_distance) {
		this.shipper_distance = shipper_distance;
		return this;
	}

	public String getShipper_duration() {
		return shipper_duration;
	}

	public ScheduleDoorToDoor setShipper_duration(String shipper_duration) {
		this.shipper_duration = shipper_duration;
		return this;
	}

	public String getConsignee_add_id() {
		return consignee_add_id;
	}

	public ScheduleDoorToDoor setConsignee_add_id(String consignee_add_id) {
		this.consignee_add_id = consignee_add_id;
		return this;
	}

	public String getConsignee_distance() {
		return consignee_distance;
	}

	public ScheduleDoorToDoor setConsignee_distance(String consignee_distance) {
		this.consignee_distance = consignee_distance;
		return this;
	}

	public String getConsignee_duration() {
		return consignee_duration;
	}

	public ScheduleDoorToDoor setConsignee_duration(String consignee_duration) {
		this.consignee_duration = consignee_duration;
		return this;
	}

	public String getRate_airlane_per_kg_from() {
		return rate_airlane_per_kg_from;
	}

	public ScheduleDoorToDoor setRate_airlane_per_kg_from(String rate_airlane_per_kg_from) {
		this.rate_airlane_per_kg_from = rate_airlane_per_kg_from;
		return this;
	}

	public String getRate_airlane_per_kg_to() {
		return rate_airlane_per_kg_to;
	}

	public ScheduleDoorToDoor setRate_airlane_per_kg_to(String rate_airlane_per_kg_to) {
		this.rate_airlane_per_kg_to = rate_airlane_per_kg_to;
		return this;
	}

	public String getTotal_airlane_from() {
		return total_airlane_from;
	}

	public ScheduleDoorToDoor setTotal_airlane_from(String total_airlane_from) {
		this.total_airlane_from = total_airlane_from;
		return this;
	}

	public String getTotal_airlane_to() {
		return total_airlane_to;
	}

	public ScheduleDoorToDoor setTotal_airlane_to(String total_airlane_to) {
		this.total_airlane_to = total_airlane_to;
		return this;
	}

	public String getFuel_charges_airlane_from() {
		return fuel_charges_airlane_from;
	}

	public ScheduleDoorToDoor setFuel_charges_airlane_from(String fuel_charges_airlane_from) {
		this.fuel_charges_airlane_from = fuel_charges_airlane_from;
		return this;
	}

	public String getFuel_charges_airlane_to() {
		return fuel_charges_airlane_to;
	}

	public ScheduleDoorToDoor setFuel_charges_airlane_to(String fuel_charges_airlane_to) {
		this.fuel_charges_airlane_to = fuel_charges_airlane_to;
		return this;
	}

	public String getSecurity_charges_airlane_from() {
		return security_charges_airlane_from;
	}

	public ScheduleDoorToDoor setSecurity_charges_airlane_from(
			String security_charges_airlane_from) {
		this.security_charges_airlane_from = security_charges_airlane_from;
		return this;
	}

	public String getSecurity_charges_airlane_to() {
		return security_charges_airlane_to;
	}

	public ScheduleDoorToDoor setSecurity_charges_airlane_to(String security_charges_airlane_to) {
		this.security_charges_airlane_to = security_charges_airlane_to;
		return this;
	}

	public String getTotal_fee_from() {
		return total_fee_from;
	}

	public ScheduleDoorToDoor setTotal_fee_from(String total_fee_from) {
		this.total_fee_from = total_fee_from;
		return this;
	}

	public String getTotal_fee_to() {
		return total_fee_to;
	}

	public ScheduleDoorToDoor setTotal_fee_to(String total_fee_to) {
		this.total_fee_to = total_fee_to;
		return this;
	}
	
	public String getShipper_rate_from() {
		return shipper_rate_from;
	}


	public ScheduleDoorToDoor setShipper_rate_from(String shipper_rate_from) {
		this.shipper_rate_from = shipper_rate_from;
		return this;
	}


	public String getShipper_rate_to() {
		return shipper_rate_to;
	}


	public ScheduleDoorToDoor setShipper_rate_to(String shipper_rate_to) {
		this.shipper_rate_to = shipper_rate_to;
		return this;
	}


	public String getConsignee_rate_from() {
		return consignee_rate_from;
	}


	public ScheduleDoorToDoor setConsignee_rate_from(String consignee_rate_from) {
		this.consignee_rate_from = consignee_rate_from;
		return this;
	}


	public String getConsignee_rate_to() {
		return consignee_rate_to;
	}


	public ScheduleDoorToDoor setConsignee_rate_to(String consignee_rate_to) {
		this.consignee_rate_to = consignee_rate_to;
		return this;
	}


	public Collection<Flight> getFlight() {
		return flight;
	}


	public ScheduleDoorToDoor setFlight(Collection flight) {
		this.flight = flight;
		return this;
	}


	public String getCommodities() {
		return commodities;
	}


	public ScheduleDoorToDoor setCommodities(String commodities) {
		this.commodities = commodities;
		return this;
	}


	public String getTotal_insurance_from() {
		return total_insurance_from;
	}


	public ScheduleDoorToDoor setTotal_insurance_from(String total_insurance_from) {
		this.total_insurance_from = total_insurance_from;
		return this;
	}


	public String getTotal_insurance_to() {
		return total_insurance_to;
	}


	public ScheduleDoorToDoor setTotal_insurance_to(String total_insurance_to) {
		this.total_insurance_to = total_insurance_to;
		return this;
	}


	public String getRateId() {
		return rateId;
	}


	public ScheduleDoorToDoor setRateId(String prefix) {
		
		this.rateId = prefix;
		return this;
	}


	public boolean isStandalone() {
		return isStandalone;
	}


	public ScheduleDoorToDoor setStandalone(boolean isStandalone) {
		this.isStandalone = isStandalone;
		return this;
	}


	public Object getCourierImpl() {
		return courierImpl;
	}


	public ScheduleDoorToDoor setCourierImpl(Object courierImpl) {
		this.courierImpl = courierImpl;
		return this;
	}


	public String getSessionKey() {
		return sessionKey;
	}


	public ScheduleDoorToDoor setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
		return this;
	}


	public String getCurrFrom() {
		return currFrom;
	}


	public ScheduleDoorToDoor setCurrFrom(String currFrom) {
		this.currFrom = currFrom;
		return this;
	}


	public String getCurrTo() {
		return currTo;
	}


	public ScheduleDoorToDoor setCurrTo(String currTo) {
		this.currTo = currTo;
		return this;
	}

	
	
	
}
