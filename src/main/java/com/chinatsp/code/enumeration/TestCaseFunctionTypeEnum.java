package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/14 17:16
 **/
public enum TestCaseFunctionTypeEnum {
    BATTERY_ACTION("电源操作"),
    ELEMENT_ACTION("元素操作"),
    RELAY_ACTION("继电器操作"),
    SCREEN_OPERATION_ACTION("屏幕操作"),
    SCREEN_SHOT_ACTION("截图操作"),
    COMMON("公共函数"),
    CAN_COMPARE("CAN信号对比"),
    ELEMENT_COMPARE("Android元素对比"),
    IMAGE_COMPARE("图片对比"),
    INFORMATION_COMPARE("信息对比"),
    INFORMATION("信息保存");


    @Setter
    @Getter
    private String value;

    TestCaseFunctionTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static TestCaseFunctionTypeEnum fromValue(String value) {
        for (TestCaseFunctionTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support TestCaseFunctionTypeEnum type[" + value + "]");
    }
}
