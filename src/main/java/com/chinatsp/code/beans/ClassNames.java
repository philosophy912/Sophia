package com.chinatsp.code.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lizhe
 * @date 2020-08-31 22:47
 */
@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "classname")
public class ClassNames {
    private String testCase;
    private String screenShotAction;
    private String screenOperationAction;
    private String elementAction;
    private String relayAction;
    private String batteryAction;
    private String information;
    private String can;
    private String element;
    private String canCompare;
    private String imageCompare;
    private String informationCompare;
    private String elementCompare;
    private String common;
}
