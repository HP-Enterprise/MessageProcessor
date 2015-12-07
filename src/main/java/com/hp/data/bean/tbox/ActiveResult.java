package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class ActiveResult extends TBoxUpBean{
    private Long eventID;
    private Short rtmLifeCycle;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Short getRtmLifeCycle() {
        return rtmLifeCycle;
    }

    public void setRtmLifeCycle(Short rtmLifeCycle) {
        this.rtmLifeCycle = rtmLifeCycle;
    }
}
