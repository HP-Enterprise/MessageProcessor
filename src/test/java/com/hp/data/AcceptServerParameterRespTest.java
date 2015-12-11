package com.hp.data;

import com.hp.data.bean.landu.AcceptServerParameterResp;
import com.hp.data.util.DataTool;
import org.junit.Test;

/**
 * Created by zxZhang on 2015/12/7.
 */
public class AcceptServerParameterRespTest {
    DataTool dataTool = new DataTool();
    private AcceptServerParameterResp resp = new AcceptServerParameterResp();

    @Test
    public void testDecoded(){
        byte[] data = {(byte) 0xAA,0x55 ,0x00 , (byte) 0x84, (byte) 0xFF,0x7B ,0x01 ,0x05 ,0x16 ,0x03 ,0x32 ,0x30 ,0x31 ,0x35 ,0x2D ,0x31 ,0x32 ,0x2D ,0x30 ,0x37 ,0x20 ,0x31 ,0x37 ,0x3A ,0x32 ,0x37
                ,0x3A ,0x33 ,0x30 ,0x00 ,0x02 ,0x36 , (byte) 0xF0,0x05 ,0x31 ,0x32 ,0x33 ,0x34 ,0x35 ,0x36 ,0x00 , (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,0x32 ,0x2E ,0x30 ,0x54 ,0x00 ,0x03 ,0x31 ,0x32 ,0x37 ,0x2E
                ,0x30 ,0x2E ,0x30 ,0x2E ,0x31 ,0x00 ,0x1F , (byte) 0x90,0x31 ,0x32 ,0x37 ,0x2E ,0x30 ,0x2E ,0x30 ,0x2E ,0x32 ,0x00 ,0x1F , (byte) 0x91,0x31 ,0x32 ,0x37 ,0x2E ,0x30 ,0x2E ,0x30 ,0x2E
                ,0x33 ,0x00 ,0x1F , (byte) 0x92,0x04 ,0x00 ,0x1E ,0x3C ,0x64 ,0x03 ,0x00 ,0x64 ,0x00 ,0x0C ,0x00 ,0x04 ,0x78 ,0x00 ,0x08 ,0x00 ,0x6F , (byte) 0x85,0x03 , (byte) 0xFF, (byte) 0xFF,0x73 ,0x00 ,0x03
                ,0x7F ,0x73 ,0x74 ,0x03 ,0x00 ,0x08 ,0x00 , (byte) 0xFF,0x00 ,0x06 ,0x01 , (byte) 0xFF,0x00 ,0x09 ,0x02 , (byte) 0xFF,0x61 ,0x62 ,0x63 ,0x64 ,0x65 ,0x66 ,0x31 ,0x32 ,0x33 ,0x34 ,0x35 ,0x00
                ,0x00 , (byte) 0x82};
        AcceptServerParameterResp acceptServerParameterResp = resp.decoded(data) ;
        acceptServerParameterResp.print();

    }

    @Test
    public void testEncoded(){
        resp.setPackageID((byte) 0x01);
        resp.setCurrentTime("2015-12-07 17:27:30");
        resp.setActionParameterNum((byte) 0x02);
        resp.setInitCode((byte) 0x36);
        resp.setIsClearAction((byte) 0xF0);
        resp.setVehicleParameterNum((byte) 0x05);
        resp.setVID("123456");
        resp.setBrand((byte) 0xFF);
        resp.setSeries((byte) 0xFF);
        resp.setYearStyle((byte) 0xFF);
        resp.setDisplacementValue("2.0");
        resp.setTypeComposition("T");
        resp.setNetworkConfigNum((byte) 3);
        String[] str = {"127.0.0.1","127.0.0.2","127.0.0.3"};
        int[] port = {8080,8081,8082}         ;
        resp.setIps(str);
        resp.setPorts(port);
        resp.setSegmentNum((byte) 0x04);
        byte[] bytes= {0x00,0x1E,0x3C,0x64};
        resp.setMaxSpeeds(bytes);
        resp.setLocationParameterNum((byte) 0x03);
        resp.setLocationGap(100);
        resp.setLocationTime(12);
        resp.setGapAndTime((byte) 0x00);

        resp.setWarnNum((byte) 0x04);
        resp.setOverSpeedMinSpeed((byte) 120);
        resp.setOverSpeedMinTime(8);
        resp.setWarnWaterTemperature(111);
        resp.setWarnChargeVoltage((byte) 133);

        resp.setMisFireDataNum((byte) 0x03);
        resp.setMisFireCloseTime(0xFFFF);
        resp.setShutCriticalVoltage((byte) 0x73);
        resp.setMisFireVoltageDataTotal(3);
        byte[] b = {0x7F,0x73,0x74};
        resp.setMisFireBatteryVoltage(b);

        resp.setDataIDNum((byte) 3);
        int[] ints = {8,6,9};
        int[] idss = {0x00FF,0x01FF,0x02FF};
        resp.setSpaceTime(ints);
        resp.setDataID(idss);

        resp.setUpdateID("abcdef12345");
        byte[] data = resp.encoded();//134个：AA 55 00 84 FF 7B 01 05 16 03 32 30 31 35 2D 31 32 2D 30 37 20 31 37 3A 32 37 3A 33 30 00 02 36 F0 05 31 32 33 34 35 36 00 FF FF FF 32 2E 30 54 00 03 31 32 37 2E 30 2E 30 2E 31 00 1F 90 31 32 37 2E 30 2E 30 2E 32 00 1F 91 31 32 37 2E 30 2E 30 2E 33 00 1F 92 04 00 1E 3C 64 03 00 64 00 0C 00 04 78 00 08 00 6F 85 03 FF FF 73 00 03 7F 73 74 03 00 08 00 FF 00 06 01 FF 00 09 02 FF 61 62 63 64 65 66 31 32 33 34 35 00 00 82
        System.out.println(dataTool.bytes2hex(data));
    }

    @Test
    public void testLoop(){
        resp.setPackageID((byte) 0x01);
        resp.setCurrentTime("2015-12-07 17:27:30");
        resp.setActionParameterNum((byte) 0x02);
        resp.setInitCode((byte) 0x36);
        resp.setIsClearAction((byte) 0xF0);
        resp.setVehicleParameterNum((byte) 0x05);//0x00 0x05
        resp.setVID("123456");
        resp.setBrand((byte) 0xFF);
        resp.setSeries((byte) 0xFF);
        resp.setYearStyle((byte) 0xFF);
        resp.setDisplacementValue("2.0");
        resp.setTypeComposition("T");
        resp.setNetworkConfigNum((byte) 3);
        String[] str = {"127.0.0.1","127.0.0.2","127.0.0.3"};
        int[] port = {8080,8081,8082}         ;
        resp.setIps(str);
        resp.setPorts(port);
        resp.setSegmentNum((byte) 0x04);
        byte[] bytes= {0x00,0x1E,0x3C,0x64};
        resp.setMaxSpeeds(bytes);
        resp.setLocationParameterNum((byte) 0x03);//0x03 0x00
        resp.setLocationGap(0);//默认75
        resp.setLocationTime(0);////默认9
        resp.setGapAndTime((byte) 0x00);

        resp.setWarnNum((byte) 0x04);
        resp.setOverSpeedMinSpeed((byte) 120);
        resp.setOverSpeedMinTime(8);
        resp.setWarnWaterTemperature(111);
        resp.setWarnChargeVoltage((byte) 133);

        resp.setMisFireDataNum((byte) 0x03);
        resp.setMisFireCloseTime(0xFFFF);
        resp.setShutCriticalVoltage((byte) 0x73);
        resp.setMisFireVoltageDataTotal(3);
        byte[] b = {0x7F,0x73,0x74};
        resp.setMisFireBatteryVoltage(b);

        resp.setDataIDNum((byte) 3);
        int[] ints = {8,6,9};
        int[] idss = {0x00FF,0x01FF,0x02FF};
        resp.setSpaceTime(ints);
        resp.setDataID(idss);

        resp.setUpdateID("abcdef12345");
        byte[] data = resp.encoded();
        System.out.println(dataTool.bytes2hex(data));
        AcceptServerParameterResp acceptServerParameterResp = resp.decoded(data) ;
        acceptServerParameterResp.print();
    }
}
