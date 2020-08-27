package com.chinatsp.code.entity.compare;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.storage.Information;
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
    private Information origin;
    /**
     * 目标元素信息
     */
    private Information target;

}
