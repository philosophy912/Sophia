package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 11:22
 **/
public enum  RelayOperationTypeEnum {
    ON("开启", "channel_on"),
    OFF("关闭","channel_off"),
    ALL_ON("全开","channel_on"),
    ALL_OFF("全关", "channel_off");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;

    RelayOperationTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
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
