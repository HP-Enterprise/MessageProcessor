package com.hp.data.bean.landu;

import com.hp.data.util.DataTool;
import io.netty.buffer.ByteBuf;

import static io.netty.buffer.Unpooled.buffer;

/**
 * 行为位置数据
 * Created by zxZhang on 15/12/24.
 */
public class UploadVehicleData extends LanDuMsgHead{
    public static final int BUFFER_SIZE = 1024;

    private int orderWord;//命令字，short 0x1606=5638
    private String obdID;//OBD串号（设备号）
    private long tripID;
    private String VID;
    private String VIN;
    private String gainDataTime;//YYYY-MM-DD hh:mm:ss
    private byte dataType;//数据类型 0x01 超速记录 0x02 急加速记录 0x03 急减速记录 0x04 急转弯记录 0xF0 拔下OBD记录
    //定位信息
    private String speed;//车数
    private String currentDriveDistance;//当前行程行驶距离
    private String longitude;//经度 字符串无结束符
    private String latitude;//纬度 字符串无结束符
    private String direct;//方向 字符串无结束符
    private String positionTime;//定位时间
    private String positionMethod;//定位方式

    public DataTool dataTool = new DataTool();

    /**
     * 解码
     */
    public UploadVehicleData decoded(byte[] data){
        UploadVehicleData uploadVehicleData = new UploadVehicleData();
        ByteBuf bb = buffer(BUFFER_SIZE);
        bb.writeBytes(data);

        uploadVehicleData.setPackageMark(bb.readUnsignedShort());
        uploadVehicleData.setPackageLength(bb.readUnsignedShort());
        uploadVehicleData.setCheckPackageLength(bb.readUnsignedShort());
        uploadVehicleData.setPackageID(bb.readByte());
        uploadVehicleData.setVersion(bb.readByte());

        uploadVehicleData.setOrderWord(bb.readUnsignedShort());
        uploadVehicleData.setObdID(dataTool.readStringZero(bb));
        uploadVehicleData.setTripID(bb.readInt());
        uploadVehicleData.setVID(dataTool.readStringZero(bb));
        uploadVehicleData.setVIN(dataTool.readStringZero(bb));
        uploadVehicleData.setGainDataTime(dataTool.readStringZero(bb));
        uploadVehicleData.setDataType(bb.readByte());
        if(uploadVehicleData.getDataType()>=1 && uploadVehicleData.getDataType()<=4) {
            //定位信息
            uploadVehicleData.setSpeed(dataTool.readStringZero(bb));
            uploadVehicleData.setCurrentDriveDistance(dataTool.readStringZero(bb));
            uploadVehicleData.setLongitude(dataTool.readStringZero(bb));
            uploadVehicleData.setLatitude(dataTool.readStringZero(bb));
            uploadVehicleData.setDirect(dataTool.readStringZero(bb));
            uploadVehicleData.setPositionTime(dataTool.readStringZero(bb));
            uploadVehicleData.setPositionMethod(dataTool.readStringZero(bb));
        }
        uploadVehicleData.setCheckSum(bb.readUnsignedShort());
        return uploadVehicleData;
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
        bb.writeShort(0x160A);//命令字
        dataTool.writeStringZero(bb, this.getObdID(), true);//末尾补了一个字节0
        bb.writeInt((int) this.getTripID());
        dataTool.writeStringZero(bb, this.getVID(), true);
        dataTool.writeStringZero(bb, this.getVIN(), true);
        dataTool.writeStringZero(bb, this.getGainDataTime(), true);
        bb.writeByte(this.getDataType());//数据类型
        countByte += 2+this.getObdID().length()+4+this.getVID().length()+this.getVIN().length()+this.getGainDataTime().length()+1;
        addByte += 1+1+1+1;

        if(this.getDataType()>=1 && this.getDataType()<=4){
            String locationMessage = dataTool.buildLocationString(this.getSpeed(),this.getCurrentDriveDistance(),this.getLongitude(),this.getLatitude(),this.getDirect(),this.getPositionTime(),this.getPositionMethod());
            dataTool.writeStringZero(bb, locationMessage, false);
            countByte += locationMessage.length();
        }else if(  this.getDataType()==(byte) 0xF0){
            //报下OBD记录，无数据内容
        }else {
            System.out.println("数据类型无效");
        }
        countByte += 2;//校验和2个字节
        //回写计算后的数据包长度
        int index=bb.writerIndex();
        bb.resetWriterIndex();
        bb.writeShort(countByte +addByte -2);
        bb.writeShort(~(countByte + addByte - 2));
        bb.writerIndex(index);

        bb.writeShort(dataTool.getCheckSum(bb));//计算校验和checkSum
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
                +" OBDID_"+ this.getObdID()
                +" tripID_"+ this.getTripID()
                +" VID_"+ this.getVID()
                +" VIN_"+ this.getVIN()
                +" GainDataTime_"+ this.getGainDataTime()
                +" DataType_"+ this.getDataType());
        System.out.println("speed_"+this.getSpeed()
                +" currentDriveDistance_"+this.getCurrentDriveDistance()
                +" longitude_"+this.getLongitude()
                +" latitude_"+this.getLatitude()
                +" direct_"+this.getDirect()
                +" positionTime_"+this.getPositionTime()
                +" positionMethod_"+this.getPositionMethod());
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

    public byte getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
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
}
