package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 11:22
 **/
public enum  RelayOperationTypeEnum {
    ON("开启"),
    OFF("关闭"),
    ALL_ON("全开"),
    ALL_OFF("全关");

    @Setter
    @Getter
    private String value;

    RelayOperationTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static RelayOperationTypeEnum fromValue(String value) {
        for (RelayOperationTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support RelayOperationTypeEnum type[" + value + "]");
    }
}
