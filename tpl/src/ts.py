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
# from src.codes.context import *
from time import sleep
import cv2


# from time import sleep
#
# folder = r"D:\Temp\screenshot"
#
# camera = Camera()
# action = KonstanterActions(port="COM8", baud_rate=19200)
# camera.open_camera()
# # camera.camera_test()
#
# action.open()
# action.set_voltage(12)
# action.set_current(10)
# action.off()
# sleep(5)
# for i in range(50):
#     logger.info(f"The {i + 1} time test.......")
#     action.on()
#     sleep(30)
#     camera.take_picture(f"{folder}\\{Utils.get_time_as_string()}.jpg")
#     action.off()
#     sleep(3)
# action.close()
# camera.close_camera()
# open_device()
# ign_on()
# sleep(50)
# close_device()
# avi_file = r"C:\Users\philo\Desktop\2020-10-10_20-20-18.avi"
# cap = cv2.VideoCapture(avi_file)
# while cap.isOpened():
#     ret, frame = cap.read()
#
#     # gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
#
#     cv2.imshow('frame', frame)
#     if cv2.waitKey(1) & 0xFF == ord('q'):
#         break
#
# cap.release()
# cv2.destroyAllWindows()
#
# camera = Camera()
# camera.camera_test()
# camera.open_camera()
# for i in range(10):
#     camera.take_picture(rf"d:\temp\video\{Utils.get_time_as_string()}.jpg")
#     sleep(5)
#     logger.info(f"the {i + 1} times")
# camera.close_camera()
# open_device()
# sleep(10)
# fl_doorajarsts_open()
# close_device()
def origin():
    client = AndroidService(ToolTypeEnum.UIAUTOMATOR2)
    client.connect("1234567")
    try:
        client.click(locator={"text": "主页", "resourceId": "com.android.systemui:id/tsp_nav_button_content"})
        logger.info("click main page")
    except Exception:
        logger.info("no main page")
    try:
        client.click(locator={"text": "车辆", "resourceId": "com.android.systemui:id/tsp_nav_button_content"})
    except Exception:
        logger.info("click car page")
    client.disconnect()


def target():
    open_device()
    # try:
    #     enter_car_setting_page()
    #     logger.info("click main page")
    # except Exception:
    #     logger.info("no main page")
    # try:
    #     enter_main_page()
    # except Exception:
    #     logger.info("click car page")
    # attr = ElementAttributeEnum.from_value("TEXT")
    info = driver_date_exist()
    print(info)
    close_device()


def tsts():
    folder = r"d:\temp\video"
    camera = Camera()
    camera.open_camera()
    camera.stop_record()
    for i in range(2):
        logger.info(f"test is now start {i + 1} times")
        # sleep(5)
        # logger.info("1s 后牌照")
        # sleep(1)
        # camera.take_picture(f"{folder}\\{i + 1}__{Utils.get_time_as_string()}.jpg")
        camera.record_video(f"{folder}\\{i + 1}__{Utils.get_time_as_string()}.avi")
        sleep(10)
        camera.stop_record()
        sleep(5)
    camera.close_camera()


tsts()
