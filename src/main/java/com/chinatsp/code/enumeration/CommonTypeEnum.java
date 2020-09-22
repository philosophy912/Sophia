package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/21 15:20
 **/
public enum CommonTypeEnum {
    PYTHON("python内置"),
    KONSTANTER("konstanter可编程电源"),
    SERIAL("串口类"),
    IT6831("IT6831可编程电源"),
    RELAY("继电器"),
    ANDROID("Android类"),
    AIR_CONDITION("空调模块"),
    CAN("Can类");

    @Setter
    @Getter
    private String value;

    CommonTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static CommonTypeEnum fromValue(String value) {
        for (CommonTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support CommonTypeEnum type[" + value + "]");
    }
}
