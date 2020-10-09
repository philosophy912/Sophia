package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 10:03
 **/
public enum TestCaseTypeEnum {
    HALF("半自动", "half"),
    FULL("全自动", "full");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;

    TestCaseTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
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
