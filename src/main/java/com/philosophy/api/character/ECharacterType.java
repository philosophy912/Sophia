package com.philosophy.api.character;

/**
 * @author lizhe
 * @since V1.0.0 2019/5/23 22:38
 **/
public enum ECharacterType {
    CHINESE(1),
    ENGLISH(3),
    SYMBOL(5),
    NUMBER(13),
    CHINESE_ENGLISH(4),
    CHINESE_SYMBOL(6),
    CHINESE_NUMBER(14),
    ENGLISH_SYMBOL(8),
    ENGLISH_NUMBER(16),
    SYMBOL_NUMBER(18),
    CHINESE_ENGLISH_SYMBOL(9),
    CHINESE_ENGLISH_NUMBER(17),
    CHINESE_NUMBER_SYMBOL(19),
    ENGLISH_SYMBOL_NUMBER(21),
    CHINESE_ENGLISH_SYMBOL_NUMBER(22);

    private int value;

    /**
     * 枚举获取枚举数据的值
     *
     * @return 返回枚举数据的值
     */
    public int getValue() {
        return value;
    }

    private ECharacterType(int value) {
        this.value = value;
    }

    /**
     * 根据值返回字符类型
     *
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static ECharacterType fromValue(int value) {
        for (ECharacterType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new RuntimeException("not support character type[" + value + "]");
    }
}
