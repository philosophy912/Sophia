# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        ops.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/24 - 9:14
# --------------------------------------------------------
import os

from automotive import *
from src.code.configure import *

# kkk2
element_kkk1 = {"resourceId": "a", "text": "b"}
# kkk3
element_kkk2 = {"text": "c"}
# kkk4
element_kkk3 = {"resourceId": "a", "text": "b"}

interval_time = 0.5


class Tester(object):
    def __init__(self):
        # 用于存放保存的内容
        self.save_data = dict()
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

        self.image_compare = ImageCompare()

        if it6831_serial:
            self.it6831 = It6831Actions(port=it6831_serial[0], baud_rate=it6831_serial[1])
        if konstanter_serial:
            self.konstanter = KonstanterActions(port=konstanter_serial[0], baud_rate=konstanter_serial[1])
        if dbc_json_file:
            self.can_service = CANService(dbc_json_file)
        if android_type:
            self.android_service = AndroidService(ToolTypeEnum.from_value(android_type))
        if max_relay_channel:
            self.relay = RelayActions()
        if test_case_type == "cluster" and qnx_serial:
            self.airCondition = AirCondition(qnx_screenshot_path, qnx_serial[0])
        if soc_serial:
            self.soc = SerialPort()
        if mcu_serial:
            self.mcu = SerialPort()

    def open_device(self):
        if self.it6831:
            self.it6831.open()
        if self.konstanter:
            self.konstanter.open()
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
        if self.can_service:
            self.can_service.open_can()
        if self.relay:
            self.relay.open()
        if self.airCondition:
            self.airCondition.connect()
        if self.mcu:
            self.mcu.connect(mcu_serial[0], mcu_serial[1])
        if self.soc:
            self.soc.connect(soc_serial[0], soc_serial[1])

    def close_device(self):
        if self.it6831:
            self.it6831.close()
        if self.konstanter:
            self.konstanter.close()
        if self.android_service:
            self.android_service.disconnect()
        if self.can_service:
            self.can_service.close_can()
        if self.relay:
            self.relay.close()
        if self.airCondition:
            self.airCondition.disconnect()

    def battery_test1(self):
        """
        test1
        """
        self.it6831.set_voltage(12)

    def battery_test2(self):
        """
        test1
        """
        self.konstanter.set_current(12)

    def battery_test3(self):
        """
        test1
        """
        self.konstanter.change_voltage(14, 12, 0.5, 5)

    def battery_test4(self):
        """
        test1
        """
        curve = Curve()
        voltage_list = curve.get_voltage_by_csv(r"d:\a.csv")
        for i in range(3):
            self.konstanter.adjust_voltage_by_curve(voltage_list)

    def can_test1(self):
        """
        test2
        """
        self.can_service.send_can_signal_message(642, {"HU_ACCSet": 0x1, "HU_FRMMsgErr": 0x2})

    def can_test2(self):
        """
        test3
        """
        self.can_service.send_can_signal_message(642, {"HU_FRMMsgErr": 0x1})

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

    def relay_test1(self):
        """
        relay_test1
        """
        self.relay.channel_on(1)

    def relay_test2(self):
        """
        relay_test1
        """
        self.relay.channel_off(2)

    def screen_operation_teset1(self):
        """
        tttt1
        """
        self.airCondition.swipe(display=1, start_x=1024, end_x=12, start_y=16, end_y=18, continue_time=2)

    def screen_operation_teset2(self):
        """
        tttt1
        """
        self.android_service.adb.click(x=12, y=18, display_id=2, device_id=android_device_id)

    def screen_operation_teset3(self):
        """
        tttt1
        """
        self.airCondition.press(display=3, x=12, y=152, continue_time=0.5)

    def screen_operation_teset4(self):
        """
        tttt1
        """
        self.airCondition.swipe(display=3, start_x=12, end_x=12, start_y=15, end_y=18, continue_time=1)

    def screenshot_tttt1(self):
        """
        oooo1
        """
        self.airCondition.screen_shot(image_name=f"test", count=1, interval_time=interval_time, display=1)

    def screenshot_tttt2(self):
        """
        oooo2
        """
        for i in range(2):
            self.android_service.adb.screen_shot(remote_file=f"{android_screenshot_path}/testq__{i + 1}",
                                                 display_id=2,
                                                 device_id=android_device_id)

    def infomation1(self):
        """
        infomation1
        """
        attr = ElementAttributeEnum.from_value("CHECKABLE")
        self.save_data["infomation1"] = self.android_service.get_element_attribute(element_kkk1)[attr]

    def infomation2(self):
        """
        infomation2
        """
        attr = ElementAttributeEnum.from_value("CLICKABLE")
        self.save_data["infomation2"] = self.android_service.get_element_attribute(element_kkk1)[attr]

    def infomation3(self):
        """
        infomation3
        """
        attr = ElementAttributeEnum.from_value("ENABLED")
        self.save_data["infomation3"] = self.android_service.get_element_attribute(element_kkk1)[attr]

    def infomation10(self):
        """
        infomation10
        """
        attr = ElementAttributeEnum.from_value("TEXT")
        self.save_data["infomation10"] = self.android_service.get_element_attribute(element_kkk1)[attr]

    def can_compare_test1(self, stack) -> tuple:
        """
        test1
        """
        result = self.can_service.check_signal_value(stack, 48, "ACU_RePassBkl_Sts", 1, 1, True)
        return result,

    def can_compare_test2(self, stack) -> tuple:
        """
        test1
        """
        result = self.can_service.check_signal_value(stack, 340, "APA_RRMDst", 1, 2, True)
        return result,

    def element_comapre1(self) -> tuple:
        """
        comapre1
        """
        result = self.android_service.exist(element_kkk1, 2)
        return result,

    def element_comapre2(self) -> tuple:
        """
        comapre2
        """
        result = not self.android_service.exist(element_kkk2, 3)
        return result,

    def image_compareaaaa1(self) -> tuple:
        """
        compareaaaa1
        """
        compare_property = CompareProperty()
        compare_property.set_value("screen_shot",
                                   "亮图",
                                   self.screenshot,
                                   "\\".join([self.templates, "template_light.jpg"]),
                                   "\\".join([self.templates, "template_dark.bmp"]),
                                   [(1, 2, 3, 4)],
                                   9.9,
                                   True,
                                   240)
        result = self.image_compare.compare(compare_property)
        files = self.image_compare.handle_images(compare_property, self.temp)
        return result, files, compare_property.type, compare_property.light_template, compare_property.dark_template

    def image_compareaaaa2(self) -> tuple:
        """
        compareaaaa2
        """
        compare_property = CompareProperty()
        compare_property.set_value("screen_shot",
                                   "暗图",
                                   self.screenshot,
                                   "\\".join([self.templates, "template_light.jpg"]),
                                   "\\".join([self.templates, "template_dark.bmp"]),
                                   [(2, 3, 45, 67), (34, 24, 54, 78)],
                                   9.9,
                                   True,
                                   240)
        result = self.image_compare.compare(compare_property)
        files = self.image_compare.handle_images(compare_property, self.temp)
        return result, files, compare_property.type, compare_property.light_template, compare_property.dark_template

    def info1(self) -> tuple:
        """
        info1
        """
        attr = ElementAttributeEnum.from_value("TEXT")
        info = self.android_service.get_element_attribute(element_kkk1)[attr]
        return self.infomation1() == info,

    def clear_stack(self):
        """
        common4
        """
        self.can_service.clear_stack_data()

    def get_stack(self):
        """
        common5
        """
        return self.can_service.get_stack()
