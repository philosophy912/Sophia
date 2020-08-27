package com.chinatsp.code.entity.storage;

import com.chinatsp.code.entity.BaseEntity;
import com.chinatsp.code.entity.collection.Element;
import com.chinatsp.code.enumeration.ElementAttributeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

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
    private Element element;
    /**
     * 要保存的属性值内容
     */
    private List<ElementAttributeEnum> elementAttributes;


}
