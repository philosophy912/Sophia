package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 13:14
 **/
public enum PositionEnum {
    X("x"),
    Y("y"),
    WIDTH("width"),
    HEIGHT("height");
    @Setter
    @Getter
    private String value;

    PositionEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static PositionEnum fromValue(String value) {
        for (PositionEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support PositionEnum type[" + value + "]");
    }
}
