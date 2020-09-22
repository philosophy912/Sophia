# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        info_compare.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/22 - 22:25
# --------------------------------------------------------
from src.code.information import *


class InformationCompare(Information):

    def info1(self):
        """
        info1
        """
        return self.infomation2() == self.infomation1()
