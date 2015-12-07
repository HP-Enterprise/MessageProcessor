package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

/**
 * Created by luj on 2015/10/22.
 */
@DataEntity(key = "8995")
public class RemoteControlPreconditionResp extends TBoxUpBean{
    private Long eventID;
    private Long time;
    private Short ambientAirTemperature;
    private Integer fuelLevel;
    private Short tempIntern;
    private Integer averageConsumptionTripA;
    private Integer averageConsumptionTripB;
    private Short averageSpeedTripA;
    private Short averageSpeedTripB;
    private Byte sesam_clamp_stat;
    private Byte stat_remote_start;
    private Byte gearLevelPositon;
    private Integer vehicleSpeed;
    private Byte stateOfChargeACM_Crash_Status;
    private byte[] bcm_Stat_Door_Flap;
    private Byte bcm_Stat_Central_Lock;
    private Byte stat_lin_2;
    private Byte epb_status;
    private Byte stat_lin_1;
    private byte[] scm_button_status;
    private Byte scm_cruise_control;
    private byte[] scm_wiper;
    private Short preconditionRespTime;

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Short getAmbientAirTemperature() {
        return ambientAirTemperature;
    }

    public void setAmbientAirTemperature(Short ambientAirTemperature) {
        this.ambientAirTemperature = ambientAirTemperature;
    }

    public Integer getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(Integer fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public Short getTempIntern() {
        return tempIntern;
    }

    public void setTempIntern(Short tempIntern) {
        this.tempIntern = tempIntern;
    }

    public Integer getAverageConsumptionTripA() {
        return averageConsumptionTripA;
    }

    public void setAverageConsumptionTripA(Integer averageConsumptionTripA) {
        this.averageConsumptionTripA = averageConsumptionTripA;
    }

    public Integer getAverageConsumptionTripB() {
        return averageConsumptionTripB;
    }

    public void setAverageConsumptionTripB(Integer averageConsumptionTripB) {
        this.averageConsumptionTripB = averageConsumptionTripB;
    }

    public Short getAverageSpeedTripA() {
        return averageSpeedTripA;
    }

    public void setAverageSpeedTripA(Short averageSpeedTripA) {
        this.averageSpeedTripA = averageSpeedTripA;
    }

    public Short getAverageSpeedTripB() {
        return averageSpeedTripB;
    }

    public void setAverageSpeedTripB(Short averageSpeedTripB) {
        this.averageSpeedTripB = averageSpeedTripB;
    }

    public Byte getSesam_clamp_stat() {
        return sesam_clamp_stat;
    }

    public void setSesam_clamp_stat(Byte sesam_clamp_stat) {
        this.sesam_clamp_stat = sesam_clamp_stat;
    }

    public Byte getStat_remote_start() {
        return stat_remote_start;
    }

    public void setStat_remote_start(Byte stat_remote_start) {
        this.stat_remote_start = stat_remote_start;
    }

    public Byte getGearLevelPositon() {
        return gearLevelPositon;
    }

    public void setGearLevelPositon(Byte gearLevelPositon) {
        this.gearLevelPositon = gearLevelPositon;
    }

    public Integer getVehicleSpeed() {
        return vehicleSpeed;
    }

    public void setVehicleSpeed(Integer vehicleSpeed) {
        this.vehicleSpeed = vehicleSpeed;
    }

    public Byte getStateOfChargeACM_Crash_Status() {
        return stateOfChargeACM_Crash_Status;
    }

    public void setStateOfChargeACM_Crash_Status(Byte stateOfChargeACM_Crash_Status) {
        this.stateOfChargeACM_Crash_Status = stateOfChargeACM_Crash_Status;
    }

    public byte[] getBcm_Stat_Door_Flap() {
        return bcm_Stat_Door_Flap;
    }

    public void setBcm_Stat_Door_Flap(byte[] bcm_Stat_Door_Flap) {
        this.bcm_Stat_Door_Flap = bcm_Stat_Door_Flap;
    }

    public Byte getBcm_Stat_Central_Lock() {
        return bcm_Stat_Central_Lock;
    }

    public void setBcm_Stat_Central_Lock(Byte bcm_Stat_Central_Lock) {
        this.bcm_Stat_Central_Lock = bcm_Stat_Central_Lock;
    }

    public Byte getStat_lin_2() {
        return stat_lin_2;
    }

    public void setStat_lin_2(Byte stat_lin_2) {
        this.stat_lin_2 = stat_lin_2;
    }

    public Byte getEpb_status() {
        return epb_status;
    }

    public void setEpb_status(Byte epb_status) {
        this.epb_status = epb_status;
    }

    public Byte getStat_lin_1() {
        return stat_lin_1;
    }

    public void setStat_lin_1(Byte stat_lin_1) {
        this.stat_lin_1 = stat_lin_1;
    }

    public byte[] getScm_button_status() {
        return scm_button_status;
    }

    public void setScm_button_status(byte[] scm_button_status) {
        this.scm_button_status = scm_button_status;
    }

    public Byte getScm_cruise_control() {
        return scm_cruise_control;
    }

    public void setScm_cruise_control(Byte scm_cruise_control) {
        this.scm_cruise_control = scm_cruise_control;
    }

    public byte[] getScm_wiper() {
        return scm_wiper;
    }

    public void setScm_wiper(byte[] scm_wiper) {
        this.scm_wiper = scm_wiper;
    }

    public Short getPreconditionRespTime() {
        return preconditionRespTime;
    }

    public void setPreconditionRespTime(Short preconditionRespTime) {
        this.preconditionRespTime = preconditionRespTime;
    }
}
