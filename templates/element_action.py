# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        element_action.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/19 - 20:43
# --------------------------------------------------------
from .element import Element


class ElementAction(Element):

    def element_test1(self):
        """
        test1
        """
        element1 = self.element_kkk1()
        element2 = self.element_kkk2()
        self.android.swipe_element(element1, element2)
