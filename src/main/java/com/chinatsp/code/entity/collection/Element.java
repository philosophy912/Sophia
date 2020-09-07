package com.chinatsp.code.entity.collection;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.enumeration.AndroidLocatorTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author lizhe
 * @date 2020/8/27 12:17
 **/
@Setter
@Getter
@ToString
public class Element extends BaseEntity {
    /**
     * 定位符
     * 至多支持两个定位符，超过一种的时候以换行符区格
     * 当两个定位符的时候表示在第一个定位符下面查找第二个定位符或者在第一个定位符中滑动查找第二个定位符
     * 以xx=xx,yy=yy的方式实现多重定位，如：
     * id=test,classname=com.android.layout
     */
    private List<Map<AndroidLocatorTypeEnum, String>> locators;
}
