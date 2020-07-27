
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
            'pos_msec': cv2.CAP_PROP_POS_MSEC,  
            'pos_frames': cv2.CAP_PROP_POS_FRAMES,  
            'pos_avi_ratio': cv2.CAP_PROP_POS_AVI_RATIO,  
            'frame_width': cv2.CAP_PROP_FRAME_WIDTH,  
            'frame_height': cv2.CAP_PROP_FRAME_HEIGHT,  
            'fps': cv2.CAP_PROP_FPS,  
            'fourcc': cv2.CAP_PROP_FOURCC,  
            'frame_count': cv2.CAP_PROP_FRAME_COUNT,  
            'format': cv2.CAP_PROP_FORMAT,  
            'mode': cv2.CAP_PROP_MODE,  
            'brightness': cv2.CAP_PROP_BRIGHTNESS,  
            'contrast': cv2.CAP_PROP_CONTRAST,  
            'saturation': cv2.CAP_PROP_SATURATION,  
            'hue': cv2.CAP_PROP_HUE,  
            'gain': cv2.CAP_PROP_GAIN,  
            'exposure': cv2.CAP_PROP_EXPOSURE,  
            'convert_rgb': cv2.CAP_PROP_CONVERT_RGB,  
            'white_balance': cv2.CAP_PROP_WHITE_BALANCE_BLUE_U,  
            'rectification': cv2.CAP_PROP_RECTIFICATION,  
            'monochrome': cv2.CAP_PROP_MONOCHROME,  #
            'sharpness': cv2.CAP_PROP_SHARPNESS,  #
            'auto_exposure': cv2.CAP_PROP_AUTO_EXPOSURE,  
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
        self.stopRecord = True
        time.sleep(3)
        self.stop_camera()
        time.sleep(3)

    def get_picture_from_record(self, path):
        cv2.imwrite(path, self.frame)
        return path

    def take_picture(self, path):
        self._take_frame(path)
        return path

    def _take_frame(self, name='test.png', gray=False):
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
        try:
            self.start_camera(**kwargs)
            rec = threading.Thread(target=self.record_video, args=(name, total_time, fps, width, height, code))
            rec.setDaemon(False)
            rec.start()
        except Exception as e:
            logger.error("start_record: {}".format(e))

    def record_video(self, path, total_time=100, fps=20, width=640, height=480, code='MJPG'):
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
        try:
            name = str(name)
            time_ = int(time_)
            fps = float(fps)
            if fps < 5 or fps > 30:
                fps = self.fps
            else:
                self.fps = fps
            code = str(code)
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
                self._check_time = time.time()
                if int(self._check_time - self._start_time) >= (time_ * 60):
                    break
                if self.stopRecord:
                    break
            out.release()
            logger.info("Stop record video: <{}> at time: {}, or {}sec, total frame: {}"
                        .format(name, self._check_time, int(self._check_time - self._start_time), self.frameCnt))
        except Exception as e:
            logger.error("_record : {}".format(e))

    def camera_test(self, wait=2, **kwargs):
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
        property_name = str(property_name).lower()
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
        if property_name:
            property_name = str(property_name).lower()
            return self.capture.get(self.FrameID[property_name])
        else:
            all_settings = {}
            for f in self.FrameID:
                all_settings[f] = self.capture.get(self.FrameID[f])
