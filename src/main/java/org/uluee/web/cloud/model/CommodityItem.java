package org.uluee.web.cloud.model;

import java.io.Serializable;

public class CommodityItem implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5701813201705262454L;
	private String name;
	private String annotation;
	private String pieces;
	private String weight;
	private String length;
	private String width;
	private String height;
	private String price;
	private String insurance;
	private String comId;
	
	public String getComId() {
		return comId;
	}
	public CommodityItem setComId(String comId) {
		this.comId = comId;
		return this;
	}
	public String getName() {
		return name;
	}
	public CommodityItem setName(String name) {
		this.name = name;
		return this;
	}
	public String getPieces() {
		return pieces;
	}
	public CommodityItem setPieces(String pieces) {
		this.pieces = pieces;
		return this;
	}
	public String getWeight() {
		return weight;
	}
	public CommodityItem setWeight(String weight) {
		this.weight = weight;
		return this;
	}
	public String getLength() {
		return length;
	}
	public CommodityItem setLength(String length) {
		this.length = length;
		return this;
	}
	public String getWidth() {
		return width;
	}
	public CommodityItem setWidth(String width) {
		this.width = width;
		return this;
	}
	public String getHeight() {
		return height;
	}
	public CommodityItem setHeight(String height) {
		this.height = height;
		return this;
	}
	public String getPrice() {
		return price;
	}
	public CommodityItem setPrice(String price) {
		this.price = price;
		return this;
	}
	public String getInsurance() {
		return insurance;
	}
	public CommodityItem setInsurance(String insurance) {
		this.insurance = insurance;
		return this;
	}
	public String getAnnotation() {
		return annotation;
	}
	public CommodityItem setAnnotation(String annotation) {
		this.annotation = annotation;
		return this;
	}
	
	
	
}
