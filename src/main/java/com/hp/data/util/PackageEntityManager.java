package com.hp.data.util;

import com.hp.data.core.DataEntity;
import com.hp.data.exception.ConversionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;

/**
 * 数据解析工具类
 */
public final class PackageEntityManager {
    private static Map<Class<?>,List<Method>> setters=new HashMap<Class<?>,List<Method>>();
    private static Map<Class<?>,List<Method>> getters=new HashMap<Class<?>,List<Method>>();

    /**
     * 静态初始化方法，用于解析对应包下面所有数据实体类的类信息
     */
    static{
        Set<Class<?>> set= ClassScanner.scan("com.hp.data.bean", DataEntity.class);
        for(Class<?> entityClass:set){
            Method[] methods=entityClass.getMethods();
            List<Method> setList=new ArrayList<Method>();
            List<Method> getList=new ArrayList<Method>();
            for(Method m:methods){
                String methodName=m.getName();
                if(methodName.indexOf("set")==0){
                    setList.add(m);
                }
                else if(methodName.indexOf("get")==0){
                    if("getClass".equals(m.getName()))continue;
                    getList.add(m);
                }
            }
            setters.put(entityClass,setList);
            getters.put(entityClass,getList);
        }
    }

    /**
     * 用标准格式打印一个数据实体类对象中数据信息
     * @param bean  数据实体类对象
     */
    public static void printEntity(Object bean){
        Class<?> entityClass=bean.getClass();
        if(getters.containsKey(entityClass)){
            System.out.println("\n对象"+entityClass.getName()+":");
            List<Method> getters=getGetters(entityClass);
            for(Method m:getters){
                try {
                    String nameStep1=m.getName().substring(3);
                    String nameStep2=nameStep1.substring(0,1).toLowerCase()+nameStep1.substring(1);
                    String propertyName=rightPad(nameStep2,20,' ');
                    Class<?> typeStep1=m.getReturnType();
                    String typeStep2=byte[].class.equals(typeStep1)?"byte[]":typeStep1.getName();
                    String type=rightPad(typeStep2,20,' ');
                    Object valueStep1=m.invoke(bean);
                    String valueStep2=valueStep1!=null?"[B".equals(valueStep1.getClass().getName())?getByteArrayString(valueStep1):valueStep1.toString():"";
                    String value=rightPad(valueStep2,20,' ');
                    String hexValue=getHexString(valueStep2,m.getReturnType(),true);
                    System.out.println(propertyName+" "+value+" "+type+" "+hexValue);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            throw new ConversionException("打印对象"+entityClass.getName()+"失败，要打印的对象必须在指定包下并由@DataEntity注释");
        }
    }

    /**
     * 计算一个值中所有字节的异或总值
     * @param value 要计算的值
     * @param size  所占字节数
     * @return  异或总值
     */
    public static short objectXor(Object value,int size){
        if(byte[].class.equals(value.getClass())){
            byte[] valueBytes=(byte[])value;
            byte result =valueBytes[0];
            for(int i=1;i<valueBytes.length;i++){
                result^=valueBytes[i];
            }
            return Byte.valueOf(result).shortValue();
        }
        else{
            String hex=getHexString(value.toString(),value.getClass(),false);
            String[] command=hex.split(" ");
            byte result=Integer.valueOf(command[0],16).byteValue();
            for(int i=1;i<command.length;i++){
                result^=Integer.valueOf(command[i],16).byteValue();
            }
            return Byte.valueOf(result).shortValue();
        }
    }

    private static String getByteArrayString(Object byteArray) {
        byte[] bytes= (byte[]) byteArray;
        return getByteString(ByteBuffer.wrap(bytes));
    }

    public static String getHexString(String value,Class<?> valueClass,boolean byteDetail){
        DataBuilder dataBuilder=DataBuilder.build();
        if(byte[].class.equals(valueClass)) {
            return value;
        }
        else if(Byte.class.equals(valueClass)) {
            Byte b=Byte.valueOf(value);
            String hexStr=Integer.toHexString(b).toUpperCase();
            if(hexStr.length()==1)hexStr="0"+hexStr;
            if(hexStr.length()!=2)hexStr=hexStr.substring(hexStr.length()-2);
            if(!byteDetail)return hexStr;
            String binaryStr=Integer.toBinaryString(b);
            String binaryString=binaryStr.length()>8?binaryStr.substring(binaryStr.length()-8):leftPad(binaryStr, 8, '0');
            return hexStr+"["+binaryString+"]";
        }
        else{
            dataBuilder.clear();
            if (String.class.equals(valueClass)) dataBuilder.putString(value, value.length());
            if (Short.class.equals(valueClass)) dataBuilder.putUInt8BE(Short.valueOf(value));
            if (Integer.class.equals(valueClass)) dataBuilder.putUInt16BE(Integer.valueOf(value));
            if (Long.class.equals(valueClass)) dataBuilder.putUInt32BE(Long.valueOf(value));
            return getByteString(dataBuilder.buffer());
        }
    }
    public static String rightPad(String text, int length, char c) {
        if(text.length()>length)text=text.substring(0,length-2)+"..";
        char[] array = new char[length];
        Arrays.fill(array, c);
        System.arraycopy(text.toCharArray(), 0, array, 0, text.length());
        return new String(array);
    }
    public static String leftPad(String text, int length, char c) {
        if(text.length()>length)text=text.substring(0,length-2)+"..";
        char[] array = new char[length];
        Arrays.fill(array, c);
        System.arraycopy(text.toCharArray(), 0, array,length-text.length(), text.length());
        return new String(array);
    }


    public static String getByteString(ByteBuffer bb){
        if(bb.position()>0)bb.flip();
        StringBuilder stringBuffer=new StringBuilder();
        for(int i=0;i<bb.limit();i++){
            String byteStr=Integer.toHexString(bb.get()).toUpperCase();
            if(byteStr.length()==1)byteStr="0"+byteStr;
            if(byteStr.length()!=2)byteStr=byteStr.substring(byteStr.length()-2);
            stringBuffer.append(byteStr).append(" ");
        }
        return stringBuffer.toString();
    }
    public static ByteBuffer getByteBuffer(String str){
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
    public static List<Method> getSetters(Class<?> entityClass){
        return setters.get(entityClass);
    }
    public static List<Method> getGetters(Class<?> entityClass){
        return getters.get(entityClass);
    }

}
