package org.uluee.web.cloud.model;

import java.io.Serializable;

public class Address implements Serializable{
    private String name;
    private String iataCode;
    private String country;
    private String tel;
    private String fax;
    private String email;
    private String street;
    private String city;
    private String postalCode;
    private String postalCodeAndCity;
    private String cityAndPostalCode;
    private String postOfficeBox;
    private String contactPerson;
    private String streetAndNumber;
    private String number;
    private String accountNumber;

    public String getContactPerson() {
        return contactPerson;
    }

    public Address setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public Address setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Address setCity(String city) {
        this.city = city;
        return this;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public String getPostalCodeAndCity() {
        return postalCodeAndCity;
    }

    public Address setPostalCodeAndCity(String postalCodeAndCity) {
        this.postalCodeAndCity = postalCodeAndCity;
        return this;
    }

    public String getCityAndPostalCode() {
        return cityAndPostalCode;
    }

    public Address setCityAndPostalCode(String cityAndPostalCode) {
        this.cityAndPostalCode = cityAndPostalCode;
        return this;
    }

    public String getPostOfficeBox() {
        return postOfficeBox;
    }

    public Address setPostOfficeBox(String postOfficeBox) {
        this.postOfficeBox = postOfficeBox;
        return this;
    }

    public String getStreetAndNumber() {
        return streetAndNumber;
    }

    public Address setStreetAndNumber(String streetAndNumber) {
        this.streetAndNumber = streetAndNumber;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Address setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public Address setName(String name) {
        this.name = name;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public Address setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public Address setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Address setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public Address setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getIataCode() {
        return iataCode;
    }

    public Address setIataCode(String iataCode) {
        this.iataCode = iataCode;
        return this;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Address setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

}
