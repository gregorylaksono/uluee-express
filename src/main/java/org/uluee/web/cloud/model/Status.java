package org.uluee.web.cloud.model;

import java.io.Serializable;

public class Status implements Serializable{
    private String status;
    private String remark;
    private String date;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
}
