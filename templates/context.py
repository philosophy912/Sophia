# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        context.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/18 - 9:51
# --------------------------------------------------------

from .configure import *
from automotive import *

it6831 = It6831Actions(port=it6831_port, baud_rate=it6831_baud_rate)
konstanter = KonstanterActions(port=konstanter_port, baud_rate=konstanter_baud_rate)
can_service = CANService(dbc_json_file)
mcu_serial = SerialPort()
soc_serial = SerialPort()

android_service = AndroidService(ToolTypeEnum.APPIUM) if android_type == "appium" else AndroidService(ToolTypeEnum.UIAUTOMATOR2)