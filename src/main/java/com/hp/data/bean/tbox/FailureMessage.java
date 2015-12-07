package com.hp.data.bean.tbox;

import com.hp.data.core.DataEntity;

@DataEntity(key = "8995")
public class FailureMessage extends TBoxUpBean{
    private Short isLocation;
    private Long latitude;
    private Long longitude;
    private Integer speed;
    private Integer heading;
    private Byte info1;
    private Byte info2;
    private Byte info3;
    private Byte info4;
    private Byte info5;
    private Byte info6;
    private Byte info7;
    private Byte info8;

    public Short getIsLocation() {
        return isLocation;
    }

    public void setIsLocation(Short isLocation) {
        this.isLocation = isLocation;
    }

    public Long getLatitude() {
        return latitude;
    }

    public void setLatitude(Long latitude) {
        this.latitude = latitude;
    }

    public Long getLongitude() {
        return longitude;
    }

    public void setLongitude(Long longitude) {
        this.longitude = longitude;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getHeading() {
        return heading;
    }

    public void setHeading(Integer heading) {
        this.heading = heading;
    }

    public Byte getInfo1() {
        return info1;
    }

    public void setInfo1(Byte info1) {
        this.info1 = info1;
    }

    public Byte getInfo2() {
        return info2;
    }

    public void setInfo2(Byte info2) {
        this.info2 = info2;
    }

    public Byte getInfo3() {
        return info3;
    }

    public void setInfo3(Byte info3) {
        this.info3 = info3;
    }

    public Byte getInfo4() {
        return info4;
    }

    public void setInfo4(Byte info4) {
        this.info4 = info4;
    }

    public Byte getInfo5() {
        return info5;
    }

    public void setInfo5(Byte info5) {
        this.info5 = info5;
    }

    public Byte getInfo6() {
        return info6;
    }

    public void setInfo6(Byte info6) {
        this.info6 = info6;
    }

    public Byte getInfo7() {
        return info7;
    }

    public void setInfo7(Byte info7) {
        this.info7 = info7;
    }

    public Byte getInfo8() {
        return info8;
    }

    public void setInfo8(Byte info8) {
        this.info8 = info8;
    }


}
