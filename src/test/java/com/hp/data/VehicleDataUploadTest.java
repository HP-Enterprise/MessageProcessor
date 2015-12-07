package com.hp.data;

import com.hp.data.bean.landu.VehicleDataUpload;
import com.hp.data.util.DataTool;
import org.junit.Test;

/**
 * Created by zxZhang on 2015/12/2.
 */
public class VehicleDataUploadTest {
    VehicleDataUpload vehicleDataUpload = new VehicleDataUpload();
    DataTool dataTool = new DataTool();

    @Test
    public void testDataUploadDecoded(){
        byte[] data= { (byte)0xAA,0x55,0x11,0x12,(byte)0xEE,(byte)0xED,0x01,0x05,0x16,0x01,0x4E,0x42,0x31,0x00,0x00,0x00,0x00,0x64,0x31,0x32,0x33,0x34,0x35,0x36,0x00,0x31,0x32,0x33,0x34,0x35,0x36,0x00,0x32,0x30,0x31,0x35,0x2D,0x31,0x32,0x2D,0x30
                ,0x33,0x20,0x31,0x36,0x3A,0x30,0x36,0x3A,0x33,0x30,0x00,0x01,0x33,0x30,0x2E,0x30,0x35,0x56,0x00,0x38,0x30,0x00,0x31,0x32,0x30,0x33,0x00,0x45,0x31,0x32,0x33,0x2E,0x30,0x30,0x38,0x31,0x32,0x32,0x2C,0x4E,0x31,0x31
                ,0x2E,0x33,0x32,0x31,0x32,0x33,0x34,0x2C,0x33,0x30,0x2C,0x32,0x30,0x31,0x35,0x2D,0x31,0x32,0x2D,0x30,0x33,0x20,0x31,0x36,0x3A,0x31,0x32,0x3A,0x33,0x30,0x2C,0x32,0x00,0x00,0x60};
        VehicleDataUpload v = vehicleDataUpload.decoded(data);
        v.print();
    }

    @Test
    public void testDataUploadEncoded(){
        vehicleDataUpload.setPackageID((byte) 0x01);

        vehicleDataUpload.setRemoteMachineID("NB1");
        vehicleDataUpload.setTripID(100);
        vehicleDataUpload.setVID("123456");
        vehicleDataUpload.setVIN("123456");
        vehicleDataUpload.setGainDataTime("2015-12-03 16:06:30");
        //测试1061不同的数据内容
        vehicleDataUpload.setDataAttrubute((byte) 0x01);
        switch (vehicleDataUpload.getDataAttrubute()){
            case 0x01://118:AA 55 00 71 FF 8E 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 01 33 30 2E 30 35 56 00 38 30 00 31 32 30 33 00 45 31 32 33 2E 30 30 38 31 32 32 2C 4E 31 31 2E 33 32 31 32 33 34 2C 33 30 2C 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 31 32 3A 33 30 2C 32 00 00 76
                vehicleDataUpload.setFireVoltage("30.05V");
                vehicleDataUpload.setSpeed("80");
                vehicleDataUpload.setCurrentDriveDistance("1203");
                vehicleDataUpload.setLongitude("E123.008122");
                vehicleDataUpload.setLatitude("N11.321234");
                vehicleDataUpload.setDirect("30");
                vehicleDataUpload.setPositionTime("2015-12-03 16:12:30");
                vehicleDataUpload.setPositionMethod("2");
                break;
            case 0x02://78个:AA 55 00 47 FF B8 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 02 00 03 00 01 41 41 41 00 00 02 42 42 42 42 00 00 03 43 43 43 43 43 00 00 4E
                vehicleDataUpload.setParameterNumber(3);
                int[] ids = {1,2,3};
                String[] strs = {"AAA","BBBB","CCCCC"};
                vehicleDataUpload.setDataID(ids);
                vehicleDataUpload.setDataIDContent(strs);
                break;
            case 0x03://158个:AA 55 00 9A FF 65 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 03 FF FF 00 00 AA 55 FF FF 00 00 FF FF 00 64 03 30 03 E8 00 00 27 10 31 03 E9 00 00 27 11 32 03 EA 00 00 27 12 00 0C 00 20 00 0F 00 00 00 05 64 38 30 00 31 32 30 33 00 45 31 32 33 2E 30 30 38 31 32 32 2C 4E 31 31 2E 33 32 31 32 33 34 2C 33 30 2C 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 31 32 3A 33 30 2C 32 00 00 9E
                vehicleDataUpload.setEngineRunTime(65535);
                vehicleDataUpload.setDriveGap(43605);
                vehicleDataUpload.setAverageOil(65535);
                vehicleDataUpload.setTotalDriveGap(65535);
                vehicleDataUpload.setTotalAverageOil(100);

                vehicleDataUpload.setDataGroupNumber((byte) 0x03);
                byte[] bs = {0x30,0x31,0x32};//0,1,2
                int[] is={1000,1001,1002};
                int[] iss ={10000,10001,10002};
                vehicleDataUpload.setInstallSpeed(bs);
                vehicleDataUpload.setTotalTime(is);
                vehicleDataUpload.setTotalGap(iss);

                vehicleDataUpload.setQuickUpSpeed(12);
                vehicleDataUpload.setQuickDownSpeed(32);
                vehicleDataUpload.setQuickTurnNumber(15);
                vehicleDataUpload.setOverSpeedTime(5);
                vehicleDataUpload.setMaxSpeed((byte)100);

                vehicleDataUpload.setSpeed("80");
                vehicleDataUpload.setCurrentDriveDistance("1203");
                vehicleDataUpload.setLongitude("E123.008122");
                vehicleDataUpload.setLatitude("N11.321234");
                vehicleDataUpload.setDirect("30");
                vehicleDataUpload.setPositionTime("2015-12-03 16:12:30");
                vehicleDataUpload.setPositionMethod("2");
                break;
            case 0x04://62个:AA 55 00 39 FF C6 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 04 33 36 2E 31 32 56 00 00 3E
                vehicleDataUpload.setBatteryVoltage("36.12V");
                break;
            case 0x05://55个:AA 55 00 33 FF CC 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 05 00 37
                break;
        }
        byte[] data = vehicleDataUpload.encoded();
        System.out.println(dataTool.bytes2hex(data));
    }

