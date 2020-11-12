package com.chinatsp.automotive.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/7 16:18
 **/
public enum AndroidLocatorTypeEnum {
    RESOURCE_ID("resourceId"),
    CLASSNAME("className"),
    XPATH("xpath"),
    TEXT("text"),
    DESCRIPTION("description"),
    TEXT_CONTAINS("textContains"),
    TEXT_MATCHES("textMatches"),
    TEXT_STARTS_WITH("textStartsWith"),
    CLASSNAME_MATCHES("classNameMatches"),
    DESCRIPTION_CONTAINS("descriptionContains"),
    DESCRIPTION_MATCHES("descriptionMatches"),
    DESCRIPTION_STARTS_WITH("descriptionStartsWith"),
    CHECKABLE("checkable"),
    CHECKED("checked"),
    CLICKABLE("clickable"),
    LONG_CLICKABLE("longClickable"),
    SCROLLABLE("scrollable"),
    ENABLED("enabled"),
    FOCUSABLE("focusable"),
    FOCUSED("focused"),
    SELECTED("selected"),
    PACKAGE_NAME("packageName"),
    PACKAGE_NAME_MATCHES("packageNameMatches"),
    RESOURCE_ID_MATCHES("resourceIdMatches"),
    INDEX("index"),
    INSTANCE("instance");


    @Setter
    @Getter
    private String value;

    AndroidLocatorTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static AndroidLocatorTypeEnum fromValue(String value) {
        for (AndroidLocatorTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support AndroidLocatorTypeEnum type[" + value + "]");
    }
}
