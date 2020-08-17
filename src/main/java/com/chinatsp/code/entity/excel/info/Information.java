package com.chinatsp.code.entity.excel.info;

import com.chinatsp.code.entity.types.ElementAttributeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author lizhe
 * @date 2020/8/14 14:57
 **/
@Setter
@Getter
public class Information {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 要保存数据的名字
     */
    private String infoName;
    /**
     * 要保存的安卓元素
     */
    private String androidElementName;
    /**
     * 要保存元素的属性
     */
    private List<String> attributes;

}
