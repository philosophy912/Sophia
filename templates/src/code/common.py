# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        common.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 15:19
# --------------------------------------------------------
from src.code.context import Action


class Common(Action):

    def clear_stack(self):
        """
        common4
        """
        self.can_service.clear_stack_data()
