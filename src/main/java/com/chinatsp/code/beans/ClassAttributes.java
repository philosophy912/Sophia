package com.chinatsp.code.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lizhe
 * @date 2020-08-31 22:44
 */
@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "attribute")
public class ClassAttributes {
    private String id;
    private String name;
    private String comment;
    private String batteryType;
    private String batteryOperationType;
    private String values;
    private String repeatTimes;
    private String curveFile;
    private String element;
    private String slideTimes;
    private String relayOperationType;
    private String channelIndex;
    private String operationActionType;
    private String screenIndex;
    private String points;
    private String continueTimes;
    private String deviceTpeEnum;
    private String count;
    private String imageName;
    private String isArea;
    private String signals;
    private String locators;
    private String params;
    private String messageId;
    private String signalName;
    private String expectValue;
    private String exact;
    private String elementCompareType;
    private String timeout;
    private String compareType;
    private String templateLight;
    private String templateDark;
    private String positions;
    private String similarity;
    private String isGray;
    private String threshold;
    private String origin;
    private String target;
    private String elementAttributes;
    private String testCaseType;
    private String moduleName;
    private String preConditionDescription;
    private String stepsDescription;
    private String expectDescription;
}
