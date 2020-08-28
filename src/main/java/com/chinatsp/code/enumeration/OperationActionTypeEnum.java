package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:12
 **/
public enum OperationActionTypeEnum {
    SLIDE("滑动"),
    CLICK("点击"),
    PRESS("长按"),
    DOUBLE_CLICK("双击"),
    SLIDE_UP("上滑"),
    SLIDE_DOWN("下滑"),
    SLIDE_LEFT("左滑"),
    SLIDE_RIGHT("右滑"),
    SLIDE_UP_END("上滑到顶"),
    SLIDE_DOWN_END("下滑到底"),
    SLIDE_LEFT_END("左滑到头"),
    SLIDE_RIGHT_END("右滑到头");

    @Setter
    @Getter
    private String value;

    OperationActionTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static OperationActionTypeEnum fromValue(String value) {
        for (OperationActionTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support OperationActionTypeEnum type[" + value + "]");
    }
}
