# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        screen_ops_action.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 13:11
# --------------------------------------------------------
from automotive import *
from src.code.context import Action
from src.code.configure import *


class ScreenshotOpsAction(Action):

    def __init__(self):
        super().__init__()
        if test_case_type == "cluster" and qnx_serial:
            self.airCondition = AirCondition(qnx_screenshot_path, qnx_serial[0])

    def open(self):
        if self.airCondition:
            self.airCondition.connect()

    def close(self):
        if self.airCondition:
            self.airCondition.disconnect()

    def screen_operation_teset1(self):
        """
        tttt1
        """
        self.airCondition.swipe(display=1, start_x=1024, end_x=12, start_y=16, end_y=18, continue_time=2)

    def screen_operation_teset2(self):
        """
        tttt1
        """
        self.android_service.adb.click(x=12, y=18, display_id=2, device_id=self.android_device_id)

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
