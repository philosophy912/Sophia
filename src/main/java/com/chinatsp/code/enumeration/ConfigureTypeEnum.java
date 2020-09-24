package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/24 13:08
 **/
public enum ConfigureTypeEnum {
    MAX_DISPLAY("最大支持的显示屏的个数"),
    VOLTAGE_RANGE("电源电压支持范围"),
    SOC_SERIAL("SOC串口配置"),
    MCU_SERIAL("MCU串口配置"),
    KONSTANTER("Konstanter串口配置"),
    IT6831("IT6831串口配置"),
    AIR_CONDITION("空调屏(QNX)串口配置"),
    QNX_SCREEN_SHOT_PATH("QNX截图存放路径"),
    ANDROID_SCREEN_SHOT_PATH("Android截图存放路径"),
    ANDROID_RESOLUTION("Android屏幕分辨率"),
    QNX_RESOLUTION("QNX屏幕分辨率"),
    DBC_JSON("DBC解析后生成的文件名称"),
    MAX_RELAY_CHANNEL("最大支持的继电器通道"),
    ANDROID_AUTOMATION_TYPE("Android自动化测试方式"),
    ANDROID_APP_PACKAGE("安卓的启动应用的package"),
    ANDROID_APP_ACTIVITY("安卓的启动应用的activity"),
    ANDROID_VERSION("安卓系统版本号"),
    ANDROID_DEVICE_ID("安卓设备号"),
    TEST_CASE_TYPE("测试类型");

    @Setter
    @Getter
    private String value;


    ConfigureTypeEnum(String value) {
        this.value = value;
    }

    /**
     * 根据Value查找枚举类型
     *
     * @param value 枚举类型的值
     * @return 枚举类型
     */
    public static ConfigureTypeEnum fromValue(String value) {
        for (ConfigureTypeEnum type : values()) {
            if (type.value.trim().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new RuntimeException("not support ConfigureTypeEnum type[" + value + "]");
    }
}
