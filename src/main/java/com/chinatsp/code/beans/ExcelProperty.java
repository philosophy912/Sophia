package com.chinatsp.code.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/9/16 13:57
 **/
@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "config")
public class ExcelProperty {
    /**
     * 实体属性以及表头中文相关
     */
    private Map<String, String> attribute = new HashMap<>();
    /**
     * 实体类名以及Sheet中文相关
     */
    private Map<String, String> classname = new HashMap<>();
}
