package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class SignalSettingCmd extends TBoxDownBean{
    private Long eventID;
    private Long realTimeDataValid;
    private Integer frequencyForRealTimeReport;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Long getRealTimeDataValid() {
        return realTimeDataValid;
    }

    public void setRealTimeDataValid(Long realTimeDataValid) {
        this.realTimeDataValid = realTimeDataValid;
    }

    public Integer getFrequencyForRealTimeReport() {
        return frequencyForRealTimeReport;
    }

    public void setFrequencyForRealTimeReport(Integer frequencyForRealTimeReport) {
        this.frequencyForRealTimeReport = frequencyForRealTimeReport;
    }
}
