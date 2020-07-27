# -*- coding:utf-8 -*-
# --------------------------------------------------------
# Copyright (C), 2016-2020, China TSP, All rights reserved
# --------------------------------------------------------
# @Name:        BaseCamera
# @Purpose:     摄像头相关操作
# @Author:      liluo
# @Created:     2018/12/27 9:47
# --------------------------------------------------------

import numpy as np
import cv2
import time
import threading
import wave
from pyaudio import PyAudio, paInt16
from automatedtest.common.tools.logger.logger import logger
from automatedtest.common.tools.logger.utils import Utils


class BaseCamera(object):
    def __init__(self):
        self._utils = Utils()
        self.FrameID = {
            'pos_msec': cv2.CAP_PROP_POS_MSEC,  # 视频的当前位置（单位:ms）
            'pos_frames': cv2.CAP_PROP_POS_FRAMES,  # 视频的当前位置（单位：帧数，从0开始计）
            'pos_avi_ratio': cv2.CAP_PROP_POS_AVI_RATIO,  # 视频的当前位置（单位：比率， 0表示开始，1表示结尾）
            'frame_width': cv2.CAP_PROP_FRAME_WIDTH,  # 帧宽度
            'frame_height': cv2.CAP_PROP_FRAME_HEIGHT,  # 帧高度
            'fps': cv2.CAP_PROP_FPS,  # 帧速率
            'fourcc': cv2.CAP_PROP_FOURCC,  # 4-字符表示的视频编码（如：’M‘, ’J‘, ’P‘, ’G‘）
            'frame_count': cv2.CAP_PROP_FRAME_COUNT,  # 总帧数
            'format': cv2.CAP_PROP_FORMAT,  # retrieve().调用返回的矩阵格式
            'mode': cv2.CAP_PROP_MODE,  # 后端变量指示的当前捕获的模式
            'brightness': cv2.CAP_PROP_BRIGHTNESS,  # 明亮度（仅用于摄像头）
            'contrast': cv2.CAP_PROP_CONTRAST,  # 对比度（仅用于摄像头）
            'saturation': cv2.CAP_PROP_SATURATION,  # 饱和度（仅用于摄像头）
            'hue': cv2.CAP_PROP_HUE,  # 色调（仅用于摄像头）
            'gain': cv2.CAP_PROP_GAIN,  # 增益（仅用于摄像头）
            'exposure': cv2.CAP_PROP_EXPOSURE,  # 曝光度 （仅用于摄像头）
            'convert_rgb': cv2.CAP_PROP_CONVERT_RGB,  # 是否应该将图像转化为RGB图像（布尔值）
            'white_balance': cv2.CAP_PROP_WHITE_BALANCE_BLUE_U,  # 白平衡（暂不支持 v2.4.3)
            'rectification': cv2.CAP_PROP_RECTIFICATION,  # 立体摄像头标定 (目前仅支持 DC1394 v 2.x 后端)
            'monochrome': cv2.CAP_PROP_MONOCHROME,  #
            'sharpness': cv2.CAP_PROP_SHARPNESS,  #
            'auto_exposure': cv2.CAP_PROP_AUTO_EXPOSURE,  # 自动曝光（仅用于摄像头）
            'gamma': cv2.CAP_PROP_GAMMA,  #
            'temperatrue': cv2.CAP_PROP_TEMPERATURE,  #
            'trigger': cv2.CAP_PROP_TRIGGER,  #
            'trigger_delay': cv2.CAP_PROP_TRIGGER_DELAY,  #
            'white_balance_red_v': cv2.CAP_PROP_WHITE_BALANCE_RED_V,  #
            'zoom': cv2.CAP_PROP_ZOOM,  #
            'focus': cv2.CAP_PROP_FOCUS,  #
            'guid': cv2.CAP_PROP_GUID,  #
            'iso_speed': cv2.CAP_PROP_ISO_SPEED,  #
            'backlight': cv2.CAP_PROP_BACKLIGHT,  #
            'pan': cv2.CAP_PROP_PAN,  #
            'tilt': cv2.CAP_PROP_TILT,  #
            'roll': cv2.CAP_PROP_ROLL,  #
            'iris': cv2.CAP_PROP_IRIS,  #
            'settings': cv2.CAP_PROP_SETTINGS,  #
            'buffersize': cv2.CAP_PROP_BUFFERSIZE,  #
            'autofocus': cv2.CAP_PROP_AUTOFOCUS,  #
            'sar_num': cv2.CAP_PROP_SAR_NUM,  #
            'sar_den': cv2.CAP_PROP_SAR_DEN,  #
            'backend': cv2.CAP_PROP_BACKEND,  #
            'channel': cv2.CAP_PROP_CHANNEL,  #
            'auto_wb': cv2.CAP_PROP_AUTO_WB,  #
            'wb_temperatrue': cv2.CAP_PROP_WB_TEMPERATURE  #
        }
        self._mark = {
            'mark': True,
            'text': ' ',
            'x': 10,
            'y': 60,
            'fontScale': 1,
            'R': 0,
            'G': 255,
            'B': 255,
            'thick': 1
        }
        self._start_time = 0
        self._check_time = 0
        self.frameCnt = 0
        self.fps = 25.0
        self.capture = None
        self.stopRecord = False

    def start_camera(self, camera_id=0, **kwargs):
        """
        功能：打开摄像头

        参数：camera_id：摄像头id，如果有超过两个摄像头，则依次为:0  1  2 ...

            kwargs : 可设置的摄像头参数

        返回：

        示例：
        """
        self._mark = {
            'mark': True,
            'text': ' ',
            'x': 10,
            'y': 60,
            'fontScale': 1,
            'R': 0,
            'G': 255,
            'B': 255,
            'thick': 1
        }
        self.capture = cv2.VideoCapture(camera_id)
        if not self.capture.isOpened():
            self.capture.open(camera_id)
        if kwargs:
            available_params = list(self.FrameID.keys())
            set_params = list(kwargs.keys())
            for p in set_params:
                if p not in available_params:
                    logger.info("un support camera param: {}={}".format(p, kwargs[p]))
                    continue
                logger.info("setting camera param: {}={}".format(p, kwargs[p]))
                self.set_property(p, kwargs[p])

    def set_mark(self, **kwargs):
        """
        功能：设置视频水印

        参数：kwargs：mark：[False:不设置水印, True:设置水印]

                        text：设置需要显示在视频中的文本， 默认为logMark

                        x, y：设置视频中显示文本的位置，起始坐标点为左上角，default=(10, 60)

                        fontScale：设置视频中显示文本的缩放， default=1

                        R, G, B：设置视频中显示文本的颜色， default=(0, 255, 255)

                        thick：设置视频中显示文本的粗细， default=1

                        logMark：将用例的执行时间打印到log中， 该参数建议使用用例名称

        返回：

        示例：set_mark(mark=True, text='test', x=50, y=50, fontScale=2, R=100, thick=2)
        """
        if 'mark' in kwargs:
            if str(kwargs['mark']).lower() == 'true':
                self._mark['mark'] = True
            else:
                self._mark['mark'] = False
        if 'text' in kwargs and len(str(kwargs['text'])) > 0:
            self._mark['text'] = kwargs['text']
        else:
            if 'logMark' in kwargs:
                self._mark['text'] = kwargs['logMark']
            else:
                self._mark['text'] = ' '
        if 'x' in kwargs and 0 <= int(kwargs['x']) <= 640:
            self._mark['x'] = int(kwargs['x'])
        if 'y' in kwargs and 0 <= int(kwargs['y']) <= 480:
            self._mark['y'] = int(kwargs['y'])
        if 'fontScale' in kwargs and int(kwargs['fontScale']) > 0:
            self._mark['fontScale'] = int(kwargs['fontScale'])
        if 'R' in kwargs and 0 <= int(kwargs['R']) <= 255:
            self._mark['R'] = int(kwargs['R'])
        if 'G' in kwargs and 0 <= int(kwargs['G']) <= 255:
            self._mark['G'] = int(kwargs['G'])
        if 'B' in kwargs and 0 <= int(kwargs['B']) <= 255:
            self._mark['B'] = int(kwargs['B'])
        if 'thick' in kwargs and int(kwargs['thick']) > 0:
            self._mark['thick'] = int(kwargs['thick'])
        if 'logMark' in kwargs:
            self._mark['mark'] = True
            video_time = int(float(self.frameCnt) / self.fps)
            if video_time < 0:
                video_time = 0
            logger.info(
                "Case: <{}> start to run at time: <{}min - {}sec>".format(str(kwargs['logMark']), video_time / 60,
                                                                          video_time % 60))

    def stop_record(self):
        """
        功能：停止录制

        参数：

        返回：

        示例：
        """
        self.stopRecord = True
        time.sleep(3)
        self.stop_camera()
        time.sleep(3)

    def get_picture_from_record(self, path):
        """
        功能：在录像过程中获取照片,与record_video配合使用

        参数：
            path : 需要传递保存图片的路径包括文件名以及后缀

        返回：一张.png类型的图片, 保存位置：automatedtest_5X3/result/screenshot_result/

        示例：get_picture_from_record('ride', 'case1', 'result')
        """
        cv2.imwrite(path, self.frame)
        return path

    def take_picture(self, path):
        """
        功能：拍照

        参数：
            path : 拍照保存的照片的路径包括文件名以及后缀

        返回：一张.png类型的图片, 保存位置：automatedtest_5X3/result/screenshot_result/

        示例：take_picture('ride', 'case1', 'result')
        """
        self._take_frame(path)
        return path

    def _take_frame(self, name='test.png', gray=False):
        """
        功能：获取一帧图像并保存

        参数：name：保存照片的名称或路径:test.png or D:/GIT/automatedtest_5X3/test.png

            gray：[False:拍摄彩色照片, True:拍摄灰度照片]

        返回：无

        示例：_take_frame('name.png')
        """
        try:
            name = str(name)
            ret, frame = self.capture.read()
            if ret:
                if gray:
                    frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
                cv2.imwrite(name, frame)
            else:
                logger.error("Camera read frame error.")
                return -1
        except Exception as e:
            logger.error("_take_frame: {}".format(e))

    def start_record(self, name='test', total_time=20, fps=20, width=640, height=480, code='MJPG', **kwargs):
        """
        功能：录制视频开启线程

        参数：name:保存视频的名称，不包含后缀名

                total_time：录制视频的总时长，单位：分钟

                fps：帧率设置[5.0, 30.0], default=20

                kwargs : 可设置的摄像头参数

        返回：无

        示例：start_record()
        """
        try:
            self.start_camera(**kwargs)
            rec = threading.Thread(target=self.record_video, args=(name, total_time, fps, width, height, code))
            rec.setDaemon(False)
            rec.start()
            # rec.join()
        except Exception as e:
            logger.error("start_record: {}".format(e))

    def record_video(self, path, total_time=100, fps=20, width=640, height=480, code='MJPG'):
        """
        功能：录制视频,使用该方法录制视频时将不能进行其他任何操作， 建议使用start_record

        参数：
            path : 保存视频的路径，包括文件名和后缀名，目前只支持.avi，其他格式无法保存

            fps：帧率设置[5.0, 30.0], default=20

            total_time：录制视频的总时长，单位：分钟

            width, height : 录像视屏的分辨率

        返回：无

        示例：record_video()
        """
        index = 1
        record_time = 20

        while True:
            datetime = self._utils.get_time_as_string()
            tmp = path[:-4] + '_' + str(index) + '_' + datetime + '.avi'

            self._record(tmp, fps, record_time, width=width, height=height, code=code)
            if index > int(float(total_time) / record_time):
                self.stop_camera()
                break
            index += 1
            if self.stopRecord:
                break

    def _record(self, name='test.avi', fps=20, time_=20, width=640, height=480, code='MJPG'):
        """
        功能：录制

        参数：name：保存视频的名称或路径:test.avi or D:/GIT/automatedtest_5X3/test.avi

            fps：帧率设置[5.0, 30.0], default=20

            t：每段录像的时长，达到该时间后自动保存录像并开启新的录像写入另一个视频文件,单位:minutes,
                如果t=0则保存为一个视频文件, 默认值：t=20

            width, height : 录像视屏的分辨率

            code：录像的编码格式，目前只支持MJPG

        返回：无

        示例：_record('name.avi')
        """
        try:
            name = str(name)
            time_ = int(time_)
            fps = float(fps)
            if fps < 5 or fps > 30:
                fps = self.fps
            else:
                self.fps = fps
            code = str(code)
            # 各种编码格式每分钟压缩后大小:MJPG(80M),
            fourcc = cv2.VideoWriter_fourcc(*code)
            if code.lower() == 'none':
                fourcc = -1
            out = cv2.VideoWriter(name, fourcc, fps, (width, height), True)
            self._start_time = time.time()
            logger.info("Start to record video: <{}> at time: {}".format(name, self._start_time))
            self.frameCnt = 0
            while self.capture.isOpened():
                ret, self.frame = self.capture.read()
                if self._mark['mark']:
                    self._mark['text'] = self._utils.get_time_as_string()
                    cv2.putText(self.frame, self._mark['text'], (self._mark['x'], self._mark['y']),
                                cv2.FONT_HERSHEY_SIMPLEX, self._mark['fontScale'],
                                (self._mark['R'], self._mark['G'], self._mark['B']), self._mark['thick'],
                                cv2.LINE_AA)
                if ret:
                    out.write(self.frame)
                    self.frameCnt += 1
                    # cv2.imshow('f', self.frame)
                    # if cv2.waitKey(1) & 0xFF == ord('q'):
                    #    break
                self._check_time = time.time()
                if int(self._check_time - self._start_time) >= (time_ * 60):
                    break
                if self.stopRecord:
                    break
            out.release()
            logger.info("Stop record video: <{}> at time: {}, or {}sec, total frame: {}"
                        .format(name, self._check_time, int(self._check_time - self._start_time), self.frameCnt))
            # cv2.destroyAllWindows()
        except Exception as e:
            logger.error("_record : {}".format(e))

    def camera_test(self, wait=2, **kwargs):
        """
        功能：测试摄像头摄像，可用于调节摄像头距离，查看录像效果时，一般作为调试使用

        参数：wait : 等待时间

            kwargs : 可设置的摄像头参数

        返回：无

        示例：
        """
        # code = 'MJPG'
        self.start_camera(**kwargs)
        start_time = time.time()
        while self.capture.isOpened():
            ret, frame = self.capture.read()
            if ret:
                cv2.imshow('f', frame)
                if cv2.waitKey(1) & 0xFF == ord('q'):
                    break
            check_time = time.time()
            if int(check_time - start_time) >= (wait * 60):
                break
        cv2.destroyAllWindows()
        self.stop_camera()

    def set_property(self, property_name, value):
        """
        功能：设置摄像头参数

        参数：property_name:设置对应参数,可设置参数见get_property

        返回：无

        示例：set_property('frame_width', 720)
        """
        property_name = str(property_name).lower()
        # value = str(value)
        if property_name in ('frame_width',
                             'frame_height',
                             'fps',
                             'brightness',
                             'hue',
                             'contrast',
                             'saturation',
                             'gain',
                             'exposure',
                             'white_balance'):
            value = float(value)
            if property_name in ('frame_width',
                                 'frame_height',
                                 'fps'):
                value = int(value)
        elif property_name in ('convert_rgb',):
            if str(value).lower() == 'true':
                value = True
            else:
                value = False
        self.capture.set(self.FrameID[property_name], value)

    def reset_property(self):
        """
        功能：重置所有摄像头参数为初始值

        参数：

        返回：无

        示例：
        """
        self.capture.set(self.FrameID['pos_msec'], -1.0)
        self.capture.set(self.FrameID['pos_frames'], -1.0)
        self.capture.set(self.FrameID['pos_avi_ratio'], -1.0)
        self.capture.set(self.FrameID['frame_width'], 640)
        self.capture.set(self.FrameID['frame_height'], 480)
        self.capture.set(self.FrameID['fps'], 0)
        self.capture.set(self.FrameID['fourcc'], -466162819.0)
        self.capture.set(self.FrameID['frame_count'], -1.0)
        self.capture.set(self.FrameID['format'], -1.0)
        self.capture.set(self.FrameID['mode'], -1.0)
        self.capture.set(self.FrameID['brightness'], 128.0)
        self.capture.set(self.FrameID['contrast'], 32.0)
        self.capture.set(self.FrameID['saturation'], 32.0)
        self.capture.set(self.FrameID['hue'], 175230088.0)
        self.capture.set(self.FrameID['gain'], 131.0)
        self.capture.set(self.FrameID['exposure'], -5.0)
        self.capture.set(self.FrameID['convert_rgb'], -1.0)
        self.capture.set(self.FrameID['white_balance'], 6150.0)
        self.capture.set(self.FrameID['rectification'], -1.0)

    def get_property(self, property_name=''):
        """
        功能：获取摄像头当前参数设置

        参数：property_name:如果该参数为空则返回所有参数，否则返回对应参数
                            pos_msec:       视频文件的当前位置， 初始值=-1.0
        
                            pos_frames:     当前帧， 初始值=-1.0

                            pos_avi_ratio:  当前相对位置[0:从视频起始位置开始, 1:从视频结束位置开始]， 初始值=-1.0

                            frame_width:    帧宽度, 初始值=640.0

                            frame_height:   帧高度, 初始值=480.0

                            fps:            帧数, 初始值=0.0

                            fourcc:         4字符编码的编码器， 初始值=-466162819.0

                            frame_count:    获取总帧数， 初始值=-1.0

                            format:         视频格式， 初始值=-1.0

                            mode:           布尔型标记图像是否应该被转换为RGB， 初始值=-1.0

                            brightness:     亮度， 初始值=128.0, 仅Camera

                            contrast:       对比度， 初始值=32.0, 仅Camera

                            saturation:     饱和度， 初始值=32.0, 仅Camera

                            hue:            色调， 初始值=175230088.0, 仅Camera

                            gain:           增益， 初始值=131.0, 仅Camera

                            exposure:       曝光， 初始值=-5.0, 仅Camera

                            convert_rgb:    布尔类型，表示图像是否需要转换成RGB， 初始值=-1.0

                            white_balance:  白平衡， 初始值=6150.0

                            rectification:  标定结果校准检验, 初始值=-1.0
                            

        返回：返回摄像头对应的参数设置

        示例：get_property('FrameHeight')
        """
        if property_name:
            property_name = str(property_name).lower()
            return self.capture.get(self.FrameID[property_name])
        else:
            all_settings = {}
            for f in self.FrameID:
                all_settings[f] = self.capture.get(self.FrameID[f])
            return all_settings

    def stop_camera(self):
        """
        功能：关闭摄像头

        参数：

        返回：无

        示例：
        """
        self.capture.release()


