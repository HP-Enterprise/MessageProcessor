package com.hp.data.bean.landu;

import com.hp.data.util.DataTool;
import io.netty.buffer.ByteBuf;

import static io.netty.buffer.Unpooled.buffer;

/**
 * 怠速车况数据 0x1608
 * Created by zxZhang on 15/12/14.
 */
public class IDLingData extends LanDuMsgHead{
    public static final int BUFFER_SIZE = 1024;

    private int orderWord;//命令字，short 0x1606=5638
    private String obdID;//OBD串号（设备号）
    private long tripID;
    private String VID;
    private String VIN;
    private String gainDataTime;//取得检测数据时间戳 YYYY-MM-DD hh:mm:ss

    private byte faultCodeNum;
    private String[] faultCode;//故障码
    private String[] faultCodeAttribute;//故障码属性
    private String[] faultMsg;  //故障描述

    private int dataStreamNum;//数据流个数
    private int[] dataStreamID;
    private String[] dataStreamMsg;

    public DataTool dataTool = new DataTool();

    /**
     * 解码
     */
    public IDLingData decoded(byte[] data){
        IDLingData idLingData = new IDLingData();
        ByteBuf bb = buffer(BUFFER_SIZE);
        bb.writeBytes(data);
        idLingData.setPackageMark(bb.readUnsignedShort());
        idLingData.setPackageLength(bb.readUnsignedShort());
        idLingData.setCheckPackageLength(bb.readUnsignedShort());
        idLingData.setPackageID(bb.readByte());
        idLingData.setVersion(bb.readByte());

        idLingData.setOrderWord(bb.readUnsignedShort());
        idLingData.setObdID(dataTool.readStringZero(bb));
        idLingData.setTripID(bb.readInt());
        idLingData.setVID(dataTool.readStringZero(bb));
        idLingData.setVIN(dataTool.readStringZero(bb));
        idLingData.setGainDataTime(dataTool.readStringZero(bb));

        idLingData.setFaultCodeNum(bb.readByte());
        String[] faults = new String[idLingData.getFaultCodeNum()];
        String[] attributes = new String[idLingData.getFaultCodeNum()];
        String[] msgs = new String[idLingData.getFaultCodeNum()];
        for(int i=0;i<idLingData.getFaultCodeNum();i++){
            faults[i] = dataTool.readStringZero(bb);
            attributes[i] = dataTool.readStringZero(bb);
            msgs[i] = dataTool.readStringZero(bb);
        }
        idLingData.setFaultCode(faults);
        idLingData.setFaultCodeAttribute(attributes);
        idLingData.setFaultMsg(msgs);

        idLingData.setDataStreamNum(bb.readUnsignedShort());
        int[] num = new int[idLingData.getDataStreamNum()];
        String[] vars = new String[idLingData.getDataStreamNum()];
        for(int i= 0;i<idLingData.getDataStreamNum();i++){
            num[i] = bb.readUnsignedShort();
            vars[i] = dataTool.readStringZero(bb);
        }
        idLingData.setDataStreamID(num);
        idLingData.setDataStreamMsg(vars);

        idLingData.setCheckSum(bb.readUnsignedShort());
        return idLingData;
    }

    /**
     * 编码
     */
    public byte[] encoded(){
        ByteBuf bb = buffer(BUFFER_SIZE);
        int countByte = 0;//消息长度
        int addByte = 0;//增加的字节
        //消息头
        bb.writeShort(0xAA55);//Short((short) 0xAA55);
        bb.markWriterIndex();
        bb.writeShort(0x3030);//填充00，预留packageLength空间
        bb.writeShort(0x3030);//填充00，预留checkPackageLength空间
        bb.writeByte(this.getPackageID());
        bb.writeByte(0x05);
        countByte = 2+2+2+1+1;
        //消息内容
        bb.writeShort(0x1608);//命令字 0x1608 = 5640
        dataTool.writeStringZero(bb, this.getObdID(), true);//末尾补了一个字节0
        bb.writeInt((int) this.getTripID());
        dataTool.writeStringZero(bb, this.getVID(), true);
        dataTool.writeStringZero(bb, this.getVIN(), true);
        dataTool.writeStringZero(bb, this.getGainDataTime(), true);
        bb.writeByte(this.getFaultCodeNum());
        countByte += 2+this.getObdID().length()+4+this.getVID().length()+this.getVIN().length()+this.getGainDataTime().length()+1;
        addByte += 1+1+1+1;
        for(int i=0;i<this.getFaultCodeNum();i++){
            dataTool.writeStringZero(bb, this.getFaultCode()[i], true);
            dataTool.writeStringZero(bb, this.getFaultCodeAttribute()[i], true);
            dataTool.writeStringZero(bb, this.getFaultMsg()[i], true);
            countByte += this.getFaultCode()[i].length()+this.getFaultCodeAttribute()[i].length()+this.getFaultMsg()[i].length();
            addByte +=1+1+1;
        }
        bb.writeShort(this.getDataStreamNum());
        countByte += 2;
        for(int i=0;i<this.getDataStreamNum();i++){
            bb.writeShort(this.getDataStreamID()[i]);
            dataTool.writeStringZero(bb, this.getDataStreamMsg()[i], true);
            countByte += 2+this.getDataStreamMsg()[i].length();
            addByte += 1;
        }
        countByte += 2;//校验和2个字节
        //回写计算后的数据包长度
        int index=bb.writerIndex();
        bb.resetWriterIndex();
        bb.writeShort(countByte +addByte -2);
        bb.writeShort(~(countByte + addByte - 2));
        bb.writerIndex(index);
        //计算校验和
        int sum=0;
        byte[] bytes = dataTool.getBytesFromByteBuf(bb);
        for(int i=2;i<bytes.length;i++){
            sum += bytes[i] & 0xFF;
        }
        bb.writeShort(sum);//checkSum

        return dataTool.getBytesFromByteBuf(bb);
    }

