package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class DiagnosticCommanAck extends TBoxUpBean{
    private Long eventID;
    private byte[] diagData;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public byte[] getDiagData() {
        return diagData;
    }

    public void setDiagData(byte[] diagData) {
        this.diagData = diagData;
    }
}
