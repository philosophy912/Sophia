package com.chinatsp.automotive.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:12
 **/
public enum ElementOperationTypeEnum {
    SLIDE("滑动", "swipe_element"),
    CLICK("点击","click"),
    PRESS("长按","press"),
    DOUBLE_CLICK("双击","double_click"),
    SLIDE_UP("上滑","swipe_up"),
    SLIDE_DOWN("下滑","swipe_down"),
    SLIDE_LEFT("左滑", "swipe_left"),
    SLIDE_RIGHT("右滑","swipe_right"),
    SLIDE_UP_END("上滑到顶","swipe_up"),
    SLIDE_DOWN_END("下滑到底", "swipe_down"),
    SLIDE_LEFT_END("左滑到头", "swipe_left"),
    SLIDE_RIGHT_END("右滑到头", "swipe_right");

    @Setter
    @Getter
    private String value;
    @Getter
    @Setter
    private String name;

    ElementOperationTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static ElementOperationTypeEnum fromValue(String value) {
        for (ElementOperationTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support OperationActionTypeEnum type[" + value + "]");
    }
}
