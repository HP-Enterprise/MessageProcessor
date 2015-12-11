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
        byte[] data= { (byte)0xAA ,0x55 ,0x00 ,(byte)0x93 ,(byte)0xFF ,0x6C ,0x01 ,0x05 ,0x16 ,0x02 ,0x4E ,0x42 ,0x31 ,0x00 ,0x00 ,0x00 ,0x00 ,0x64 ,0x31 ,0x32 ,0x33 ,0x34 ,0x35 ,0x36 ,0x00 ,0x31
                ,0x32 ,0x33 ,0x34 ,0x35 ,0x36 ,0x00 ,0x32 ,0x30 ,0x31 ,0x35 ,0x2D ,0x31 ,0x32 ,0x2D ,0x30 ,0x39 ,0x20 ,0x31 ,0x30 ,0x3A ,0x30 ,0x36 ,0x3A ,0x33 ,0x30 ,0x00 ,0x01 ,0x38
                ,0x30 ,0x00 ,0x31 ,0x32 ,0x30 ,0x33 ,0x00 ,0x45 ,0x31 ,0x32 ,0x33 ,0x2E ,0x30 ,0x30 ,0x38 ,0x31 ,0x32 ,0x32 ,0x2C ,0x4E ,0x31 ,0x31 ,0x2E ,0x33 ,0x32 ,0x31 ,0x32 ,0x33 ,0x34
                ,0x2C ,0x33 ,0x30 ,0x2C ,0x32 ,0x30 ,0x31 ,0x35 ,0x2D ,0x31 ,0x32 ,0x2D ,0x30 ,0x33 ,0x20 ,0x31 ,0x36 ,0x3A ,0x31 ,0x32 ,0x3A ,0x33 ,0x30 ,0x2C ,0x32 ,0x00 ,0x03 ,0x41 ,0x41
                ,0x41 ,0x00 ,0x31 ,0x00 ,0x6E ,0x6F ,0x5F ,0x70 ,0x6F ,0x77 ,0x65 ,0x72 ,0x00 ,0x42 ,0x42 ,0x42 ,0x00 ,0x32 ,0x00 ,0x65 ,0x72 ,0x72 ,0x6F ,0x72 ,0x00 ,0x43 ,0x43 ,0x43
                ,0x00 ,0x33 ,0x00 ,0x65 ,0x78 ,0x63 ,0x65 ,0x70 ,0x74 ,0x69 ,0x6F ,0x6E ,0x00 ,0x00 ,(byte)0x91};
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
            case 0x01://149个：AA 55 00 93 FF 6C 01 05 16 02 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 39 20 31 30 3A 30 36 3A 33 30 00 01 38 30 00 31 32 30 33 00 45 31 32 33 2E 30 30 38 31 32 32 2C 4E 31 31 2E 33 32 31 32 33 34 2C 33 30 2C 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 31 32 3A 33 30 2C 32 00 03 41 41 41 00 31 00 6E 6F 5F 70 6F 77 65 72 00 42 42 42 00 32 00 65 72 72 6F 72 00 43 43 43 00 33 00 65 78 63 65 70 74 69 6F 6E 00 00 91
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
