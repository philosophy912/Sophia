package com.philosophy.image.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2019/10/12:17:28
 */
public enum CompareEnum {
    // 汉明距比较
    HAMMING("HAMMING"),
    // 直方图比较
    HISTOGRAM("HISTOGRAM"),
    // 像素编辑
    PIXEL("PIXEL");

    @Getter
    @Setter
    private String value;

    CompareEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回字符类型
     *
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static CompareEnum fromValue(String value) {
        for (CompareEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support character type[" + value + "]");
    }
}
