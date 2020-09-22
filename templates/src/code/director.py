# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        director.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/22 - 22:35
# --------------------------------------------------------
from src.code.battery_action import BatteryAction
from src.code.can_action import CanAction
from src.code.can_compare import CanCompare
from src.code.common import Common
from src.code.element_action import ElementAction
from src.code.element_compare import ElementCompare
from src.code.image_compare import ImageCompare
from src.code.information import Information
from src.code.information_compare import InformationCompare
from src.code.relay_action import RelayAction
from src.code.screen_ops_action import ScreenshotOpsAction
from src.code.screenshot_action import ScreenShotAction


class Director(object):

    def __init__(self):
        self.battery_action = BatteryAction()
        self.can_action = CanAction()
