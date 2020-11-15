package com.chinatsp.automotive.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.Map;

/**
 * 该类的作用主要用于关联Entity实体以及Excel的Sheet
 * 当添加Excel的表或者表头的时候，只需要编辑application.yml中的数据即可
 *
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
