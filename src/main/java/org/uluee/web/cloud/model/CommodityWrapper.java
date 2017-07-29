package org.uluee.web.cloud.model;

import java.io.Serializable;

public class CommodityWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2766091213494811882L;
	private String Commodity;
	private Double pieces;
	private Double weight;
	private Double length;
	private Double width;
	private Double height;
	private Double volume;
	private String com_id;
	private Long ann_id;
	private String scc;
	private Double insurance;
	
	public String getCommodity() {
		return Commodity;
	}
	public CommodityWrapper setCommodity(String commodity) {
		Commodity = commodity;
		return this;
	}
	public Double getPieces() {
		return pieces;
	}
	public CommodityWrapper setPieces(Double pieces) {
		this.pieces = pieces;
		return this;
	}
	public Double getWeight() {
		return weight;
	}
	public CommodityWrapper setWeight(Double weight) {
		this.weight = weight;
		return this;
	}
	public Double getVolume() {
		return volume;
	}
	public CommodityWrapper setVolume(Double volume) {
		this.volume = volume;
		return this;
	}
	public String getCom_id() {
		return com_id;
	}
	public CommodityWrapper setCom_id(String com_id) {
		this.com_id = com_id;
		return this;
	}
	public Long getAnn_id() {
		return ann_id;
	}
	public CommodityWrapper setAnn_id(Long ann_id) {
		this.ann_id = ann_id;
		return this;
	}
	public String getScc() {
		return scc;
	}
	public CommodityWrapper setScc(String scc) {
		this.scc = scc;
		return this;
	}
	public Double getLength() {
		return length;
	}
	public CommodityWrapper setLength(Double length) {
		this.length = length;
		return this;
	}
	public Double getWidth() {
		return width;
	}
	public CommodityWrapper setWidth(Double width) {
		this.width = width;
		return this;
	}
	public Double getHeight() {
		return height;
	}
	public CommodityWrapper setHeight(Double height) {
		this.height = height;
		return this;
	}
	public Double getInsurance() {
		return insurance;
	}
	public CommodityWrapper setInsurance(Double insurance) {
		this.insurance = insurance;
		return this;
	}
	
}
