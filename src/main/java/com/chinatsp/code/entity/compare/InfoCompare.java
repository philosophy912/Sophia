package com.chinatsp.code.entity.compare;

import com.chinatsp.code.entity.elements.AndroidElement;
import com.chinatsp.code.entity.info.Information;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/14 16:14
 **/
@Setter
@Getter
@ToString
public class InfoCompare {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 比较函数名
     */
    private String functionName;
    /**
     * 相关信息
     */
    private String informationName;
    /**
     * 安卓元素
     */
    private String androidElementName;


}
