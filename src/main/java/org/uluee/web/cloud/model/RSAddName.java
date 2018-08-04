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
	private String countryId;
	private boolean isNotSaved;
	
	public String getStreet() {
		return street;
	}

	public RSAddName setStreet(String street) {
		this.street = street;
		return this;
	}

	public String getCity() {
		return City;
	}

	public RSAddName setCity(String city) {
		City = city;
		return this;
	}

	public String getCountry() {
		return Country;
	}

	public RSAddName setCountry(String country) {
		Country = country;
		return this;
	}

	public String getLatitude() {
		return latitude;
	}

	public RSAddName setLatitude(String latitude) {
		this.latitude = latitude;
		return this;
	}

	public String getLongitude() {
		return longitude;
	}

	public RSAddName setLongitude(String longitude) {
		this.longitude = longitude;
		return this;
	}

	
	private String parentID;

	public String getCompanyID() {
		return companyID;
	}

	public RSAddName setCompanyID(String companyID) {
		this.companyID = companyID;
		return this;
	}

	public String getCompanyName() {
		return companyName;
	}

	public RSAddName setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public String getDefaultAirport() {
		return defaultAirport;
	}

	public RSAddName setDefaultAirport(String defaultAirport) {
		this.defaultAirport = defaultAirport;
		return this;
	}

	public String getType() {
		return type;
	}

	public RSAddName setType(String type) {
		this.type = type;
		return this;
	}

	public int compareTo(RSAddName o) {
		return o.companyID.compareToIgnoreCase(companyID);
	}

	public String getParentID()
	{
		return parentID;
	}

	public RSAddName setParentID(String parentID)
	{
		this.parentID = parentID;
		return this;
	}

	public Long getCreatorAddId()
	{
		return creatorAddId;
	}

	public RSAddName setCreatorAddId(Long creatorAddId)
	{
		this.creatorAddId = creatorAddId;
		return this;
	}

	public boolean isNotSaved() {
		return isNotSaved;
	}

	public void setNotSaved(boolean isNotSaved) {
		this.isNotSaved = isNotSaved;
	}

	public String getCountryId() {
		return countryId;
	}

	public RSAddName setCountryId(String countryId) {
		this.countryId = countryId;
		return this;
	}
	
	
	
}
