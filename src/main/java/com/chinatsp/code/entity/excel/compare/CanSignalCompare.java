package com.chinatsp.code.entity.excel.compare;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/14 15:04
 **/
@Setter
@Getter
@ToString
public class CanSignalCompare {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 对比函数名
     */
    private String name;
    /**
     * 消息ID
     */
    private Long messageId;
    /**
     * 信号名称
     */
    private String signalName;
    /**
     * 期望值
     */
    private Long expectValue;
    /**
     * 出现次数
     */
    private Integer count;
    /**
     * 是否精确对比
     */
    private Boolean exactCompare;
    /**
     * 备注
     */
    private String comments;
}
