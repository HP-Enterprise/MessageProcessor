package com.hp.data.core;


import com.hp.data.util.PackageEntityManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据包中间对象，用于存放键值式数据，提供这些数据和数据实体类互转的接口
 */
public class DataPackage {
    private String key;

    /**
     * 构造器，每一个数据包中间对象都必须有一个key，这个key可在解析类中获得包定义信息
     * @param key   包定义唯一标识
     */
    public DataPackage(String key){
        this.key=key;
    }
    private Map<String,Object> propertyValueMap=new HashMap<String, Object>();
    void put(String propertyName,Object value){
        this.propertyValueMap.put(propertyName,value);
    }
    Object get(String propertyName){
        return this.propertyValueMap.get(propertyName);
    }
    void putDataInstance(Map<String,Object> newMap){
        this.propertyValueMap.putAll(newMap);
    }
    Map<String,Object> getDataInstance(){
        Map<String,Object> newMap=new HashMap<String, Object>();
        newMap.putAll(this.propertyValueMap);
        return newMap;
    }

    /**
     * 方法名转属性名
     * @param methodName    方法名
     * @return  属性名
     */
    private String methodName2PropertyName(String methodName){
        String temp=methodName.substring(3);
        return temp.substring(0,1).toLowerCase()+temp.substring(1);
    }

    /**
     * 将自身数据填入一个数据实体类对象
     * @param beanClass 数据实体类对象类型
     * @param <T>   数据实体类对象泛型
     * @return  数据实体类对象
     */
    public <T> T loadBean(Class<T> beanClass){
        try {
            T bean=beanClass.newInstance();
            List<Method> setters= PackageEntityManager.getSetters(beanClass);
            for(Method m:setters){
                String property=methodName2PropertyName(m.getName());
                Object value=propertyValueMap.get(property);
                if(value==null)continue;
                m.invoke(bean,value);
            }
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将一个数据实体类对象的数据抽出并保存为键值式数据
     * @param bean  数据实体类对象
     */
    public void fillBean(Object bean){
        Class<?> beanClass=bean.getClass();
        DataEntity ann=beanClass.getAnnotation(DataEntity.class);
        String key=ann.key();
        List<Method> getters= PackageEntityManager.getGetters(beanClass);
        try {
            for(Method m:getters){
                String propertyName=methodName2PropertyName(m.getName());
                Object value=m.invoke(bean);
                this.propertyValueMap.put(propertyName,value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public String getKey() {
        return key;
    }
}
