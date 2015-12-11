package com.hp.data;

import com.hp.data.bean.landu.VehicleWarningUpload;
import com.hp.data.util.DataTool;
import org.junit.Test;

/**
 * Created by yuh on 2015/12/2.
 */
public class VehicleWarningUploadTest {
    VehicleWarningUpload vehicleWarningUpload = new VehicleWarningUpload();
    DataTool dataTool = new DataTool();

    @Test
    public void testDataWarningDecoded(){
        byte[] data= { (byte)0xAA,0x55,0x11,0x12,(byte)0xEE,(byte)0xED,0x01,0x05,0x16,0x01,0x4E,0x42,0x31,0x00,0x00,0x00,0x00,0x64,0x31,0x32,0x33,0x34,0x35,0x36,0x00,0x31,0x32,0x33,0x34,0x35,0x36,0x00,0x32,0x30,0x31,0x35,0x2D,0x31,0x32,0x2D,0x30
                ,0x33,0x20,0x31,0x36,0x3A,0x30,0x36,0x3A,0x33,0x30,0x00,0x01,0x33,0x30,0x2E,0x30,0x35,0x56,0x00,0x38,0x30,0x00,0x31,0x32,0x30,0x33,0x00,0x45,0x31,0x32,0x33,0x2E,0x30,0x30,0x38,0x31,0x32,0x32,0x2C,0x4E,0x31,0x31
                ,0x2E,0x33,0x32,0x31,0x32,0x33,0x34,0x2C,0x33,0x30,0x2C,0x32,0x30,0x31,0x35,0x2D,0x31,0x32,0x2D,0x30,0x33,0x20,0x31,0x36,0x3A,0x31,0x32,0x3A,0x33,0x30,0x2C,0x32,0x00,0x00,0x60};
        VehicleWarningUpload v = vehicleWarningUpload.decoded(data);
        v.print();
    }

    @Test
    public void testDataUploadEncoded(){
        vehicleWarningUpload.setPackageID((byte) 0x01);

        vehicleWarningUpload.setObdID("NB1");
        vehicleWarningUpload.setTripID(100);
        vehicleWarningUpload.setVID("123456");
        vehicleWarningUpload.setVIN("123456");
        vehicleWarningUpload.setGainDataTime("2015-12-09 10:06:30");

        //测试1062不同的数据内容
        vehicleWarningUpload.setWarningType((byte) 0x01);

        //定位
        vehicleWarningUpload.setSpeed("80");
        vehicleWarningUpload.setCurrentDriveDistance("1203");
        vehicleWarningUpload.setLongitude("E123.008122");
        vehicleWarningUpload.setLatitude("N11.321234");
        vehicleWarningUpload.setDirect("30");
        vehicleWarningUpload.setPositionTime("2015-12-03 16:12:30");
        vehicleWarningUpload.setPositionMethod("2");

        switch (vehicleWarningUpload.getWarningType()){
            case 0x01://
                String[] faultCode = {"AAA","BBB","CCC"};
                String[] faultCodeAttribute = {"1","2","3"};
                String[] faultMsg = {"no_power","error","exception"};
                vehicleWarningUpload.setFaultNum((byte)3);
                vehicleWarningUpload.setFaultCode(faultCode);
                vehicleWarningUpload.setFaultCodeAttribute(faultCodeAttribute);
                vehicleWarningUpload.setFaultMsg(faultMsg);
                break;

            case 0x04:
                vehicleWarningUpload.setWaterTempNum("50C");
                break;
            case 0x05:
                vehicleWarningUpload.setVoltageNum("36.12V");
                break;
        }
        byte[] data = vehicleWarningUpload.encoded();
        System.out.println(dataTool.bytes2hex(data));
    }

    @Test
    public void testLoop(){
        vehicleWarningUpload.setPackageLength((short) 0x1112);//4370
        vehicleWarningUpload.setCheckPackageLength((short) 0xEEED);//61165
        vehicleWarningUpload.setPackageID((byte) 0x01);//1

        vehicleWarningUpload.setObdID("NB1");//4E 42 31
        vehicleWarningUpload.setTripID(100);//100
        vehicleWarningUpload.setVID("123456");//31 32 33 34 35 36
        vehicleWarningUpload.setVIN("123456");//31 32 33 34 35 36
        vehicleWarningUpload.setGainDataTime("2015-12-03 16:06:30");

        vehicleWarningUpload.setWarningType((byte) 0x03);//报警类型 0x01：新故障码报警 0x02：碰撞报警 0x03：防盗报警 0x04：水温报警 0x05：充电电压报警（小于13.1v）0xF0: 拔下OBD报警 其他值: 无效

        //定位
        vehicleWarningUpload.setSpeed("80");
        vehicleWarningUpload.setCurrentDriveDistance("1203");
        vehicleWarningUpload.setLongitude("E123.008122");
        vehicleWarningUpload.setLatitude("N11.321234");
        vehicleWarningUpload.setDirect("30");
        vehicleWarningUpload.setPositionTime("2015-12-03 16:12:30");
        vehicleWarningUpload.setPositionMethod("2");

        switch (vehicleWarningUpload.getWarningType()){
            case 0x01:
                String[] faultCode = {"AAA","BBB","CCC"};
                String[] faultCodeAttribute = {"1","2","3"};
                String[] faultMsg = {"no_power","error","exception"};
                vehicleWarningUpload.setFaultNum((byte)3);
                vehicleWarningUpload.setFaultCode(faultCode);
                vehicleWarningUpload.setFaultCodeAttribute(faultCodeAttribute);
                vehicleWarningUpload.setFaultMsg(faultMsg);
                break;
            case 0x04:
                vehicleWarningUpload.setWaterTempNum("50C");
                break;
            case 0x05:
                vehicleWarningUpload.setVoltageNum("36.12V");
                break;
        }
        vehicleWarningUpload.setCheckSum((short) 0x0060);
        byte[] data = vehicleWarningUpload.encoded();
        VehicleWarningUpload vs = vehicleWarningUpload.decoded(data);
        vs.print();
    }
}
