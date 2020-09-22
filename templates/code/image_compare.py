# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        image_compare.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 15:58
# --------------------------------------------------------
from .context import Action
from .configure import screen_shot_temp_path
from automotive import CompareProperty


class ImageCompare(Action):

    def image_compareaaaa1(self):
        """
        compareaaaa1
        """
        compare_property = CompareProperty.set_value("screen_shot", "亮图", screen_shot_temp_path, "")
        self.image_compare.compare()