    public void print(){
        System.out.println("LanDuMsg:__________________________" );
        System.out.println("packageMark_"+ this.getPackageMark()
                +" packageLength_"+ this.getPackageLength()
                +" checkPackageLength_"+ this.getCheckPackageLength()
                +" packageID_"+ this.getPackageID()
                +" version_"+ this.getVersion()
                +" checkSum_"+this.getCheckSum());
        System.out.println("LanDuMsgContent:__________________________" );
        System.out.println("orderWord_"+ this.getOrderWord()
                +" obdID_"+ this.getObdID()
                +" tripID_"+ this.getTripID()
                +" VID_"+ this.getVID()
                +" VIN_"+ this.getVIN()
                +" GainDataTime_"+ this.getGainDataTime()
                +" FaultCodeNum_"+ this.getFaultCodeNum());
        for(int i=0;i<this.getFaultCodeNum();i++){
            System.out.println(" FaultCode_"+i+"_"+ this.getFaultCode()[i]
                    +" FaultCodeAttribute_"+i+"_"+ this.getFaultCodeAttribute()[i]
                    +" FaultMsg_"+i+"_"+ this.getFaultMsg()[i]);
        }
        System.out.println("DataStreamNum_"+ this.getDataStreamNum());
        for(int i=0;i<this.getDataStreamNum();i++){
            System.out.println(" DataStreamID_"+i+"_"+ this.getDataStreamID()[i]
                    +" DataStreamMsg_"+i+"_"+ this.getDataStreamMsg()[i]);
        }
    }

    public int getOrderWord() {
        return orderWord;
    }

    public void setOrderWord(int orderWord) {
        this.orderWord = orderWord;
    }

    public String getObdID() {
        return obdID;
    }

    public void setObdID(String obdID) {
        this.obdID = obdID;
    }

    public long getTripID() {
        return tripID;
    }

    public void setTripID(long tripID) {
        this.tripID = tripID;
    }

    public String getVID() {
        return VID;
    }

    public void setVID(String VID) {
        this.VID = VID;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getGainDataTime() {
        return gainDataTime;
    }

    public void setGainDataTime(String gainDataTime) {
        this.gainDataTime = gainDataTime;
    }

    public byte getFaultCodeNum() {
        return faultCodeNum;
    }

    public void setFaultCodeNum(byte faultCodeNum) {
        this.faultCodeNum = faultCodeNum;
    }

    public String[] getFaultCode() {
        return faultCode;
    }

    public void setFaultCode(String[] faultCode) {
        this.faultCode = faultCode;
    }

    public String[] getFaultCodeAttribute() {
        return faultCodeAttribute;
    }

    public void setFaultCodeAttribute(String[] faultCodeAttribute) {
        this.faultCodeAttribute = faultCodeAttribute;
    }

    public String[] getFaultMsg() {
        return faultMsg;
    }

    public void setFaultMsg(String[] faultMsg) {
        this.faultMsg = faultMsg;
    }

    public int getDataStreamNum() {
        return dataStreamNum;
    }

    public void setDataStreamNum(int dataStreamNum) {
        this.dataStreamNum = dataStreamNum;
    }

    public int[] getDataStreamID() {
        return dataStreamID;
    }

    public void setDataStreamID(int[] dataStreamID) {
        this.dataStreamID = dataStreamID;
    }

    public String[] getDataStreamMsg() {
        return dataStreamMsg;
    }

    public void setDataStreamMsg(String[] dataStreamMsg) {
        this.dataStreamMsg = dataStreamMsg;
    }
}
