package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class DiaResp extends TBoxDownBean{
    private Long eventID;
    private Short diaReportResp;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Short getDiaReportResp() {
        return diaReportResp;
    }

    public void setDiaReportResp(Short diaReportResp) {
        this.diaReportResp = diaReportResp;
    }
}
