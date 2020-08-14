package com.chinatsp.code.entity.types;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/14 15:08
 **/
public enum CompareTypeEnum {

    LIGHT("light"),
    DARK("dark"),
    BLINK("blink");

    @Setter
    @Getter
    private String value;

    CompareTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回类型
     *
     * @param value 枚举值
     * @return 返回类型
     */
    public static CompareTypeEnum fromValue(String value) {
        for (CompareTypeEnum type : values()) {
            if (value.equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        throw new RuntimeException("not support compare type[" + value + "]");
    }
}
