package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class RemoteWakeUpReq extends TBoxUpBean {
    private Long eventID;
    private String serialNumber;
    private String vin;
    private String swVersion;
    private String pramVersion;
    private String dbcVersion;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getSwVersion() {
        return swVersion;
    }

    public void setSwVersion(String swVersion) {
        this.swVersion = swVersion;
    }

    public String getPramVersion() {
        return pramVersion;
    }

    public void setPramVersion(String pramVersion) {
        this.pramVersion = pramVersion;
    }

    public String getDbcVersion() {
        return dbcVersion;
    }

    public void setDbcVersion(String dbcVersion) {
        this.dbcVersion = dbcVersion;
    }
}