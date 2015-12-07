package com.hp.data.core;


import com.hp.data.exception.ConversionException;

/**
 * 包定义类，存放包中一个元素的定义信息
 */
public class PackageElement {
    private String name;
    private DataType dataType;
    private int size;

    public PackageElement(String eleName, String eleType,int size) {
        this.name=eleName;
        this.dataType=DataType.valueOf(eleType);
        if(size>0) {
            this.size = size;
        }
        else{
            this.size=this.dataType.getSize();
            if(this.size==0)throw new ConversionException("不合法的元素长度0[name:"+name+",dataType:"+this.dataType+"]");
        }
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
