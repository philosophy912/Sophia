# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        ts.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/24 - 11:06
# --------------------------------------------------------
from automotive.core.actions.it6831_actions import It6831Actions
from automotive import *
from automotive.utils import camera
from time import sleep
import os

folder = r"D:\EVB"
if not os.path.exists(folder):
    os.makedirs(folder)
camera = Camera()
image = Images()
action = It6831Actions(port="COM7", baud_rate=9600)
camera.open_camera()
camera.camera_test()
template = fr"{folder}\template.jpg"
camera.take_picture(template)
thresold = 10

action.open()
action.set_voltage_current(voltage=12, current=10)
action.off()
sleep(5)
for i in range(1000):
    logger.info(f"The {i + 1} time test.......")
    action.on()
    sleep(15)
    Status = action.get_battery()
    current = Status.current
    voltage = Status.voltage
    pic = f"{folder}\\{Utils.get_time_as_string()}__{current}__{voltage}.jpg"
    # pic = f"{folder}\\{Utils.get_time_as_string()}
    camera.take_picture(pic)
    a_hash, p_hash, d_hash = image.compare_by_hamming_distance(template, pic)
    if a_hash > thresold or p_hash > thresold or d_hash > thresold:
        logger.error("有问题")
        in_content = input("请输入：")
        if in_content == 'q':
            break
    action.off()
    sleep(3)
action.close()
camera.close_camera()
