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
from automotive import *


class ElementAction(object):

    def __init__(self, android_service: AndroidService):
        self.android = android_service

    def element_test1(self):
        """
        test1
        """
        element1 = element_kkk1()
        element2 = element_kkk2()
        self.android.swipe_element(element1, element2)
