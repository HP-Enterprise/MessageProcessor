package com.hp.data.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 数据实体，标识某个类是一个可以承载数据包数据的实体类
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface DataEntity{
    public String key();
}
