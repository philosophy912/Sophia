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
 * @date 2020/9/11 16:13
 **/
@Setter
@Getter
@ToString
@Component
@PropertySource(value = "classpath:config.yml")
@ConfigurationProperties(prefix = "configures")
public class Configures {
    @Value("${maxDisplay}")
    private String maxDisplay;
    @Value("${maxVoltage}")
    private String maxVoltage;
    @Value("${minVoltage}")
    private String minVoltage;
    @Value("${SocPort}")
    private String SocPort;
    @Value("${SocBaudRate}")
    private String SocBaudRate;
    @Value("${McuPort}")
    private String McuPort;
    @Value("${McuBaudRate}")
    private String McuBaudRate;
    @Value("${maxWidth}")
    private String maxWidth;
    @Value("${maxHeight}")
    private String maxHeight;
    @Value("${screenShotTempPath}")
    private String screenShotTempPath;
    @Value("${templateImagePath}")
    private String templateImagePath;
    @Value("${dbcFile}")
    private String dbcFile;

}
