# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        element_action.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/19 - 20:43
# --------------------------------------------------------
from automotive import *
from src.code.configure import *
from code.collections.element import *
from src.code.context import Action


class ElementAction(Action):

    def __init__(self):
        super().__init__()
        if android_type:
            self.android_service = AndroidService(ToolTypeEnum.APPIUM) if android_type == "appium" else AndroidService(
                ToolTypeEnum.UIAUTOMATOR2)

    def open(self):
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

    def close(self):
        if self.android_service:
            self.android_service.disconnect()

    def element_test1(self):
        """
        test1
        """
        self.android_service.swipe_element(element_kkk1, element_kkk2)

    def element_test2(self):
        """
        test2
        """
        self.android_service.click(element_kkk2)

    def element_test3(self):
        """
        test3
        """
        self.android_service.press(element_kkk3, 2)
