package com.chinatsp.code.entity.types;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/14 14:45
 **/
public enum UiSelectorsEnum {
    TEXT("text"),
    TEXT_CONTAINS("textContains"),
    TEXT_STARTS_WITH("textStartsWith"),
    CLASS_NAME("className"),
    CLASS_NAME_MATCHES("classNameMatches"),
    DESCRIPTION("description"),
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
    RESOURCE_ID("resourceId"),
    RESOURCE_ID_MATCHES("resourceIdMatches"),
    INDEX("index"),
    INSTANCE("instance");

    @Setter
    @Getter
    private String value;

    UiSelectorsEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回类型
     *
     * @param value 枚举值
     * @return 返回类型
     */
    public static UiSelectorsEnum fromValue(String value) {
        for (UiSelectorsEnum type : values()) {
            if (value.equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        throw new RuntimeException("not support ui selector type[" + value + "]");
    }

    }
