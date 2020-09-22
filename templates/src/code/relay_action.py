# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        relay_action.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 10:55
# --------------------------------------------------------
from src.code.context import Action


class RelayAction(Action):

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
