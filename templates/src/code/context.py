# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        context.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/18 - 9:51
# --------------------------------------------------------
import os
from abc import ABCMeta, abstractmethod
from src.code.configure import *


class Action(metaclass=ABCMeta):

    def __init__(self):
        # 用于存放保存的内容
        self._save_data = dict()
        top_folder = os.path.dirname(os.getcwd())
        resource = "\\".join([top_folder, "resources"])
        # DBC解析出来的文件的路径
        self.dbc = "\\".join([resource, "dbc", dbc_json_file])
        # 模板图片存放路径
        self.templates = "\\".join([resource, "templates"])
        # 截图图片存放路径
        self.screenshot = "\\".join([resource, "result", "screenshot"])
        # 临时文件存放路径
        self.temp = "\\".join([resource, "result", "screenshot"])
        # 报告存放路径 todo
        self.android_device_id = android_device_id
        self.android_screenshot_path = android_screenshot_path

    @abstractmethod
    def open(self):
        pass

    @abstractmethod
    def close(self):
        pass
