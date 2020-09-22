package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:12
 **/
public enum ScreenOperationTypeEnum {
    SLIDE("滑动"),
    CLICK("点击"),
    PRESS("长按");

    @Setter
    @Getter
    private String value;

    ScreenOperationTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static ScreenOperationTypeEnum fromValue(String value) {
        for (ScreenOperationTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support OperationActionTypeEnum type[" + value + "]");
    }
}
