package com.chinatsp.code.entity.excel.elements;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/8/14 14:39
 **/
@Setter
@Getter
@ToString
public class AndroidElement {
    /**
     * 序号
     */
    private Integer id;
    /**
     * 元素名
     */
    private String elementName;
    /**
     * 定位符
     */
    private List<Map<String, String>> locators;
    /**
     * 备注
     */
    private String comments;
}
