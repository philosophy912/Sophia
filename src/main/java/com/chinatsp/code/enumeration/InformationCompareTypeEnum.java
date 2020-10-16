package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:12
 **/
public enum InformationCompareTypeEnum {
    EQUAL("相同"),
    NOT_EQUAL("不相同");

    @Setter
    @Getter
    private String value;


    InformationCompareTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static InformationCompareTypeEnum fromValue(String value) {
        for (InformationCompareTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support InformationCompareTypeEnum type[" + value + "]");
    }
}
