package org.uluee.web.cloud.model;

import java.io.Serializable;

public class Shipment implements Serializable {
    private Address shipper;
    private Address consignee;
    private Address agent;
    private Address deliveredTo;
    private Address alsoNotify;

    public Shipment() {
    }

    public Shipment(Address shipper, Address consignee, Address agent, Address deliveredTo, Address alsoNotify) {
        this.shipper = shipper;
        this.consignee = consignee;
        this.agent = agent;
        this.deliveredTo = deliveredTo;
        this.alsoNotify = alsoNotify;
    }

    public Address getShipper() {
        return shipper;
    }

    public void setShipper(Address shipper) {
        this.shipper = shipper;
    }

    public Address getConsignee() {
        return consignee;
    }

    public void setConsignee(Address consignee) {
        this.consignee = consignee;
    }

    public Address getAgent() {
        return agent;
    }

    public void setAgent(Address agent) {
        this.agent = agent;
    }

    public Address getDeliveredTo() {
        return deliveredTo;
    }

    public void setDeliveredTo(Address deliveredTo) {
        this.deliveredTo = deliveredTo;
    }

    public Address getAlsoNotify() {
        return alsoNotify;
    }

    public void setAlsoNotify(Address alsoNotify) {
        this.alsoNotify = alsoNotify;
    }

}
