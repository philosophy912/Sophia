# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        ts.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/24 - 11:06
# --------------------------------------------------------
from automotive import *
from time import sleep

folder = r"D:\Temp\screenshot"

camera = Camera()
action = KonstanterActions(port="COM8", baud_rate=19200)
camera.open_camera()

action.open()
action.set_voltage(12)
action.set_current(10)
action.off()
sleep(5)
for i in range(100):
    logger.info(f"The {i + 1} time test.......")
    action.on()
    sleep(40)
    camera.take_picture(f"{folder}\\{Utils.get_time_as_string()}.jpg")
    action.off()
    sleep(3)
action.close()
camera.close_camera()
