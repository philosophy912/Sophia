# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        context.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/18 - 9:51
# --------------------------------------------------------
import os
from automotive import *
from src.code.configure import *


class Action(object):

    def __init__(self):
        # 用于存放保存的内容
        self._save_data = dict()
        top_folder = os.path.dirname(os.getcwd())
        resource = "\\".join([top_folder, "resources"])
        # DBC解析出来的文件的路径
        self.dbc = "\\".join([resource, "dbc", dbc_json_file])
        # 模板图片存放路径
        self.templates = "\\".join([resource, "templates"])
        # 截图图片存放路径
        self.screenshot = "\\".join([resource, "result", "screenshot"])
        # 临时文件存放路径
        self.temp = "\\".join([resource, "result", "screenshot"])
        # 报告存放路径 todo

        if it6831_serial:
            self.it6831 = It6831Actions(port=it6831_serial[0], baud_rate=it6831_serial[1])
        if konstanter_serial:
            self.konstanter = KonstanterActions(port=konstanter_serial[0], baud_rate=konstanter_serial[1])
        if dbc_json_file:
            self.can_service = CANService(dbc_json_file)
        if mcu_serial:
            self.mcu_serial = SerialPort()
        if soc_serial:
            self.soc_serial = SerialPort()
        if max_relay_channel:
            self.relay = RelayActions()
        if android_type:
            self.android_service = AndroidService(ToolTypeEnum.APPIUM) if android_type == "appium" else AndroidService(
                ToolTypeEnum.UIAUTOMATOR2)
        if test_case_type == "cluster":
            self.airCondition = AirCondition(qnx_screenshot_path, qnx_serial[0])
        self.image_compare = ImageCompare()
        self.android_device_id = android_device_id
        self.android_screenshot_path = android_screenshot_path

    def open(self):
        if self.it6831:
            self.it6831.open()
        if self.konstanter:
            self.konstanter.open()
        if self.can_service:
            self.can_service.open_can()
        if self.mcu_serial:
            self.mcu_serial.connect(mcu_serial[0], mcu_serial[1])
        if self.soc_serial:
            self.soc_serial.connect(soc_serial[0], soc_serial[1])
        if self.relay:
            self.relay.open()
        if self.android_service:
            if android_type == "appium":
                capability = {
                    "deviceName": android_device_id,
                    "platformVersion": android_platform_version,
                    "platformName": "Android",
                    "automationName": "UiAutomator2",
                    "appPackage": android_app_package,
                    "appActivity": android_app_activity
                }
                self.android_service.connect(android_device_id, capability)
            else:
                self.android_service.connect(android_device_id)
        if self.airCondition:
            self.airCondition.connect()

    def close(self):
        if self.it6831:
            self.it6831.close()
        if self.konstanter:
            self.konstanter.close()
        if self.can_service:
            self.can_service.close_can()
        if self.mcu_serial:
            self.mcu_serial.disconnect()
        if self.soc_serial:
            self.soc_serial.disconnect()
        if self.relay:
            self.relay.close()
        if self.android_service:
            self.android_service.disconnect()
        if self.airCondition:
            self.airCondition.disconnect()
