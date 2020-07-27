package com.chinatsp.automation.api.builder;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/5/29 12:05
 **/
public enum TestCaseTypeEnum {
    /**
     * 仪表
     */
    CLUSTER("cluster"),
    /**
     * 空调屏
     */
    AIR_CONDITION("aircondition"),
    /**
     * 通用
     */
    COMMON("common");

    @Getter
    @Setter
    private String value;

    TestCaseTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回字符类型
     *
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static TestCaseTypeEnum fromValue(String value) {
        for (TestCaseTypeEnum type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support character type[" + value + "]");
    }
}
