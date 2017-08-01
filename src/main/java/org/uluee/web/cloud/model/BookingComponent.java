package org.uluee.web.cloud.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookingComponent {
	private Integer totalPieces = 0;
	private Double totalWeight = 0.0;
	private Double totalVolume = 0.0;
	private Double volumeWeight = 0.0;


	private String insurance = null;
	private String stringCommodities = "";
	private List<CommodityWrapper> commodities;
	private LinkedHashMap<String, Object> param;
	public List<CommodityWrapper> getCommodities() {
		return commodities;
	}

	public BookingComponent setCommodities(List<CommodityWrapper> commodities) {
		this.commodities = commodities;
		return this;
	}

	public Integer getTotalPieces() {
		return totalPieces;
	}

	public BookingComponent setTotalPieces(Integer totalPieces) {
		this.totalPieces = totalPieces;
		return this;
	}

	public Double getTotalWeight() {
		return totalWeight;
	}

	public BookingComponent setTotalWeight(Double totalWeight) {
		this.totalWeight = totalWeight;
		return this;
	}

	public Double getTotalVolume() {
		return totalVolume;
	}

	public BookingComponent setTotalVolume(Double totalVolume) {
		this.totalVolume = totalVolume;
		return this;
	}

	public Double getVolumeWeight() {
		return volumeWeight;
	}

	public BookingComponent setVolumeWeight(Double volumeWeight) {
		this.volumeWeight = volumeWeight;
		return this;
	}

	public String getInsurance() {
		return insurance;
	}

	public BookingComponent setInsurance(String insurance) {
		this.insurance = insurance;
		return this;
	}

	public String getStringCommodities() {
		return stringCommodities;
	}

	public BookingComponent setStringCommodities(String stringCommodities) {
		this.stringCommodities = stringCommodities;
		return this;
	}

	public LinkedHashMap<String, Object> getParam() {
		return param;
	}

	public void setParam(LinkedHashMap<String, Object> param) {
		this.param = param;
	}
	
	
}
