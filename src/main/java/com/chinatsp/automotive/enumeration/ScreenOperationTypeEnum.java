package com.chinatsp.automotive.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:12
 **/
public enum ScreenOperationTypeEnum {
    SLIDE("滑动", "swipe"),
    CLICK("点击","click"),
    PRESS("长按","press"),
    DRAG("拖动", "drag");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;

    ScreenOperationTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
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
