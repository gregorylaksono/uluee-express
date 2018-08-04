package org.uluee.web.cloud.model;

public class Preferences {

	private String companyName ;
	private String  airportCode;
	private String  airportName;
	private String  contactPerson;
	private String  country;
	private String  street;
	private String  telephone;
	private String email ;
	private String currency ;
	public String getCompanyName() {
		return companyName;
	}
	public Preferences setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public Preferences setAirportCode(String airportCode) {
		this.airportCode = airportCode;
		return this;
	}
	public String getAirportName() {
		return airportName;
	}
	public Preferences setAirportName(String airportName) {
		this.airportName = airportName;
		return this;
	}
	public String getContactPerson() {
		return contactPerson;
	}
	public Preferences setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
		return this;
	}
	public String getCountry() {
		return country;
	}
	public Preferences setCountry(String country) {
		this.country = country;
		return this;
	}
	public String getStreet() {
		return street;
	}
	public Preferences setStreet(String street) {
		this.street = street;
		return this;
	}
	public String getTelephone() {
		return telephone;
	}
	public Preferences setTelephone(String telephone) {
		this.telephone = telephone;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public Preferences setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getCurrency() {
		return currency;
	}
	public Preferences setCurrency(String currency) {
		this.currency = currency;
		return this;
	}
	
}
