# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        can_action.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/17 - 14:23
# --------------------------------------------------------


class CanAction(object):

    def __init__(self):
        self.can = None

    def can_test1(self):
        """
        test2
        """
        self.can.send_can_signal_message(642, {"HU_ACCSet": 0x1, "HU_FRMMsgErr": 0x2})

    def can_test2(self):
        """
        test3
        """
        self.can.send_can_signal_message(642, {"HU_FRMMsgErr": 0x1})
