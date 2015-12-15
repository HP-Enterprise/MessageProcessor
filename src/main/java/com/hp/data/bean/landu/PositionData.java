package com.hp.data.bean.landu;

import com.hp.data.util.DataTool;
import io.netty.buffer.ByteBuf;

import static io.netty.buffer.Unpooled.buffer;

/**
 * 位置数据 0x1606
 * Created by zxZhang on 2015/12/11.
 */
public class PositionData extends LanDuMsgHead{
    public static final int BUFFER_SIZE = 1024;

    private int orderWord;//命令字，short 0x1606=5638
    private String obdID;//OBD串号（设备号）
    private long tripID;
    private String VID;
    private String VIN;
    //定位数据内容
    private int locationNum;//定位数据个数
    //定位信息
    private String[] speed;//车数
    private String[] currentDriveDistance;//当前行程行驶距离
    private String[] longitude;//经度 字符串无结束符
    private String[] latitude;//纬度 字符串无结束符
    private String[] direct;//方向 字符串无结束符
    private String[] positionTime;//定位时间
    private String[] positionMethod;//定位方式

    private DataTool dataTool = new DataTool();

    /**
     * 解码
     */
    public PositionData decoded(byte[] data){
        PositionData positionData = new PositionData();
        ByteBuf bb = buffer(BUFFER_SIZE);
        bb.writeBytes(data);
        positionData.setPackageMark(bb.readUnsignedShort());
        positionData.setPackageLength(bb.readUnsignedShort());
        positionData.setCheckPackageLength(bb.readUnsignedShort());
        positionData.setPackageID(bb.readByte());
        positionData.setVersion(bb.readByte());

        positionData.setOrderWord(bb.readUnsignedShort());
        positionData.setObdID(dataTool.readStringZero(bb));
        positionData.setTripID(bb.readInt());
        positionData.setVID(dataTool.readStringZero(bb));
        positionData.setVIN(dataTool.readStringZero(bb));
        positionData.setLocationNum(bb.readUnsignedShort());
        String[] speeds = new String[positionData.getLocationNum()];
        String[] currentDriveDistances = new String[positionData.getLocationNum()];
        String[] longitudes = new String[positionData.getLocationNum()];
        String[] latitudes = new String[positionData.getLocationNum()];
        String[] directs = new String[positionData.getLocationNum()];
        String[] positionTimes = new String[positionData.getLocationNum()];
        String[] positionMethods = new String[positionData.getLocationNum()];
        for(int i=0;i<positionData.getLocationNum();i++){
            speeds[i] = dataTool.readStringZero(bb);
            currentDriveDistances[i] = dataTool.readStringZero(bb);
            longitudes[i] = dataTool.readStringZero(bb);
            latitudes[i] = dataTool.readStringZero(bb);
            directs[i] = dataTool.readStringZero(bb);
            positionTimes[i] = dataTool.readStringZero(bb);
            positionMethods[i] = dataTool.readStringZero(bb);
        }
        positionData.setSpeed(speeds);
        positionData.setCurrentDriveDistance(currentDriveDistances);
        positionData.setLongitude(longitudes);
        positionData.setLatitude(latitudes);
        positionData.setDirect(directs);
        positionData.setPositionTime(positionTimes);
        positionData.setPositionMethod(positionMethods);

        positionData.setCheckSum(bb.readUnsignedShort());
        return positionData;
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
        countByte = 2+2+2+1+1;
        //消息内容
        bb.writeShort(0x1606);//命令字
        dataTool.writeStringZero(bb, this.getObdID(), true);//末尾补了一个字节0
        bb.writeInt((int) this.getTripID());// DWORD
        dataTool.writeStringZero(bb, this.getVID(), true);
        dataTool.writeStringZero(bb, this.getVIN(), true);
        bb.writeShort(this.getLocationNum());
        countByte +=2+this.getObdID().length()+4+this.getVID().length()+this.getVIN().length()+2;
        addByte +=1+1+1+1;
        //定位信息
        for(int i=0; i<this.getLocationNum();i++){
            String locationMsg = buildLocationString(this.getSpeed()[i], this.getCurrentDriveDistance()[i],this.getLongitude()[i],this.getLatitude()[i],this.getDirect()[i],this.getPositionTime()[i],this.getPositionMethod()[i]);
            dataTool.writeStringZero(bb, locationMsg, false);
            countByte += locationMsg.length();
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
    /**
     * 构建定位信息字符串
     */
    public String buildLocationString(String speed,String currentDriveDistance,String longitude,String latitude,String direct,String positionTime,String positionMethod){
        String s = "\000";
        StringBuilder sb = new StringBuilder();
        sb.append(speed);
        sb.append(s);
        sb.append(currentDriveDistance);
        sb.append(s);
        sb.append(longitude);
        sb.append(',');
        sb.append(latitude);
        sb.append(',');
        sb.append(direct);
        sb.append(',');
        sb.append(positionTime);
        sb.append(',');
        sb.append(positionMethod);
        sb.append(s);
        return sb.toString();
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
                        +" LocationNum_"+ this.getLocationNum());
        for(int i=0; i<this.getLocationNum();i++){
            System.out.println(" speed_"+i+"_"+ this.getSpeed()[i]
                    +" CurrentDriveDistance_"+i+"_"+ this.getCurrentDriveDistance()[i]
                    +" Longitude_"+i+"_"+ this.getLongitude()[i]
                    +" Latitude_"+i+"_"+ this.getLatitude()[i]
                    +" Direct_"+i+"_"+ this.getDirect()[i]
                    +" PositionTime_"+i+"_"+ this.getPositionTime()[i]
                    +" PositionMethod_"+i+"_"+ this.getPositionMethod()[i]);
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

    public int getLocationNum() {
        return locationNum;
    }

    public void setLocationNum(int locationNum) {
        this.locationNum = locationNum;
    }

    public String[] getSpeed() {
        return speed;
    }

    public void setSpeed(String[] speed) {
        this.speed = speed;
    }

    public String[] getCurrentDriveDistance() {
        return currentDriveDistance;
    }

    public void setCurrentDriveDistance(String[] currentDriveDistance) {
        this.currentDriveDistance = currentDriveDistance;
    }

    public String[] getLongitude() {
        return longitude;
    }

    public void setLongitude(String[] longitude) {
        this.longitude = longitude;
    }

    public String[] getLatitude() {
        return latitude;
    }

    public void setLatitude(String[] latitude) {
        this.latitude = latitude;
    }

    public String[] getDirect() {
        return direct;
    }

    public void setDirect(String[] direct) {
        this.direct = direct;
    }

    public String[] getPositionTime() {
        return positionTime;
    }

    public void setPositionTime(String[] positionTime) {
        this.positionTime = positionTime;
    }

    public String[] getPositionMethod() {
        return positionMethod;
    }

    public void setPositionMethod(String[] positionMethod) {
        this.positionMethod = positionMethod;
    }
}
