package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class PramSetupAck extends TBoxUpBean{
    private Long eventID;
    private Short pramSetAck;
    private byte[] pramSetID;
    private byte[] pramValue;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Short getPramSetAck() {
        return pramSetAck;
    }

    public void setPramSetAck(Short pramSetAck) {
        this.pramSetAck = pramSetAck;
    }

    public byte[] getPramSetID() {
        return pramSetID;
    }

    public void setPramSetID(byte[] pramSetID) {
        this.pramSetID = pramSetID;
    }

    public byte[] getPramValue() {
        return pramValue;
    }

    public void setPramValue(byte[] pramValue) {
        this.pramValue = pramValue;
    }
}
