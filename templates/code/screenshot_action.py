# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        screenshot_action.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 15:03
# --------------------------------------------------------
from .configure import *
from .context import Action

interval_time = 0.5


class ScreenShotAction(Action):

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
            self.android_service.adb.screen_shot(remote_file=f"{android_screenshot_path}/testq__{i + 1}", display_id=2,
                                                 device_id=android_device_id)