    @Test
    public void testLoop(){
        vehicleDataUpload.setPackageLength((short) 0x1112);//4370
        vehicleDataUpload.setCheckPackageLength((short) 0xEEED);//61165
        vehicleDataUpload.setPackageID((byte) 0x01);//1

        vehicleDataUpload.setRemoteMachineID("NB1");//4E 42 31
        vehicleDataUpload.setTripID(100);//100
        vehicleDataUpload.setVID("123456");//31 32 33 34 35 36
        vehicleDataUpload.setVIN("123456");//31 32 33 34 35 36
        vehicleDataUpload.setGainDataTime("2015-12-03 16:06:30");

        vehicleDataUpload.setDataAttrubute((byte) 0x03);//数据属性标识 0x01：发动机点火时 0x02：发动机运行中 0x03：发动机熄火时 0x04：发动机熄火后 0x05：车辆不能检测
        switch (vehicleDataUpload.getDataAttrubute()){
            case 0x01://118:AA 55 11 12 EE ED 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 01 33 30 2E 30 35 56 00 38 30 00 31 32 30 33 00 45 31 32 33 2E 30 30 38 31 32 32 2C 4E 31 31 2E 33 32 31 32 33 34 2C 33 30 2C 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 31 32 3A 33 30 2C 32 00 00 60
                vehicleDataUpload.setFireVoltage("30.05V");
                vehicleDataUpload.setSpeed("80");
                vehicleDataUpload.setCurrentDriveDistance("1203");
                vehicleDataUpload.setLongitude("E123.008122");
                vehicleDataUpload.setLatitude("N11.321234");
                vehicleDataUpload.setDirect("30");
                vehicleDataUpload.setPositionTime("2015-12-03 16:12:30");
                vehicleDataUpload.setPositionMethod("2");
                break;
            case 0x02://78个:AA 55 11 12 EE ED 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 02 00 03 00 01 41 41 41 00 00 02 42 42 42 42 00 00 03 43 43 43 43 43 00 00 60
                vehicleDataUpload.setParameterNumber(3);
                int[] ids = {1,2,3};
                String[] strs = {"AAA","BBBB","CCCCC"};
                vehicleDataUpload.setDataID(ids);
                vehicleDataUpload.setDataIDContent(strs);
                break;
            case 0x03://156个:AA 55 11 12 EE ED 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 03 FF FF 00 00 AA 55 FF FF 00 00 FF FF 02 32 03 E8 00 00 27 10 3C 03 E9 00 00 27 11 00 0C 00 20 00 0F 00 00 00 05 64 38 30 00 31 32 30 33 00 45 31 32 33 2E 30 30 38 31 32 32 2C 4E 31 31 2E 33 32 31 32 33 34 2C 33 30 2C 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 31 32 3A 33 30 2C 32 00 00 60
                vehicleDataUpload.setEngineRunTime(65535);
                vehicleDataUpload.setDriveGap(43605);
                vehicleDataUpload.setAverageOil(65535);
                vehicleDataUpload.setTotalDriveGap(65535);
                vehicleDataUpload.setTotalAverageOil(100);

                vehicleDataUpload.setDataGroupNumber((byte) 0x03);
                byte[] bs = {0x30,0x31,0x32};//0,1,2
                int[] is={1000,1001,1002};
                int[] iss ={10000,10001,10002};
                vehicleDataUpload.setInstallSpeed(bs);
                vehicleDataUpload.setTotalTime(is);
                vehicleDataUpload.setTotalGap(iss);

                vehicleDataUpload.setQuickUpSpeed(12);
                vehicleDataUpload.setQuickDownSpeed(32);
                vehicleDataUpload.setQuickTurnNumber(15);
                vehicleDataUpload.setOverSpeedTime(5);
                vehicleDataUpload.setMaxSpeed((byte)100);

                vehicleDataUpload.setSpeed("80");
                vehicleDataUpload.setCurrentDriveDistance("1203");
                vehicleDataUpload.setLongitude("E123.008122");
                vehicleDataUpload.setLatitude("N11.321234");
                vehicleDataUpload.setDirect("30");
                vehicleDataUpload.setPositionTime("2015-12-03 16:12:30");
                vehicleDataUpload.setPositionMethod("2");
                break;
            case 0x04://62个:AA 55 11 12 EE ED 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 04 33 36 2E 31 32 56 00 00 60
                vehicleDataUpload.setBatteryVoltage("36.12V");
                break;
            case 0x05://55个:AA 55 11 12 EE ED 01 05 16 01 4E 42 31 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 32 30 31 35 2D 31 32 2D 30 33 20 31 36 3A 30 36 3A 33 30 00 05 00 37
                break;
        }
        vehicleDataUpload.setCheckSum((short) 0x0060);
        byte[] data = vehicleDataUpload.encoded();
        VehicleDataUpload vs = vehicleDataUpload.decoded(data);
        vs.print();
    }
}
