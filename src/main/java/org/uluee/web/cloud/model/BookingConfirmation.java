package org.uluee.web.cloud.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class BookingConfirmation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ca3dg;
	private String awbStock;
	private String awbNo;
	private Address consignee;
	private Address shipper;
	private LinkedList<Status> statusInformation;
	private List<CommodityItem> itemDetails;
	public String getCa3dg() {
		return ca3dg;
	}
	public void setCa3dg(String ca3dg) {
		this.ca3dg = ca3dg;
	}
	public String getAwbStock() {
		return awbStock;
	}
	public void setAwbStock(String awbStock) {
		this.awbStock = awbStock;
	}
	public String getAwbNo() {
		return awbNo;
	}
	public void setAwbNo(String awbNo) {
		this.awbNo = awbNo;
	}

	public LinkedList<Status> getStatusInformation() {
		return statusInformation;
	}
	public void setStatusInformation(LinkedList<Status> statusInformation) {
		this.statusInformation = statusInformation;
	}
	public Address getConsignee() {
		return consignee;
	}
	public void setConsignee(Address consignee) {
		this.consignee = consignee;
	}
	public Address getShipper() {
		return shipper;
	}
	public void setShipper(Address shipper) {
		this.shipper = shipper;
	}
	public List<CommodityItem> getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(List<CommodityItem> itemDetails) {
		this.itemDetails = itemDetails;
	}
	
	

}
