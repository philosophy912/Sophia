package com.philosophy.codec.api;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @since V1.0.0 2019/10/13 17:23
 **/
public enum CodecEnum {
    // MD5加密
    MD5("MD5"),
    // SHA加密
    SHA("SHA"),
    // AES 加密解密
    AES("AES"),
    // 3DES 加密解密
    DES3("DESede"),
    // DES 加密解密
    DES("DES");

    @Getter
    @Setter
    private String value;

    CodecEnum(String value) {
        this.value = value;
    }

    /**
     * 根据值返回字符类型
     *
     * @param value 枚举值
     * @return 返回字符的枚举值
     */
    public static CodecEnum fromValue(String value) {
        for (CodecEnum type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support character type[" + value + "]");
    }
}
