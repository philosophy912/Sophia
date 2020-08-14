package com.chinatsp.code.entity.types;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/14 14:46
 **/
public enum LocatorEnum {
    RESOURCE_ID("resourceId"),
    CLASS_NAME("className"),
    XPATH("xpath"),
    TEXT("text"),
    DESCRIPTION("description");

    @Setter
    @Getter
    private String value;

    LocatorEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回类型
     *
     * @param value 枚举值
     * @return 返回类型
     */
    public static LocatorEnum fromValue(String value) {
        for (LocatorEnum type : values()) {
            if (value.equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        throw new RuntimeException("not support Locator type[" + value + "]");
    }

}
