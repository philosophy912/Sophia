package com.chinatsp.code.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/24 13:08
 **/
public enum ConfigureTypeEnum {
    DBC_FILE("DBC文件名称","dbc_file"),
    MAX_DISPLAY("最大支持的显示屏的个数", "max_display"),
    VOLTAGE_RANGE("电源电压支持范围","voltage_range"),
    SOC_SERIAL("SOC串口配置","soc_serial"),
    MCU_SERIAL("MCU串口配置","mcu_serial"),
    KONSTANTER_SERIAL("Konstanter串口配置","konstanter_serial"),
    IT6831_SERIAL("IT6831串口配置","it6831_serial"),
    AIR_CONDITION("空调屏(QNX)串口配置","air_condition"),
    QNX_SCREEN_SHOT_PATH("QNX截图存放路径", "qnx_screen_shot_path"),
    ANDROID_SCREEN_SHOT_PATH("Android截图存放路径", "android_screen_shot_path"),
    ANDROID_RESOLUTION("Android屏幕分辨率", "android_resolution"),
    QNX_RESOLUTION("QNX屏幕分辨率","qnx_resolution"),
    DBC_JSON("DBC解析后生成的文件名称", "dbc_json"),
    MAX_RELAY_CHANNEL("最大支持的继电器通道", "max_relay_channel"),
    ANDROID_AUTOMATION_TYPE("Android自动化测试方式", "android_automation_type"),
    ANDROID_APP_PACKAGE("安卓的启动应用的package", "android_app_package"),
    ANDROID_APP_ACTIVITY("安卓的启动应用的activity", "android_app_activity"),
    ANDROID_VERSION("安卓系统版本号", "android_version"),
    ANDROID_DEVICE_ID("安卓设备号", "android_device_id"),
    TEST_CASE_TYPE("测试类型","test_case_type");

    @Setter
    @Getter
    private String value;
    @Setter
    @Getter
    private String name;


    ConfigureTypeEnum(String value, String name) {
        this.name = name;
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
