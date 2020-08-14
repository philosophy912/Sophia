package com.chinatsp.code.entity.types;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/12 14:57
 **/
public enum SystemEnum {

    ANDROID("android"),
    QNX("qnx"),
    LINUX("linux");
    @Setter
    @Getter
    private String value;

    SystemEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回类型
     *
     * @param value 枚举值
     * @return 返回类型
     */
    public static SystemEnum fromValue(String value) {
        for (SystemEnum type : values()) {
            if (value.equalsIgnoreCase(type.value)) {
                return type;
            }
        }
        throw new RuntimeException("not support System type[" + value + "]");
    }
}
