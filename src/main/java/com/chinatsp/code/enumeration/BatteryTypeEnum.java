package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 11:25
 **/
public enum BatteryTypeEnum {
    IT6831("IT6831"),
    KONSTANTER("KONSTANTER");

    @Setter
    @Getter
    private String value;

    BatteryTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static BatteryTypeEnum fromValue(String value) {
        for (BatteryTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support BatteryTypeEnum type[" + value + "]");
    }
}
