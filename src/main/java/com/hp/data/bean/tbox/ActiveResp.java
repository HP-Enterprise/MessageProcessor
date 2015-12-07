package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class ActiveResp extends TBoxDownBean{
    private Long eventID;
    private Short tBoxStatus;
    private String vin;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Short gettBoxStatus() {
        return tBoxStatus;
    }

    public void settBoxStatus(Short tBoxStatus) {
        this.tBoxStatus = tBoxStatus;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
}
