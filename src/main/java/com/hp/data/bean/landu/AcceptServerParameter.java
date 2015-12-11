package com.hp.data.bean.landu;

import com.hp.data.util.DataTool;
import io.netty.buffer.ByteBuf;

import static io.netty.buffer.Unpooled.buffer;

/**
 * 从服务器取得参数 0x1603
 * Created by zxZhang on 2015/12/7.
 */
public class AcceptServerParameter extends LanDuMsgHead{
    public static final int BUFFER_SIZE = 1024;
    //数据头
    private int orderWord;//命令字，short 0x1603=5635
    private String remoteMachineID;//远程诊断仪串号（设备号）
    private long tripID;// DWORD
    //车辆信息
    private String VID;
    private String VIN;
    //模块信息
    private String hardwareVersion;//硬件版本号
    private String firmwareVersion;//固件版本号
    private String softwareVersion;//软件版本号
    private byte diagnosisType;//诊断程序类型 0xFF
    //执行动作初值
    private byte initCode;//回复出厂设置序号

    public DataTool dataTool = new DataTool();

    /**
     * 解码
     * @param data
     * @return
     */
    public AcceptServerParameter decoded(byte[] data){
        AcceptServerParameter acceptServerParameter = new AcceptServerParameter();
        ByteBuf bb = buffer(BUFFER_SIZE);
        bb.writeBytes(data);
        acceptServerParameter.setPackageMark(bb.readUnsignedShort());
        acceptServerParameter.setPackageLength(bb.readUnsignedShort());
        acceptServerParameter.setCheckPackageLength(bb.readUnsignedShort());
        acceptServerParameter.setPackageID(bb.readByte());
        acceptServerParameter.setVersion(bb.readByte());

        acceptServerParameter.setOrderWord(bb.readUnsignedShort());
        acceptServerParameter.setRemoteMachineID(dataTool.readStringZero(bb));
        acceptServerParameter.setTripID(bb.readInt());

        acceptServerParameter.setVID(dataTool.readStringZero(bb));
        acceptServerParameter.setVIN(dataTool.readStringZero(bb));

        acceptServerParameter.setHardwareVersion(dataTool.readStringZero(bb));
        acceptServerParameter.setFirmwareVersion(dataTool.readStringZero(bb));
        acceptServerParameter.setSoftwareVersion(dataTool.readStringZero(bb));
        acceptServerParameter.setDiagnosisType(bb.readByte());

        acceptServerParameter.setInitCode(bb.readByte());

        acceptServerParameter.setCheckSum(bb.readUnsignedShort());
        return acceptServerParameter;
    }

    /**
     * 编码
     * @return
     */
    public byte[] encoded() {
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
        countByte = 2 + 2 + 2 + 1 + 1;
        //消息内容
        bb.writeShort(0x1603);//命令字
        dataTool.writeStringZero(bb, this.getRemoteMachineID(), true);//末尾补了一个字节0
        bb.writeInt((int) this.getTripID());

        dataTool.writeStringZero(bb, this.getVID(), true);
        dataTool.writeStringZero(bb, this.getVIN(), true);
        countByte += 2 + this.remoteMachineID.length() + 4+  this.VID.length() + this.VIN.length();
        addByte += 1 + 1 + 1;
        dataTool.writeStringZero(bb, this.getHardwareVersion(), true);
        dataTool.writeStringZero(bb, this.getFirmwareVersion(), true);
        dataTool.writeStringZero(bb, this.getSoftwareVersion(), true);
        bb.writeByte(this.getDiagnosisType());

        bb.writeByte(this.getInitCode());
        countByte += this.getHardwareVersion().length() + this.getFirmwareVersion().length()+this.getSoftwareVersion().length()+1+1;
        addByte += 1 + 1 + 1;

        bb.writeShort(countByte + addByte - 2);//checkSum 去掉数据包标志2个字节
        countByte += 2;
        int index=bb.writerIndex();
        bb.resetWriterIndex();
        bb.writeShort(countByte + addByte - 2);
        bb.writeShort(~(countByte + addByte - 2));
        bb.writerIndex(index);
        System.out.println("------>>>统计字节个数:" + (countByte+addByte));
        return dataTool.getBytesFromByteBuf(bb);
    }

    public int getOrderWord() {
        return orderWord;
    }

    public void setOrderWord(int orderWord) {
        this.orderWord = orderWord;
    }

    public String getRemoteMachineID() {
        return remoteMachineID;
    }

    public void setRemoteMachineID(String remoteMachineID) {
        this.remoteMachineID = remoteMachineID;
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

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public String getFirmwareVersion() {
        return firmwareVersion;
    }

    public void setFirmwareVersion(String firmwareVersion) {
        this.firmwareVersion = firmwareVersion;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public byte getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(byte diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    public byte getInitCode() {
        return initCode;
    }

    public void setInitCode(byte initCode) {
        this.initCode = initCode;
    }

    public void print() {
        System.out.println("LanDuMsg:__________________________");
        System.out.println("packageMark_" + this.getPackageMark()
                + " packageLength_" + this.getPackageLength()
                + " checkPackageLength_" + this.getCheckPackageLength()
                + " packageID_" + this.getPackageID()
                + " version_" + this.getVersion()
                + " checkSum_" + this.getCheckSum());
        System.out.println("LanDuMsgContent:__________________________");
        System.out.println("orderWord_" + this.getOrderWord()
                        + " remoteMachineID_" + this.getRemoteMachineID()
                        + " tripID_" + this.getTripID()
                        + " VID_" + this.getVID()
                        + " VIN_" + this.getVIN()
                        + " HardwareVersion_" + this.getHardwareVersion()
                        + " FirmwareVersion_" + this.getFirmwareVersion()
                        + " SoftwareVersion_" + this.getSoftwareVersion()
                        + " DiagnosisType_" + this.getDiagnosisType()
                        + " InitCode_" + this.getInitCode()
        );
    }
}
