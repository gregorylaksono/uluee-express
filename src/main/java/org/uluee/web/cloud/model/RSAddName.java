/**
 * 
 */
package org.uluee.web.cloud.model;

import java.io.Serializable;

/**
 * @author bukan Nicko
 *
 */
public class RSAddName implements Serializable, Comparable<RSAddName>{

	private static final long serialVersionUID = -190862951428887826L;
	private String companyID;
	private String companyName;
	private String defaultAirport;
	private String type;
	private Long creatorAddId;
	private String City;
	private String Country;
	private String latitude;
	private String longitude;
	private String street;
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
	private String parentID;

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDefaultAirport() {
		return defaultAirport;
	}

	public void setDefaultAirport(String defaultAirport) {
		this.defaultAirport = defaultAirport;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int compareTo(RSAddName o) {
		return o.companyID.compareToIgnoreCase(companyID);
	}

	public String getParentID()
	{
		return parentID;
	}

	public void setParentID(String parentID)
	{
		this.parentID = parentID;
	}

	public Long getCreatorAddId()
	{
		return creatorAddId;
	}

	public void setCreatorAddId(Long creatorAddId)
	{
		this.creatorAddId = creatorAddId;
	}
	
	
	
}
