package com.hp.data.core;

/**
 * 数据类型枚举类
 */
public enum DataType {
    BYTE,       //单字节，对应类型Byte.如果size>1则对应byte[]
    INT_8,      //单字节整数，对应类型Short
    INT_16,     //双字节整数，对应类型Integer
    INT_32,     //四字节整数，对应类型Long
    U_INT_8,    //无符号单字节整数，对应类型Short
    U_INT_16,   //无符号双字节整数，对应类型Integer
    U_INT_32,   //无符号四字节整数，对应类型Long
    STRING;      //字符串型，对应类型String。如果未设置size则按变长字符串处理
    public int getSize(){
        if(BYTE.equals(this)||INT_8.equals(this)||U_INT_8.equals(this))return 1;
        if(INT_16.equals(this)||U_INT_16.equals(this))return 2;
        if(INT_32.equals(this)||U_INT_32.equals(this))return 4;
        return 0;
    }
}
