package com.philosophy.character.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/11 21:31
 **/
public enum CharEnum {
    // 中文
    CHINESE(1),
    // 英文
    ENGLISH(3),
    // 字符
    SYMBOL(5),
    // 数字
    NUMBER(13),
    // 中文、英文混合
    CHINESE_ENGLISH(4),
    // 中文、字符混合
    CHINESE_SYMBOL(6),
    // 中文、数字混合
    CHINESE_NUMBER(14),
    // 英文、字符混合
    ENGLISH_SYMBOL(8),
    // 英文、数字混合
    ENGLISH_NUMBER(16),
    // 字符、数字混合
    SYMBOL_NUMBER(18),
    // 中文、英文、字符混合
    CHINESE_ENGLISH_SYMBOL(9),
    // 中文、英文、数字混合
    CHINESE_ENGLISH_NUMBER(17),
    // 中文、数字、字符混合
    CHINESE_NUMBER_SYMBOL(19),
    // 英文、字符、数字混合
    ENGLISH_SYMBOL_NUMBER(21),
    // 中文、英文、字符、数字混合
    CHINESE_ENGLISH_SYMBOL_NUMBER(22);

    @Getter
    @Setter
    private int value;

    CharEnum(int value) {
        this.value = value;
    }

    /**
     * 根据值返回字符类型
     *
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static CharEnum fromValue(int value) {
        for (CharEnum type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new RuntimeException("not support character type[" + value + "]");
    }
}
