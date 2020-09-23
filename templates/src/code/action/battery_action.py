# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        battery_action.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/15 - 16:28
# --------------------------------------------------------
from automotive import *
from src.code.context import Action
from src.code.configure import it6831_serial, konstanter_serial


class BatteryAction(Action):

    def __init__(self):
        super().__init__()
        if it6831_serial:
            self.it6831 = It6831Actions(port=it6831_serial[0], baud_rate=it6831_serial[1])
        if konstanter_serial:
            self.konstanter = KonstanterActions(port=konstanter_serial[0], baud_rate=konstanter_serial[1])

    def open(self):
        if self.it6831:
            self.it6831.open()
        if self.konstanter:
            self.konstanter.open()

    def close(self):
        if self.it6831:
            self.it6831.close()
        if self.konstanter:
            self.konstanter.close()

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
