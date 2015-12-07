package com.hp.data;

import com.hp.data.bean.tbox.*;
import com.hp.data.util.PackageEntityManager;
import com.hp.data.core.Conversion;
import com.hp.data.core.DataPackage;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.ByteBuffer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class TBoxTest {
    @Autowired
    private Conversion conversionTBox;
    @Before
    public void setUp() {}
    @After
    public void tearDown() {}
    @Test
    public void testTBOX_RegisterReq() {
       String byteString="23 23 00 4C 00 56 0A 3C C4 13 01 33 35 32 32 35 35 30 36 30 31 31 36 32 32 39 01 02 03 00 01 00 01 C4 3C 0A 56 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 00 01 00 00 00 00 01 60 ";
       standardTest(byteString,RegisterReq.class);

       RegisterReq req=new RegisterReq();
        req.setSendingTime(1444812349l);
        req.setApplicationID((short) 19);
        req.setMessageID((short) 1);
        req.setEventID(1444812349l);
        req.setVin("12345678919991234");
        req.setSerialNumber("123456789199");//12位
        req.setImei("355065053311001");
        req.setProtocolVersionNumber((short) 0);
        req.setTripID(12);
        req.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        req.setReserved(0);
        req.setDbcVersion("00000");
        req.setParamVersion("00000");
        req.setSwVersion("00000");

        DataPackage dp=new DataPackage("8995_19_1");
        dp.fillBean(req);
        ByteBuffer bb=conversionTBox.generate(dp);
        String str=PackageEntityManager.getByteString(bb);
        System.out.println(str);
        RegisterReq rr=this.conversionTBox.generate(bb).loadBean(RegisterReq.class);
        PackageEntityManager.printEntity(rr);
    }
    @Test
    public void testTBOX_RegisterResp() {
        RegisterResp resp=new RegisterResp();
        resp.setSendingTime(1234567l);
        resp.setApplicationID((short)19);
        resp.setMessageID((short)2);
        resp.setEventID(123456l);
        resp.setRegisterResult((short)(1));
        DataPackage dp=new DataPackage("8995_19_2");
        dp.fillBean(resp);
        ByteBuffer bb=conversionTBox.generate(dp);
        String str=PackageEntityManager.getByteString(bb);
        System.out.println(str);
        RegisterResp rr=this.conversionTBox.generate(bb).loadBean(RegisterResp.class);
        PackageEntityManager.printEntity(rr);
    }


    @Test
    public void testTBOX_BuildRemoteControlPreconditionReq() {
        RemoteControlPreconditionReq hr=new RemoteControlPreconditionReq();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 49);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setEventID((long) 1444812349);

        DataPackage dpw=new DataPackage("8995_49_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        RemoteControlPreconditionReq bean=dp.loadBean(RemoteControlPreconditionReq.class);
        PackageEntityManager.printEntity(bean);
    }
    @Test
    public void testTBOX_RemoteControlPreconditionResp() {
        RemoteControlPreconditionResp hr=new RemoteControlPreconditionResp();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 49);//>>>
        hr.setMessageID((short) 2);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);
        hr.setEventID((long) 1444812349);

        hr.setTime(1443151834l);
        hr.setAmbientAirTemperature((short) 60);
        hr.setFuelLevel(1000);
        hr.setTempIntern((short) 65);
        hr.setAverageConsumptionTripA(300);
        hr.setAverageConsumptionTripB(350);
        hr.setAverageSpeedTripA((short) 80);
        hr.setAverageSpeedTripB((short) 80);
        hr.setSesam_clamp_stat((byte) 170);
        hr.setStat_remote_start((byte) 170);
        hr.setGearLevelPositon((byte) 170);
        hr.setVehicleSpeed(9000);
        hr.setStateOfChargeACM_Crash_Status((byte) 170);
        hr.setBcm_Stat_Door_Flap(new byte[]{(byte) 170, (byte) 170});
        hr.setBcm_Stat_Central_Lock((byte) 170);
        hr.setStat_lin_2((byte) 170);
        hr.setEpb_status((byte) 170);
        hr.setStat_lin_1((byte) 170);
        hr.setScm_button_status(new byte[]{(byte) 170, (byte) 170, (byte) 170});
        hr.setScm_cruise_control((byte)170);
        hr.setScm_wiper(new byte[]{(byte) 170, (byte) 170});
        hr.setPreconditionRespTime((short)5);


        DataPackage dpw=new DataPackage("8995_49_2");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        RemoteControlPreconditionResp bean=dp.loadBean(RemoteControlPreconditionResp.class);
        PackageEntityManager.printEntity(bean);
    }
    @Test
    public void testTBOX_BuildRemoteControlCmd() {
        RemoteControlCmd hr=new RemoteControlCmd();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 49);//>>>
        hr.setMessageID((short) 3);//>>>
        hr.setEventID((long) 1444812349);
        hr.setRemoteControlType(4);
        hr.setAcTemperature((short)22);

        DataPackage dpw=new DataPackage("8995_49_3");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        RemoteControlCmd bean=dp.loadBean(RemoteControlCmd.class);
        PackageEntityManager.printEntity(bean);
    }
    @Test
    public void testTBOX_BuildRemoteControlAck() {
        RemoteControlAck hr=new RemoteControlAck();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 49);//>>>
        hr.setMessageID((short) 4);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);
        hr.setEventID((long) 1444812349);

        hr.setRemoteControlAck((short) 1);

        DataPackage dpw=new DataPackage("8995_49_4");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        RemoteControlAck bean=dp.loadBean(RemoteControlAck.class);
        PackageEntityManager.printEntity(bean);
    }
    @Test
    public void testTBOX_BuildRemoteControlRst() {
        RemoteControlRst hr=new RemoteControlRst();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 49);//>>>
        hr.setMessageID((short) 5);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);
        hr.setEventID((long) 1444812349);

        hr.setRemoteControlAck((short) 0);
        hr.setRemoteControlTime((short) 2);

        DataPackage dpw=new DataPackage("8995_49_5");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        RemoteControlAck bean=dp.loadBean(RemoteControlAck.class);
        PackageEntityManager.printEntity(bean);
    }

    ////////////////////////
    @Test
    public void testTBOX_ActiveHandle() {
        String byteString="23 23 00 3D 01 56 04 B7 1E 12 01 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 00 00 00 00 00 00 00 55 BE E2 58 31 32 33 34 35 36 37 38 39 31 39 39 31 32 33 34 35 36 37 38 39 31 39 39 39 31 32 33 34 88 ";
        standardTest(byteString, ActiveReq.class);
        String byteStr="23 23 00 21 01 56 04 BF DA 12 03 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 00 00 00 00 00 00 00 55 BE E2 58 00 67 ";
        standardTest(byteStr, ActiveResult.class);

    }
    @Test
    public void  testTBOX_RemoteWakeUpReq() {
        //远程唤醒测试
        String byteString="23 23 00 4C 01 56 04 AD 8C 14 01 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 00 00 00 00 00 00 00 55 BE E2 58 31 32 33 34 35 36 37 38 39 31 39 39 31 32 33 34 35 36 37 38 39 31 39 39 39 31 32 33 34 30 30 30 30 30 30 30 30 30 30 30 30 30 30 30 47 ";
        standardTest(byteString, RemoteWakeUpReq.class);

    }
    @Test
    public void test_BuildDiaRequest() {
        //电检测试

        DiaRequest hr=new DiaRequest();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 17);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);
        hr.setTestTime(1443151834l);
        hr.setSerialNumber("123456123456");
        hr.setServerCommTest((short) 1);
        hr.setSdTest((short) 1);

        hr.setLedTest((short) 1);
        hr.setResetBatteryMapArrayTest((short) 1);
        hr.setGpsTest((short) 1);
        hr.setGprsTest((short) 1);
        hr.setCanActionTest((short) 1);
        hr.setFarmTest((short) 1);

        hr.setDiaReportDataSize((short) 8);
        hr.setEventID((long) 1444812349);

        DataPackage dpw=new DataPackage("8995_17_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        DiaRequest bean=dp.loadBean(DiaRequest.class);
        PackageEntityManager.printEntity(bean);

    }

    @Test
    public void test_BuildSleepReq() {
        SleepReq hr=new SleepReq();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 39);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);
        hr.setEventID((long) 1444812349);

        DataPackage dpw=new DataPackage("8995_39_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        SleepReq bean=dp.loadBean(SleepReq.class);
        PackageEntityManager.printEntity(bean);
    }

    @Test
    public void test_BuildSleepResp() {
        SleepResp hr=new SleepResp();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 39);//>>>
        hr.setMessageID((short) 2);//>>>
        hr.setEventID((long) 1444812349);

        DataPackage dpw=new DataPackage("8995_39_2");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        SleepResp bean=dp.loadBean(SleepResp.class);
        PackageEntityManager.printEntity(bean);
    }


    @Test
    public void test_HeartbeatReq() {
        //心跳测试
        String byteString="23 23 00 20 01 55 D2 0F E7 26 01 00 00 00 00 00 00 00 00 00 00 11 00 00 00 00 00 00 00 00 00 00 00 55 BE E2 58 29 ";
        standardTest(byteString, HeartbeatReq.class);
        }



    @Test
    public void test_handleParmSetAck(){
        String byteString="23 23 00 3B 01 56 24 99 71 52 02 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 01 00 00 00 01 00 00 56 1E 16 3D 0D 01 02 03 04 05 06 07 08 09 0A 0B 0C 0D 00 00 00 00 00 00 00 00 00 00 00 00 00 AF ";
        standardTest(byteString, PramSetupAck.class);

    }
    @Test
    public void test_RegularReportMes(){
        //测试固定数据保存
        String byteString="23 23 00 3D 01 56 04 BF DA 21 01 31 32 33 34 35 36 37 38 39 30 31 32 33 34 35 01 00 00 00 01 00 00 00 0A 00 0A 0A 00 0A 00 0A 01 00 01 64 31 32 33 34 35 31 32 33 34 35 00 0A 00 00 C0 A8 01 0B 23 28 16 ";
        standardTest(byteString, RegularReportMes.class);
    }
    @Test
    public void test_BuildRealTimeReportMes(){
        RealTimeReportMes hr=new RealTimeReportMes();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 34);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setIsLocation((short) 0);//00000000表示有效北纬东经
        hr.setLatitude(7745l);
        hr.setLongitude(29215l);
        hr.setSpeed(1567);
        hr.setHeading(234);
        hr.setFuelOil(99);
        hr.setAvgOilA(123);
        hr.setAvgOilB(134);
        hr.setServiceIntervall(1234);
        hr.setLeftFrontTirePressure((short) 100);
        hr.setLeftRearTirePressure((short) 101);
        hr.setRightFrontTirePressure((short) 102);
        hr.setRightRearTirePressure((short) 103);
        hr.setWindowInformation((short) 15);//00001111开开关关
        hr.setVehicleTemperature((short) 65);
        hr.setVehicleOuterTemperature((short) 67);
        hr.setDoorInformation((short) 15);


        DataPackage dpw=new DataPackage("8995_34_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        RealTimeReportMes bean=dp.loadBean(RealTimeReportMes.class);
        PackageEntityManager.printEntity(bean);
    }



    @Test
    public void test_BuildRegularReportMes(){
        RegularReportMes hr=new RegularReportMes();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 33);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setFrequencyForRealTimeReport(10);
        hr.setFrequencyForWarningReport(10);
        hr.setFrequencyHeartbeat((short) 10);
        hr.setTimeOutForTerminalSearch(10);
        hr.setTimeOutForServerSearch(10);
        hr.setVehicleType((short) 1);
        hr.setVehicleModels(1);
        hr.setMaxSpeed((short) 100);
        hr.setHardwareVersion("12345");
        hr.setSoftwareVersion("12345");
        hr.setFrequencySaveLocalMedia(10);
        hr.setEnterpriseBroadcastAddress(new byte[]{(byte) 0, (byte) 0, (byte) 192, (byte) 168, (byte) 1, (byte) 11});
        hr.setEnterpriseBroadcastPort(9000);


        DataPackage dpw=new DataPackage("8995_33_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        RegularReportMes bean=dp.loadBean(RegularReportMes.class);
        PackageEntityManager.printEntity(bean);
    }
    @Test
    public void test_BuildWarningMes(){
        WarningMessage hr=new WarningMessage();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 36);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setIsLocation((short) 0);//00000000表示有效北纬东经
        hr.setLatitude(7745l);
        hr.setLongitude(29215l);
        hr.setSpeed(1567);
        hr.setHeading(234);

        hr.setSrsWarning((byte) 1);//01000000 触发
        hr.setAtaWarning((byte) 1);//01000000 触发
        hr.setSafetyBeltCount((short) 2);
        Integer[] speeds=new Integer[150];
        for(int i=0;i<150;i++){
            speeds[i] = 100;
        }
        System.out.println(">>>>>>>>"+speeds);
        hr.setVehicleSpeedLast(speeds);



        DataPackage dpw=new DataPackage("8995_36_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        WarningMessage bean=dp.loadBean(WarningMessage.class);
        PackageEntityManager.printEntity(bean);
    }

    @Test
    public void test_BuildFailureMes(){
        FailureMessage hr=new FailureMessage();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 40);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setIsLocation((short) 0);//00000000表示有效北纬东经
        hr.setLatitude(7745l);
        hr.setLongitude(29215l);
        hr.setSpeed(1567);
        hr.setHeading(234);
        hr.setInfo1((byte) 52);
        hr.setInfo2((byte) 53);
        hr.setInfo3((byte) 54);
        hr.setInfo4((byte) 57);
        hr.setInfo5((byte) 170);
        hr.setInfo6((byte) 170);
        hr.setInfo7((byte) 170);
        hr.setInfo8((byte) 170);

        DataPackage dpw=new DataPackage("8995_40_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        FailureMessage bean=dp.loadBean(FailureMessage.class);
        PackageEntityManager.printEntity(bean);
    }

    @Test
    public void test_BuildResendRealTimeMes(){
        DataResendRealTimeMes hr=new DataResendRealTimeMes();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 35);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setIsLocation((short) 0);//00000000表示有效北纬东经
        hr.setLatitude(7745l);
        hr.setLongitude(29215l);
        hr.setSpeed(1567);
        hr.setHeading(234);
        hr.setFuelOil(99);
        hr.setAvgOilA(123);
        hr.setAvgOilB(134);
        hr.setServiceIntervall(1234);
        hr.setLeftFrontTirePressure((short) 100);
        hr.setLeftRearTirePressure((short) 101);
        hr.setRightFrontTirePressure((short) 102);
        hr.setRightRearTirePressure((short) 103);
        hr.setWindowInformation((short) 15);//00001111开开关关
        hr.setVehicleTemperature((short) 65);
        hr.setVehicleOuterTemperature((short) 67);
        hr.setDoorInformation((short) 15);


        DataPackage dpw=new DataPackage("8995_35_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        DataResendRealTimeMes bean=dp.loadBean(DataResendRealTimeMes.class);
        PackageEntityManager.printEntity(bean);
    }

    @Test
    public void test_BuildResendWarningMes(){
        DataResendWarningMes hr=new DataResendWarningMes();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 37);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setIsLocation((short) 0);//00000000表示有效北纬东经
        hr.setLatitude(7745l);
        hr.setLongitude(29215l);
        hr.setSpeed(1567);
        hr.setHeading(234);

        hr.setSrsWarning((byte) 1);//01000000 触发
        hr.setAtaWarning((byte) 1);//01000000 触发
        hr.setSafetyBeltCount((short) 2);
        Integer[] speeds=new Integer[150];
        for(int i=0;i<150;i++){
            speeds[i] = 100;
        }
        System.out.println(">>>>>>>>" + speeds);
        hr.setVehicleSpeedLast(speeds);


        DataPackage dpw=new DataPackage("8995_37_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        DataResendWarningMes bean=dp.loadBean(DataResendWarningMes.class);
        PackageEntityManager.printEntity(bean);
    }

    @Test
    public void test_BuildResendFailureMes(){
        DataResendFailureData hr=new DataResendFailureData();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 41);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setIsLocation((short) 0);//00000000表示有效北纬东经
        hr.setLatitude(7745l);
        hr.setLongitude(29215l);
        hr.setSpeed(1567);
        hr.setHeading(234);

        hr.setInfo1((byte) 170);
        hr.setInfo2((byte) 170);
        hr.setInfo3((byte) 170);
        hr.setInfo4((byte) 170);
        hr.setInfo5((byte) 81);
        hr.setInfo6((byte) 82);
        hr.setInfo7((byte) 83);
        hr.setInfo8((byte) 84);

        DataPackage dpw=new DataPackage("8995_41_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        DataResendFailureData bean=dp.loadBean(DataResendFailureData.class);
        PackageEntityManager.printEntity(bean);
    }

    @Test
    public void test_BuildDiagnosticCommandCmd(){
        DiagnosticCommandCmd hr=new DiagnosticCommandCmd();
        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 66);//>>>
        hr.setMessageID((short) 1);//>>>
        hr.setEventID((long) 1444812349);

        DataPackage dpw=new DataPackage("8995_66_1");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        DiagnosticCommandCmd bean=dp.loadBean(DiagnosticCommandCmd.class);
        PackageEntityManager.printEntity(bean);

    }

    @Test
    public void test_BuildDiagnosticCommanAck(){
        DiagnosticCommanAck hr=new DiagnosticCommanAck();

        hr.setTestFlag((short) 0);
        hr.setSendingTime(1443151834l);
        hr.setApplicationID((short) 66);//>>>
        hr.setMessageID((short) 2);//>>>
        hr.setImei("123456789012345");
        hr.setProtocolVersionNumber((short) 1);
        hr.setVehicleID(new byte[]{(byte) 0, (byte) 0});
        hr.setTripID(1);
        hr.setReserved(0);

        hr.setEventID((long) 1444812349);
        hr.setDiagData(new byte[]{(byte) 170, (byte) 170});

        DataPackage dpw=new DataPackage("8995_66_2");//>>>
        dpw.fillBean(hr);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);

        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteStr);
        DataPackage dp=conversionTBox.generate(bb);
        DiagnosticCommanAck bean=dp.loadBean(DiagnosticCommanAck.class);
        PackageEntityManager.printEntity(bean);

    }

    /**
     * 标准测试方法
     * 首先将传入字符串解析成传入的数据包实体类，然后打印该实体类，然后再将这个实体类解析成ByteBuffer，最后判断该ByteBuffer是否和传入的byte字符串一致
     * @param byteString    16进制字符串
     * @param dataEntityClass   数据包实体类类型
     * @param <T>   数据包实体类泛型
     */
    private <T> void standardTest(String byteString,Class<T> dataEntityClass){
        ByteBuffer bb=PackageEntityManager.getByteBuffer(byteString);
        //读
        DataPackage dp=conversionTBox.generate(bb);
        T bean=dp.loadBean(dataEntityClass);
        String key = dp.getKey();
        PackageEntityManager.printEntity(bean);
        //写
        DataPackage dpw=new DataPackage(key);
        dpw.fillBean(bean);
        ByteBuffer bbw=conversionTBox.generate(dpw);
        String byteStr=PackageEntityManager.getByteString(bbw);
        System.out.println(byteStr);
        System.out.println(byteString);
        Assert.assertTrue(byteStr.trim().equals(byteString.trim()));
    }
}