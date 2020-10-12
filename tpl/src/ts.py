# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        ts.py.py
# @Purpose:     todo
# @Author:      lizhe
# @Created:     2020/9/24 - 11:06
# --------------------------------------------------------
# from automotive import *
# from src.codes.context import *
# from time import sleep
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
avi_file = r"C:\Users\philo\Desktop\2020-10-10_20-20-18.avi"
cap = cv2.VideoCapture(avi_file)
while cap.isOpened():
    ret, frame = cap.read()

    # gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    cv2.imshow('frame', frame)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

cap.release()
cv2.destroyAllWindows()
