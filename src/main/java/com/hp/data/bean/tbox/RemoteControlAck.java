package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class RemoteControlAck extends TBoxUpBean{
    private Long eventID;
    private Short remoteControlAck;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Short getRemoteControlAck() {
        return remoteControlAck;
    }

    public void setRemoteControlAck(Short remoteControlAck) {
        this.remoteControlAck = remoteControlAck;
    }
}
