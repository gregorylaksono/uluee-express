package org.uluee.web.cloud.model;

import java.io.Serializable;

public class CommodityWrapper implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2766091213494811882L;
	private String Commodity;
	private Integer pieces;
	private Double weight;
	private Integer length;
	private Integer width;
	private Integer height;
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
	public Integer getPieces() {
		return pieces;
	}
	public CommodityWrapper setPieces(Integer pieces) {
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
	public Integer getLength() {
		return length;
	}
	public CommodityWrapper setLength(Integer length) {
		this.length = length;
		return this;
	}
	public Integer getWidth() {
		return width;
	}
	public CommodityWrapper setWidth(Integer width) {
		this.width = width;
		return this;
	}
	public Integer getHeight() {
		return height;
	}
	public CommodityWrapper setHeight(Integer height) {
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
