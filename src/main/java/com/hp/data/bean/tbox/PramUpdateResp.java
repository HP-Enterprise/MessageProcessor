package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class PramUpdateResp extends TBoxDownBean{
    private Long eventID;
    private String PramVersion;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public String getPramVersion() {
        return PramVersion;
    }

    public void setPramVersion(String pramVersion) {
        PramVersion = pramVersion;
    }
}
