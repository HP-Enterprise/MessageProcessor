package com.hp.data.bean.landu;

import com.hp.data.util.DataTool;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.buffer.Unpooled.buffer;

/**
 * 从服务器取得参数回复格式 0x1603
 * Created by zxZhang on 2015/12/7.
 */
public class AcceptServerParameterResp extends LanDuMsgHead{
    private Logger _logger = LoggerFactory.getLogger(AcceptServerParameterResp.class);
    public static final int BUFFER_SIZE = 1024;

    private int orderWord;//命令字，short 0x1603=5635
    private String currentTime;//YYYY-MM-DD hh:mm:ss
    //执行动作值
    private byte actionParameterNum;//动作参数数量 只可是0x00x或0x02
    private byte initCode;//回复出厂设置序号
    private byte isClearAction;//是否执行清码动作 0xF0:执行,否则不执行
    // 车辆信息
    private byte vehicleParameterNum;//车辆信息参数数量 仅可0x00或 0x05；
    private String VID;
    private byte brand;//品牌 0xFF默认
    private byte series;//系列 0xFF未确定
    private byte yearStyle;//年款 0xFF未确定

    private String displacementValue;//排量数值 无结束符，保留一位小数
    private String typeComposition;//类型组成 “L”|“T”
    //上传数据网络配置
    private byte networkConfigNum;//网络配置数量
    private String[] ips;//域名IP
    private int[] ports;//端口号
    //车速分段统计设置
    private byte segmentNum;//分段数量<=10
    private byte[] maxSpeeds;//最高车速 递增
    //定位数据设置
    private byte locationParameterNum;//定位参数数量 0x00 0x03
    private int locationGap;//定位间隔距离 75
    private int locationTime;//定位间隔距离 9
    private byte gapAndTime;//距离与时间关系 0x00与关系 0x01或关系
    //报警设置
    private byte warnNum;//报警设置参数数量 0x00和0x04
    private byte overSpeedMinSpeed;//超速最小速度 120
    private int overSpeedMinTime;//超速报警最小持续时间  6
    private int warnWaterTemperature;//报警水温值
    private byte warnChargeVoltage;//充电电压报警值 13.2
    //熄火后数据设置
    private byte misFireDataNum;//熄火后数据数量 0x00 0x03
    private int misFireCloseTime;//熄火后关闭时间点 0xFFFF
    private byte shutCriticalVoltage;//关机临界电压值 保留一位小数，临界电压值*10

    private int misFireVoltageDataTotal;//熄火后电压数据总数 1-10间
    private byte[] misFireBatteryVoltage;//熄火后电池电压阀值
    //运行中数据设置
    private byte dataIDNum;//数据ID数量
    private int[] spaceTime;//间隔时间
    private int[] dataID;//数据ID
    //软件升级ID
    private String updateID;//不超过18字节

    private DataTool dataTool = new DataTool();

