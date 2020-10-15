package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/21 15:20
 **/
public enum CommonTypeEnum {
    PAUSE("暂停信号发送", "stop_transmit"),
    RESUME("恢复信号发送", "resume_transmit"),
    SCROLL_UP_GET_ELEMENT("上滑动查找元素", "scroll_up_get_element"),
    SCROLL_DOWN_GET_ELEMENT("下滑动查找元素", "scroll_down_get_element"),
    SCROLL_LEFT_GET_ELEMENT("左滑动查找元素", "scroll_left_get_element"),
    SCROLL_RIGHT_GET_ELEMENT("右滑动查找元素", "scroll_right_get_element"),
    SCROLL_UP_GET_ELEMENT_CLICK("上滑动查找元素并点击", "scroll_up_get_element_and_click"),
    SCROLL_DOWN_GET_ELEMENT_CLICK("下滑动查找元素并点击", "scroll_down_get_element_and_click"),
    SCROLL_LEFT_GET_ELEMENT_CLICK("左滑动查找元素并点击", "scroll_left_get_element_and_click"),
    SCROLL_RIGHT_GET_ELEMENT_CLICK("右滑动查找元素并点击", "scroll_right_get_element_and_click"),
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
