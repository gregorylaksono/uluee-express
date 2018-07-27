package org.uluee.web.cloud.model;

import java.util.Date;

public class Flight implements java.io.Serializable{

	String caId;
	String status;
	String remark;
	String getStatus;
	String getRemark;
	String rate;
	String currency;
	Date date;
	Date getDate;
	Long fltId;
	Date departureTime;
	Date arrivalTime;
	Integer mode;
	String depart;
	String destin;
	
	public String getDepart() {
		return depart;
	}
	public void setDepart(String depart) {
		this.depart = depart;
	}
	public String getDestin() {
		return destin;
	}
	public void setDestin(String destin) {
		this.destin = destin;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	
	public String getCaId() {
		return caId;
	}
	public void setCaId(String caId) {
		this.caId = caId;
	}
	public Long getFltId() {
		return fltId;
	}
	public void setFltId(Long fltId) {
		this.fltId = fltId;
	}
	public Date getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	public Date getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getRemark() {
		return remark;
	}


}	
