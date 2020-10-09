package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/8/27 11:26
 **/
public enum BatteryOperationTypeEnum {
    SET_VOLTAGE("设置电压", "set_voltage"),
    SET_CURRENT("设置电流", "set_current"),
    ADJUST_VOLTAGE("调节电压", "change_voltage"),
    CURVE("电压曲线", "adjust_voltage_by_curve");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;

    BatteryOperationTypeEnum(String value, String name) {
        this.value = value;
        this.name = name;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static BatteryOperationTypeEnum fromValue(String value) {
        for (BatteryOperationTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support BatteryOperationTypeEnum type[" + value + "]");
    }

}
