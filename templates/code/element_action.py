# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        element_action.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/19 - 20:43
# --------------------------------------------------------
from .element import *
from .context import Action


class ElementAction(Action):


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
