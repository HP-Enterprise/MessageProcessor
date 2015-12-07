package com.hp.data.core;

import java.nio.ByteBuffer;

/**
 * 数据解析中心核心接口
 */
public interface Conversion {
    /**
     * 将一段16进制字节码ByteButter解析成为装载实际数据的中间类DataPackage
     * @param buffer    字节码缓存对象
     * @return  数据中间类对象
     */
    public DataPackage generate(ByteBuffer buffer);

    /**
     * 将装载实际数据的中间类DataPackage解析成为一段16进制字节码ByteButter
     * @param pkg   数据中间类对象
     * @return  字节码缓存对象
     */
    public ByteBuffer generate(DataPackage pkg);
}
