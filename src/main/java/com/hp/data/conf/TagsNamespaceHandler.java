package com.hp.data.conf;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * protocol标签加载器，由spring进行回调加载标签类
 */
public class TagsNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser("protocol", new ProtocolParser());
    }
}  