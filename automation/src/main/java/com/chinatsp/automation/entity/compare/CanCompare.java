package com.chinatsp.automation.entity.compare;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author lizhe
 * @date 2020/4/29 16:33
 **/
@Setter
@Getter
@ToString
public class CanCompare {
    /**
     * CAN信号ID
     */
    private String messageId;

    /**
     * CAN消息名字
     */
    private String signalName;

    /**
     * 期望CAN消息值
     */
    private String signalValue;

    /**
     * 检查帧数
     */
    private Integer frameCount;

    /**
     * 检查是否精确对比
     */
    private Boolean exact = true;
}
