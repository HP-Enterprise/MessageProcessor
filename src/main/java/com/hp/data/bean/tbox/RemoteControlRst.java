package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

/**
 * Created by luj on 2015/10/22.
 */
@DataEntity(key = "8995")
public class RemoteControlRst extends TBoxUpBean{
    private Long eventID;
    private Short remoteControlAck;
    private Short remoteControlTime;

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

    public Short getRemoteControlTime() {
        return remoteControlTime;
    }

    public void setRemoteControlTime(Short remoteControlTime) {
        this.remoteControlTime = remoteControlTime;
    }
}
