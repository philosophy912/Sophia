package com.sophia.bank.api;

import lombok.Getter;

public enum ExchangeTypeEnum {

    IN("收入"),
    OUT("支出");

    @Getter
    private final String value;

    ExchangeTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回字符类型
     *
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static ExchangeTypeEnum fromValue(String value) {
        for (ExchangeTypeEnum type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support ExchangeType [" + value + "]");
    }

}
