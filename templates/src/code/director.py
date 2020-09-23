# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        director.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/22 - 22:35
# --------------------------------------------------------
from code.action.battery_action import BatteryAction
from code.action.can_action import CanAction
from src.code.can_compare import CanCompare
from src.code.common import Common
from code.action.element_action import ElementAction
from src.code.element_compare import ElementCompare
from src.code.image_compare import ImageCompare
from src.code.information import Information
from src.code.information_compare import InformationCompare
from code.action.relay_action import RelayAction
from code.action.screen_ops_action import ScreenshotOpsAction
from code.action.screenshot_action import ScreenShotAction


class Director(object):

    def __init__(self):
        self.battery_action = BatteryAction()
        self.can_action = CanAction()
        self.can_compare = CanCompare()
        self.common = Common()
        self.element_action = ElementAction()
        self.element_compare = ElementCompare()
        self.image_compare = ImageCompare()
        self.information = Information()
        self.information_compare = InformationCompare()
        self.relay_action = RelayAction()
        self.screen_ops_action = ScreenshotOpsAction()
        self.screenshot_action = ScreenShotAction()