class AudioRecord(object):
    def __init__(self):
        self.num_samples = 2000  # pyaudio内置缓冲区
        self.sampling_rate = 8000  # 采样率
        self.stopRecord = False
        self._utils = Utils()

    def stop_record(self):
        """
        功能：停止录制

        参数：

        返回：

        示例：
        """
        self.stopRecord = True

    def start_record(self, name='test', p=30, total_time=1):
        """
        功能：录制音频开启线程

        参数：name:保存音频的名称，不包含后缀名

                p：录制的每段时间，单位：分， 默认值=30

                total_time：录制音频的个数

        返回：无

        示例：start_record()
        """
        rec = threading.Thread(target=self.record_audio, args=(name, p, total_time))
        rec.setDaemon(False)
        rec.start()

    def record_audio(self, path, p=30, total_time=1):
        """
        功能：录制音频，建议使用start_record

        参数：
            path : 保存语音的路径，需要包含文件名和后缀名，目前只支持.wav

            p：录制的每段时间，单位：分， 默认值=30

            total_time：录制音频的个数

        返回：无

        示例：record_audio()
        """
        p = int(p)
        index = 1

        while True:
            datetime = self._utils.get_time_as_string()
            tmp = path[:-4] + '_' + str(index) + '_' + datetime + '.wav'
            self._read_frame(tmp, p * 60)
            if index >= int(total_time):
                break
            if self.stopRecord:
                break
            index += 1

    def _read_frame(self, filename='test.wav', record_time=30):
        """
        功能：采集声音

        参数：record_time：录音时间，单位s

                filename：保存文件名称，可以是路径

        返回：无

        示例：
        """
        record_time = int(record_time)

        pa = PyAudio()
        stream = pa.open(format=paInt16, channels=1, rate=self.sampling_rate, input=True,
                         frames_per_buffer=self.num_samples)

        wf = wave.open(filename, 'wb')
        wf.setnchannels(1)
        wf.setsampwidth(2)
        wf.setframerate(self.sampling_rate)

        start_time = time.time()
        logger.info("Audio record start from time: %s", str(start_time))
        while True:
            string_audio_data = stream.read(self.num_samples)
            wf.writeframes(np.array(string_audio_data).tostring())
            check_time = time.time()
            if int(check_time - start_time) >= record_time:
                logger.info("Audio record end at time: %s, total record time: %s", str(check_time),
                            str(check_time - start_time))
                break
            if self.stopRecord:
                break
        stream.close()
        wf.close()


if __name__ == '__main__':
    c = BaseCamera()
    # c.start_camera()
    # c.set_property('fps', 60)
    # import time
    # logger.info(time.time())
    # for i in range(120):
    #     c.take_picture("D:/Repo/3F3/automatedtest/tmp/" + str(i) + '.jpg')
    #     #time.sleep(5)
    # logger.info(time.time())
    # c.stop_camera()
    c.camera_test(5)
