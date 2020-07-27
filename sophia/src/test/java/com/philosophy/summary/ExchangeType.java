package com.philosophy.summary;

import lombok.Setter;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/11 21:31
 **/
public enum ExchangeType {
    INCOME("收入"),
    PAY("支出");
    //    TRANSFER;
    @Setter
    private String value;

    ExchangeType(String value) {
        this.value = value;
    }

    /**
     * 根据值返回字符类型
     *
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static ExchangeType fromValue(String value) {
        for (ExchangeType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support character type[" + value + "]");
    }
}
