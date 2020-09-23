# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        information.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/22 - 22:17
# --------------------------------------------------------
from src.code.context import Action
from src.code.element import *
from automotive import ElementAttributeEnum


class Information(Action):

    def infomation1(self):
        """
        infomation1
        """
        attr = ElementAttributeEnum.from_value("CHECKABLE")
        self._save_data["infomation1"] = self.android_service.get_element_attribute(element_kkk1)[attr]

    def infomation2(self):
        """
        infomation2
        """
        attr = ElementAttributeEnum.from_value("CLICKABLE")
        self._save_data["infomation2"] = self.android_service.get_element_attribute(element_kkk1)[attr]

    def infomation3(self):
        """
        infomation3
        """
        attr = ElementAttributeEnum.from_value("ENABLED")
        self._save_data["infomation3"] = self.android_service.get_element_attribute(element_kkk1)[attr]

    def infomation10(self):
        """
        infomation10
        """
        self._save_data["infomation10"] = self.android_service.get_text(element_kkk1)
