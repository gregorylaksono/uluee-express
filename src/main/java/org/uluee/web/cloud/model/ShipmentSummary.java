package org.uluee.web.cloud.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class ShipmentSummary implements Serializable{

    private String awb;
    private Collection statusInformation;
    private Collection bookingInformation;
    private Collection shipmentInformation;
    private Shipment address;
    private RateCharge rateCharge;
    private Boolean disabledSendFWB;
    private boolean disabledRateNCharge;
    private Boolean disabledCucAndMrn;
    private String f_id;
    private Map detailInfo;

    public Collection getStatusInformation() {
        return statusInformation;
    }

    public void setStatusInformation(Collection statusInformation) {
        this.statusInformation = statusInformation;
    }

    public void setBookingInformation(Collection bookingInformation) {
        this.bookingInformation = bookingInformation;
    }

    public Collection getBookingInformation() {
        return bookingInformation;
    }

    public void setShipmentInformation(Collection shipmentInformation) {
        this.shipmentInformation = shipmentInformation;
    }

    public Collection getShipmentInformation() {
        return shipmentInformation;
    }

    public Shipment getAddress() {
        return address;
    }

    public void setAddress(Shipment address) {
        this.address = address;
    }

    public void setRateCharge(RateCharge rateCharge) {
        this.rateCharge = rateCharge;
    }

    public RateCharge getRateCharge() {
        return rateCharge;
    }

    public Boolean getDisabledSendFWB() {
        return disabledSendFWB;
    }

    public void setDisabledSendFWB(Boolean disabledSendFWB) {
        this.disabledSendFWB = disabledSendFWB;
    }

    public void setDisabledRateNCharge(boolean disabledRateNCharge) {
        this.disabledRateNCharge = disabledRateNCharge;
    }

    public boolean isDisabledRateNCharge() {
        return disabledRateNCharge;
    }

    public Boolean getDisabledCucAndMrn() {
        return disabledCucAndMrn;
    }

    public void setDisabledCucAndMrn(Boolean disabledCucAndMrn) {
        this.disabledCucAndMrn = disabledCucAndMrn;
    }

    public void setAwb(String awb) {
        this.awb = awb;
    }

    public String getAwb() {
        return awb;
    }

	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	public Map getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(Map detailInfo) {
		this.detailInfo = detailInfo;
	}
	
    
}