    /**
     * 解码
     * @param data
     * @return
     */
    public AcceptServerParameterResp decoded(byte[] data){
        AcceptServerParameterResp resp = new AcceptServerParameterResp();
        ByteBuf bb = buffer(BUFFER_SIZE);
        bb.writeBytes(data);
        resp.setPackageMark(bb.readUnsignedShort());
        resp.setPackageLength(bb.readUnsignedShort());
        resp.setCheckPackageLength(bb.readUnsignedShort());
        resp.setPackageID(bb.readByte());
        resp.setVersion(bb.readByte());

        resp.setOrderWord(bb.readUnsignedShort());
        resp.setCurrentTime(dataTool.readStringZero(bb));
        resp.setActionParameterNum(bb.readByte());
        if(resp.getActionParameterNum() == 0x02){
            resp.setInitCode(bb.readByte());
            resp.setIsClearAction(bb.readByte());
        }else if(resp.getActionParameterNum()==0x00){//不改变配置
        }else {
            _logger.info("_________>>>执行动作值非法");
        }
        resp.setVehicleParameterNum(bb.readByte());
        if(resp.getVehicleParameterNum() == 0x05){
            resp.setVID(dataTool.readStringZero(bb));
            resp.setBrand(bb.readByte());
            resp.setSeries(bb.readByte());
            resp.setYearStyle(bb.readByte());
            String displacement = dataTool.readStringZero(bb);
            resp.setDisplacementValue(displacement.substring(0, displacement.length() - 1));
            resp.setTypeComposition(displacement.substring(displacement.length() - 1));//截取L|T
        }else if(resp.getVehicleParameterNum()==0x00){//不改变配置
        }else {
            _logger.info("_________>>>车辆信息非法");
        }
        resp.setNetworkConfigNum(bb.readByte());
        if(resp.getNetworkConfigNum() != 0x00){
            String[] ipStr = new String[resp.getNetworkConfigNum()];
            int[] port = new int[resp.getNetworkConfigNum()];
            for(int i=0;i<resp.getNetworkConfigNum();i++){
                ipStr[i] = dataTool.readStringZero(bb);
                port[i] = bb.readUnsignedShort();
            }
            resp.setIps(ipStr);
            resp.setPorts(port);
        }else{//不改变配置
            _logger.info("_________>>>上传数据网络配置不修改");
        }
        resp.setSegmentNum(bb.readByte());
        byte[] speeds= new byte[resp.getSegmentNum()];
        for(int i=0;i<resp.getSegmentNum();i++){
            speeds[i] = bb.readByte();
        }
        resp.setMaxSpeeds(speeds);
        resp.setLocationParameterNum(bb.readByte());
        if(resp.getLocationParameterNum() == 0x03){
            resp.setLocationGap(bb.readUnsignedShort());
            resp.setLocationTime(bb.readUnsignedShort());
            resp.setGapAndTime(bb.readByte());
        }else if(resp.getLocationParameterNum()==0x00){//不改变配置
        }else {
            _logger.info("_________>>>定位数据非法");
        }
        resp.setWarnNum(bb.readByte());
        if(resp.getWarnNum() == 0x04){
            resp.setOverSpeedMinSpeed(bb.readByte());
            resp.setOverSpeedMinTime(bb.readUnsignedShort());
            resp.setWarnWaterTemperature(bb.readUnsignedShort());
            resp.setWarnChargeVoltage(bb.readByte());
        }else if(resp.getWarnNum()== 0x00){//不改变配置
        }else {
            _logger.info("_________>>>报警设置非法");
        }
        resp.setMisFireDataNum(bb.readByte());
        if(resp.getMisFireDataNum() == 0x03){
            resp.setMisFireCloseTime(bb.readUnsignedShort());
            resp.setShutCriticalVoltage(bb.readByte());
            resp.setMisFireVoltageDataTotal(bb.readUnsignedShort());
            byte[] bytes = new byte[resp.getMisFireVoltageDataTotal()];
            for(int i=0;i<resp.getMisFireVoltageDataTotal();i++){
                bytes[i] = bb.readByte();
            }
            resp.setMisFireBatteryVoltage(bytes);
        }else if(resp.getMisFireDataNum()== 0x00){//不改变配置
        }else {
            _logger.info("_________>>>熄火后数据设置非法");
        }
        resp.setDataIDNum(bb.readByte());
        if(resp.getDataIDNum() > 0){
            int[] time = new int[resp.getDataIDNum()];
            int[] ids = new int[resp.getDataIDNum()];
            for(int i=0; i <resp.getDataIDNum();i++){
                time[i] = bb.readUnsignedShort();
                ids[i] = bb.readUnsignedShort();
            }
            resp.setSpaceTime(time);
            resp.setDataID(ids);
        }
        resp.setUpdateID(dataTool.readStringZero(bb));

        resp.setCheckSum(bb.readUnsignedShort());
        return resp;
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
        bb.markWriterIndex();//标记ByteBuf的writerIndex
        bb.writeShort(0x3030);//填充00，预留packageLength空间
        bb.writeShort(0x3030);//填充00，预留checkPackageLength空间
        bb.writeByte(this.getPackageID());
        bb.writeByte(0x05);
        countByte +=2 + 2 + 2 + 1 + 1;
        //消息内容
        bb.writeShort(0x1603);//命令字
        dataTool.writeStringZero(bb, this.getCurrentTime(), true);
        addByte +=1;
        bb.writeByte(this.getActionParameterNum());
        if(this.getActionParameterNum() ==0x02){
            bb.writeByte(this.getInitCode());
            bb.writeByte(this.getIsClearAction());
            countByte+=1+1;
        }
        countByte += 2+this.getCurrentTime().length()+1;

        bb.writeByte(this.getVehicleParameterNum());
        if(this.getVehicleParameterNum() ==0x05){
            dataTool.writeStringZero(bb, this.getVID(), true);
            bb.writeByte(this.getBrand());
            bb.writeByte(this.getSeries() == 0 ? 0xFF :this.getSeries());
            bb.writeByte(this.getYearStyle());
            String displacement = this.getDisplacementValue()+this.getTypeComposition();
            dataTool.writeStringZero(bb, displacement, true);
            countByte +=this.getVID().length()+1+1+1+displacement.length();
            addByte +=1+1;
        }
        countByte +=1;

        bb.writeByte(this.getNetworkConfigNum());
        for(int i=0;i<this.getNetworkConfigNum();i++){
            if(this.getIps()[i].length() <=48){
                dataTool.writeStringZero(bb, this.getIps()[i], true);
            }else {
                _logger.info("_________>>>ip超长");
            }
            bb.writeShort(this.getPorts()[i]);
            countByte +=this.getIps()[i].length()+2;
            addByte +=1;
        }
        countByte +=1;

        if(this.getSegmentNum()<=10){
            bb.writeByte(this.getSegmentNum());
            for (int i = 0; i < this.getSegmentNum();i++){
                if(i <= this.getSegmentNum()-2 && this.getMaxSpeeds()[i]<this.getMaxSpeeds()[i+1]){
                    bb.writeByte(this.getMaxSpeeds()[i]);
                    countByte += 1;
                }else if(i == this.getSegmentNum()-1 ){
                    bb.writeByte(this.getMaxSpeeds()[i]);
                    countByte += 1;
                }else {
                    _logger.info("_________>>>第"+i+"分段最高车速不递增");
                }
            }
            countByte +=1;
        }else {
            _logger.info("_________>>>车速分段统计设置分段数量过多");
        }

        bb.writeByte(this.getLocationParameterNum());
        if(this.getLocationParameterNum()==0x03){
            bb.writeShort(this.getLocationGap()==0? 75: this.getLocationGap());
            bb.writeShort(this.getLocationTime() ==0? 9:this.getLocationTime());
            bb.writeByte(this.getGapAndTime());
            countByte += 2+2+1;
        }else if(this.getLocationParameterNum()==0x00){
        }else {
            _logger.info("_________>>>定位数据设置非法");
        }
        countByte +=1;

        bb.writeByte(this.getWarnNum());
        if(this.getWarnNum()==0x04){
            bb.writeByte(this.getOverSpeedMinSpeed() == 0 ? 120 : this.getOverSpeedMinSpeed());
            bb.writeShort(this.getOverSpeedMinTime() == 0 ? 6 : this.getOverSpeedMinTime());
            bb.writeShort(this.getWarnWaterTemperature() == 0 ? 110 : this.getWarnWaterTemperature());
            bb.writeByte(this.getWarnChargeVoltage()==0 ? 132: this.getWarnChargeVoltage());
            countByte += 1+2+2+1;
        }else if(this.getWarnNum()==0x00){
        }else {
            _logger.info("_________>>>报警数据设置非法");
        }
        countByte +=1;

        bb.writeByte(this.getMisFireDataNum());
        if(this.getMisFireDataNum()==0x03){
            bb.writeShort(this.getMisFireCloseTime());
            bb.writeByte(this.getShutCriticalVoltage());
            countByte += 2+1;
            if(this.getMisFireVoltageDataTotal()>=1 && this.getMisFireVoltageDataTotal()<=10){
                bb.writeShort(this.getMisFireVoltageDataTotal());
                bb.writeBytes(this.getMisFireBatteryVoltage());//数组
                countByte += 2+this.getMisFireBatteryVoltage().length;
            }
        }else if(this.getLocationParameterNum()==0x00){
        }else {
            _logger.info("_________>>>熄火后数据设置非法");
        }
        countByte +=1;

        bb.writeByte(this.getDataIDNum());
        if(this.getDataIDNum()!=0){
            for(int i=0;i<this.getDataIDNum();i++){
                bb.writeShort(this.getSpaceTime()[i]);
                bb.writeShort(this.getDataID()[i]);
                countByte+= 1+1;
            }
        }
        countByte +=1;

        if(this.getUpdateID().length()<=18){
            dataTool.writeStringZero(bb,this.getUpdateID(),true);
            countByte +=this.getUpdateID().length();
            addByte +=1;
        }else {
            _logger.info("_________>>>软件升级ID超长");
            dataTool.writeStringZero(bb, this.getUpdateID().substring(0, 18), true);
            countByte +=18;
            addByte +=1;
        }
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

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public byte getActionParameterNum() {
        return actionParameterNum;
    }

    public void setActionParameterNum(byte actionParameterNum) {
        this.actionParameterNum = actionParameterNum;
    }

    public byte getInitCode() {
        return initCode;
    }

    public void setInitCode(byte initCode) {
        this.initCode = initCode;
    }

    public byte getIsClearAction() {
        return isClearAction;
    }

    public void setIsClearAction(byte isClearAction) {
        this.isClearAction = isClearAction;
    }

    public byte getVehicleParameterNum() {
        return vehicleParameterNum;
    }

    public void setVehicleParameterNum(byte vehicleParameterNum) {
        this.vehicleParameterNum = vehicleParameterNum;
    }

    public String getVID() {
        return VID;
    }

    public void setVID(String VID) {
        this.VID = VID;
    }

    public byte getBrand() {
        return brand;
    }

    public void setBrand(byte brand) {
        this.brand = brand;
    }

    public byte getSeries() {
        return series;
    }

    public void setSeries(byte series) {
        this.series = series;
    }

    public byte getYearStyle() {
        return yearStyle;
    }

    public void setYearStyle(byte yearStyle) {
        this.yearStyle = yearStyle;
    }

    public String getDisplacementValue() {
        return displacementValue;
    }

    public void setDisplacementValue(String displacementValue) {
        this.displacementValue = displacementValue;
    }

    public String getTypeComposition() {
        return typeComposition;
    }

    public void setTypeComposition(String typeComposition) {
        this.typeComposition = typeComposition;
    }

    public byte getNetworkConfigNum() {
        return networkConfigNum;
    }

    public void setNetworkConfigNum(byte networkConfigNum) {
        this.networkConfigNum = networkConfigNum;
    }

    public String[] getIps() {
        return ips;
    }

    public void setIps(String[] ips) {
        this.ips = ips;
    }

    public int[] getPorts() {
        return ports;
    }

    public void setPorts(int[] ports) {
        this.ports = ports;
    }

    public byte getSegmentNum() {
        return segmentNum;
    }

    public void setSegmentNum(byte segmentNum) {
        this.segmentNum = segmentNum;
    }

    public byte[] getMaxSpeeds() {
        return maxSpeeds;
    }

    public void setMaxSpeeds(byte[] maxSpeeds) {
        this.maxSpeeds = maxSpeeds;
    }

    public byte getLocationParameterNum() {
        return locationParameterNum;
    }

    public void setLocationParameterNum(byte locationParameterNum) {
        this.locationParameterNum = locationParameterNum;
    }

    public int getLocationGap() {
        return locationGap;
    }

    public void setLocationGap(int locationGap) {
        this.locationGap = locationGap;
    }

    public int getLocationTime() {
        return locationTime;
    }

    public void setLocationTime(int locationTime) {
        this.locationTime = locationTime;
    }

    public byte getGapAndTime() {
        return gapAndTime;
    }

    public void setGapAndTime(byte gapAndTime) {
        this.gapAndTime = gapAndTime;
    }

    public byte getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(byte warnNum) {
        this.warnNum = warnNum;
    }

    public byte getOverSpeedMinSpeed() {
        return overSpeedMinSpeed;
    }

    public void setOverSpeedMinSpeed(byte overSpeedMinSpeed) {
        this.overSpeedMinSpeed = overSpeedMinSpeed;
    }

    public int getOverSpeedMinTime() {
        return overSpeedMinTime;
    }

    public void setOverSpeedMinTime(int overSpeedMinTime) {
        this.overSpeedMinTime = overSpeedMinTime;
    }

    public int getWarnWaterTemperature() {
        return warnWaterTemperature;
    }

    public void setWarnWaterTemperature(int warnWaterTemperature) {
        this.warnWaterTemperature = warnWaterTemperature;
    }

    public byte getWarnChargeVoltage() {
        return warnChargeVoltage;
    }

    public void setWarnChargeVoltage(byte warnChargeVoltage) {
        this.warnChargeVoltage = warnChargeVoltage;
    }

    public byte getMisFireDataNum() {
        return misFireDataNum;
    }

    public void setMisFireDataNum(byte misFireDataNum) {
        this.misFireDataNum = misFireDataNum;
    }

    public int getMisFireCloseTime() {
        return misFireCloseTime;
    }

    public void setMisFireCloseTime(int misFireCloseTime) {
        this.misFireCloseTime = misFireCloseTime;
    }

    public byte getShutCriticalVoltage() {
        return shutCriticalVoltage;
    }

    public void setShutCriticalVoltage(byte shutCriticalVoltage) {
        this.shutCriticalVoltage = shutCriticalVoltage;
    }

    public int getMisFireVoltageDataTotal() {
        return misFireVoltageDataTotal;
    }

    public void setMisFireVoltageDataTotal(int misFireVoltageDataTotal) {
        this.misFireVoltageDataTotal = misFireVoltageDataTotal;
    }

    public byte[] getMisFireBatteryVoltage() {
        return misFireBatteryVoltage;
    }

    public void setMisFireBatteryVoltage(byte[] misFireBatteryVoltage) {
        this.misFireBatteryVoltage = misFireBatteryVoltage;
    }

    public byte getDataIDNum() {
        return dataIDNum;
    }

    public void setDataIDNum(byte dataIDNum) {
        this.dataIDNum = dataIDNum;
    }

    public int[] getSpaceTime() {
        return spaceTime;
    }

    public void setSpaceTime(int[] spaceTime) {
        this.spaceTime = spaceTime;
    }

    public int[] getDataID() {
        return dataID;
    }

    public void setDataID(int[] dataID) {
        this.dataID = dataID;
    }

    public String getUpdateID() {
        return updateID;
    }

    public void setUpdateID(String updateID) {
        this.updateID = updateID;
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
                        + " remoteMachineID_" + this.getOrderWord()
                        + " tripID_" + this.getCurrentTime()
                        + " ActionParameterNum_" + this.getActionParameterNum()
                        + " InitCode_" + this.getInitCode()
                        + " IsClearAction_" + this.getIsClearAction()
                        + " VehicleParameterNum_" + this.getVehicleParameterNum()
                        + " VID_" + this.getVID()
                        + " Brand_" + this.getBrand()
                        + " Series_" + this.getSeries()
                        + " YearStyle_" + this.getYearStyle()
                        + " DisplacementValue_" + this.getDisplacementValue()
                        + " TypeComposition_" + this.getTypeComposition()
                        + " NetworkConfigNum_" + this.getNetworkConfigNum()
        );
        for(int i=0;i<this.getIps().length;i++){
            System.out.print(" Ips__"+i+"_*"+ this.getIps()[i]);
        }
        for(int i=0;i<this.getPorts().length;i++){
            System.out.print(" Ports__"+i+"_*"+ this.getPorts()[i]);
        }
        System.out.println(" SegmentNum_" + this.getSegmentNum());
        for(int i=0;i<this.getMaxSpeeds().length;i++){
            System.out.print(" MaxSpeeds_"+i+"_*"+ this.getMaxSpeeds()[i]);
        }
        System.out.println(" LocationParameterNum_" + this.getLocationParameterNum()
                        + " LocationGap_" + this.getLocationGap()
                        + " LocationTime_" + this.getLocationTime()
                        + " GapAndTime_" + this.getGapAndTime()
                        + " WarnNum_" + this.getWarnNum()
                        + " OverSpeedMinSpeed_" + this.getOverSpeedMinSpeed()
                        + " OverSpeedMinTime_" + this.getOverSpeedMinTime()
                        + " WarnWaterTemperature_" + this.getWarnWaterTemperature()
                        + " WarnChargeVoltage_" + this.getWarnChargeVoltage()
                        + " MisFireDataNum_" + this.getMisFireDataNum()
                        + " MisFireCloseTime_" + this.getMisFireCloseTime()
                        + " ShutCriticalVoltage_" + this.getShutCriticalVoltage()
                        + " MisFireVoltageDataTotal_" + this.getMisFireVoltageDataTotal()
        );
        for(int i=0;i<this.getMisFireBatteryVoltage().length;i++){
            System.out.print(" MisFireBatteryVoltage_"+i+"_*"+ this.getMisFireBatteryVoltage()[i]);
        }
        System.out.println(" DataIDNum_" + this.getDataIDNum());
        for(int i=0;i<this.getSpaceTime().length;i++){
            System.out.print(" SpaceTime__"+i+"_*"+ this.getSpaceTime()[i]);
        }
        for(int i=0;i<this.getDataID().length;i++){
            System.out.print(" DataID__"+i+"_*"+ this.getDataID()[i]);
        }
        System.out.println(" UpdateID_" + this.getUpdateID());
    }
}
