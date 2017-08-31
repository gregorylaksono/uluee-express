package org.uluee.web.cloud.model;

import java.io.Serializable;
import java.util.List;

public class StatusInfoWrapper implements Serializable{
	
	private String ca3dg;
	private String awbStock;
	private String awbNo;
	
	private List<Status> statusInformation;

	public String getCa3dg() {
		return ca3dg;
	}

	public StatusInfoWrapper setCa3dg(String ca3dg) {
		this.ca3dg = ca3dg;
		return this;
	}

	public String getAwbStock() {
		return awbStock;
	}

	public StatusInfoWrapper setAwbStock(String awbStock) {
		this.awbStock = awbStock;
		return this;
	}

	public String getAwbNo() {
		return awbNo;
	}

	public StatusInfoWrapper setAwbNo(String awbNo) {
		this.awbNo = awbNo;
		return this;
	}

	public List<Status> getStatusInformation() {
		return statusInformation;
	}

	public StatusInfoWrapper setStatusInformation(List<Status> statusInformation) {
		this.statusInformation = statusInformation;
		return this;
	}
	
	

}
