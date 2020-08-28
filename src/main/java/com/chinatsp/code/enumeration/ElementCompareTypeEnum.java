package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 13:28
 **/
public enum ElementCompareTypeEnum {
    EXIST("存在"),
    NOT_EXIST("不存在");

    @Setter
    @Getter
    private String value;

    ElementCompareTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static ElementCompareTypeEnum fromValue(String value) {
        for (ElementCompareTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support ElementCompareTypeEnum type[" + value + "]");
    }
}
