# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        module1.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/22 - 22:34
# --------------------------------------------------------
import allure
import pytest
from time import sleep
from src.code.director import Director

tester = Director()


def suite():
    with allure.step("初始化设备"):
        for key, item in Director.__dict__.items():
            print(key, item)


for key, item in Director.__dict__.items():
    print(key, item)
