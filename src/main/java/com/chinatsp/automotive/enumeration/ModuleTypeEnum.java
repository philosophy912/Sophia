package com.chinatsp.automotive.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/21 15:20
 **/
public enum ModuleTypeEnum {
    IT6831("it6831"),
    KONSTANTER("konstanter"),
    CAN_SERVICE("can_service"),
    ANDROID_SERVICE("android_service"),
    RELAY("relay"),
    AIR_CONDITION("air_condition"),
    SOC("soc"),
    MCU("mcu"),
    HYPERVISOR("hypervisor"),
    CLUSTER_HMI("cluster_hmi");

    @Setter
    @Getter
    private String value;

    ModuleTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static ModuleTypeEnum fromValue(String value) {
        for (ModuleTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support ModuleTypeEnum type[" + value + "]");
    }
}
