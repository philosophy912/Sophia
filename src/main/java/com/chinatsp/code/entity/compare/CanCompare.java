package com.chinatsp.code.entity.compare;

import com.chinatsp.code.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/27 13:00
 **/
@Setter
@Getter
@ToString
public class CanCompare extends BaseEntity {
    /**
     * 信号名
     */
    private String signalName;
    /**
     * 期望值
     */
    private Long expectValue;
    /**
     * 出现次数
     */
    private Integer appearCount = 0;
    /**
     * 是否精确对比
     */
    private Boolean exact = false;
}
