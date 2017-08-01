package org.uluee.web.cloud.model;

import java.util.Date;

public class Flight implements java.io.Serializable{
	
	String caId;
	String status;
	String remark;
	String getStatus;
	String getRemark;
	String tempCa3dg;
	String currency;
	String totalRates;
	Date date;
	Date getDate;
	Long fltId;
	Date departureTime;
	Date arrivalTime;
	Integer mode;
	
	public String getCaId() {
		return caId;
	}
	
	public String getTempCa3dg() {
		return tempCa3dg;
	}
	public void setTempCa3dg(String tempCa3dg) {
		this.tempCa3dg = tempCa3dg;
	}
	
	public void setCaId(String caId) {
		this.caId = caId;
	}
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTotalRates() {
		return totalRates;
	}

	public void setTotalRates(String totalRates) {
		this.totalRates = totalRates;
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
