package com.hp.data;

import com.hp.data.bean.landu.VehicleDataUpload;
import com.hp.data.util.DataTool;
import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by zxZhang on 2015/12/1.
 */
public class DataToolTest {
    DataTool dataTool = new DataTool();

    @Test
    public void testReadStringZero(){
        byte[] data = {0x33 ,0x30 ,0x33 ,0x38 ,0x36 ,0x35 ,0x33 ,0x00,0x26, 0x56, 0x15, 0x2c };
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        String result = dataTool.readStringZero(dis);
        System.out.println(result);
    }
    @Test
    public void testWriteStringZero(){
        ByteBuffer bb = ByteBuffer.allocate(20);
        System.out.println(bb);
        String str="123456";
        dataTool.writeStringZero(bb,str,true);
        System.out.println(bb);
        for(int i=0;i<bb.position();i++){
            System.out.print(bb.array()[i]+" ");
        }
    }

    @Test
    public void testCheckSum(){
        String strReq = "AA 55 00 50 FF AF BF 05 16 03 49 4E 43 41 52 31 30 30 30 31 38 36 31 39 39 37 00 00 00 00 5D 31 38 30 00 4C 46 56 33 41 32 38 4B 38 46 33 30 33 38 36 35 33 00 56 31 2E 36 31 2E 30 30 00 56 31 2E 30 2E 30 00 56 33 2E 31 36 2E 35 37 00 FF 00 11 17 ";
        ByteBuffer byteBuffer = dataTool.getByteBuffer(strReq);
        byte[] bytes = dataTool.getBytesFromByteBuffer(byteBuffer);
        System.out.println(dataTool.checkSum(bytes));
    }

    @Test
    public void testBuildLocationString(){
        VehicleDataUpload v= new VehicleDataUpload();//{"80","1203","E123.008122","N11.321234","30","2014-01-23 10:59:05","1"};
        v.setSpeed("80");
        v.setCurrentDriveDistance("1203");
        v.setLongitude("E123.008122");
        v.setLatitude("N11.321234");
        v.setDirect("30");
        v.setPositionTime("2014-01-23 10:59:05");
        v.setPositionMethod("1");
        String locationMsg = dataTool.buildLocationString(v);
        System.out.println(locationMsg.length());//80 1203 E123.008122,N11.321234,30,2014-01-23 10:59:05,1
    }
}
