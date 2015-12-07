package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class PramUpdateResult extends TBoxUpBean{
    private Long eventID;
    private Short pramUpdateResult;
    private Integer packetNumber;
    private Long backPacketNumber;
    private Short responseStatus;
    private Short check;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Short getPramUpdateResult() {
        return pramUpdateResult;
    }

    public void setPramUpdateResult(Short pramUpdateResult) {
        this.pramUpdateResult = pramUpdateResult;
    }

    public Integer getPacketNumber() {
        return packetNumber;
    }

    public void setPacketNumber(Integer packetNumber) {
        this.packetNumber = packetNumber;
    }

    public Long getBackPacketNumber() {
        return backPacketNumber;
    }

    public void setBackPacketNumber(Long backPacketNumber) {
        this.backPacketNumber = backPacketNumber;
    }

    public Short getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Short responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Short getCheck() {
        return check;
    }

    public void setCheck(Short check) {
        this.check = check;
    }
}
