package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class RemoteControlCmd extends TBoxDownBean{
    private Long eventID;
    private Integer remoteControlType;
    private Short acTemperature;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Integer getRemoteControlType() {
        return remoteControlType;
    }

    public void setRemoteControlType(Integer remoteControlType) {
        this.remoteControlType = remoteControlType;
    }

    public Short getAcTemperature() {
        return acTemperature;
    }

    public void setAcTemperature(Short acTemperature) {
        this.acTemperature = acTemperature;
    }
}
