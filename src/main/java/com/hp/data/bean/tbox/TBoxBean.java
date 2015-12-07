package com.hp.data.bean.tbox;


public abstract class TBoxBean {
    private Integer head;
    private Integer length;
    private Short testFlag;
    private Byte checkSum;
    public TBoxBean(){

    }
    public Integer getHead() {
        return head;
    }

    public void setHead(Integer head) {
        this.head = head;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Short getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Short testFlag) {
        this.testFlag = testFlag;
    }

    public Byte getCheckSum() {
        return checkSum;
    }

    public void setCheckSum(Byte checkSum) {
        this.checkSum = checkSum;
    }
}
