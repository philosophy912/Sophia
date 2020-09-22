# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        battery_action.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/15 - 16:28
# --------------------------------------------------------
from automotive import Curve
from .context import Action


class BatteryAction(Action):

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
