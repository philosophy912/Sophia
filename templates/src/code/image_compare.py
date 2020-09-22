# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        image_compare.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 15:58
# --------------------------------------------------------
from automotive import CompareProperty
from src.code.context import Action


class ImageCompare(Action):

    def image_compareaaaa1(self):
        """
        compareaaaa1
        """
        compare_property = CompareProperty()
        compare_property.set_value("screen_shot",
                                   "亮图",
                                   self.screenshot,
                                   "\\".join([self.templates, "template_light.jpg"]),
                                   "\\".join([self.templates, "template_dark.bmp"]),
                                   [(1, 2, 3, 4)],
                                   9.9,
                                   True,
                                   240)
        self.image_compare.compare(compare_property)

    def image_compareaaaa2(self):
        """
        compareaaaa2
        """
        compare_property = CompareProperty()
        compare_property.set_value("screen_shot",
                                   "暗图",
                                   self.screenshot,
                                   "\\".join([self.templates, "template_light.jpg"]),
                                   "\\".join([self.templates, "template_dark.bmp"]),
                                   [(2, 3, 45, 67), (34, 24, 54, 78)],
                                   9.9,
                                   True,
                                   240)
        self.image_compare.compare(compare_property)