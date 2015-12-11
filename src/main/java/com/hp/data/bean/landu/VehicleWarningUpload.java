package com.hp.data.bean.landu;

import com.hp.data.util.DataTool;
import io.netty.buffer.ByteBuf;

import static io.netty.buffer.Unpooled.buffer;

/**
 * 车辆报警上传
 * Created by yuh on 2015/12/7.
 */
public class VehicleWarningUpload extends LanDuMsgHead{
    public static final int BUFFER_SIZE = 1024;

    private int orderWord;//命令字，short 0x1602=5634
    private String obdID;//OBD串号(设备号)
    private long tripID;// DWORD
    private String VID;
    private String VIN;
    private String gainDataTime;//YYYY-MM-DD hh:mm:ss
    private byte warningType;//报警类型 0x01：新故障码报警 0x02：碰撞报警 0x03：防盗报警 0x04：水温报警 0x05：充电电压报警（小于13.1v）0xF0: 拔下OBD报警 其他值: 无效

    //定位信息
    private String speed;//车数
    private String currentDriveDistance;//当前行程行驶距离
    private String longitude;//经度 字符串无结束符
    private String latitude;//纬度 字符串无结束符
    private String direct;//方向 字符串无结束符
    private String positionTime;//定位时间
    private String positionMethod;//定位方式

    //报警数据
    //新故障码报警
    private byte faultNum;//故障个数
    private String[] faultCode;//故障码
    private String[] faultCodeAttribute;//故障码属性
    private String[] faultMsg;  //故障描述

    //水温报警
    private String waterTempNum;   //实际水温数值

    //充电电压报警
    private String voltageNum;  //充电电压值

    public DataTool dataTool = new DataTool();

    /**
     * 解码
     */
    public VehicleWarningUpload decoded(byte[] data){
        VehicleWarningUpload vehicleWarningUpload = new VehicleWarningUpload();
        ByteBuf bb = buffer(BUFFER_SIZE);
        bb.writeBytes(data);
        try{
            vehicleWarningUpload.setPackageMark(bb.readUnsignedShort());
            vehicleWarningUpload.setPackageLength(bb.readUnsignedShort());
            vehicleWarningUpload.setCheckPackageLength(bb.readUnsignedShort());
            vehicleWarningUpload.setPackageID(bb.readByte());
            vehicleWarningUpload.setVersion(bb.readByte());

            vehicleWarningUpload.setOrderWord(bb.readUnsignedShort());
            vehicleWarningUpload.setObdID(dataTool.readStringZero(bb));
            vehicleWarningUpload.setTripID(bb.readInt());
            vehicleWarningUpload.setVID(dataTool.readStringZero(bb));
            vehicleWarningUpload.setVIN(dataTool.readStringZero(bb));
            vehicleWarningUpload.setGainDataTime(dataTool.readStringZero(bb));
            vehicleWarningUpload.setWarningType(bb.readByte());

            vehicleWarningUpload.setSpeed(dataTool.readStringZero(bb));
            vehicleWarningUpload.setCurrentDriveDistance(dataTool.readStringZero(bb));
            vehicleWarningUpload.setLongitude(dataTool.readStringZero(bb));
            vehicleWarningUpload.setLatitude(dataTool.readStringZero(bb));
            vehicleWarningUpload.setDirect(dataTool.readStringZero(bb));
            vehicleWarningUpload.setPositionTime(dataTool.readStringZero(bb));
            vehicleWarningUpload.setPositionMethod(dataTool.readStringZero(bb));
            switch(vehicleWarningUpload.getWarningType()){
                case 0x01 ://新故障报警
                    vehicleWarningUpload.setFaultNum(bb.readByte());
                    String[] faultCodes = vehicleWarningUpload.getFaultCode();
                    String[] faultAttributes = vehicleWarningUpload.getFaultCodeAttribute();
                    String[] faultMsgs = vehicleWarningUpload.getFaultMsg();
                    for(int i=0;i<vehicleWarningUpload.getFaultNum();i++){
                        faultCodes[i] = dataTool.readStringZero(bb);
                        faultAttributes[i] = dataTool.readStringZero(bb);
                        faultMsgs[i] = dataTool.readStringZero(bb);
                    }
                    vehicleWarningUpload.setFaultCode(faultCodes);
                    vehicleWarningUpload.setFaultCodeAttribute(faultAttributes);
                    vehicleWarningUpload.setFaultMsg(faultMsgs);
                    break;
                case 0x04 ://水温报警
                    vehicleWarningUpload.setWaterTempNum(dataTool.readStringZero(bb));
                    break;
                case 0x05 ://充电电压报警
                    vehicleWarningUpload.setVoltageNum(dataTool.readStringZero(bb));
                    break;
            }
            vehicleWarningUpload.setCheckSum(bb.readUnsignedShort());
        }catch (Exception e){
            e.printStackTrace();
        }
        return vehicleWarningUpload;
    }

