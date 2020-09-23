# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        element_compare.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 15:51
# --------------------------------------------------------
from .context import Action
from code.collections.element import *


class ElementCompare(Action):

    def element_comapre1(self) -> bool:
        """
        comapre1
        """
        return self.android_service.exist(element_kkk1, 2)

    def element_comapre2(self) -> bool:
        """
        comapre2
        """
        return not self.android_service.exist(element_kkk2, 3)
