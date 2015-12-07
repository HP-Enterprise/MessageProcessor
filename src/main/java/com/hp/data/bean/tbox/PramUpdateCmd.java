package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class PramUpdateCmd extends TBoxDownBean{
    private Long eventID;
    private String pramUpdateReady;

    public String getPramUpdateReady() {
        return pramUpdateReady;
    }

    public void setPramUpdateReady(String pramUpdateReady) {
        this.pramUpdateReady = pramUpdateReady;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }
}
