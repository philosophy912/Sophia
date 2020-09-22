# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        configure.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/15 - 15:53
# --------------------------------------------------------
# 最大支持的显示屏的个数
max_display = 5
# 最大支持的电压值
max_voltage = 20
# 最小支持的电压值
min_voltage = 0
# it6831
it6831_serial = "COM25", 9600
# konstanter
konstanter_serial = "COM26", 19200
# qnx
qnx_serial = "COM23", 115200
# soc
soc_serial = "COM24", 115200
# mcu
mcu_serial = "COM24", 115200
# qnx截图存放路径
qnx_screenshot_path = "/tsp"
# 安卓截图存放路径
android_screenshot_path = "/tsp"
# 显示屏宽
max_width = 1920
# 显示屏高
max_height = 720
# CAN的dbc文件转换成json文件
dbc_json_file = r"HiFire_B31CP_Info_HU_CAN_V2.0.json"
# 最大支持的继电器通道
max_relay_channel = 8
# 安卓工具使用
android_type = "appium"
# android device id
android_device_id = 1234567
# package
android_app_package = "com.chinatsp.settings"
# activity
android_app_activity = ".SettingsActivity"
# android 版本号
android_platform_version = "8.1.0"
# 测试类型
test_case_type = "cluster"
