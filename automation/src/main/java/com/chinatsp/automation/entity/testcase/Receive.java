package com.chinatsp.automation.entity.testcase;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 空调屏接收测试类似于仪表
 * @author lizhe
 * @date 2020/5/26 16:11
 **/
@Setter
@Getter
@ToString
public class Receive extends Cluster {

    /**
     * 屏幕序号
     */
    private String displayIndex;
}
