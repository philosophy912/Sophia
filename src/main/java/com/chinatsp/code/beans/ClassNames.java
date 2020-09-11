package com.chinatsp.code.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 配置类，关联了Sheet页面，也就是说读取excel的时候，获取一个sheet页面是从这里获取的
 * 举例： testCase表示有一个Sheet的名字一定包含testcase，同时也表示这个Sheet对应的就是TestCase对象
 *
 * @author lizhe
 * @date 2020-08-31 22:47
 */
@Setter
@Getter
@ToString
@Component
@PropertySource(value = "classpath:config.yml")
@ConfigurationProperties(prefix = "classname")
public class ClassNames {
    @Value("${testCase}")
    private String testCase;
    @Value("${screenShotAction}")
    private String screenShotAction;
    @Value("${screenOperationAction}")
    private String screenOperationAction;
    @Value("${elementAction}")
    private String elementAction;
    @Value("${relayAction}")
    private String relayAction;
    @Value("${batteryAction}")
    private String batteryAction;
    @Value("${information}")
    private String information;
    @Value("${can}")
    private String can;
    @Value("${element}")
    private String element;
    @Value("${canCompare}")
    private String canCompare;
    @Value("${imageCompare}")
    private String imageCompare;
    @Value("${informationCompare}")
    private String informationCompare;
    @Value("${elementCompare}")
    private String elementCompare;
    @Value("${common}")
    private String common;
    @Value(("${configure}"))
    private String configure;

}
