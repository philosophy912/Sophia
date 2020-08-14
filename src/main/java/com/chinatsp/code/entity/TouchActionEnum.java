package com.chinatsp.code.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/12 15:26
 **/
public enum TouchActionEnum {

    CLICK("click"),
    PRESS("longpress"),
    DOUBLE_CLICK("doubleclick"),
    DRAG("drag");

    @Setter
    @Getter
    private String value;

    TouchActionEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回类型
     *
     * @param value 枚举值
     * @return 返回类型
     */
    public static TouchActionEnum fromValue(String value) {
        for (TouchActionEnum type : values()) {
            if (value.replace("-", "").equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        throw new RuntimeException("not support touch action type[" + value + "]");
    }
}
