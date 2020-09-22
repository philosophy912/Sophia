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

from .configure import *
from automotive import *


class Action(object):

    def __init__(self):
        top_folder = os.path.dirname(os.getcwd())
        # 定义相关路径
        self.dbc = "\\".join([top_folder, "resources", "dbc", dbc_json_file])
        self.it6831 = It6831Actions(port=it6831_serial[0], baud_rate=it6831_serial[1])
        self.konstanter = KonstanterActions(port=konstanter_serial[0], baud_rate=konstanter_serial[1])
        self.can_service = CANService(dbc_json_file)
        self.mcu_serial = SerialPort()
        self.soc_serial = SerialPort()
        self.relay = RelayActions()
        self.android_service = AndroidService(ToolTypeEnum.APPIUM) if android_type == "appium" else AndroidService(
            ToolTypeEnum.UIAUTOMATOR2)
        self.airCondition = AirCondition(qnx_screenshot_path, qnx_serial[0])
        self.image_compare = ImageCompare()

    def open(self):
        self.it6831.open()
        self.konstanter.open()
        self.can_service.open_can()
        self.mcu_serial.connect(mcu_serial[0], mcu_serial[1])
        self.soc_serial.connect(soc_serial[0], soc_serial[1])
        self.relay.open()
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
        self.airCondition.connect()

    def close(self):
        self.it6831.close()
        self.konstanter.close()
        self.can_service.close_can()
        self.mcu_serial.disconnect()
        self.soc_serial.disconnect()
        self.relay.close()
        self.android_service.disconnect()
        self.airCondition.disconnect()


if __name__ == '__main__':
    action = Action()
    print(action.dbc)
