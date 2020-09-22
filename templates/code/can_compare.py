# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        can_compare.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/21 - 15:40
# --------------------------------------------------------

from .context import Action


class CanCompare(Action):

    def can_compare_test1(self, stack) -> bool:
        """
        test1
        """
        return self.can_service.check_signal_value(stack, 48, "ACU_RePassBkl_Sts", 1, 1, True)

    def can_compare_test2(self, stack) -> bool:
        """
        test1
        """
        return self.can_service.check_signal_value(stack, 340, "APA_RRMDst", 1, 2, True)
