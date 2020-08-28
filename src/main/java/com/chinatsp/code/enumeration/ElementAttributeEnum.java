package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 12:24
 **/
public enum ElementAttributeEnum {
    CHECKABLE("CHECKABLE"),
    CHECKED("CHECKED"),
    CLICKABLE("CLICKABLE"),
    ENABLED("ENABLED"),
    FOCUSABLE("FOCUSABLE"),
    FOCUSED("FOCUSED"),
    SCROLLABLE("SCROLLABLE"),
    LONG_CLICKABLE("LONG_CLICKABLE"),
    DISPLAYED("DISPLAYED"),
    SELECTED("SELECTED"),
    TEXT("TEXT");

    @Setter
    @Getter
    private String value;

    ElementAttributeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static ElementAttributeEnum fromValue(String value) {
        for (ElementAttributeEnum type : values()) {
            if (type.value.trim().replace("_", "").equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support ElementAttributeEnum type[" + value + "]");
    }
}
