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

need_template = True
need_adjust_camera = True


def cycle_test(count: int = 1000):
    for i in range(count):
        logger.info(f"The {i + 1} time test.......")
        action.on()
        Utils.sleep(start_finish_time)
        pic = f"{folder}\\{Utils.get_time_as_string()}__{i + 1}.jpg"
        camera.take_picture(pic)
        a_hash, p_hash, d_hash = image.compare_by_hamming_distance(template, pic)
        logger.info(f"{a_hash}, {p_hash}, {d_hash} as {pic}")
        if a_hash > threshold or p_hash > threshold or d_hash > threshold:
            pic = f"{folder}\\{Utils.get_time_as_string()}__{i + 1}__second.jpg"
            camera.take_picture(pic)
            a_hash, p_hash, d_hash = image.compare_by_hamming_distance(template, pic)
            logger.info(f"{a_hash}, {p_hash}, {d_hash} as {pic}")
            if a_hash > threshold or p_hash > threshold or d_hash > threshold:
                camera.close_camera()
                in_content = input(f"请查看图片[ {pic} ]确认是否继续测试，按q退出：")
                if in_content.lower() == 'q':
                    return
                else:
                    camera.open_camera()
        action.off()
        Utils.sleep(3)


start_finish_time = 30
threshold = 7
folder = r"D:\temp\video"
template = fr"{folder}\template.jpg"
camera = Camera()
image = Images()
action = KonstanterActions(port="COM8", baud_rate=19200)
# 先把电源打开灯15秒到正常模式才好拍照
action.open()
action.set_current(10)
action.set_voltage(12)

# 调整姿势
if need_adjust_camera:
    camera.camera_test()

if need_template:
    action.on()
    Utils.sleep(start_finish_time)
    camera.open_camera()
    camera.take_picture(template)
else:
    camera.open_camera()
action.off()
Utils.sleep(5)

cycle_test()

action.close()
camera.close_camera()
