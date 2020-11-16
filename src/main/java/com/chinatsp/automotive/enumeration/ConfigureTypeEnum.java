package com.chinatsp.automotive.enumeration;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lizhe
 * @date 2020/9/24 13:08
 **/
public enum ConfigureTypeEnum {
    MAX_ANDROID_DISPLAY("最大安卓显示屏的个数", "max_android_display"),
    MAX_QNX_DISPLAY("最大QNX显示屏的个数", "max_qnx_display"),
    VOLTAGE_MIN("电源电压支持最小电压", "voltage_min"),
    VOLTAGE_MAX("电源电压支持最大电压", "voltage_max"),
    SOC_SERIAL_PORT("SOC串口号", "soc_serial_port"),
    SOC_SERIAL_BAUD_RATE("SOC串口波特率", "soc_serial_baud_rate"),
    MCU_SERIAL_PORT("MCU串口号", "mcu_serial_port"),
    MCU_SERIAL_BAUD_RATE("MCU串口波特率", "mcu_serial_baud_rate"),
    KONSTANTER_SERIAL_PORT("Konstanter串口号", "konstanter_serial_port"),
    KONSTANTER_SERIAL_BAUD_RATE("Konstanter串口波特率", "konstanter_serial_baud_rate"),
    IT6831_SERIAL_PORT("IT6831串口号", "it6831_serial_port"),
    IT6831_SERIAL_BAUD_RATE("IT6831串口波特率", "it6831_serial_baud_rate"),
    AIR_CONDITION_PORT("空调屏(QNX)串口配置", "air_condition_port"),
    AIR_CONDITION_BAUD_RATE("空调屏(QNX)串口波特率", "air_condition_baud_rate"),
    QNX_SCREEN_SHOT_PATH("QNX截图存放路径", "qnx_screen_shot_path"),
    ANDROID_SCREEN_SHOT_PATH("Android截图存放路径", "android_screen_shot_path"),
    ANDROID_RESOLUTION_WIDTH("Android屏幕宽", "android_resolution_width"),
    ANDROID_RESOLUTION_HEIGHT("Android屏幕高", "android_resolution_height"),
    QNX_RESOLUTION_WIDTH("QNX屏幕宽", "qnx_resolution_width"),
    QNX_RESOLUTION_HEIGHT("QNX屏幕高", "qnx_resolution_height"),
    DBC_FILE("DBC文件名称", "dbc_file"),
    DBC_JSON("DBC解析后生成的文件名称", "dbc_json"),
    MAX_RELAY_CHANNEL("最大支持的继电器通道", "max_relay_channel"),
    ANDROID_AUTOMATION_TYPE("Android自动化测试方式", "android_automation_type"),
    ANDROID_APP_PACKAGE("安卓的启动应用的package", "android_app_package"),
    ANDROID_APP_ACTIVITY("安卓的启动应用的activity", "android_app_activity"),
    ANDROID_VERSION("安卓系统版本号", "android_version"),
    ANDROID_DEVICE_ID("安卓设备号", "android_device_id"),
    TEST_CASE_TYPE("测试类型", "test_case_type"),
    USERNAME("用户名", "username"),
    PASSWORD("密码", "password"),
    HMI_USERNAME("HMI用户名", "hmi_username"),
    HMI_PASSWORD("HMI密码", "hmi_password"),
    HMI_BOARD_PATH("HMI板子路径", "hmi_board_path"),
    HMI_TEST_BINARY("HMI测试程序路径", "hmi_test_binary"),
    IPADDRESS("ip地址", "ip_address");

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
