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
 * @date 2020-08-31 22:44
 */
@Setter
@Getter
@ToString
@Component
@PropertySource(value = "classpath:config.yml")
@ConfigurationProperties(prefix = "attribute")
public class ClassAttributes {
    @Value("${id}")
    private String id;
    @Value("${name}")
    private String name;
    @Value("${comment}")
    private String comment;
    @Value("${batteryType}")
    private String batteryType;
    @Value("${batteryOperationType}")
    private String batteryOperationType;
    @Value("${values}")
    private String values;
    @Value("${repeatTimes}")
    private String repeatTimes;
    @Value("${curveFile}")
    private String curveFile;
    @Value("${element}")
    private String element;
    @Value("${slideTimes}")
    private String slideTimes;
    @Value("${relayOperationType}")
    private String relayOperationType;
    @Value("${channelIndex}")
    private String channelIndex;
    @Value("${operationActionType}")
    private String operationActionType;
    @Value("${screenIndex}")
    private String screenIndex;
    @Value("${points}")
    private String points;
    @Value("${continueTimes}")
    private String continueTimes;
    @Value("${deviceTpeEnum}")
    private String deviceTpeEnum;
    @Value("${count}")
    private String count;
    @Value("${imageName}")
    private String imageName;
    @Value("${isArea}")
    private String isArea;
    @Value("${signals}")
    private String signals;
    @Value("${locators}")
    private String locators;
    @Value("${params}")
    private String params;
    @Value("${messageId}")
    private String messageId;
    @Value("${signalName}")
    private String signalName;
    @Value("${expectValue}")
    private String expectValue;
    @Value("${exact}")
    private String exact;
    @Value("${elementCompareType}")
    private String elementCompareType;
    @Value("${timeout}")
    private String timeout;
    @Value("${compareType}")
    private String compareType;
    @Value("${templateLight}")
    private String templateLight;
    @Value("${templateDark}")
    private String templateDark;
    @Value("${positions}")
    private String positions;
    @Value("${similarity}")
    private String similarity;
    @Value("${isGray}")
    private String isGray;
    @Value("${threshold}")
    private String threshold;
    @Value("${origin}")
    private String origin;
    @Value("${target}")
    private String target;
    @Value("${elementAttributes}")
    private String elementAttributes;
    @Value("${testCaseType}")
    private String testCaseType;
    @Value("${moduleName}")
    private String moduleName;
    @Value("${preConditionDescription}")
    private String preConditionDescription;
    @Value("${stepsDescription}")
    private String stepsDescription;
    @Value("${expectDescription}")
    private String expectDescription;

}
