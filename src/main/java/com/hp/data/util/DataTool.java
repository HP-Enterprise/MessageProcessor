package com.hp.data.util;

import com.hp.data.bean.landu.VehicleDataUpload;
import com.hp.data.bean.landu.VehicleWarningUpload;
import com.hp.data.exception.ConversionException;
import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import static io.netty.buffer.Unpooled.buffer;

/**
 * Created by zxZhang on 2015/12/1.
 */
public class DataTool {
    private static final String DEFAULT_CHARSET="GBK";
    private boolean readMode = false;
    private ByteBuffer buf;

    private Logger _logger = LoggerFactory.getLogger(DataTool.class);
    //校验和验证
    public  boolean checkSum(byte[] bytes){
        int sum=0;
        for(int i=2;i<bytes.length-2;i++){
          sum += bytes[i] & 0xFF;
        }
        String hexStr = Integer.toHexString(bytes[bytes.length-2])+ Integer.toHexString(bytes[bytes.length -1]);
        _logger.info(">>checkSum:" + Integer.toHexString(sum) + "<>" + hexStr);
        return hexStr == Integer.toHexString(sum);
    }
    //计算校验和并返回
    public int getCheckSum(ByteBuf bb){
        int sum=0;
        byte[] bytes = getBytesFromByteBuf(bb);
        for(int i=2;i<bytes.length;i++){
            sum += bytes[i] & 0xFF;
        }
        return sum;
    }

    public  ByteBuf getByteBuf(String str){
        //根据16进制字符串得到ByteBuf对象(netty)
        ByteBuf bb= buffer(1024);

        String[] command=str.split(" ");
        byte[] abc=new byte[command.length];
        for(int i=0;i<command.length;i++){
            abc[i]=Integer.valueOf(command[i],16).byteValue();
        }
        bb.writeBytes(abc);
        return bb;
    }
    public  ByteBuffer getByteBuffer(String str){
        ByteBuffer bb= ByteBuffer.allocate(1024);
        String[] command=str.split(" ");
        byte[] abc=new byte[command.length];
        for(int i=0;i<command.length;i++){
            abc[i]=Integer.valueOf(command[i],16).byteValue();
        }
        bb.put(abc);
        bb.flip();
        return bb;
    }
    public  static byte[] getBytesFromByteBuf(ByteBuf buf){
        //基于netty
        byte[] result = new byte[buf.readableBytes()];
        buf.readBytes(result, 0, buf.readableBytes());
        buf.resetReaderIndex();
        return result;
    }
    public  byte[] getBytesFromByteBuffer(ByteBuffer buff){
        byte[] result = new byte[buff.remaining()];
        if (buff.remaining() > 0) {
            buff.get(result, 0, buff.remaining());
        }
        return result;
    }

    /**
     *截取ByteBuffer有效的字节数组
     * @param bb ByteBuffer posion,limit指示有效的数组字段
     * @return 截取bb中有效的字节数组
     */
    public byte[] getValidBytesFromByteBuffer(ByteBuffer bb){
        int position = bb.position();
        int limit = bb.limit();
        if(limit<= position){
            _logger.info("无法确定有效字节数组，请检查参数是否正确!");
            return null;
        }else {
            byte[] bytes = new byte[limit-position];
            for(int i=0; i<limit-position;i++){
                bytes[i] = bb.get();
            }
            return bytes;
        }
    }

    /**
     * 读出字符串，去掉末尾的0
     * @param dis
     * @return
     */
    public String readStringZero(DataInputStream dis){
        String sb = "";
        try {
            while(dis!= null){
                byte b= dis.readByte();
                if(b!='\000' && b!=','){//','=0x2C  '\000'=0x00
                    char c = (char) (b&0xFF);
                    String temp = String.valueOf(c);
                    sb = sb+temp;
                } else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConversionException("无法读出以0结尾的字符串");
        }
        return sb;
    }
    public String readStringZero(ByteBuf bb) {
        String sb = "";
        try {
            while(bb!= null){
                byte b= bb.readByte();
                if(b!='\000' && b!=','){//','=0x2C  '\000'=0x00
                    char c = (char) (b&0xFF);
                    String temp = String.valueOf(c);
                    sb = sb+temp;
                } else{
                    break;
                }
            }
        } catch (Exception e ){
            e.printStackTrace();
            throw new ConversionException("无法读出以0结尾的字符串");
        }
        return sb;
    }

    /**
     * 写入字符串，末尾补0
     * @param str   字符串
     */
    public void writeStringZero(DataOutputStream dos, String str, boolean addZero){
        try {
            byte[] strBytes = str.getBytes(DEFAULT_CHARSET);
            dos.write(strBytes);
            if(addZero){
                dos.writeByte(0x00);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConversionException("字符串"+str+"0无法转换成byte数组");
        }
    }
    public void writeStringZero(ByteBuffer bb, String str, boolean addZero){
        try {
            byte[] strBytes = str.getBytes(DEFAULT_CHARSET);
            bb.put(strBytes);
            if(addZero){
                bb.put((byte) 0x00);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConversionException("字符串"+str+"0无法转换成byte数组");
        }
    }
    public void writeStringZero(ByteBuf bb, String str, boolean addZero) {
        try {
            byte[] strBytes = str.getBytes(DEFAULT_CHARSET);
            bb.writeBytes(strBytes);
            if(addZero){
                bb.writeByte((byte) 0x00);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ConversionException("字符串"+str+"0无法转换成byte数组");
        }
    }

    /**
     * 构建定位信息字符串
     */
    public String buildLocationString(String speed,String currentDriveDistance,String longitude,String latitude,String direct,String positionTime,String positionMethod) {
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

    public  String bytes2hex(byte[] bArray) {
        //字节数据转16进制字符串
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return getSpaceHex(sb.toString());
    }
    public  String getSpaceHex(String str){
        //将不带空格的16进制字符串加上空格
        String re="";
        String regex = "(.{2})";
        re = str.replaceAll (regex, "$1 ");
        return re;
    }



}
