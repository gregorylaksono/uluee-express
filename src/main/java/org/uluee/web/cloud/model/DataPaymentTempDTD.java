package org.uluee.web.cloud.model;

public class DataPaymentTempDTD {
	private String shipper_id;
	private String shipper_name;
	private String consignee;
	private String consignee_name;
	private String dep_date;
	private String flights;
	private String commodities;
	private String token_id;
	private String payment_id;
	private String amount_from;
	private String amount_to;
	private String currency_from;
	private String currency_to;
	private String ca3dg;
	private String rateId;
	private String insuranceFrom;
	private String insuranceTo;
	
	public String getShipper_id() {
		return shipper_id;
	}
	public void setShipper_id(String shipper_id) {
		this.shipper_id = shipper_id;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	
	public String getDep_date() {
		return dep_date;
	}
	public void setDep_date(String dep_date) {
		this.dep_date = dep_date;
	}
	public String getFlights() {
		return flights;
	}
	public void setFlights(String flights) {
		this.flights = flights;
	}
	public String getCommodities() {
		return commodities;
	}
	public void setCommodities(String commodities) {
		this.commodities = commodities;
	}
	public String getToken_id() {
		return token_id;
	}
	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}
	public String getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}
	
	public String getShipper_name() {
		return shipper_name;
	}
	public void setShipper_name(String shipper_name) {
		this.shipper_name = shipper_name;
	}
	public String getConsignee_name() {
		return consignee_name;
	}
	public void setConsignee_name(String consignee_name) {
		this.consignee_name = consignee_name;
	}

	public String getCa3dg() {
		return ca3dg;
	}
	public void setCa3dg(String ca3dg) {
		this.ca3dg = ca3dg;
	}
	public String getAmount_from() {
		return amount_from;
	}
	public void setAmount_from(String amount_from) {
		this.amount_from = amount_from;
	}
	public String getAmount_to() {
		return amount_to;
	}
	public void setAmount_to(String amount_to) {
		this.amount_to = amount_to;
	}
	public String getCurrency_from() {
		return currency_from;
	}
	public void setCurrency_from(String currency_from) {
		this.currency_from = currency_from;
	}
	public String getCurrency_to() {
		return currency_to;
	}
	public void setCurrency_to(String currency_to) {
		this.currency_to = currency_to;
	}
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	public String getInsuranceFrom() {
		return insuranceFrom;
	}
	public void setInsuranceFrom(String insuranceFrom) {
		this.insuranceFrom = insuranceFrom;
	}
	public String getInsuranceTo() {
		return insuranceTo;
	}
	public void setInsuranceTo(String insuranceTo) {
		this.insuranceTo = insuranceTo;
	}
	
}
