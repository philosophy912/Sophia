package com.chinatsp.code.entity.types;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/14 15:00
 **/
public enum ElementAttributeEnum {
    CHECKABLE("checkable"),
    CHECKED("checked"),
    CLICKABLE("clickable"),
    ENABLED("enabled"),
    FOCUSABLE("focusable"),
    FOCUSED("focused"),
    SCROLLABLE("scrollable"),
    LONG_CLICKABLE("longClickable"),
    DISPLAYED("displayed"),
    SELECTED("selected");
    @Setter
    @Getter
    private String value;

    ElementAttributeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回类型
     *
     * @param value 枚举值
     * @return 返回类型
     */
    public static ElementAttributeEnum fromValue(String value) {
        for (ElementAttributeEnum type : values()) {
            if (value.equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        throw new RuntimeException("not support element attribute type[" + value + "]");
    }
}
