package com.chinatsp.automotive.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 11:25
 **/
public enum BatteryTypeEnum {
    IT6831("IT6831", "it6831"),
    KONSTANTER("KONSTANTER", "konstanter");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;

    BatteryTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
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
