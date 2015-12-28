package com.hp.data.bean.landu;

import com.hp.data.util.DataTool;
import io.netty.buffer.ByteBuf;

import static io.netty.buffer.Unpooled.buffer;

/**
 * 上传调试数据 0x1605
 * Created by zxZhang on 15/12/28.
 */
public class UploadDebugData extends LanDuMsgHead{
    public static final int BUFFER_SIZE = 1024;

    private int orderWord;//命令字，short 0x1606=5638
    private String obdID;//OBD串号（设备号）
    private long tripID;
    private String VID;
    private String VIN;
    private String gainDataTime;//YYYY-MM-DD hh:mm:ss
    private byte[] dataMsg;//十六进制数据

    public DataTool dataTool = new DataTool();

    /**
     * 解码
     */
    public UploadDebugData decoded(byte[] data) {
        UploadDebugData uploadDebugData = new UploadDebugData();
        ByteBuf bb = buffer(BUFFER_SIZE);
        bb.writeBytes(data);

        uploadDebugData.setPackageMark(bb.readUnsignedShort());
        uploadDebugData.setPackageLength(bb.readUnsignedShort());
        uploadDebugData.setCheckPackageLength(bb.readUnsignedShort());
        uploadDebugData.setPackageID(bb.readByte());
        uploadDebugData.setVersion(bb.readByte());

        uploadDebugData.setOrderWord(bb.readUnsignedShort());
        uploadDebugData.setObdID(dataTool.readStringZero(bb));
        uploadDebugData.setTripID(bb.readInt());
        uploadDebugData.setVID(dataTool.readStringZero(bb));
        uploadDebugData.setVIN(dataTool.readStringZero(bb));
        uploadDebugData.setGainDataTime(dataTool.readStringZero(bb));
        bb.readerIndex(bb.writerIndex() -2);//数据内容不必解析，跳过
        uploadDebugData.setCheckSum(bb.readUnsignedShort());
        return uploadDebugData;
    }
    /**
     * 编码
     * @return 16进制字符数组
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
        bb.writeShort(0x1605);//命令字
        dataTool.writeStringZero(bb, this.getObdID(), true);//末尾补了一个字节0
        bb.writeInt((int) this.getTripID());
        dataTool.writeStringZero(bb, this.getVID(), true);
        dataTool.writeStringZero(bb, this.getVIN(), true);
        dataTool.writeStringZero(bb, this.getGainDataTime(), true);
        bb.writeBytes(this.getDataMsg());
        countByte += 2+this.getObdID().length()+4+this.getVID().length()+this.getVIN().length()+this.getGainDataTime().length() + this.getDataMsg().length ;
        addByte += 1+1+1+1;

        countByte += 2;//校验和2个字节
        //回写计算后的数据包长度
        int index = bb.writerIndex();
        bb.resetWriterIndex();
        bb.writeShort(countByte + addByte - 2);
        bb.writeShort(~(countByte + addByte - 2));
        bb.writerIndex(index);

        bb.writeShort(dataTool.getCheckSum(bb));//计算校验和checkSum
        return dataTool.getBytesFromByteBuf(bb);
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
                + " OBDID_" + this.getObdID()
                + " tripID_" + this.getTripID()
                + " VID_" + this.getVID()
                + " VIN_" + this.getVIN()
                + " GainDataTime_" + this.getGainDataTime()
                + " DataMsg_不解析" );
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

    public byte[] getDataMsg() {
        return dataMsg;
    }

    public void setDataMsg(byte[] dataMsg) {
        this.dataMsg = dataMsg;
    }
}
