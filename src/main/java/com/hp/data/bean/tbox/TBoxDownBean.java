package com.hp.data.bean.tbox;


public abstract class TBoxDownBean extends TBoxBean{
    private Long sendingTime;
    private Short applicationID;
    private Short messageID;

    public Long getSendingTime() {
        return sendingTime;
    }

    public void setSendingTime(Long sendingTime) {
        this.sendingTime = sendingTime;
    }

    public Short getApplicationID() {
        return applicationID;
    }

    public void setApplicationID(Short applicationID) {
        this.applicationID = applicationID;
    }

    public Short getMessageID() {
        return messageID;
    }

    public void setMessageID(Short messageID) {
        this.messageID = messageID;
    }


}
