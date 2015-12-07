package com.hp.data.core;


import com.hp.data.exception.ConversionException;
import com.hp.data.util.DataBuilder;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * 数据解析核心接口抽象实现类
 */
public abstract class AbstractConversion implements Conversion {
    private Map<String,List<PackageElement>> unitMap;

    /**
     * 抽象方法，用于从字节码中获取数据包定义的唯一标识
     * @param buffer    字节码缓存对象
     * @return  唯一标识
     */
    public abstract String getPackageKey(ByteBuffer buffer);

    /**
     * 抽象方法，在bean--byteBuffer的过程中在转换成byteBuffer之前用于重构类似head、length、checkSum等属性的值
     * @param pkgMap    bean对象中现有属性的键值对
     * @param eleList   包定义集合
     * @return  重构完成后的属性的键值对
     */
    public abstract Map<String,Object> restructure(Map<String,Object> pkgMap,List<PackageElement> eleList);
    /**
     * 将一段16进制字节码ByteButter解析成为装载实际数据的中间类DataPackage
     * @param buffer    字节码缓存对象
     * @return  数据中间类对象
     */
    @Override
    public DataPackage generate(ByteBuffer buffer) {
        String key=this.getPackageKey(buffer);
        DataPackage dp=new DataPackage(key);
        List<PackageElement> list=this.unitMap.get(key);
        DataBuilder builder=DataBuilder.build(buffer);
        for(PackageElement pe:list){
            DataType dataType=pe.getDataType();
            String property=pe.getName();
            int size=pe.getSize();
            Object value=getValue(dataType, builder, size);
            dp.put(property, value);
        }
        return dp;
    }
    /**
     * 将装载实际数据的中间类DataPackage解析成为一段16进制字节码ByteButter
     * @param pkg   数据中间类对象
     * @return  字节码缓存对象
     */
    @Override
    public ByteBuffer generate(DataPackage pkg) {
        List<PackageElement> eleList= this.unitMap.get(pkg.getKey());
        pkg.putDataInstance(this.restructure(pkg.getDataInstance(),eleList));
        DataBuilder db=DataBuilder.build();
        for(PackageElement pe:eleList){
            DataType dataType=pe.getDataType();
            String property=pe.getName();
            int size=pe.getSize();
            Object value=pkg.get(property);
            putValue(dataType, db, value,size);
        }
        return db.buffer();
    }

    /**
     * 从字节码缓存对象中获取一个值
     * @param dataType  数据类型
     * @param builder   字节码构建对象
     * @param size      获取值所占字节数
     * @return  值
     */
    private Object getValue(DataType dataType,DataBuilder builder,int size){
        if(dataType.equals(DataType.STRING)){
            if(size>0) {
                return builder.getString(size);
            }
            else{
                return builder.getString();
            }
        }
        if(dataType.equals(DataType.U_INT_8)) return builder.getUInt8();
        if(dataType.equals(DataType.U_INT_16))
            if(size>2){
                int num=size/2;//共有多少个u_int_16
                Integer[] numbers = new Integer[num];
                for (int i = 0; i < num; i++) {
                    numbers[i] =  builder.getUInt16BE();
                }
                return numbers;
            }else{
                return builder.getUInt16BE();
            }
        if(dataType.equals(DataType.U_INT_32)) return builder.getUInt32BE();
        if(dataType.equals(DataType.INT_8)) return builder.getInt8();
        if(dataType.equals(DataType.INT_16)) return builder.getInt16BE();
        if(dataType.equals(DataType.INT_32)) return builder.getInt32BE();
        if(dataType.equals(DataType.BYTE)){
            if(size>1) {
                return builder.getByte(size);
            }
            else{
                return builder.getByte();
            }

        }
        return null;
    }

    /**
     * 向字节码缓存对象中写入一个值
     * @param dataType  数据类型
     * @param builder   字节码构建对象
     * @param value     值
     * @param size      写入值所占字节数
     */
    private void putValue(DataType dataType,DataBuilder builder,Object value,int size){
        if(dataType.equals(DataType.STRING)) {
            if(size>0) {
                builder.putString(castTo(String.class, value),size);
            }
            else{
                builder.putString(castTo(String.class, value));
            }
        }
        if(dataType.equals(DataType.U_INT_8)) builder.putUInt8BE(castTo(Short.class,value));
        if(dataType.equals(DataType.U_INT_16))
            if(size>2){
                builder.putUInt16BE(castTo(Integer[].class,value));
            }else{
                builder.putUInt16BE(castTo(Integer.class,value));
            }

        if(dataType.equals(DataType.U_INT_32)) builder.putUInt32BE(castTo(Long.class, value));
        if(dataType.equals(DataType.INT_8)) builder.putInt8BE(castTo(Short.class, value));
        if(dataType.equals(DataType.INT_16)) builder.putInt16BE(castTo(Integer.class, value));
        if(dataType.equals(DataType.INT_32)) builder.putInt32BE(castTo(Integer.class, value));
        if(dataType.equals(DataType.BYTE)) {
            if(size>1){
                builder.putByte(castTo(byte[].class, value));
            }
            else{
                builder.putByte(castTo(Byte.class, value));
            }
        }
    }

    /**
     * 类型转换
     * @param castClass 要转换的类型
     * @param target    转换对象
     * @param <T>       转换对象的泛型类
     * @return  转换完成后的对象
     */
    private <T> T castTo(Class<T> castClass,Object target){
        try{
            return castClass.cast(target);
        }
        catch (ClassCastException e){
            e.printStackTrace();

            throw new ConversionException("类型转换错误，需要类型["+castClass.getName()+"],得到类型["+target.getClass().getName()+"]");
        }
    }


    public Map<String, List<PackageElement>> getUnitMap() {
        return unitMap;
    }

    public void setUnitMap(Map<String, List<PackageElement>> unitMap) {
        this.unitMap = unitMap;
    }

}
