package com.chinatsp.code.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
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

}
