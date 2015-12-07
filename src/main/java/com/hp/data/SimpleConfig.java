package com.hp.data;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration("com.hp.dataCenter.simpleConfig")
@ImportResource("classpath:spring-config.xml")
public class SimpleConfig {

}