    /**
     * 编码
     * @return 16进制字符数组
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
        bb.writeShort(0x1602);//命令字
        dataTool.writeStringZero(bb, this.getObdID(), true);//末尾补了一个字节0
        bb.writeInt((int) this.getTripID());
        dataTool.writeStringZero(bb, this.getVID(), true);
        dataTool.writeStringZero(bb, this.getVIN(), true);
        dataTool.writeStringZero(bb, this.getGainDataTime(), true);
        bb.writeByte(this.getWarningType());
        countByte += 2+this.obdID.length()+4+this.VID.length()+this.VIN.length()+this.gainDataTime.length()+1;
        addByte +=1+1+1+1;
        //定位信息
        String locationMsg = dataTool.buildLocationString(this);
        dataTool.writeStringZero(bb, locationMsg, false);
        countByte += locationMsg.length();

        switch (this.warningType) {
            case 0x01://新故障码报警
                bb.writeByte(this.getFaultNum());
                countByte += 1;
                for(int i=0;i< this.getFaultNum();i++){
                    dataTool.writeStringZero(bb,this.getFaultCode()[i],true);
                    dataTool.writeStringZero(bb,this.getFaultCodeAttribute()[i],true);
                    dataTool.writeStringZero(bb,this.getFaultMsg()[i],true);
                    countByte += 2 + 2 +this.getFaultMsg()[i].length();
                    addByte +=1;
                }
                break;

            case 0x04://水温报警
                dataTool.writeStringZero(bb, this.getWaterTempNum(), true);
                countByte += this.waterTempNum.length();
                addByte +=1;
                break;
            case 0x05://充电电压报警
                dataTool.writeStringZero(bb, this.getVoltageNum(), true);
                countByte += this.voltageNum.length();
                addByte +=1;
                break;
        }
        bb.writeShort(countByte + addByte -2);//checkSum 去掉数据包标志2个字节
        countByte += 2;
        int index=bb.writerIndex();
        bb.resetWriterIndex();
        bb.writeShort(countByte +addByte -2);
        bb.writeShort(~(countByte +addByte -2));
        bb.writerIndex(index);
        System.out.println("------>>>统计字节个数:" + (countByte+addByte));
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
                +" remoteMachineID_"+ this.getObdID()
                +" tripID_"+ this.getTripID()
                +" VID_"+ this.getVID()
                +" VIN_"+ this.getVIN()
                +" warningType_"+this.getWarningType()
        );
        switch(this.getWarningType()){
            case 0x01:
                System.out.print("faultNum_" + this.getFaultNum());
                for(int i=0;i<this.getFaultCode().length;i++){
                    System.out.print(" faultCode_"+i+"_*"+this.getFaultCode()[i]);
                }
                System.out.println();
                for(int i=0;i<this.getFaultCodeAttribute().length;i++){
                    System.out.print(" faultCodeAttribute_"+i+"_*"+this.getFaultCodeAttribute()[i]);
                }
                System.out.println();
                for(int i=0;i<this.getFaultMsg().length;i++){
                    System.out.println(" faultMsg_"+i+"_*"+this.getFaultMsg()[i]);
                }
                break;

            case 0x04:
                System.out.println("waterTempNum_"+ this.getWaterTempNum());
                break;
            case 0x05:
                System.out.println("voltageNum"+ this.getVoltageNum());
                break;
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

    public byte getWarningType() {
        return warningType;
    }

    public void setWarningType(byte warningType) {
        this.warningType = warningType;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getCurrentDriveDistance() {
        return currentDriveDistance;
    }

    public void setCurrentDriveDistance(String currentDriveDistance) {
        this.currentDriveDistance = currentDriveDistance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(String positionTime) {
        this.positionTime = positionTime;
    }

    public String getPositionMethod() {
        return positionMethod;
    }

    public void setPositionMethod(String positionMethod) {
        this.positionMethod = positionMethod;
    }

    public byte getFaultNum() {
        return faultNum;
    }

    public void setFaultNum(byte faultNum) {
        this.faultNum = faultNum;
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

    public String getWaterTempNum() {
        return waterTempNum;
    }

    public void setWaterTempNum(String waterTempNum) {
        this.waterTempNum = waterTempNum;
    }

    public String getVoltageNum() {
        return voltageNum;
    }

    public void setVoltageNum(String voltageNum) {
        this.voltageNum = voltageNum;
    }
}
