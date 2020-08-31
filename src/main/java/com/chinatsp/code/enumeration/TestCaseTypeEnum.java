package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:03
 **/
public enum TestCaseTypeEnum {
    CLUSTER("仪表"),
    ANDROID("中控"),
    AIR_CONDITION("空调屏");

    @Setter
    @Getter
    private String value;

    TestCaseTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static TestCaseTypeEnum fromValue(String value) {
        for (TestCaseTypeEnum type : values()) {
            if (type.value.trim().replaceAll("_", "").equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support TestCaseTypeEnum type[" + value + "]");
    }
}
