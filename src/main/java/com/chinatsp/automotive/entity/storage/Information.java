package com.chinatsp.automotive.entity.storage;

import com.chinatsp.automotive.entity.BaseEntity;
import com.chinatsp.automotive.enumeration.ElementAttributeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author lizhe
 * @date 2020/8/27 11:31
 **/
@Setter
@Getter
@ToString
public class Information extends BaseEntity {
    /**
     * 要保存的元素名
     */
    private String element;
    /**
     * 要保存的属性值内容
     */
    private ElementAttributeEnum elementAttribute;


}
