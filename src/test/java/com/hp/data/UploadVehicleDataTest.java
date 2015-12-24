package com.hp.data;

import com.hp.data.bean.landu.UploadVehicleData;
import com.hp.data.util.DataTool;
import org.junit.Test;

/**
 * Created by zxZhang on 15/12/24.
 */
public class UploadVehicleDataTest {
    UploadVehicleData uploadVehicleData = new UploadVehicleData();
    DataTool dataTool = new DataTool();

    @Test
    public void testUploadVehicleDataDecoded(){
        byte[] data= {(byte) 0xAA,0x55 ,0x00 , (byte) 0x80, (byte) 0xFF,0x7F ,0x31 ,0x05 ,0x16 ,0x0A ,0x49 ,0x4E ,0x43 ,0x41 ,0x52 ,0x31 ,0x30 ,0x30 ,0x30 ,0x32 ,0x34 ,0x37 ,0x30 ,0x36 ,0x38 ,0x32
                ,0x00 ,0x00 ,0x00 ,0x07 ,0x69 ,0x34 ,0x33 ,0x00 ,0x4C ,0x53 ,0x47 ,0x53 ,0x41 ,0x35 ,0x32 ,0x4D ,0x32 ,0x41 ,0x59 ,0x31 ,0x38 ,0x36 ,0x33 ,0x33 ,0x30 ,0x00 ,0x32 ,0x30 ,0x31 ,0x35 ,0x2D ,0x31 ,0x32 ,0x2D ,0x32 ,0x34 ,0x20 ,0x31 ,0x34 ,0x3A ,0x31 ,0x37 ,0x3A ,0x33 ,0x30 ,0x00 ,0x03 ,0x34 ,0x00 ,0x38 ,0x33 ,0x37 ,0x39 ,0x00 ,0x45 ,0x31 ,0x31 ,0x36 ,0x2E ,0x34 ,0x37 ,0x35 ,0x30 ,0x35 ,0x31 ,0x2C ,0x4E ,0x34 ,0x30 ,0x2E ,0x30 ,0x31 ,0x32 ,0x37 ,0x30 ,0x35 ,0x2C ,0x39 ,0x38 ,0x2C ,0x32 ,0x30 ,0x31 ,0x35 ,0x2D ,0x31 ,0x32 ,0x2D ,0x32 ,0x34 ,0x20 ,0x30 ,0x36 ,0x3A ,0x31 ,0x37 ,0x3A ,0x32 ,0x39 ,0x2C ,0x32 ,0x00 ,0x19 ,0x11 };
        UploadVehicleData u = uploadVehicleData.decoded(data);
        u.print();
    }

    @Test
    public void testUploadVehicleDataEncoded(){
        uploadVehicleData.setPackageID((byte) 49);

        uploadVehicleData.setObdID("123456");
        uploadVehicleData.setTripID(1897);
        uploadVehicleData.setVID("43");
        uploadVehicleData.setVIN("1234567M2AY186330");
        uploadVehicleData.setGainDataTime("2015-12-24 14:17:30");
        uploadVehicleData.setDataType((byte) 5);
        if (uploadVehicleData.getDataType()>=1 && uploadVehicleData.getDataType()<=4) {
            uploadVehicleData.setSpeed("4");
            uploadVehicleData.setCurrentDriveDistance("8379");
            uploadVehicleData.setLongitude("E116.475051");
            uploadVehicleData.setLatitude("N10.012705");
            uploadVehicleData.setDirect("98");
            uploadVehicleData.setPositionTime("2015-12-24 06:17:29");
            uploadVehicleData.setPositionMethod("2");
        }
        byte[] data = uploadVehicleData.encoded();
        System.out.println(dataTool.bytes2hex(data));
    }

    @Test
    public void testLoop(){
        uploadVehicleData.setPackageID((byte) 49);

        uploadVehicleData.setObdID("123456");
        uploadVehicleData.setTripID(1897);
        uploadVehicleData.setVID("43");
        uploadVehicleData.setVIN("1234567M2AY186330");
        uploadVehicleData.setGainDataTime("2015-12-24 14:17:30");
        uploadVehicleData.setDataType((byte) 3);
        if (uploadVehicleData.getDataType()>=1 && uploadVehicleData.getDataType()<=4) {
            uploadVehicleData.setSpeed("4");
            uploadVehicleData.setCurrentDriveDistance("8379");
            uploadVehicleData.setLongitude("E116.475051");
            uploadVehicleData.setLatitude("N10.012705");
            uploadVehicleData.setDirect("98");
            uploadVehicleData.setPositionTime("2015-12-24 06:17:29");
            uploadVehicleData.setPositionMethod("2");
        }
        byte[] data = uploadVehicleData.encoded();
        System.out.println(dataTool.bytes2hex(data));
        UploadVehicleData u = uploadVehicleData.decoded(data);
        u.print();
    }
}
