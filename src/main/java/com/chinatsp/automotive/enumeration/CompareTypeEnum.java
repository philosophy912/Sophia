package com.chinatsp.automotive.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 13:11
 **/
public enum CompareTypeEnum {
    LIGHT("亮图"),
    DARK("暗图"),
    BLINK("闪烁图");

    @Setter
    @Getter
    private String value;

    CompareTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static CompareTypeEnum fromValue(String value) {
        for (CompareTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support CompareTypeEnum type[" + value + "]");
    }
}
