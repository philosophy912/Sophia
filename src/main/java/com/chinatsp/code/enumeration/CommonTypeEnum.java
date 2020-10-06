package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/21 15:20
 **/
public enum CommonTypeEnum {
    SWIPE_FIND_ELEMENT("滑动查找元素", "swipe_find_element"),
    CLEAR_TEXT("清空编辑框中的文字", "clear_text"),
    INPUT_TEXT("输入文本框中的文字", "input_text");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;

    CommonTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static CommonTypeEnum fromValue(String value) {
        for (CommonTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support CommonTypeEnum type[" + value + "]");
    }
}
