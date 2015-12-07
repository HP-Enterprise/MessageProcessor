package com.hp.data.protocol;


import com.hp.data.core.AbstractConversion;
import com.hp.data.core.PackageElement;
import com.hp.data.exception.ConversionException;
import com.hp.data.util.DataBuilder;
import com.hp.data.util.PackageEntityManager;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.Map;

/**
 * 蓝度数据解析实现类
 */
@Component
public class ConversionLanDu extends AbstractConversion {
    /**
     * 用于从字节码中获取数据包定义的唯一标识
     * @param buffer    字节码缓存对象
     * @return  唯一标识
     */
    @Override
    public String getPackageKey(ByteBuffer buffer){
        DataBuilder db=DataBuilder.build(buffer);
        String head=String.valueOf(db.moveTo(0).getUInt16BE());
        String applicationId=String.valueOf(db.moveTo(9).getUInt8());
        String messageId=String.valueOf(db.moveTo(10).getUInt8());
        return head+"_"+applicationId+"_"+messageId;
    }
    /**
     * 在bean--byteBuffer的过程中在转换成byteBuffer之前用于重构类似head、length、checkSum等属性的值
     * @param pkgMap    bean对象中现有属性的键值对
     * @param eleList   包定义集合
     * @return  重构完成后的属性的键值对
     */
    @Override
    public Map<String, Object> restructure(Map<String, Object> pkgMap, List<PackageElement> eleList) {
        short checkSum=0;
        Integer length=0;
        for(PackageElement pe:eleList){
            String name=pe.getName().trim();
            Object value=pkgMap.get(name);
            if("head".equals(name)){
                value=8995;
                pkgMap.put(name,value);
            }
            else if("testFlag".equals(name)){
                if(value==null){
                    value=(short)0;
                }
                pkgMap.put(name,value);
            }
            else if("length".equals(name)||"checkSum".equals(name)){
                continue;
            }
            else{
                length+=pe.getSize();
            }

            if(value==null) throw new ConversionException("属性"+name+"值为空，无法完成解析工作");
            checkSum^=PackageEntityManager.objectXor(value,pe.getSize());
        }
        pkgMap.put("length",length);
        Short lengthXor=PackageEntityManager.objectXor(length,2);
        checkSum^=lengthXor;
        pkgMap.put("checkSum",Short.valueOf(checkSum).byteValue());
        return pkgMap;
    }

}
