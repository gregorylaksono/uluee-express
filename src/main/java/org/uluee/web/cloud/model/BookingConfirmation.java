package org.uluee.web.cloud.model;

import java.io.Serializable;

public class BookingConfirmation implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ca3dg;
	private String awbStock;
	private String awbNo;
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
	
	

}
