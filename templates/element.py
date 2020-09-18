# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        element.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/17 - 17:27
# --------------------------------------------------------
from selenium.webdriver.remote.webelement import WebElement
from uiautomator2 import UiObject


class Element(object):

    def __init__(self):
        self.android_service = None

    def element_kkk1(self) -> (WebElement, UiObject):
        """
        kkk2
        """
        return self.android_service.get_element({"resourceId": "a", "text": "b"})

    def element_kkk2(self) -> (WebElement, UiObject):
        """
        kkk3
        """
        element = self.android_service.get_element({"resourceId": "a", "text": "b"})
        return self.android_service.get_child_element(element, {"text": "c"})
