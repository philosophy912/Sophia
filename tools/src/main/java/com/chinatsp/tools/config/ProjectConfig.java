package com.chinatsp.tools.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author lizhe
 * @date 2020/6/3 9:37
 **/
@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectConfig {
    /**
     * 项目名称，跟生成出来的DBC有关系
     */
    private String projectName;
    /**
     * DBC文件的名字， 必须放到运行路径下
     */
    private String dbcName;
    /**
     * 测试用例excel的名字， 必须放到运行路径下
     */
    private String testCaseName;
    /**
     * 目前仅支持类型 cluster和AirCondition
     */
    private String type;
    /**
     * 屏幕尺寸的宽
     */
    private Integer width;
    /**
     * 屏幕尺寸的高
     */
    private Integer height;
    /**
     * automotive最低版本号
     */
    private String automotive;
    /**
     * 串口端口
     */
    private String socPort;
}
