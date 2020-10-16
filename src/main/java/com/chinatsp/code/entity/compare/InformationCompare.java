package com.chinatsp.code.entity.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.storage.Information;
import com.chinatsp.code.enumeration.ElementAttributeEnum;
import com.chinatsp.code.enumeration.InformationCompareTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author lizhe
 * @date 2020/8/27 13:18
 **/
@Setter
@Getter
@ToString
public class InformationCompare extends BaseEntity {
    /**
     * 原始元素信息
     */
    private String element;
    /**
     * 元素属性
     */
    private ElementAttributeEnum elementAttribute;
    /**
     * 要对比的信息，可以填true/false或者text文本等
     */
    private String info;
    /**
     * 保存的信息
     */
    private String savedInformation;
    /**
     * 信息对比类型
     */
    private InformationCompareTypeEnum informationCompareType;

}
