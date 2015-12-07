package com.hp.data;


import org.junit.Test;

public class StringTest {
    @Test
    public void testByteShow(){
        String a="0x23 0x23 0x00 0x0b 0x01 0x55 0xd2 0x0f 0xe7 0x13 0x02 0x55 0xbe 0xe2 0x58 0x00 0x25 ";
        String b=getByteString(a);
        System.out.println(b);
    }
    @Test
    public void testOthers(){
        byte[] abc=new byte[12];
        System.out.println(abc.getClass().getName());
    }
    private static String getByteString(String str){
        StringBuffer stringBuffer=new StringBuffer();
        String[] strArray=str.split(" ");
        for(String s:strArray){
            stringBuffer.append(s.substring(2).toUpperCase()).append(" ");
        }
        return stringBuffer.toString();
    }

}
