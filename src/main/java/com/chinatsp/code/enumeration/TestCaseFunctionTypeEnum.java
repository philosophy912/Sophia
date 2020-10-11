package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/14 17:16
 **/
public enum TestCaseFunctionTypeEnum {
    BATTERY_ACTION("BatteryAction"),
    ELEMENT_ACTION("ElementAction"),
    RELAY_ACTION("RelayAction"),
    SCREEN_OPS_ACTION("ScreenOpsAction"),
    SCREEN_SHOT_ACTION("ScreenShotAction"),
    CAN_ACTION("CanAction"),
    COMMON("Common"),
    CAN_COMPARE("CanCompare"),
    ELEMENT_COMPARE("ElementCompare"),
    IMAGE_COMPARE("ImageCompare"),
    INFORMATION_COMPARE("InformationCompare"),
    INFORMATION("Information"),
    PASS("pass"),
    SLEEP("sleep"),
    STACK("stack"),
    CLEAR("clear"),
    LOST("lost"),
    OPEN("open"),
    CLOSE("close"),
    YIELD("yield");


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
