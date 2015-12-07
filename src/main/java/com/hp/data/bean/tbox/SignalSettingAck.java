package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class SignalSettingAck extends TBoxUpBean{
    private Long eventID;
    private Short signalSettingAck;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Short getSignalSettingAck() {
        return signalSettingAck;
    }

    public void setSignalSettingAck(Short signalSettingAck) {
        this.signalSettingAck = signalSettingAck;
    }
}
