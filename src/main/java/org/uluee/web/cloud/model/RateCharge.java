package org.uluee.web.cloud.model;

import java.io.Serializable;

public class RateCharge implements Serializable{
    private String chargeableWeight;
    private String commodity;
    private String rateClass;
    private String ratePerKg;
    private String totalRate;
    private String securityCharges;
    private String fuelCharges;
    private String otherCharges;
    private String total;

    public String getChargeableWeight() {
        return chargeableWeight;
    }

    public void setChargeableWeight(String chargeableWeight) {
        this.chargeableWeight = chargeableWeight;
    }

    public String getCommodity() {
        return commodity;
    }

    public void setCommodity(String commodity) {
        this.commodity = commodity;
    }

    public String getRateClass() {
        return rateClass;
    }

    public void setRateClass(String rateClass) {
        this.rateClass = rateClass;
    }

    public String getRatePerKg() {
        return ratePerKg;
    }

    public void setRatePerKg(String ratePerKg) {
        this.ratePerKg = ratePerKg;
    }

    public String getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(String totalRate) {
        this.totalRate = totalRate;
    }

    public String getSecurityCharges() {
        return securityCharges;
    }

    public void setSecurityCharges(String securityCharges) {
        this.securityCharges = securityCharges;
    }

    public String getFuelCharges() {
        return fuelCharges;
    }

    public void setFuelCharges(String fuelCharges) {
        this.fuelCharges = fuelCharges;
    }

    public String getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(String otherCharges) {
        this.otherCharges = otherCharges;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
