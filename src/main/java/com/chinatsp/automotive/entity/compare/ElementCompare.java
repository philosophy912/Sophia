package com.chinatsp.automotive.entity.compare;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ElementCompareTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/27 13:26
 **/
@Setter
@Getter
@ToString
public class ElementCompare extends BaseEntity {
    /**
     * 元素对比类型
     */
    private ElementCompareTypeEnum elementCompareType;
    /**
     * 安卓元素
     */
    private String element;
    /**
     * 超时时间
     */
    private Double timeout;
}
