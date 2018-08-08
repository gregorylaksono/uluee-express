/**
 * 
 */
package org.uluee.web.cloud.model;

import java.io.Serializable;

/**
 * @author ACTERNITY
 *
 */
public class PartnershipWrapper implements Serializable{
	private String accountNo;
	private String amountDeposit;
	private String paymentLabel;
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAmountDeposit() {
		return amountDeposit;
	}
	public void setAmountDeposit(String amountDeposit) {
		this.amountDeposit = amountDeposit;
	}	
	public String getPaymentLabel() {
		return paymentLabel;
	}
	public void setPaymentLabel(String paymentLabel) {
		this.paymentLabel = paymentLabel;
	}	
}
