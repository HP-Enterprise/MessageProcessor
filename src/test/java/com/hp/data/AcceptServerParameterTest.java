package com.hp.data;

import com.hp.data.bean.landu.AcceptServerParameter;
import com.hp.data.util.DataTool;
import org.junit.Test;

/**
 * Created by zxZhang on 2015/12/7.
 */
public class AcceptServerParameterTest {
    DataTool dataTool = new DataTool();

    private AcceptServerParameter acceptServerParameter = new AcceptServerParameter();

    @Test
    public void testAcceptServerParameterDecode(){
        byte[] data = {(byte) 0xAA,0x55 ,0x00 ,0x3E , (byte) 0xFF, (byte) 0xC1,0x01 ,0x05 ,0x16 ,0x03 ,0x42 ,0x4D ,0x57 ,0x5F ,0x58 ,0x38 ,0x00 ,0x00 ,0x00 ,0x00 ,0x64 ,0x31 ,0x32 ,0x33 ,0x34 ,0x35 ,0x36 ,0x00
                ,0x31 ,0x32 ,0x33 ,0x34 ,0x35 ,0x36 ,0x00 ,0x56 ,0x31 ,0x2E ,0x32 ,0x33 ,0x00 ,0x56 ,0x30 ,0x2E ,0x30 ,0x30 ,0x2E ,0x30 ,0x30 ,0x00 ,0x56 ,0x31 ,0x32 ,0x2E ,0x33 ,0x34 ,0x2E
                ,0x35 ,0x36 ,0x00 , (byte) 0xFF,0x56 ,0x00 ,0x3C};
        AcceptServerParameter a = acceptServerParameter.decoded(data);
        a.print();
    }

    @Test
    public void testAcceptServerParameterEncode(){
        acceptServerParameter.setPackageID((byte) 0x01);

        acceptServerParameter.setRemoteMachineID("BMW_X8");
        acceptServerParameter.setTripID(100);
        acceptServerParameter.setVID("123456");
        acceptServerParameter.setVIN("123456");

        acceptServerParameter.setHardwareVersion("V1.23");
        acceptServerParameter.setFirmwareVersion("V0.00.00");
        acceptServerParameter.setSoftwareVersion("V12.34.56");
        acceptServerParameter.setDiagnosisType((byte) 0xFF);

        acceptServerParameter.setInitCode((byte) 0x56);
        byte[] data = acceptServerParameter.encoded();//AA 55 00 3E FF C1 01 05 16 03 42 4D 57 5F 58 38 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 56 31 2E 32 33 00 56 30 2E 30 30 2E 30 30 00 56 31 32 2E 33 34 2E 35 36 00 FF 56 00 3C
        System.out.println(dataTool.bytes2hex(data));
    }

    @Test
    public void testLoop(){
        acceptServerParameter.setPackageID((byte) 0x01);

        acceptServerParameter.setRemoteMachineID("BMW_X8");
        acceptServerParameter.setTripID(100);
        acceptServerParameter.setVID("123456");
        acceptServerParameter.setVIN("123456");

        acceptServerParameter.setHardwareVersion("V1.23");
        acceptServerParameter.setFirmwareVersion("V0.00.00");
        acceptServerParameter.setSoftwareVersion("V12.34.56");
        acceptServerParameter.setDiagnosisType((byte) 0xFF);

        acceptServerParameter.setInitCode((byte) 0x56);
        byte[] data = acceptServerParameter.encoded();//AA 55 00 3E FF C1 01 05 16 03 42 4D 57 5F 58 38 00 00 00 00 64 31 32 33 34 35 36 00 31 32 33 34 35 36 00 56 31 2E 32 33 00 56 30 2E 30 30 2E 30 30 00 56 31 32 2E 33 34 2E 35 36 00 FF 56 00 3C
        System.out.println(dataTool.bytes2hex(data));
        AcceptServerParameter a = acceptServerParameter.decoded(data);
        a.print();
    }
}
