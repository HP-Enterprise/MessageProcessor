package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class PramStatusAck extends TBoxUpBean{
    private Long eventID;
    private byte[] pramCmdDataSize;
    private Integer frequencySaveLocalMedia;
    private Integer frequencyForRealTimeReport;
    private Integer frequencyForWarningReport;
    private String hardwareVersion;
    private String softwareVersion;
    private Short frequencyHeartbeat;
    private Integer timeOutForTerminalSearch;
    private Integer timeOutForServerSearch;
    private byte[] enterpriseBroadcastAddress;
    private Integer enterpriseBroadcastPort;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public byte[] getPramCmdDataSize() {
        return pramCmdDataSize;
    }

    public void setPramCmdDataSize(byte[] pramCmdDataSize) {
        this.pramCmdDataSize = pramCmdDataSize;
    }

    public Integer getFrequencySaveLocalMedia() {
        return frequencySaveLocalMedia;
    }

    public void setFrequencySaveLocalMedia(Integer frequencySaveLocalMedia) {
        this.frequencySaveLocalMedia = frequencySaveLocalMedia;
    }

    public Integer getFrequencyForRealTimeReport() {
        return frequencyForRealTimeReport;
    }

    public void setFrequencyForRealTimeReport(Integer frequencyForRealTimeReport) {
        this.frequencyForRealTimeReport = frequencyForRealTimeReport;
    }

    public Integer getFrequencyForWarningReport() {
        return frequencyForWarningReport;
    }

    public void setFrequencyForWarningReport(Integer frequencyForWarningReport) {
        this.frequencyForWarningReport = frequencyForWarningReport;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public Short getFrequencyHeartbeat() {
        return frequencyHeartbeat;
    }

    public void setFrequencyHeartbeat(Short frequencyHeartbeat) {
        this.frequencyHeartbeat = frequencyHeartbeat;
    }

    public Integer getTimeOutForTerminalSearch() {
        return timeOutForTerminalSearch;
    }

    public void setTimeOutForTerminalSearch(Integer timeOutForTerminalSearch) {
        this.timeOutForTerminalSearch = timeOutForTerminalSearch;
    }

    public Integer getTimeOutForServerSearch() {
        return timeOutForServerSearch;
    }

    public void setTimeOutForServerSearch(Integer timeOutForServerSearch) {
        this.timeOutForServerSearch = timeOutForServerSearch;
    }

    public byte[] getEnterpriseBroadcastAddress() {
        return enterpriseBroadcastAddress;
    }

    public void setEnterpriseBroadcastAddress(byte[] enterpriseBroadcastAddress) {
        this.enterpriseBroadcastAddress = enterpriseBroadcastAddress;
    }

    public Integer getEnterpriseBroadcastPort() {
        return enterpriseBroadcastPort;
    }

    public void setEnterpriseBroadcastPort(Integer enterpriseBroadcastPort) {
        this.enterpriseBroadcastPort = enterpriseBroadcastPort;
    }
}
