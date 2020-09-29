package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:07
 **/
public enum DeviceTpeEnum {
    QNX("QNX", "airCondition"),
    ANDROID("ANDROID", "android_service");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;

    DeviceTpeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static DeviceTpeEnum fromValue(String value) {
        for (DeviceTpeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support DeviceTpeEnum type[" + value + "]");
    }
}
