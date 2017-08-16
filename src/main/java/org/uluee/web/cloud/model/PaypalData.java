package org.uluee.web.cloud.model;

import java.io.Serializable;

public class PaypalData implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -4906781091051589098L;
private String paymentId;
private String token;
private String redirectUrl;
public String getPaymentId() {
	return paymentId;
}
public PaypalData setPaymentId(String paymentId) {
	this.paymentId = paymentId;
	return this;
}
public String getToken() {
	return token;
}
public PaypalData setToken(String token) {
	this.token = token;
	return this;
}
public String getRedirectUrl() {
	return redirectUrl;
}
public PaypalData setRedirectUrl(String redirectUrl) {
	this.redirectUrl = redirectUrl;
	return this;
}

}
