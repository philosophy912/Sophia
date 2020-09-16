package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/16 13:44
 **/
public enum ScreenShotTypeEnum {
    QNX_DISPLAY("QNX屏"),
    ANDROID_DISPLAY("安卓屏"),
    CLUSTER_DISPLAY("仪表屏");

    @Setter
    @Getter
    private String value;

    ScreenShotTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static ScreenShotTypeEnum fromValue(String value) {
        for (ScreenShotTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support ScreenShotTypeEnum type[" + value + "]");
    }
}
