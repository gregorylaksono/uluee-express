package org.uluee.web.cloud.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FlightSchedule implements java.io.Serializable{
	
	List<Flight> flightList;
	String transportRateFrom;
	String transportRateTo;
	String currTo;
	String currFrom;
	Double durationConsignee;
	Double durationShipper;
	String totalChargesTo;
	String totalChargesFrom;
	List<CommodityItem> commodities = new ArrayList<CommodityItem>();
	
	public Double getDurationConsignee() {
		return durationConsignee;
	}
	public void setDurationConsignee(Double durationConsignee) {
		this.durationConsignee = durationConsignee;
	}
	public Double getDurationShipper() {
		return durationShipper;
	}
	public void setDurationShipper(Double durationShipper) {
		this.durationShipper = durationShipper;
	}
	
	public List<Flight> getFlightList() {
		return flightList;
	}
	public void setFlightList(List<Flight> flightList) {
		this.flightList = flightList;
	}

	public String getCurrTo() {
		return currTo;
	}
	public void setCurrTo(String currTo) {
		this.currTo = currTo;
	}
	public String getCurrFrom() {
		return currFrom;
	}
	public void setCurrFrom(String currFrom) {
		this.currFrom = currFrom;
	}
	public Date getDepartureDate()
	{
		if(flightList != null && flightList.size() > 0)
		{
			return flightList.get(0).getDepartureTime();
		}
		
		return null;
	}
	
	public Date getArrivalDate()
	{
		if(flightList != null && flightList.size() > 0)
		{
			return flightList.get(flightList.size()-1).getArrivalTime();
		}
		
		return null;
	}
	
	public void setCommodities(String comm){
		String[] commodities = comm.split("&&");
		
		for(String com : commodities){
			String comArgs[] = com.split("\\|");
			
			CommodityItem item = new CommodityItem();
			String[] comAn = comArgs[0].split(":");
			item.setComId(comAn[0]);
			item.setName(comAn[2]);
			item.setAnnotation(comAn[1]);
			item.setPieces(comArgs[2]);
			item.setWeight(comArgs[4]);
			item.setLength(comArgs[5]);
			item.setWidth(comArgs[6]);
			item.setHeight(comArgs[7]);
			item.setPrice(comArgs[9]);
			item.setInsurance(comArgs[10]);
			this.commodities.add(item);
		}
	}
	
	public List<CommodityItem> getCommodities() {
		return commodities;
	}
	public String getTransportRateTo() {
		return transportRateTo;
	}
	public void setTransportRateTo(String rateTo) {
		this.transportRateTo = rateTo;
	}
	public String getTransportRateFrom() {
		return transportRateFrom;
	}
	public void setTransportRateFrom(String rateFrom) {
		this.transportRateFrom = rateFrom;
	}
	public String getTotalChargesTo() {
		return totalChargesTo;
	}
	public void setTotalChargesTo(String totalChargesTo) {
		this.totalChargesTo = totalChargesTo;
	}
	public String getTotalChargesFrom() {
		return totalChargesFrom;
	}
	public void setTotalChargesFrom(String totalChargesFrom) {
		this.totalChargesFrom = totalChargesFrom;
	}
	
	
	
}
